/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.viettel.dto.ViettelResponse;
import com.vss.viettel.proxy.ViettelServiceProxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class ViettelUpdateTransactionJob extends AbstractCronTriggerJob {

    ITransactionService transactionService;

    public void setTransactionService(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public ViettelUpdateTransactionJob() {
        super();
    }

    public void excute() {
        int totalRow = transactionService.countTransactionByDay(0, 2);
        int page = totalRow / 50;
        if (page * 50 < totalRow) {
            page++;
        }
        Map<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < page; i++) {
            int start = 50 * i;
            int end = start + 50;
            List<Transaction> lst = transactionService.getListTransactionByDay(0, 2, start, end);
            for (Transaction tran : lst) {
                String transactionId = tran.getTelcoTransRefId();
                if (!map.containsKey(tran.getCardCode())) {
                    map.put(tran.getCardCode(), transactionId);
                    ViettelResponse response = null;
                    try {
                        response = ViettelServiceProxy.getTransactionDetail(transactionId);
                        if (response == null) {
                            continue;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        continue;
                    }
                    if (response == null) {
                        continue;
                    }
                    String errorCode = response.getErrorCode();
                    String msg = response.getErrorMessage();
                    tran.setUseCardResponse(transactionId + "|" + errorCode + "|" + msg);
                    tran.setTelcoTransRefId(response.getTransId());

                    // Loi timeout phat sinh tu viettel
                    if (errorCode.equals("58")) {
                        tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER.split("\\|")[0]);
                        tran.setResponseDescription(WebParameter.LOI_KET_NOI_SERVER.split("\\|")[1]);
                    } /**
                     * Loi phat sinh tu he thong viettel
                     */
                    else if (errorCode.equals("57") || errorCode.equals("97") ||
                            errorCode.equals("98") || errorCode.equals("99")) {
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER.split("\\|")[0]);
                        tran.setResponseDescription(WebParameter.LOI_GOI_HAM_PROVIDER.split("\\|")[1]);
                    } // So giao dich dong thoi vuot qua so luong cho phep (200)
                    else if (errorCode.equals("55")) {
                        tran.setResponseStatus(WebParameter.HE_THONG_DANG_BAN.split("\\|")[0]);
                        tran.setResponseDescription(WebParameter.HE_THONG_DANG_BAN.split("\\|")[1]);
                    } // Ma the va serial khong khop
                    else if (errorCode.equals("04")) {
                        tran.setResponseStatus(WebParameter.MATHE_SERIAL_KHONG_KHOP.split("\\|")[0]);
                        tran.setResponseDescription(WebParameter.MATHE_SERIAL_KHONG_KHOP.split("\\|")[1]);
                    } // Ma the va serial khong khop
                    else if (errorCode.equals("01") || errorCode.equals("02")) {
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI.split("\\|")[0]);
                        tran.setResponseDescription(WebParameter.THE_KHONG_TON_TAI.split("\\|")[1]);
                    } // Gach the thanh cong
                    else if (errorCode.equals("00")) {
                        String amount = response.getAmount();
                        tran.setAmount(amount);
                        tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG.split("\\|")[0]);
                        tran.setResponseDescription(WebParameter.GIAO_DICH_THANH_CONG.split("\\|")[1]);
                        tran.setUseCardResponse(transactionId + "|" + errorCode + "|" + msg + "|" + amount);
                    }
                    transactionService.updateTransaction(tran);
                }
            }
        }
    }
}

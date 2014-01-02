/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.message.util.LoggingUtil;
import com.vss.viettel.dto.ViettelResponse;
import com.vss.viettel.proxy.ViettelServiceProxy;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author zannami
 */
public class ViettelCardServiceImpl implements ICardService {

    public Transaction useCard(Transaction tran) {
        try {
            ViettelResponse response = ViettelServiceProxy.useCard(tran.getCardSerial(), tran.getCardCode());
            tran.setResponseTime(new Date());
            if (response == null || response.getErrorCode() == null) {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
                return tran;
            }
            String errorCode = response.getErrorCode();
            String msg = response.getErrorMessage();

            tran.setUseCardResponse(errorCode + "|" + msg);
            tran.setTelcoTransRefId(response.getTransId());
            // Loi timeout phat sinh tu viettel
            if (errorCode.equals("58")) {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
            } /**
             * Loi phat sinh tu he thong viettel
             */
            else if (errorCode.equals("51") || errorCode.equals("57") || errorCode.equals("97") ||
                    errorCode.equals("98") || errorCode.equals("99")) {
                tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            } // So giao dich dong thoi vuot qua so luong cho phep (200)
            else if (errorCode.equals("55")) {
                tran.setResponseStatus(WebParameter.HE_THONG_DANG_BAN);
            } // Ma the va serial khong khop
            else if (errorCode.equals("04")) {
                tran.setResponseStatus(WebParameter.MATHE_SERIAL_KHONG_KHOP);
            } // The khong ton tai hoac da duoc su dung, chua kich hoat
            else if (errorCode.equals("01") || errorCode.equals("02") || errorCode.equals("08")) {
                tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                if (!errorCode.equals("08")) {
                    tran.setStatus("2"); // card invalid
                    tran.setTelcoResponse(true);
                }
            } // Gach the thanh cong
            else if (errorCode.equals("00")) {
                String amount = response.getAmount();
                tran.setAmount(amount);
                tran.setStatus("3");
                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                tran.setUseCardResponse(errorCode + "|" + msg + "|" + amount);
            } else {
                tran.setResponseStatus(WebParameter.LOI_KHONG_XAC_DINH);
            }
            return tran;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException(e);
        }
    }

    public Transaction checkCard(Transaction tran) {
        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
        return tran;
    }

    public String loginToTelco() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

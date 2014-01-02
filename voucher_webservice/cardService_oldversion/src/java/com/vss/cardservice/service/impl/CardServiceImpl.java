/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.proxy.VoucherServiceProxy;
import com.vss.cardservice.service.util.WebParameter;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class CardServiceImpl implements ICardService {

    private Map mapService;

    public Transaction useCard(Transaction tran) {
        String issuerId = tran.getIssuer();
        if (issuerId != null && !issuerId.isEmpty() && (issuerId.equalsIgnoreCase("vcoin") || issuerId.equalsIgnoreCase("bac"))) {
            ICardService paCardServiceImpl = (ICardService) mapService.get(issuerId.toUpperCase());
            tran = paCardServiceImpl.useCard(tran);
            tran.setRequestTime(new Date());
            return tran;
        }
        try {
//            String transRef = new SimpleDateFormat("MMyyyy").format(new Date()) + "_" + System.currentTimeMillis();
//            tran.setTelcoTransRefId(transRef);
            String response = VoucherServiceProxy.useCard(tran.getIssuer(), tran.getCardSerial(), tran.getCardCode(), tran.getTransactionId());
            if (response == null || response.isEmpty()) {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
            } else {
                tran.setUseCardResponse(response);
                String[] msg = response.split("\\|");
                tran.setResponseStatus(msg[0]);
                if (msg.length>1) {
                    tran.setResponseDescription(msg[1]);
                }
                if (msg != null && msg[0].equals(WebParameter.THE_KHONG_TON_TAI)) {
                    tran.setStatus("2");
                    tran.setTelcoResponse(true);
                }
                if (msg != null && msg.length > 2) {
                    tran.setStatus("3");
                    tran.setAmount(msg[2]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
        }
        tran.setResponseTime(new Date());
        return tran;
    }

    public Transaction checkCard(Transaction tran) {
        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
        return tran;
    }

    public Map getMapService() {
        return mapService;
    }

    public void setMapService(Map mapService) {
        this.mapService = mapService;
    }

    public String loginToTelco() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import com.vss.cardservice.service.exception.PromotionServiceException;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.promotion.proxy.PromotionServiceProxy;
import com.vss.promotions.dto.Promotion;
import java.util.Date;

/**
 * @author zannami
 */
public class PromotionServiceImpl implements ICardService {

    private String phone = null;
    private String customerCode = null;
    private String code = null;
    private String targetName = null;

    public Transaction useCard(Transaction tran) {
        try {
            tran = checkCard(tran);
            if (tran == null || tran.getResponseStatus() == null || !tran.getResponseStatus().equals("01")) {
                if (tran == null) {
                    tran = new Transaction();
                }
                return tran;
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            throw new CardServiceException();
        }
        try {
            String response = PromotionServiceProxy.insertReward(phone, customerCode, code, targetName);
            if (response == null) {
                tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                return tran;
            }
            if (!response.equals("01")) {
                tran.setResponseStatus(response);
                tran.setResponseDescription(ServiceUtil.getDescription(response));
                return tran;
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            throw new CardServiceException();
        }
        try {
            PromotionServiceProxy.updatePromotionInfo(customerCode, code);
            tran.setResponseTime(new Date());
        } catch (Exception ex) {
            ex.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            throw new CardServiceException();
        }
        return tran;
    }

    public Transaction checkCard(Transaction tran) {
        try {
            String[] str = getCardCodeDescription(tran.getCardCode());
            if (str == null || str[0].equals(WebParameter.THE_KHONG_TON_TAI)) {
                tran.setResponseStatus(str[0]);
                return tran;
            }
            tran.setCardCode(str[2]);
            customerCode = str[0];
            phone = str[1];
            code = str[2];
            targetName = "";
            Promotion p = PromotionServiceProxy.validCode(customerCode, code);
            if (p.getReturnCode() != null) {
                tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
            } else {
                System.out.println(p.getReturnCode() + "|" + p.getValue());
                tran.setAmount(String.valueOf(p.getValue()));
                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException();
        }
        return tran;
    }

    private String[] getCardCodeDescription(String cardCode) {
        String[] str = cardCode.split("_");
        if (str == null || (str.length != 3 && str.length != 4)) {
            return new String[]{WebParameter.THE_KHONG_TON_TAI};
        }
        return str;
    }

    public String loginToTelco() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

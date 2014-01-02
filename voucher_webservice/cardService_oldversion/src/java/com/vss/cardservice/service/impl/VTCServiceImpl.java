/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.hdpay.vtc.dto.RequestResponse;
import com.hdpay.vtc.proxy.VTCTelcoProxy;
import com.hdpay.vtc.proxy.VTCVcoinProxy;
import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.util.CardServiceLocalServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author koziwa
 */
public class VTCServiceImpl implements ICardService {

    IGameAccountService gameAccountService = CardServiceLocalServiceUtil.getGameAccountService();

    private Transaction useVCoinCard(Transaction tran) {
//        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
        String cardSerial = tran.getCardSerial();
        if (cardSerial == null || !cardSerial.toUpperCase().startsWith("PM")) {
            tran.setResponseStatus(WebParameter.SERIAL_KHONG_HOP_LE);
        } else {
            String account = tran.getAccountId();
            RequestResponse request = null;
            try {
                request = VTCVcoinProxy.checkCardStatus(VTCVcoinProxy.wsURL, VTCVcoinProxy.partnerKey,
                        VTCVcoinProxy.partnerId, cardSerial, account);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (request != null) {
                tran = getResponseStatus(request, tran);
                if (tran.getResponseStatus().equals(WebParameter.GIAO_DICH_THANH_CONG)) {
                    try {
                        request = VTCVcoinProxy.useCard(VTCVcoinProxy.wsURL, VTCVcoinProxy.partnerKey,
                                VTCVcoinProxy.partnerId, tran.getCardCode(), cardSerial, account);
                        tran.setResponseTime(new Date());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (request != null) {
                        tran = getResponseStatus(request, tran);
                    } else {
                        tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
                    }
                }
            }
        }
        return tran;
    }

    public Transaction useCard(Transaction tran) {
        String provider = "";
        String account = "";
        try {
            Random randomGenerator = new Random();
            Integer accountId = randomGenerator.nextInt(238750);
            account = ServiceUtil.getGameAccountList(gameAccountService).get(accountId);
        } catch (Exception e) {
            e.printStackTrace();
            account = String.valueOf(tran.getTransactionId());
        }
        if (account == null || account.isEmpty()) {
            account = String.valueOf(tran.getTransactionId());
        }
        tran.setAccountId(account);
        if (tran.getIssuer().equalsIgnoreCase(Issuer.MOBIPHONE)) {
            provider = VTCTelcoProxy.MOBIPHONE;
        } else if (tran.getIssuer().equalsIgnoreCase(Issuer.VINAPHONE)) {
            provider = VTCTelcoProxy.VINAPHONE;
        } else if (tran.getIssuer().equalsIgnoreCase(Issuer.VIETTEL)) {
            provider = VTCTelcoProxy.VIETTEL;
        } else {
            return useVCoinCard(tran); // vcoin
        }
        RequestResponse response = null;
        try {
            response = VTCTelcoProxy.request(provider, tran.getCardSerial(), tran.getCardCode(), Long.valueOf(tran.getTransactionId()), account);
            tran.setResponseTime(new Date());
        } catch (Exception e) {
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
        }
        return getResponseStatus(response, tran);
    }

    public Transaction checkCard(Transaction tran) {
        String cardSerial = tran.getCardSerial();
        if (cardSerial == null || !cardSerial.toUpperCase().startsWith("PM")) {
            tran.setResponseStatus(WebParameter.SERIAL_KHONG_HOP_LE);
        } else {
            String account = tran.getAccountId();
            RequestResponse request = null;
            try {
                request = VTCVcoinProxy.checkCardStatus(VTCVcoinProxy.wsURL, VTCVcoinProxy.partnerKey,
                        VTCVcoinProxy.partnerId, cardSerial, account);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (request != null) {
                tran = getResponseStatus(request, tran);
                if (tran.getResponseStatus().equals(WebParameter.GIAO_DICH_THANH_CONG)) {
                    try {
                        request = VTCVcoinProxy.checkCardValue(VTCVcoinProxy.wsURL, VTCVcoinProxy.partnerKey,
                                VTCVcoinProxy.partnerId, tran.getCardCode(), cardSerial, account);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (request != null) {
                        tran = getResponseStatus(request, tran);
                    } else {
                        tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
                    }
                }
            }
        }
        return tran;
    }

    public String loginToTelco() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private Transaction getResponseStatus(RequestResponse response, Transaction tran) {
        if (response != null) {
            tran.setStatus("2");
            int status = response.getResponseStatus();
            if (status % 1000 == 0) { // Thanh Cong?
                tran.setAmount(String.valueOf(status));
                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                tran.setStatus("3");
            } else {
                switch (status) {
                    case -1:
                        tran.setResponseStatus(WebParameter.THE_DA_DUOC_SU_DUNG);
                        break;
                    case -2:
                        tran.setResponseStatus(WebParameter.THE_DA_KHOA);
                        break;
                    case -3:
                        tran.setResponseStatus(WebParameter.THE_HET_HAN_SU_DUNG);
                        break;
                    case -4:
                        tran.setResponseStatus(WebParameter.THE_CHUA_DUOC_KICH_HOAT);
                        break;
                    case -6:
                        tran.setResponseStatus(WebParameter.MATHE_SERIAL_KHONG_KHOP);
                        break;
                    case -10:
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        break;
                    case -11:
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        break;
                    case -12:
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        break;
                    case -98:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        tran.setStatus("1");
                        break;
                    case -99:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        tran.setStatus("1");
                        break;
                    default:
                        System.out.println("VTC : Khong xac dinh : "+response.getResponseStatus()+" _ "+response.getDescription());
                        tran.setResponseStatus(WebParameter.LOI_KHONG_XAC_DINH);
                        tran.setStatus("1");
                        break;
                }
            }
        }
        return tran;
    }
}

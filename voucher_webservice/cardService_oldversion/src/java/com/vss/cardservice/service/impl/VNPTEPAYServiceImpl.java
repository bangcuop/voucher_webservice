/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.hdpay.vnptepay.proxy.vnptepayProxy;
import com.vss.clientws.dto.LoginResponse;
import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import com.vss.cardservice.service.util.CardServiceLocalServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.clientws.dto.ChargeReponse;
import java.util.Date;
import java.util.Random;

/**
 *
 * @author zannami
 */
public class VNPTEPAYServiceImpl implements ICardService {

    IGameAccountService gameAccountService = CardServiceLocalServiceUtil.getGameAccountService();
    /*
     * Login: lay sessionID neu chet thi login, neu khong su dung luon
     * sessionPass -> goi vao cardCharing -> CarchargRespone
     *      Status: + 3 or 7 -> login lai va thuc hien lai cardCharing
     *              + 9 or 10 or 99: Goi lai checkTransactionStatus -> cap nhat thong tin giao dich.
     *      Neu status = 1: Cap nhat thong tin manh gia the tra ve khach hang
     *      Con lai tra ve 00: The da su dung hoac khong ton tai
     *  Trong code
     */

    public Transaction useCard(Transaction tran) {
        WebParameter.invalidSession = Boolean.FALSE;
        StringBuffer userStatus = new StringBuffer();
        String session = WebParameter.vnptEpaySessionValue;
        String session_;

        try {
            if (session == null || session.isEmpty()) {
                session = loginToTelco();
            }
            //----------------- Xu ly nghiep vu doan nay ra sao????
            if (session == null || session.isEmpty()) {
                tran = new Transaction();
                return tran;
            }
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
            ChargeReponse chargeReponse;
            try {
                session_ = vnptepayProxy.RSADecrypt(session);
                chargeReponse = vnptepayProxy.cardCharging(tran.getIssuer(), tran.getCardSerial(), tran.getCardCode(), tran.getTransactionId(), account, session_, true);
            } catch (Exception ex) {
                ex.printStackTrace();
                chargeReponse = null;
            }
            tran.setResponseTime(new Date());
            if (chargeReponse != null) {
                String status = chargeReponse.getStatus();
                if (status != null && (status.equalsIgnoreCase("3") || status.equalsIgnoreCase("7"))) {
                    //Neu ma loi lien quan toi loi session, thuc hien login va giao dich lai
                    try {
                        session = loginToTelco();//Login lai
                        session_ = vnptepayProxy.RSADecrypt(session);
                        chargeReponse = vnptepayProxy.cardCharging(tran.getIssuer(), tran.getCardSerial(),
                                tran.getCardCode(), tran.getTransactionId(), account, session_, Boolean.TRUE);//Goi lai ham su dung card
                    } catch (Exception ex) {
                        throw new CardServiceException(ex);
                    }
                } else if (status != null && (status.equalsIgnoreCase("9") || status.equalsIgnoreCase("10"))) {
                    //Neu ma loi lien quan toi mang, kiem tra lai trang thai giao dich
                    try {
                        session_ = vnptepayProxy.RSADecrypt(session);
                        chargeReponse = checkTransactionStatus(tran.getTransactionId(), session_);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                //Reset lai gia tri status neu co khi loi session hoac, loi mang
                status = chargeReponse.getStatus();
                userStatus.append(status);
                userStatus.append("|");

                if (status != null && !status.isEmpty() && status.equalsIgnoreCase("1")) {
                    tran.setAmount(chargeReponse.getResponseamount());
                    tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                    tran.setResponseDescription(WebParameter.GIAO_DICH_THANH_CONG.split("@")[1]);
                    userStatus.append(WebParameter.GIAO_DICH_THANH_CONG);
                    userStatus.append("|");
                    userStatus.append(chargeReponse.getResponseamount());
                } else if (status != null && (status.equalsIgnoreCase("9") || status.equalsIgnoreCase("10"))) {
                    tran.setResponseStatus(WebParameter.HE_THONG_DANG_BAN);
                    userStatus.append(WebParameter.HE_THONG_DANG_BAN);
                } else if (status != null && status.equalsIgnoreCase("11")) {
                    tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                    userStatus.append(WebParameter.PARTNER_BI_KHOA);
                } else {
                    tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                    userStatus.append(WebParameter.THE_KHONG_TON_TAI);
                }

                tran.setRequestTime(new Date());
                tran.setUseCardResponse(userStatus.toString());
                //Status,mota,amount
//                tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
//                tran.setResponseDescription(WebParameter.THE_KHONG_TON_TAI.split("@")[1]);
            } else {
                new ServiceUtil().logTransaction(tran, null);
                tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            }
            return tran;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CardServiceException(ex);
        }
//        lam sao biet dc loi goi tu VTC hay tu FPT?
//        tran.getIssuer(); //FPT or VTC
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    private ChargeReponse checkTransactionStatus(String transactionId, String sessionId) {
        try {
            return vnptepayProxy.getTransactionStatus(transactionId, sessionId);
        } catch (Exception ex) {
            throw new CardServiceException(ex);
        }
    }

    public Transaction checkCard(Transaction tran) {
        //Giong vinaphone, khong support
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String loginToTelco() {
        try {
            LoginResponse loginResponse = vnptepayProxy.login();
            WebParameter.vnptEpaySessionValue = null;
            if (loginResponse == null) {
                return WebParameter.LOI_KET_NOI_SERVER;
            }
            if (loginResponse.getStatus() == null || loginResponse.getStatus().isEmpty()) {
                return WebParameter.LOI_KET_NOI_SERVER;
            }
            if (loginResponse.getStatus().trim().equalsIgnoreCase("8")) {
                return WebParameter.SAI_DIA_CHI_IP;
            }
            if (loginResponse.getStatus().trim().equalsIgnoreCase("13")) {
                return WebParameter.HE_THONG_DANG_BAN;
            }
            if (loginResponse.getStatus().trim().equalsIgnoreCase("6")) {
                return WebParameter.PARTNER_HOP_LE;
            }
            if (loginResponse.getStatus().trim().equalsIgnoreCase("11")) {
                return WebParameter.PARTNER_BI_KHOA;
            }
            if (loginResponse.getStatus().trim().equalsIgnoreCase("1") && loginResponse.getSessionid() != null && !loginResponse.getSessionid().isEmpty()) {
                WebParameter.vnptEpaySessionValue = loginResponse.getSessionid();
                return WebParameter.vnptEpaySessionValue;
            }
            return WebParameter.LOI_KHONG_XAC_DINH;
//
//            loginResponse.getStatus();
//            if (loginResponse == null || loginResponse.getSessionid() == null ||
//                    loginResponse.getSessionid().isEmpty() || loginResponse.getSessionid().trim().isEmpty()) {
//                return WebParameter.LOI_KET_NOI_SERVER;
//            } else {
//                WebParameter.vnptEpaySessionValue = loginResponse.getSessionid();
//            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CardServiceException(ex);
        }
    }
}

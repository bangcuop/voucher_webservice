///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vss.cardservice.service.impl;
//
//import com.vss.cardservice.api.ICardService;
//import com.vss.cardservice.dto.Issuer;
//import com.vss.cardservice.dto.Provider;
//import com.vss.cardservice.dto.Transaction;
//import com.vss.cardservice.service.exception.CardServiceException;
//import com.vss.cardservice.service.util.WebParameter;
//import java.util.Date;
//import vdccardserviceproxy.Config;
//import vdccardserviceproxy.TripleDESVDC;
//import vdccardserviceproxy.VDCCardServiceProxy;
//import vn.vdconline.secondtelcoapi.ws.ChargeResponse;
//import vn.vdconline.secondtelcoapi.ws.LoginResponse;
//
///**
// *
// * @author thibt
// */
//public class VDCServiceImpl implements ICardService {
//
//    public Transaction useCard(Transaction tran) {
//        WebParameter.invalidSession = Boolean.FALSE;
//        String session = WebParameter.vnptEpaySessionValue;
//
//        try {
//            if (session == null || session.isEmpty()) {
//                session = loginToTelco();
//            }
//            if (session == null || session.isEmpty()) {
//                tran = new Transaction();
//                return tran;
//            }
//            Integer status;
//            String provider = tran.getIssuer();
//            if (provider.equals(Issuer.MOBIPHONE)) {
//                provider = Config.PROVIDER_MOBIPHONE;
//            } else if (provider.equals(Issuer.VINAPHONE)) {
//                provider = Config.PROVIDER_VINAPHONE;
//            }
//            ChargeResponse chargeResponse = VDCCardServiceProxy.useCard(provider, tran.getCardSerial(), tran.getCardCode(), tran.getTransactionId().toString(), session);
//            status = chargeResponse.getStatus();
//            tran.setRequestTime(new Date());
//            if (status != null && status.intValue() == 2) {
//                session = loginToTelco();
//                if (session == null || session.isEmpty()) {
//                    tran = new Transaction();
//                    return tran;
//                }
//                chargeResponse = VDCCardServiceProxy.useCard(tran.getIssuer(), tran.getCardSerial(), tran.getCardCode(), tran.getTransactionId().toString(), session);
//                status = chargeResponse.getStatus();
//            }
//            if (status != null && status.intValue() == 1) {
//                tran.setAmount(TripleDESVDC.Decrypt(session, chargeResponse.getDRemainAmount()));
//                tran.setStatus("3");
//                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
//                tran.setUseCardResponse("01" + "|" + TripleDESVDC.Decrypt(session, chargeResponse.getDRemainAmount()));
////                tran.setTelcoResponse(true);
//            } else {
//                tran.setResponseStatus(processVDCCardService(status));
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new CardServiceException(ex);
//        }
//        tran.setProviderId(Provider.VDC0);
//        return tran;
//    }
//
//    public Transaction checkCard(Transaction tran) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public String loginToTelco() {
//        try {
//            LoginResponse loginResponse = VDCCardServiceProxy.logIn();
//            WebParameter.vnptEpaySessionValue = null;
//            if (loginResponse == null) {
//                return WebParameter.LOI_KET_NOI_PROVIDER;
//            }
//            if (loginResponse.getStatus() == null) {
//                return WebParameter.LOI_KET_NOI_PROVIDER;
//            }
//            if (loginResponse.getStatus().intValue() == 1 && loginResponse.getSessionid() != null) {
//                WebParameter.vnptEpaySessionValue = loginResponse.getSessionid();
//                return WebParameter.vnptEpaySessionValue;
//            } else {
//                return processVDCCardService(loginResponse.getStatus());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new CardServiceException(ex);
//        }
//    }
//
//    private String processVDCCardService(Integer status) {
//        System.out.println("processVDCCardService--------|" + status);
//        if (status == null) {
//            return WebParameter.GIAO_DICH_NGHI_VAN;
//        }
//        switch (status.intValue()) {
//            case -1://The da su dung
////                return  InvalidCardCodeException();
//                return WebParameter.THE_KHONG_TON_TAI;
//            case -2:    //the da khoa
////                return  LockedCardCodeException();
//                return WebParameter.THE_DA_KHOA;
//            case -3://the het han su dung
////                return  CardCodeExpiredException();
//                return WebParameter.THE_HET_HAN_SU_DUNG;
//            case -4://The chua duoc kick hoat
////                return  CardCodeNotActiveException();
//                return WebParameter.THE_CHUA_DUOC_KICH_HOAT;
//            case -10:
////                return  NumberFormatException();
//                return WebParameter.THE_KHONG_DUNG_DINH_DANG;
//            case -12:
////                return  InvalidCardCodeException();
//                return WebParameter.THE_KHONG_TON_TAI;
//            case 0:
////                return  TelcoSystemException();
//                return WebParameter.GIAO_DICH_NGHI_VAN;
//            case -99:
////                return  TelcoSystemException();
//                return WebParameter.GIAO_DICH_NGHI_VAN;
//            case 3:
////                return  TelcoSystemException();
//                return WebParameter.GIAO_DICH_NGHI_VAN;
//            case 4:
////                return  InvalidCardCodeException();
//                return WebParameter.THE_KHONG_TON_TAI;
//            case 5:
////                return  TooManyErrorException();
//                return WebParameter.THUC_HIEN_SAI_QUA_SO_LAN_CHO_PHEP;
//            case 7:
////                return  TelcoSystemException();
//                return WebParameter.GIAO_DICH_NGHI_VAN;
//            case 8://Giao dich nghi van
////                return  TransactionWaitUpdateException();
//                return WebParameter.GIAO_DICH_NGHI_VAN;
//            case 9:
////                return  IssuerProcessingException();
//                return WebParameter.LOI_GOI_HAM_PROVIDER;
//            case 10:
////                return  InvalidFormatException();
//                return WebParameter.SAI_DINH_DANG_THONG_TIN;
//            case 11:
//                return WebParameter.GIAO_DICH_NGHI_VAN;
//            case -88:
//                return WebParameter.GIAO_DICH_NGHI_VAN;
//            default:
//                return WebParameter.LOI_KHONG_XAC_DINH;
//        }
//    }
//}

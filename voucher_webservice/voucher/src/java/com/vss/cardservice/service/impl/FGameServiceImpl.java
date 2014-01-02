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
//import com.vss.cardservice.service.exception.CardProcessingException;
//import com.vss.cardservice.service.exception.CardServiceException;
//import com.vss.cardservice.service.exception.ConnectionTimeoutException;
//import com.vss.cardservice.service.exception.InvalidCardCodeException;
//import com.vss.cardservice.service.exception.IssuerProcessingException;
//import com.vss.cardservice.service.exception.UnknownIssuerException;
//import com.vss.cardservice.service.util.WebParameter;
//import fgamedtuproxy.FGameDtuProxy;
//import fgamedtuproxy.FGameParameter;
//import java.util.Date;
//
///**
// *
// * @author thibt
// */
//public class FGameServiceImpl implements ICardService {
//
//    public Transaction useCard(Transaction tran) {
//        try {
//            String provider = tran.getIssuer();
//            if (provider.equals(Issuer.GATE)) {
//                provider = FGameParameter.GATE;
//            } else if (provider.equals(Issuer.VTC_COIN)) {
//                provider = FGameParameter.VTC_COIN;
//            } else if (provider.equals(Issuer.MOBIPHONE)) {
//                provider = FGameParameter.MOBIPHONE;
//            } else if (provider.equals(Issuer.VINAPHONE)) {
//                provider = FGameParameter.VINAPHONE;
//            } else if (provider.equals(Issuer.VIETTEL)) {
//                provider = FGameParameter.VIETTEL;
//            }
//            String response = FGameDtuProxy.request(provider, tran.getCardSerial(), tran.getCardCode(), tran.getTransactionId().toString());
//            tran.setAmount(response);
//            tran.setStatus("3");
//            tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
//            tran.setUseCardResponse("01" + "|" + response);
////            tran.setTelcoResponse(true);
//            tran.setRequestTime(new Date());
//        } catch (ConnectionTimeoutException e) {
//            tran.setResponseStatus(WebParameter.CONNECTION_TIMEOUT);
//        } catch (CardProcessingException e) {
//            tran.setResponseStatus(WebParameter.GIAO_DICH_NGHI_VAN);
//        } catch (UnknownIssuerException e) {
//            tran.setResponseStatus(WebParameter.MA_DICH_VU_KHONG_HOP_LE);
//        } catch (NumberFormatException e) {
//            tran.setResponseStatus(WebParameter.THE_KHONG_DUNG_DINH_DANG);
//        } catch (IssuerProcessingException e) {
//            tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
//        } catch (InvalidCardCodeException e) {
//            tran.setStatus("2");
////            tran.setTelcoResponse(true);
//            tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CardServiceException(e);
//        }
//        tran.setProviderId(Provider.FGAME);
//        return tran;
//    }
//
//    public Transaction checkCard(Transaction tran) {
//        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
//        return tran;
//    }
//
//    public String loginToTelco() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//}

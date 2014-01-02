//package com.vss.cardservice.service.impl;
//
//import com.vss.cardservice.api.ICardService;
//import com.vss.cardservice.dto.Partner;
//import com.vss.cardservice.dto.Transaction;
//import com.vss.cardservice.service.exception.CardServiceException;
//import com.vss.cardservice.service.util.ServiceUtil;
//import com.vss.cardservice.service.util.WebParameter;
//import com.vss.message.util.LoggingUtil;
//import com.vss.mobicard.service.MobiCardProxy;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * 
// * @author zannami
// * 
// *         Jul 13, 2011 4:42:24 PM
// */
//public class MobiCardServiceImpl implements ICardService {
//
//    final String ISSUER_NAME = "MOBI";
//
//    public Transaction useCard(Transaction tran) {
//        WebParameter.invalidSession = false;
//        String useCardStatus = "";
//        Object responseDescription = "", responseStatus = "";
//        String session = WebParameter.mobiSessionValue;
//        if (session == null || session.isEmpty()) {
//            session = loginToTelco();
//        }
//        if (session == null || session.isEmpty()) {
//            if (tran == null) {
//                tran = new Transaction();
//            }
//            return tran;
//        }
//        Map map = null;
//        try {
//            map = MobiCardProxy.useCard(tran.getTransactionId().toString(), session.toString(), tran.getCardCode(), tran.getCardSerial());
//        } catch (Exception e) {
//            e.printStackTrace();
//            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
//            return tran;
//        }
//        try {
//            if (map != null && !map.isEmpty()) {
//                useCardStatus = map.toString();
//                tran.setResponseTime(new Date());
//
//                if (map != null) {
//                    useCardStatus = map.toString();
//                }
//                Iterator iterator = map.keySet().iterator();
//                while (iterator.hasNext()) {
//                    Object key = iterator.next();
//                    if (key.toString().indexOf("description") > -1) {
//                        responseDescription = map.get(key);
//                    }
//                    if (key.toString().indexOf("response") > -1) {
//                        responseStatus = map.get(key);
//                    }
//                }
////                try {
////                    LoggingUtil.log("useCard MOBI:" + tran.getPartnerId() + "|" + tran.getTransRefId() + "|" + tran.getCardCode() + "=" + responseStatus + "|" + responseDescription, "useCard_transaction");
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//                if (responseStatus == null || responseStatus.equals("")
//                        || responseStatus.equals("-1") || responseStatus.equals("-2")
//                        || responseStatus.equals("-3") || responseStatus.equals("-4")
//                        || responseStatus.equals("-10") || responseStatus.equals("-12")) {
////                    if (responseStatus.toString().indexOf("-1") > -1 || responseStatus.toString().indexOf("-4") > -1) {
////                        tran.setStatus("2"); // card invalid
////                        tran.setTelcoResponse(true);
////                    }
//                    if (responseStatus.equals("-4")) {
//                        tran.setResponseStatus(WebParameter.THE_CHUA_DUOC_KICH_HOAT);
//                        tran.setStatus("2"); // card invalid
////                        tran.setTelcoResponse(true);
//                    } else if (responseStatus.equals("-1")) {
//                        tran.setResponseStatus(WebParameter.THE_DA_DUOC_SU_DUNG);
//                        tran.setStatus("2"); // card invalid
////                        tran.setTelcoResponse(true);
//                    } else {
//                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
//                    }
//                } else if (responseStatus.equals("-99") || responseStatus.equals("0")) {
//                    WebParameter.invalidSession = (responseDescription != null && responseDescription.toString().indexOf("Invalid session id") > -1);
//                    if (WebParameter.invalidSession) {
//                        WebParameter.mobiSessionValue = null;
//                        return useCard(tran);
//                    } else {
//                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
//                    }
//                } else if (responseStatus.equals("1")) {
//                    tran.setStatus("3"); // card invalid
//                    String amount = null;
//                    if (responseDescription != null && responseDescription.toString().indexOf(":") > -1) {
//                        amount = responseDescription.toString().split(":")[1];
//                        tran.setAmount(amount);
//                        tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
//                    } else {
//                        tran.setResponseStatus(WebParameter.LOI_KHONG_XAC_DINH);
//                    }
//                } else {
//                    tran.setResponseStatus(WebParameter.LOI_KHONG_XAC_DINH);
//                }
//                tran.setUseCardResponse(useCardStatus);
//                tran.setResponseTime(new Date());
//            } else {
//                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
//            }
//            return tran;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CardServiceException(e);
//        }
//    }
//
//    public String loginToTelco() {
//        try {
//            if (WebParameter.mobiSessionValue == null) {
//                Map map = MobiCardProxy.login();
//                if (map == null || map.isEmpty()) {
//                    return WebParameter.LOI_KET_NOI_PROVIDER;
//                }
//                Object session = "", description = "", responseStatus = "";
//                Iterator iterator = map.keySet().iterator();
//                while (iterator.hasNext()) {
//                    Object key = iterator.next();
//                    if (key.toString().indexOf("session") > -1) {
//                        session = map.get(key);
//                    }
//                    if (key.toString().indexOf("description") > -1) {
//                        description = map.get(key);
//                    }
//                    if (key.toString().indexOf("response") > -1) {
//                        responseStatus = map.get(key);
//                    }
//                }
//                if (session == null || session.equals("")) {
//                    return responseStatus + "|" + description;
//                }
//                WebParameter.mobiSessionValue = session.toString();
//            }
//            return WebParameter.mobiSessionValue;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CardServiceException(e);
//        }
//    }
//
//    /**
//     * String cardCode, String transId
//     * @return
//     */
//    public Transaction checkCard(Transaction tran) {
//        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
//        tran.setResponseDescription(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO.split("\\|")[1]);
//        return tran;
////        Object responseDescription = null, responseStatus = null;
////        String checkCardResponse = null;
////        String session = WebParameter.mobiSessionValue;
////        if (session == null || session.isEmpty()) {
////            session = loginToTelco();
////        }
////        if (session == null || session.isEmpty()) {
////            if (tran == null) {
////                tran = new Transaction();
////            }
////            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
////            tran.setResponseDescription(WebParameter.LOI_KET_NOI_PROVIDER.split("\\|")[1]);
////            return tran;
////        }
////        Map map = null;
////        try {
////            map = MobiCardProxy.checkCard(session, tran.getCardCode());
////        } catch (Exception e) {
////            WebParameter.mobiSessionValue = null;
////            WebParameter.mobiSessionValue = loginToTelco();
////            try {
////                map = MobiCardProxy.checkCard(WebParameter.mobiSessionValue, tran.getCardCode());
////            } catch (Exception ex) {
////                throw new CardServiceException(e);
////            }
////        }
////        try {
////            if (map != null && !map.isEmpty()) {
////                checkCardResponse = map.toString();
////                Iterator iterator = map.keySet().iterator();
////                while (iterator.hasNext()) {
////                    Object key = iterator.next();
////                    if (key.toString().indexOf("description") > -1) {
////                        responseDescription = map.get(key);
////                    }
////                    if (key.toString().indexOf("response") > -1) {
////                        responseStatus = map.get(key);
////                    }
////                }
////                String amount = null;
////                if (responseDescription != null && responseDescription.toString().indexOf(":") > -1) {
////                    amount = responseDescription.toString().split(":")[1];
////                }
////                tran.setResponseStatus(responseStatus.toString());
////                tran.setResponseDescription(responseDescription.toString());
////                tran.setAmount(amount);
////                tran.setCheckCardResponse(checkCardResponse);
////
////                if (responseStatus == null || responseStatus.equals("") || responseStatus.equals("-1") || responseStatus.equals("-2") || responseStatus.equals("-3") || responseStatus.equals("-4") || responseStatus.equals("-10") || responseStatus.equals("-12")) {
////                    tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
////                    tran.setResponseDescription(WebParameter.THE_KHONG_TON_TAI.split("\\|")[1]);
////                } else if (responseStatus.equals("-99") || responseStatus.equals("0")) {
////                    tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
////                    tran.setResponseDescription(WebParameter.LOI_GOI_HAM_PROVIDER.split("\\|")[1] + " checkCard");
////                } else if (responseStatus.equals("1")) {
////                    tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
////                    tran.setResponseDescription(WebParameter.GIAO_DICH_THANH_CONG.split("\\|")[1]);
////                } else {
////                    tran.setResponseStatus(WebParameter.LOI_KHONG_XAC_DINH);
////                    tran.setResponseDescription(WebParameter.LOI_KHONG_XAC_DINH.split("\\|")[1]);
////                }
////            } else {
////                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
////                tran.setResponseDescription(WebParameter.LOI_KET_NOI_PROVIDER.split("\\|")[1]);
////            }
////            return tran;
////        } catch (Exception e) {
////            e.printStackTrace();
////            throw new CardServiceException(e);
////        }
//    }
//}
//
//

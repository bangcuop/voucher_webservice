///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vss.cardservice.service.impl;
//
//import com.homedirect.voucher.exception.ConnectWebserviceException;
//import com.vss.cardservice.api.ICardService;
//import com.vss.cardservice.dto.Provider;
//import com.vss.cardservice.dto.Transaction;
//import com.vss.cardservice.service.exception.CardServiceException;
//import com.vss.cardservice.service.exception.TelcoSystemException;
//import com.vss.cardservice.service.util.WebParameter;
//import epbwebserviceproxy.EPBWebserviceProxy;
//import java.util.Date;
//import vn.proonline.secondtelcoapi.ws.ChargeResponse;
//
///**
// *
// * @author thibt
// */
//public class EBPServiceImpl implements ICardService {
//
//    public Transaction useCard(Transaction tran) {
//        try {
//            ChargeResponse chargeResponse = EPBWebserviceProxy.useCard(tran.getCardCode(),
//                    tran.getCardSerial(), tran.getIssuer());
//            Integer status = chargeResponse.getStatus();
//            tran.setRequestTime(new Date());
//            if (status != null && status.intValue() == 1) {
//                tran.setAmount(chargeResponse.getDRemainAmount());
//                tran.setStatus("3");
//                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
//                tran.setUseCardResponse("01" + "|" + chargeResponse.getDRemainAmount());
////                tran.setTelcoResponse(true);
//            } else {
//                tran.setResponseStatus(processEBPCardService(status));
//            }
//        } catch (ConnectWebserviceException e) {
//            throw new TelcoSystemException();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            throw new CardServiceException(ex);
//        }
//
//        tran.setProviderId(Provider.EBP);
//        return tran;
//    }
//
//    public Transaction checkCard(Transaction tran) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    public String loginToTelco() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
//
//    private String processEBPCardService(Integer status) {
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
//            case -14:
////                return  InvalidCardCodeException();
//                return WebParameter.SERIAL_KHONG_HOP_LE;
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
//            case 6:
////                return  TooManyErrorException();
//                return WebParameter.LOI_GOI_HAM_PROVIDER;
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

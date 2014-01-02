/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import java.net.ConnectException;

/**
 *
 * @author koziwa
 */
public class ErrorUtil {

    public static String captureResponseFromException(Exception exception) {
        String response = "";
        if (exception instanceof UnknownPartnerException) {
            response = WebParameter.PARTNER_KHONG_TON_TAI;
        } else if (exception instanceof LockedPartnerException) {
            response = WebParameter.PARTNER_BI_KHOA;
        } else if (exception instanceof IncorrectPasswordException) {
            response = WebParameter.SAI_PASSWORD;
        } else if (exception instanceof InvalidIpException) {
            response = WebParameter.SAI_DIA_CHI_IP;
        } else if (exception instanceof InvalidSignatureException) {
            response = WebParameter.SAI_CHU_KY;
        } else if (exception instanceof InvalidCardLengthException) {
            response = WebParameter.SAI_DO_DAI_MA_THE;
        } else if (exception instanceof InvalidCardSerialException) {
            response = WebParameter.SERIAL_KHONG_HOP_LE;
        } else if (exception instanceof InvalidCardException) {
            response = WebParameter.MATHE_SERIAL_KHONG_KHOP;
        } else if (exception instanceof InvalidRequestRefException) {
            response = WebParameter.SO_HIEU_GIAO_DICH_KHONG_HOP_LE;
        } else if (exception instanceof DuplicatedRequestRefException) {
            response = WebParameter.TRUNG_MA_GIAO_DICH;
        } else if (exception instanceof IssuerConnectionException) {
            response = WebParameter.LOI_KET_NOI_SERVER;
        } else if (exception instanceof IssuerProcessingException) {
            response = WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if (exception instanceof UnknownIssuerException) {
            response = WebParameter.MA_DICH_VU_KHONG_HOP_LE;
        } else if (exception instanceof UnknownProcessingException) {
            response = WebParameter.LOI_KHONG_XAC_DINH;
        } else if (exception instanceof IssuerInvalidProcessingException) {
            response = WebParameter.DICH_VU_CHUA_DUOC_HO_TRO;
        } else if (exception instanceof InvalidCardCodeException) {
            response = WebParameter.THE_KHONG_TON_TAI;
        } else if (exception instanceof CardServiceDBException) {
            response = WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if (exception instanceof CardServiceException) {
            response = WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if (exception instanceof TelcoSystemException) {
            response = WebParameter.LOI_GOI_HAM_PROVIDER;
        } else if (exception instanceof CardProcessingException) {
            response = WebParameter.THE_DANG_XU_LY;
        } else if (exception instanceof IssuerBusyException) {
            response = WebParameter.HE_THONG_DANG_BAN;
        } else if (exception instanceof ConnectionTimeoutException) {
            response = WebParameter.HE_THONG_DANG_BAN;
        } else if (exception instanceof NumberFormatException) {
            response = WebParameter.THE_KHONG_DUNG_DINH_DANG;
        } else if (((exception instanceof ConnectException)) ||
                ((exception instanceof InterruptedException)) ||
                ((exception instanceof RemoteServiceException))) {
            response = WebParameter.LOI_KET_NOI_SERVER;
        } else if ((exception instanceof CardServiceDBException)) {
            response = WebParameter.LOI_GOI_HAM_PROVIDER;
        }
        try {
            String description = ServiceUtil.CARD_SERVICE.getString(response);
            if (!description.isEmpty()) {
                response = response + "|" + description;
            }
            if (exception instanceof LockedPartnerException) {
                response = response + "|" + exception.getMessage();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}

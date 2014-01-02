/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package vdccardserviceproxy;

import vn.vdconline.secondtelcoapi.ws.ChargeResponse;
import vn.vdconline.secondtelcoapi.ws.LoginResponse;
import vn.vdconline.secondtelcoapi.ws.LogoutResponse;

/**
 *
 * @author thibt
 */
public class VDCCardServiceProxy extends WebserviceProxy {

    public static ChargeResponse useCard(String provider, String cardSerial, String cardCode, String transactionId, String sessionId) throws Exception {
        String urlEnpoint = Config.END_POINT;
        String userName = Config.USER_NAME;
        int parnerId = Config.PARTNER_ID;
        String mpin = Config.MPIN;
//        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        if (sessionId == null) {
//            System.out.println("-----------logIn--sessionId == null--------" + format.format(new Date()));
//            LoginResponse loginResponse = logIn(userName, password, parnerId, urlEnpoint);
//            status = loginResponse.getStatus();
////            System.out.println("--------------useCard----loginResponse--status-1---------|" + status);
//            if (status != null && status.intValue() == 1) {
//                sessionId = loginResponse.getSessionid();
//                System.out.println("sessionId---1|" + sessionId);
//            } else {
//                ProccessVDCCardService.throwExceptionFromErrorCode(status);
//            }
//
//        }
//        ChargeResponse chargeResponse = getTelcoWebService(urlEnpoint).cardCharge(userName, parnerId, TripleDESVDC.Encrypt(sessionId, mpin), TripleDESVDC.Encrypt(sessionId, cardCode + ":" + provider), userName, "thibt@pasolutions.vn", "0942554233");
        return getTelcoWebService(urlEnpoint).cardCharge(userName, parnerId, TripleDESVDC.Encrypt(sessionId, mpin), TripleDESVDC.Encrypt(sessionId, cardCode + ":" + provider), userName, Config.EMAIL, Config.PHONE);
//        status = chargeResponse.getStatus();
//        if (status != null && status.intValue() == 2) {
//            System.out.println("-----------logIn--chargeResponse == status ==2 --------" + format.format(new Date()));
////            LogoutResponse logoutResponse = logOut(userName, parnerId, sessionId, urlEnpoint);
////            status = logoutResponse.getStatus();
////            if (status == null || status.intValue() != 1) {
////                return "logOut|" + status + "|" + logoutResponse.getMessage();
////            }
//            LoginResponse loginResponse = logIn(userName, password, parnerId, urlEnpoint);
//            status = loginResponse.getStatus();
//            if (status != null && status.intValue() == 1) {
//                sessionId = loginResponse.getSessionid();
//                System.out.println("sessionId---2|" + sessionId);
//            } else {
//                ProccessVDCCardService.throwExceptionFromErrorCode(status);
////                return "login|" + status + "|" + loginResponse.getMessage();
//            }
//            chargeResponse = getTelcoWebService(urlEnpoint).cardCharge(userName, parnerId, TripleDESVDC.Encrypt(sessionId, mpin), TripleDESVDC.Encrypt(sessionId, cardCode + ":" + provider), userName, "thibt@pasolutions.vn", "0942554233");
//            status = chargeResponse.getStatus();
//        }
//        if (status != null && status.intValue() == 1) {
//            return TripleDESVDC.Decrypt(sessionId, chargeResponse.getDRemainAmount()) + "|" + chargeResponse.getTransid();
//        } else {
//            ProccessVDCCardService.throwExceptionFromErrorCode(status);
//            return null;
//            return status + "|" + chargeResponse.getMessage() + "|" + chargeResponse.getTransid();
//        }
    }

    public static LoginResponse logIn() throws Exception {
        String urlEnpoint = Config.END_POINT;
        String userName = Config.USER_NAME;
        String password = Config.PASSWORD;
        int parnerId = Config.PARTNER_ID;
        return getTelcoWebService(urlEnpoint).logIn(userName, TripleDESVDC.sha1(password), parnerId);
    }

    public static LogoutResponse logOut(String sessionId) throws Exception {
        String urlEnpoint = Config.END_POINT;
        String userName = Config.USER_NAME;
        int parnerId = Config.PARTNER_ID;
        return getTelcoWebService(urlEnpoint).logOut(userName, parnerId, sessionId);
    }

    public static void main(String[] args) throws Exception {
        String provider = "VMS";
        String cardSerial = "12345678912345";
        String cardCode = "123456789012";
        String transactionId = "1";
//        System.out.println(useCard(provider, cardSerial, cardCode, transactionId));
    }
}

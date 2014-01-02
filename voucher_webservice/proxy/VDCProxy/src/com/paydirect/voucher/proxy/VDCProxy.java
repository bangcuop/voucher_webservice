/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import com.paydirect.voucher.exception.CardServiceException;
import com.paydirect.voucher.exception.ConnectWebserviceException;
import com.vss.cardservice.dto.Transaction;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Scanner;
import javax.xml.namespace.QName;
import vn.vdconline.secondtelcoapi.ws.ChargeResponse;
import vn.vdconline.secondtelcoapi.ws.LoginResponse;
import vn.vdconline.secondtelcoapi.ws.LogoutResponse;
import vn.vdconline.secondtelcoapi.ws.TelcoWebService;
import vn.vdconline.secondtelcoapi.ws.TelcoWebServiceImplService;

/**
 *
 * @author thibt
 */
public class VDCProxy {

    private static final String DELIMITER = "|";
    private static Class issuerServiceUtil;
    private static Method getRandomGameAccountMethod;
    private static String sessionId;
    private static TelcoWebService telcoWebService;
    private static String urlString;
    private static Integer partnerId;
    private static String userName;
    private static String password;
    private static String mpin;
    private static String targetMail;
    private static String targetPhone;
    private static String prefix = "[VDCProxy]";
    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static Map<String, String> issuerMap;
    private static boolean isFirstTry = true;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("wsconfig"+File.separator+"VDCProxy");
        urlString = bundle.getString("vdc_url").trim();
        partnerId = new Integer(bundle.getString("vdc_id").trim());
        userName = bundle.getString("vdc_userName").trim();
        password = bundle.getString("vdc_password").trim();
        mpin = bundle.getString("vdc_mpin").trim();
        targetMail = bundle.getString("vdc_targetMail").trim();
        targetPhone = bundle.getString("vdc_targetPhone").trim();
        issuerMap = new HashMap<String, String>();

        try {
            String issuerSupport = bundle.getString("vdc_telcoCode");
            if (issuerSupport != null) {
                String[] telcoList = issuerSupport.split("\\|");
                for (int i = 0; i < telcoList.length; i++) {
                    String[] telco = telcoList[i].split("-");
                    if (telco.length == 2) {
                        issuerMap.put(telco[0], telco[1]);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            URL url = new URL(urlString);
            telcoWebService = new TelcoWebServiceImplService(url, new QName("http://ws.secondtelcoAPI.vdconline.vn/", "TelcoWebServiceImplService")).getTelcoWebServiceImplPort();
        } catch (Exception ex) {
            throw new ConnectWebserviceException(ex);
        }

        try {
            URLClassLoader CLASSLOADER = (URLClassLoader) VDCProxy.class.getClassLoader();
            issuerServiceUtil = Class.forName("com.vss.cardservice.service.util.IssuerServiceUtil", true, CLASSLOADER);
            getRandomGameAccountMethod = issuerServiceUtil.getDeclaredMethod("getRandomGameAccount");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CardServiceException();
        }
    }

    public Transaction useCard(Transaction tran) {
        StringBuilder sb = new StringBuilder();
        try {
            String target = (String) getRandomGameAccountMethod.invoke(issuerServiceUtil);
            tran.setAccountId(target);
            String provider = issuerMap.get(tran.getIssuer());
            ChargeResponse chargeResponse = useCard(provider, tran.getCardSerial(), tran.getCardCode(), target);
            Integer status = chargeResponse.getStatus();
            sb.append(status);
            sb.append(DELIMITER);
            sb.append(chargeResponse.getMessage());
            sb.append(DELIMITER);
            sb.append(chargeResponse.getTransid());

            tran.setUseCardResponse(chargeResponse.getMessage());
            if (status != null && status.intValue() == 1) {
                String amount = TripleDESVDC.Decrypt(sessionId, chargeResponse.getDRemainAmount());
                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                tran.setAmount(amount);
                sb.append(DELIMITER);
                sb.append(amount);
            } else {
                tran.setResponseStatus(processVDCCardService(status));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
        }
        tran.setUseCardResponse(sb.toString());
        tran.setResponseTime(new Date());
        return tran;
    }

    public Boolean checkConnection() {
        boolean result = true;
        try {
            new URL(urlString).openConnection().connect();
        } catch (Exception ex) {
            System.out.println("VDCProxy.checkConnection.telco : ERROR");
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    static void logIn() throws Exception {
        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
        prefixBuilder.append(prefix);
        prefixBuilder.append("[logIn - userName:");
        prefixBuilder.append(userName);
        prefixBuilder.append("][password:");
        prefixBuilder.append(password);
        prefixBuilder.append("]");
        prefixBuilder.append("][partnerId:");
        prefixBuilder.append(partnerId);
        prefixBuilder.append("]");
        try {
            LoginResponse loginResponse = telcoWebService.logIn(userName, TripleDESVDC.sha1(password), partnerId);
            sessionId = loginResponse.getSessionid();
            prefixBuilder.append("[result:");
            prefixBuilder.append(sessionId);
            prefixBuilder.append("]");
            System.out.println(prefixBuilder.toString());
        } catch (Exception e) {
            throw new ConnectWebserviceException(e);
        }
    }

    static void logOut(String sessionId) throws Exception {
        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
        prefixBuilder.append(prefix);
        prefixBuilder.append("[logOut - userName:");
        prefixBuilder.append(userName);
        prefixBuilder.append("][password:");
        prefixBuilder.append(password);
        prefixBuilder.append("]");
        prefixBuilder.append("][partnerId:");
        prefixBuilder.append(partnerId);
        prefixBuilder.append("]");
        try {
            LogoutResponse logOutResponse = telcoWebService.logOut(userName, partnerId, sessionId);
            prefixBuilder.append("[result:");
            prefixBuilder.append(logOutResponse.getStatus());
            prefixBuilder.append("]message:[");
            prefixBuilder.append(logOutResponse.getMessage());
            prefixBuilder.append("]");
            System.out.println(prefixBuilder.toString());
        } catch (Exception e) {
            throw new ConnectWebserviceException(e);
        }
    }

    public static ChargeResponse useCard(String provider, String cardSerial, String cardCode, String target) throws Exception {
        if (sessionId == null) {
            logIn();
        }
        try {
            StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
            prefixBuilder.append(prefix);
            prefixBuilder.append("[userCardChargeResponse - sessionId:");
            prefixBuilder.append(sessionId);
            prefixBuilder.append("][userName:");
            prefixBuilder.append(userName);
            prefixBuilder.append("][cardCode:");
            prefixBuilder.append(cardCode);
            prefixBuilder.append("][cardSerial:");
            prefixBuilder.append(cardSerial);
            prefixBuilder.append("][partnerId:");
            prefixBuilder.append(partnerId);
            prefixBuilder.append("]");
            System.out.println(prefixBuilder.toString());

            String mpinEncrypt = TripleDESVDC.Encrypt(sessionId, mpin);
            String dataCardEncrypt = TripleDESVDC.Encrypt(sessionId, cardCode + ":" + provider+":"+cardSerial);
            ChargeResponse chargeResponse = telcoWebService.cardCharge(userName, partnerId, mpinEncrypt, dataCardEncrypt, userName, targetMail, targetPhone);
            Integer status = chargeResponse.getStatus();

            StringBuilder resultBuilder = new StringBuilder(prefixBuilder);
            resultBuilder.append("[result - status:");
            resultBuilder.append(status);
            resultBuilder.append("|getTransid:");
            resultBuilder.append(chargeResponse.getTransid());
            resultBuilder.append("|mesage:");
            resultBuilder.append(chargeResponse.getMessage());
            resultBuilder.append("|getDRemainAmount:");
            resultBuilder.append(chargeResponse.getDRemainAmount());
            resultBuilder.append("]");
            System.out.println(resultBuilder.toString());

            if (status != null && status.intValue() == 2 && isFirstTry) {
                isFirstTry = false;
                sessionId = null;
                useCard(provider, cardSerial, cardCode, target);
            }
            if ((chargeResponse != null) && (chargeResponse.getDRemainAmount() != null)) {
                try {
                    chargeResponse.setDRemainAmount(TripleDESVDC.Decrypt(sessionId, chargeResponse.getDRemainAmount()));
                    resultBuilder.append("[Decrypt amount:");
                    resultBuilder.append(chargeResponse.getDRemainAmount());
                    resultBuilder.append("]");
                    System.out.println(resultBuilder.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return chargeResponse;
        } catch (Exception ex) {
            throw new ConnectWebserviceException(ex);
        }
    }

    private String processVDCCardService(Integer status) {
        if (status == null) {
            return WebParameter.GIAO_DICH_NGHI_VAN;
        }
        switch (status.intValue()) {
            case -1://The da su dung
//                return  InvalidCardCodeException();
                return WebParameter.THE_KHONG_TON_TAI;
            case -2:    //the da khoa
//                return  LockedCardCodeException();
                return WebParameter.THE_DA_KHOA;
            case -3://the het han su dung
//                return  CardCodeExpiredException();
                return WebParameter.THE_HET_HAN_SU_DUNG;
            case -4://The chua duoc kick hoat
//                return  CardCodeNotActiveException();
                return WebParameter.THE_CHUA_DUOC_KICH_HOAT;
            case -10:
//                return  NumberFormatException();
                return WebParameter.THE_KHONG_DUNG_DINH_DANG;
            case -12:
//                return  InvalidCardCodeException();
                return WebParameter.THE_KHONG_TON_TAI;
            case 0:
//                return  TelcoSystemException();
                return WebParameter.GIAO_DICH_NGHI_VAN;
            case -99:
//                return  TelcoSystemException();
                return WebParameter.GIAO_DICH_NGHI_VAN;
            case 3:
//                return  TelcoSystemException();
                return WebParameter.GIAO_DICH_NGHI_VAN;
            case 4:
//                return  InvalidCardCodeException();
                return WebParameter.THE_KHONG_TON_TAI;
            case 5:
//                return  TooManyErrorException();
                return WebParameter.THUC_HIEN_SAI_QUA_SO_LAN_CHO_PHEP;
            case 7:
//                return  TelcoSystemException();
                return WebParameter.GIAO_DICH_NGHI_VAN;
            case 8://Giao dich nghi van
//                return  TransactionWaitUpdateException();
                return WebParameter.GIAO_DICH_NGHI_VAN;
            case 9:
//                return  IssuerProcessingException();
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 10:
//                return  InvalidFormatException();
                return WebParameter.SAI_DINH_DANG_THONG_TIN;
            case 11:
                return WebParameter.GIAO_DICH_NGHI_VAN;
            case -88:
                return WebParameter.GIAO_DICH_NGHI_VAN;
            default:
                return WebParameter.LOI_KHONG_XAC_DINH;
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner inScanner = new Scanner(System.in);
//        String provider = "VMS";
//        String cardSerial = "010721001406038";
//        String cardCode = "066052163042";
        System.out.println("Nhập Provider :");
        String provider = inScanner.nextLine();
        System.out.println("Nhập cardSerial :");
        String cardSerial = inScanner.nextLine();
        System.out.println("Nhập cardCode :");
        String cardCode = inScanner.nextLine();
        String transactionId = "123456789";
        System.out.println("Provider:" + provider + "|cardSerial:" + cardSerial + "|cardCode:" + cardCode);
        ChargeResponse chargeResponse = useCard(provider, cardSerial, cardCode, transactionId);
        System.out.println("result:" + chargeResponse.getMessage());
    }
}

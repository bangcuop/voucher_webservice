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
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import vn.proonline.secondtelcoapi.ws.LoginResponse;
import vn.proonline.secondtelcoapi.ws.TelcoWebService;
import vn.proonline.secondtelcoapi.ws.TelcoWebServiceImplService;
import vn.proonline.secondtelcoapi.ws.ChargeResponse;
import vn.proonline.secondtelcoapi.ws.LogoutResponse;

/**
 *
 * @author hoangha2503
 * @modify liemnh
 */
public class EBSProxy {

    private static final String DELIMITER = "|";
    private static Class issuerServiceUtil;
    private static Method getRandomGameAccountMethod;
    private static TelcoWebService service;
    private static String urlString;
    private static int merchantId;
    private static String userName;
    private static String password;
    private static String mpin;
    private static String targetMail;
    private static String targetPhone;
    private static String sessionId;
    private static Map<String, String> issuerMap;
    private static String prefix = "[EBSProxy]";
    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private boolean isFirstTry = true;

//    static {
//        issuerMap = new HashMap();
//        issuerMap.put("MOBI", "VMS");
//        issuerMap.put("VINA", "VNP");
//        issuerMap.put("VT", "VTT");
//    }
    static {
        ResourceBundle bundle = ResourceBundle.getBundle("wsconfig" + File.separator + "EBSProxy");
        urlString = bundle.getString("ebs_url").trim();
        merchantId = new Integer(bundle.getString("ebs_id").trim());
        userName = bundle.getString("ebs_userName").trim();
        password = bundle.getString("ebs_password").trim();
        mpin = bundle.getString("ebs_mpin").trim();
        targetMail = bundle.getString("ebs_targetMail").trim();
        targetPhone = bundle.getString("ebs_targetPhone").trim();
        issuerMap = new HashMap<String, String>();

        try {
            String issuerSupport = bundle.getString("ebs_telcoCode");
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
            service = new TelcoWebServiceImplService(url, new QName("http://ws.secondtelcoAPI.proonline.vn/", "TelcoWebServiceImplService")).getTelcoWebServiceImplPort();
        } catch (Exception ex) {
            throw new ConnectWebserviceException(ex);
        }

        try {
            URLClassLoader CLASSLOADER = (URLClassLoader) EBSProxy.class.getClassLoader();
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
            String providerName = issuerMap.get(tran.getIssuer());
            ChargeResponse chargeResponse = useCard(providerName, tran.getCardCode(), tran.getCardSerial(), target);
            Integer status = chargeResponse.getStatus();
            sb.append(status);
            sb.append(DELIMITER);
            sb.append(chargeResponse.getMessage());
            sb.append(DELIMITER);
            sb.append(chargeResponse.getTransid());

            tran.setUseCardResponse(chargeResponse.getMessage());
            if (status != null && status.intValue() == 1) {
                String amount = String.valueOf(new Float(chargeResponse.getDRemainAmount()).intValue());
                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                tran.setAmount(amount);
                sb.append(DELIMITER);
                sb.append(amount);
            } else {
                tran.setResponseStatus(processEBPCardService(status));
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
            System.out.println("EBSProxy.checkConnection.telco : ERROR");
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    static void login() {
//        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
//        prefixBuilder.append(prefix);
//        prefixBuilder.append("[logIn - userName:");
//        prefixBuilder.append(userName);
//        prefixBuilder.append("][password:");
//        prefixBuilder.append(password);
//        prefixBuilder.append("]");
//        prefixBuilder.append("][merchantId:");
//        prefixBuilder.append(merchantId);
//        prefixBuilder.append("]");
        try {
            LoginResponse loginResponse = service.logIn(userName, hash(password, "SHA"), merchantId);
            sessionId = loginResponse.getSessionid();
//            prefixBuilder.append("[result:");
//            prefixBuilder.append(sessionId);
//            prefixBuilder.append("]");
//            System.out.println(prefixBuilder.toString());
        } catch (Exception ex) {
            throw new ConnectWebserviceException(ex);
        }
    }

    static void logout(String sessionId) {
//        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
//        prefixBuilder.append(prefix);
//        prefixBuilder.append("[logOut - userName:");
//        prefixBuilder.append(userName);
//        prefixBuilder.append("][password:");
//        prefixBuilder.append(password);
//        prefixBuilder.append("]");
//        prefixBuilder.append("][merchantId:");
//        prefixBuilder.append(merchantId);
//        prefixBuilder.append("]");
        try {
            LogoutResponse logOutResponse = service.logOut(userName, merchantId, sessionId);
//            prefixBuilder.append("[result:");
//            prefixBuilder.append(logOutResponse.getStatus());
//            prefixBuilder.append("]message:[");
//            prefixBuilder.append(logOutResponse.getMessage());
//            prefixBuilder.append("]");
//            System.out.println(prefixBuilder.toString());
        } catch (Exception e) {
            throw new ConnectWebserviceException(e);
        }
    }

    public ChargeResponse useCard(String providerName, String cardCode, String cardSerial, String target) {
        if (sessionId == null) {
            login();
        }
//        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
//        prefixBuilder.append(prefix);
//        prefixBuilder.append("[userCardChargeResponse - sessionId:");
//        prefixBuilder.append(sessionId);
//        prefixBuilder.append("][userName:");
//        prefixBuilder.append(userName);
//        prefixBuilder.append("][cardCode:");
//        prefixBuilder.append(cardCode);
//        prefixBuilder.append("][cardSerial:");
//        prefixBuilder.append(cardSerial);
//        prefixBuilder.append("][providerName:");
//        prefixBuilder.append(providerName);
//        prefixBuilder.append("][merchantId:");
//        prefixBuilder.append(merchantId);
//        prefixBuilder.append("]");
//        System.out.println(prefixBuilder.toString());

        try {
            String mpinEncrypt = TripleDESEBS.Encrypt(sessionId, mpin);
            String dataCardEncrypt = TripleDESEBS.Encrypt(sessionId, cardSerial + ":" + cardCode + "::" + providerName);
            ChargeResponse chargeResponse = service.cardCharge(userName, merchantId, mpinEncrypt, dataCardEncrypt, target, targetMail, targetPhone);
            Integer chargeResponseStatus = chargeResponse.getStatus();
//            StringBuilder resultBuilder = new StringBuilder(prefixBuilder);
//            resultBuilder.append("[result - status:");
//            resultBuilder.append(chargeResponseStatus);
//            resultBuilder.append("|getTransid:");
//            resultBuilder.append(chargeResponse.getTransid());
//            resultBuilder.append("|mesage:");
//            resultBuilder.append(chargeResponse.getMessage());
//            resultBuilder.append("|getDRemainAmount:");
//            resultBuilder.append(chargeResponse.getDRemainAmount());
//            resultBuilder.append("]");
//            System.out.println(resultBuilder.toString());

            if (chargeResponseStatus != null && chargeResponseStatus.intValue() == 2 && isFirstTry) {
                isFirstTry = false;
                sessionId = null;
                return useCard(providerName, cardCode, cardSerial, target);
            }

            if ((chargeResponse != null) && (chargeResponse.getDRemainAmount() != null)) {
                try {
                    chargeResponse.setDRemainAmount(TripleDESEBS.Decrypt(sessionId, chargeResponse.getDRemainAmount()));
//                    resultBuilder.append("[Decrypt amount:");
//                    resultBuilder.append(chargeResponse.getDRemainAmount());
//                    resultBuilder.append("]");
//                    System.out.println(resultBuilder.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return chargeResponse;
        } catch (Exception ex) {
            throw new ConnectWebserviceException();
        }
    }

    private String processEBPCardService(Integer status) {
        if (status == null) {
            return WebParameter.GIAO_DICH_NGHI_VAN;
        }
        switch (status.intValue()) {
            case -1://The da su dung
//                return  InvalidCardCodeException();
                return WebParameter.THE_DA_DUOC_SU_DUNG;
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
            case -14:
//                return  InvalidCardCodeException();
                return WebParameter.THE_KHONG_DUNG_DINH_DANG;
            case 0:
//                return  TelcoSystemException();
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case -99:
//                return  TelcoSystemException();
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 3:
//                return  TelcoSystemException();
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 4:
//                return  InvalidCardCodeException();
                return WebParameter.THE_KHONG_TON_TAI;
            case 5:
//                return  TooManyErrorException();
                return WebParameter.THUC_HIEN_SAI_QUA_SO_LAN_CHO_PHEP;
            case 6:
//                return  TooManyErrorException();
                return WebParameter.THE_DA_DUOC_SU_DUNG;
            case 7:
//                return  TelcoSystemException();
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 8://Giao dich nghi van
//                return  TransactionWaitUpdateException();
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 9:
//                return  IssuerProcessingException();
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 10:
//                return  InvalidFormatException();
                return WebParameter.SAI_DINH_DANG_THONG_TIN;
            case 11:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 15:
                return WebParameter.THE_DA_DUOC_SU_DUNG;
            case -88:
                return WebParameter.GIAO_DICH_NGHI_VAN;
            default:
                return WebParameter.LOI_KHONG_XAC_DINH;
        }
    }

    private static String hash(String strValue, String strAlgorithm)
            throws Exception {
        byte[] btValue = strValue.getBytes();
        //  BASE64Encoder enc = new BASE64Encoder();
        MessageDigest md = MessageDigest.getInstance(strAlgorithm);
        // return enc.encodeBuffer(md.digest(btValue));
        return Base64Coder.encodeLines(md.digest(btValue));

    }

    public static void main(String[] args) {
        // TODO code application logic here
//        String cardCode = "6992326228";
//        String cardCode = "699232622803";
//        String cardCode = "939816903634";
//        String cardSerial = "010471002945733";
////        String cardSerial = "011162000962784";
//        String issuerId = "MOBI";
//        ChargeResponse chargeResponse = new EBSProxy().useCard("VMS", cardCode, cardSerial, "target_test");
//        System.out.println("--->> chargeResponse:" + chargeResponse.getStatus());
//        System.out.println("--->> chargeResponse:" + chargeResponse.getMessage());
        String liem = "10000.0";
        System.out.println(String.valueOf(new Float(liem).intValue()));
    }
}

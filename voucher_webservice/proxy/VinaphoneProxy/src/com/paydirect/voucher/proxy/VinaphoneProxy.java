package com.paydirect.voucher.proxy;

import com.elcom.vchgws.VCardServer;
import com.elcom.vchgws.VCardServerPortType;
import com.elcom.vchgws.message.ChangeMPINResponse;
import com.elcom.vchgws.message.ChangePasswordResponse;
import com.elcom.vchgws.message.LoadResponse;
import com.elcom.vchgws.message.LoginResponse;
import com.elcom.vchgws.message.LogoutResponse;
import com.elcom.vchgws.message.TodayEnquiryResponse;
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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import sun.misc.BASE64Encoder;

public class VinaphoneProxy {

    private static final String ISSUER_NAME = "VINA";
    private static Class issuerServiceUtil;
    private static Method getSessionValueMethod;
    private static Method updateSessionValueMethod;
    private static Method getRandomGameAccountMethod;
    private static String userName;
    private static String agentID;
    private static String mPin;
    private static String pass;
    private static String urlString;
    private static VCardServerPortType service;
    private static String prefix = "[VinaphoneProxy]";
    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private static String sessionValue;
    private static Calendar reloadCal = Calendar.getInstance();
    private boolean isFirstTry = true;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("wsconfig" + File.separator + "VinaphoneProxy");
        userName = bundle.getString("vinaphone_userName").trim();
        agentID = bundle.getString("vinaphone_agentID").trim();
        mPin = bundle.getString("vinaphone_mPin").trim();
        pass = bundle.getString("vinaphone_pass").trim();
        urlString = bundle.getString("vinaphone_wsdl").trim();

        try {
            URLClassLoader classLoader = (URLClassLoader) VinaphoneProxy.class.getClassLoader();
            issuerServiceUtil = Class.forName("com.vss.cardservice.getService().util.IssuerServiceUtil", true, classLoader);
            getSessionValueMethod = issuerServiceUtil.getDeclaredMethod("getSessionValue", String.class);
            updateSessionValueMethod = issuerServiceUtil.getDeclaredMethod("updateSessionValue", String.class, String.class);
            getRandomGameAccountMethod = issuerServiceUtil.getDeclaredMethod("getRandomGameAccount");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CardServiceException();
        }
    }

    private static VCardServerPortType getService() {
        if (service == null) {
            try {
                URL url = new URL(urlString);
                service = new VCardServer(url, new QName("http://vchgws.elcom.com", "VCardServer")).getVCardServerHttpPort();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new ConnectWebserviceException(ex);
            }
        }
        return service;
    }

    private void getSessionValue() throws Exception {
        System.out.println("****** Vina.getSessionValue : ... ");
        if (sessionValue == null || reloadCal.before(Calendar.getInstance())) {
            System.out.println("Vina.getSessionValue : " + (sessionValue == null ? "sessionValue == null" : ("reloadCal = " + dateFormat.format(reloadCal.getTime()))));
            sessionValue = (String) getSessionValueMethod.invoke(issuerServiceUtil, ISSUER_NAME);
            if (sessionValue != null) {
                System.out.println("Vina.getSessionValue : IssuerServiceUtil.getSessionValue = " + sessionValue);
                reloadCal = Calendar.getInstance();
                reloadCal.add(Calendar.MINUTE, 10);
            }
        }
        if (sessionValue == null) {
            sessionValue = login();
            System.out.println("Vina.getSessionValue : login to VINA, newSession = " + sessionValue);
            updateSessionValueMethod.invoke(issuerServiceUtil, ISSUER_NAME, sessionValue);
        }
    }

    public Transaction useCard(Transaction tran) {
        try {
            getSessionValue();
            if (sessionValue != null) {
                String account = (String) getRandomGameAccountMethod.invoke(issuerServiceUtil);

                tran.setAccountId(account);
                LoadResponse load = loadNew(sessionValue, tran.getCardCode(), account, Integer.parseInt(tran.getTransactionId().toString()), tran.getCardSerial().toUpperCase());
                load.getDRemainAmount();
//                Integer status = load.getStatus();
                JAXBElement<String> msg = load.getMessage();
                String transactionMessage;
                if (msg != null) {
                    transactionMessage = msg.getValue();
                } else {
                    transactionMessage = "";
                }
                if (load.getTransid() != null) {
                    tran.setTelcoTransRefId(String.valueOf(load.getTransid()));
                }
                if (transactionMessage.indexOf("username/pass") > -1) {
                    logout(sessionValue);
                    sessionValue = null;
                    if (isFirstTry) {
                        isFirstTry = false;
                        useCard(tran);
                    }
                }

                if (load.getDRemainAmount() == null || String.valueOf(load.getDRemainAmount()).equals("0.0")) {
                    if (transactionMessage.indexOf("Account cua ban da bi khoa") > 0 || transactionMessage.indexOf("SYSTEM OVERLOAD") > -1 || transactionMessage.indexOf("NOT_DISTRIBUTED") > -1 || transactionMessage.indexOf("Co loi trong qua trinh xu ly") > -1) {
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        tran.setUseCardResponse(load.getTransid() + "|" + load.getStatus() + "|" + (transactionMessage == null ? "" : transactionMessage));
                    } else {
                        if (transactionMessage.indexOf("USED_BEFORE") > -1) {
                            tran.setResponseStatus(WebParameter.THE_DA_DUOC_SU_DUNG);
                        } else if (transactionMessage.indexOf("TERMINATED_BEFORE") > -1) {
                            tran.setResponseStatus(WebParameter.THE_HET_HAN_SU_DUNG);
                        } else {
                            tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        }
                        tran.setUseCardResponse(load.getTransid() + "|" + load.getStatus() + "|" + (transactionMessage == null ? "" : transactionMessage));
                    }
                } else {
                    tran.setAmount(String.valueOf(load.getDRemainAmount().intValue()));
                    tran.setUseCardResponse(load.getTransid() + "|" + load.getStatus() + "|" + load.getMessage().getValue() + "|" + load.getDRemainAmount());
                    tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
//                    tran.setCardSerial(load.getSSerialNumber().getValue());
                }
            } else {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
            }
            if (tran.getUseCardResponse().length() > 100) {
                tran.setUseCardResponse(tran.getUseCardResponse().substring(0, 96) + "...");
            }
            tran.setResponseTime(new Date());

        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
        }
        return tran;
    }

    public Boolean checkConnection() {
        boolean result = true;
        try {
            new URL(urlString).openConnection().connect();
        } catch (Exception ex) {
            System.out.println("VinaphoneProxy.checkConnection : ERROR");
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    public Transaction checkCard(Transaction tran) {
        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
        return tran;
    }

    public static ChangeMPINResponse changeMpin(String sessionid, String newMPIN)
            throws Exception {
        TripleDESUtil.key = TripleDESUtil.createKey(sessionid);
        String oldMPINTripleDES = TripleDESUtil.encrypt(mPin);
        String newMPINTripleDES = TripleDESUtil.encrypt(newMPIN);
        return getService().changeMpin(userName, agentID, oldMPINTripleDES, newMPINTripleDES);
    }

    public static LogoutResponse logout(String sessinid) throws Exception {
        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
        prefixBuilder.append(prefix);
        prefixBuilder.append("[logout - sessinid:");
        prefixBuilder.append(sessinid);
        prefixBuilder.append("]");
        System.out.println(prefixBuilder.toString());
        LogoutResponse logoutResponse = getService().logout(sessinid);
        updateSessionValueMethod.invoke(issuerServiceUtil, ISSUER_NAME, null);
        return logoutResponse;
    }

    public static String login() throws Exception {
        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
        prefixBuilder.append(prefix);
        prefixBuilder.append("[login - userName:");
        prefixBuilder.append(userName);
        prefixBuilder.append("][pass:");
        prefixBuilder.append(pass);
        prefixBuilder.append("][agentID:");
        prefixBuilder.append(agentID);
        prefixBuilder.append("]");
        System.out.println(prefixBuilder.toString());
        String passSHA = encrypt(pass, "SHA");

        LoginResponse loginResponse = getService().login(userName, passSHA, agentID);
        int status = loginResponse.getStatus();
        if (status == 0) {
            JAXBElement<String> sessionid = loginResponse.getSessionid();
            return sessionid.getValue();
//                    LoggingUtil.log(fm.format(new Date()) + " |Login to vinaphone with sessionid: " + WebParameter.vinaSessionValue, "useCard_transaction");
//                    LoggingUtil.log(fm.format(new Date()) + " |WebParameter.vinaSessionValue:" + WebParameter.vinaSessionValue, "useCard_transaction");
//                if (WebParameter.vinaSessionValue != null) {
//                        issuerService.updateSessionValue(ISSUER_NAME, WebParameter.vinaSessionValue);
//                }
        } else {
            System.out.println(dateFormat.format(new Date()) + " |Login false to vinaphone with message: " + loginResponse.getMessage().getValue());
//                    MailServiceUtil.mailAlert(ServiceUtil.mailSubject, ServiceUtil.getString("login_error_content").replace("telco", "VINAPHONE"));
            return null;
        }
    }

    public static LoadResponse loadNew(String sessionid, String maSoBiMat, String account, int counter, String cardSerial) throws Exception {
//        StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
//        prefixBuilder.append(prefix);
//        prefixBuilder.append("[loadNew - sessionId:");
//        prefixBuilder.append(sessionid);
//        prefixBuilder.append("][maSoBiMat:");
//        prefixBuilder.append(maSoBiMat);
//        prefixBuilder.append("][account:");
//        prefixBuilder.append(account);
//        prefixBuilder.append("][counter:");
//        prefixBuilder.append(counter);
//        prefixBuilder.append("][cardSerial:");
//        prefixBuilder.append(cardSerial);
//        prefixBuilder.append("]");
//        System.out.println(prefixBuilder.toString());
        TripleDESUtil.key = TripleDESUtil.createKey(sessionid);
        String agentMPIN = TripleDESUtil.encrypt(mPin);
        String maSoBiMatTripleDES = TripleDESUtil.encrypt(maSoBiMat);
        return getService().loadNew(userName, agentID, agentMPIN, account, maSoBiMatTripleDES, counter, cardSerial);
    }

    public static LoadResponse load(String sessionid, String maSoBiMat, String account, int counter) throws Exception {
        TripleDESUtil.key = TripleDESUtil.createKey(sessionid);
        String agentMPIN = TripleDESUtil.encrypt(mPin);
        String maSoBiMatTripleDES = TripleDESUtil.encrypt(maSoBiMat);
        return getService().load(userName, agentID, agentMPIN, account, maSoBiMatTripleDES, counter);
    }

    public static TodayEnquiryResponse todayEnquiry(String sessionid) throws Exception {
        TripleDESUtil.key = TripleDESUtil.createKey(sessionid);
        String mPinTripleDES = TripleDESUtil.encrypt(mPin);
        return getService().todayEnquiry(userName, agentID, mPinTripleDES);
    }

    public static ChangePasswordResponse changePassword(String newPass) throws Exception {
        String passSHA = encrypt(pass, "SHA");

        return getService().changePassword(userName, agentID, passSHA, newPass);
    }

    public static String encrypt(String strValue, String strAlgorithm)
            throws Exception {
        return encrypt(strValue.getBytes(), strAlgorithm);
    }

    public static String encrypt(byte[] btValue, String strAlgorithm)
            throws Exception {
        BASE64Encoder enc = new BASE64Encoder();
        MessageDigest md = MessageDigest.getInstance(strAlgorithm);
        return enc.encodeBuffer(md.digest(btValue));
    }

    public static void main(String[] args) {
        try {
//            String session = VinaphoneProxy.login();
//            System.out.println(session);
//            LoadResponse loadRes = VinaphoneProxy.loadNew(session, "58866297326502", "216411585141", 1657687879, "BCG141735");
//            System.out.println("serialNumber " + loadRes.getSSerialNumber().getValue());
//            System.out.println("transRefId " + loadRes.getTransid());
//            System.out.println("message " + loadRes.getMessage().getValue());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

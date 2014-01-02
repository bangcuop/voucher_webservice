/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import com.paydirect.voucher.exception.CardServiceException;
import com.paydirect.voucher.exception.ConnectWebserviceException;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.vss.cardservice.dto.Transaction;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Signature;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.xml.rpc.ServiceException;
import sandbox.axis.*;
import sun.misc.BASE64Encoder;

/**
 *
 * @author nghiapq
 */
public class FPTTelcoProxy {

    /**
     * @param args the command line arguments
     */
    private static Class issuerServiceUtil;
    private static Method getRandomGameAccountMethod;
    static KeyStore keyStore;
    static FileInputStream fileInputStream;
    static InputStream inputStream;
    static X509Certificate cert = null;
    static PrivateKey privateKey = null;
    private static String KEY_PATH;
    private static String SECRET_KEY;
    private static String KEY_PASS;
    private static String FUNCTION_CARD_INPUT = "CardInput";
    private static String FUNCTION_CHECK_TRAN = "CheckTran";
    private static String MERCHANTID;
    private static String URL;
//    private static String SERVICE_ID = "CardInputGate";
    private static Map<String, String> serviceIdMap;
    private static CP_PortType service;
//    private static String prefix = "[GateProxy]";
//    private static DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    static {
        try {
            KEY_PATH = "file" + File.separator + "FPT" + File.separator + "key";
//            KEY_PATH = "/home/liemnh/Desktop/Gate/JAVA/GateProxy/src/file/FPT/key";
            ResourceBundle configure = ResourceBundle.getBundle("wsconfig" + File.separator + "GATEProxy");
            SECRET_KEY = configure.getString("gate_secretKey").trim();
            KEY_PASS = configure.getString("gate_privateKey").trim();
            MERCHANTID = configure.getString("gate_merchantId").trim();
            URL = configure.getString("gate_url").trim();
            CP_ServiceLocator locator = new CP_ServiceLocator();
            service = locator.getCPPort();
            serviceIdMap = new HashMap<String, String>();
            try {
                String issuerSupport = configure.getString("fptTelco_serviceId");
                if (issuerSupport != null) {
                    String[] telcoList = issuerSupport.split("\\|");
                    for (int i = 0; i < telcoList.length; i++) {
                        String[] telco = telcoList[i].split("-");
                        if (telco.length == 2) {
                            serviceIdMap.put(telco[0], telco[1]);
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (ServiceException ex) {
            ex.printStackTrace();
            throw new ConnectWebserviceException();
        }

        try {
            URLClassLoader CLASSLOADER = (URLClassLoader) FPTTelcoProxy.class.getClassLoader();
            issuerServiceUtil = Class.forName("com.vss.cardservice.service.util.IssuerServiceUtil", true, CLASSLOADER);
            getRandomGameAccountMethod = issuerServiceUtil.getDeclaredMethod("getRandomGameAccount");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CardServiceException();
        }
    }

    public static String toXML(RequestData rq) {
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("RequestData", RequestData.class);
        return xstream.toXML(rq);
    }

    public static String Excute(String OriginData) throws Exception {
        String inputStr = OriginData + SECRET_KEY;
        keyStore = KeyStore.getInstance("PKCS12");
        inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(KEY_PATH);
        keyStore.load(inputStream, KEY_PASS.toCharArray());
        byte[] bOriginData = inputStr.getBytes("UTF-8");
        Enumeration en = keyStore.aliases();
        String alias = "";
        Vector vectaliases = new Vector();

        while (en.hasMoreElements()) {
            vectaliases.add(en.nextElement());
            String[] aliases = (String[]) (vectaliases.toArray(new String[0]));

            for (int i = 0; i < aliases.length; i++) {

                if (keyStore.isKeyEntry(aliases[i])) {
                    alias = aliases[i];
                    break;
                }
            }
        }
        cert = (X509Certificate) keyStore.getCertificate(alias);
        privateKey = (PrivateKey) keyStore.getKey(alias, KEY_PASS.toCharArray());
        Provider p = keyStore.getProvider();
        Signature sig = Signature.getInstance("SHA1withRSA", p);
        sig.initSign(privateKey);
        sig.update(bOriginData);
        byte[] bSignature = sig.sign();
        BASE64Encoder base64 = new BASE64Encoder();
        String signature = base64.encode(bSignature);
        return signature;
    }

    public Transaction useCard(Transaction tran) {
        try {
//            String target = "homedirect" + tran.getTransactionId();
            String target = (String) getRandomGameAccountMethod.invoke(issuerServiceUtil);
            tran.setAccountId(target);
            String serviceId = serviceIdMap.get(tran.getIssuer());
            String cardSerial = tran.getCardSerial();
            StringBuilder serialBuilder = new StringBuilder();
            if ("MOBI".equals(tran.getIssuer())) {
                if (cardSerial.length() != 15) {
                    for (int i = 0; i < (15 - cardSerial.length()); i++) {
                        serialBuilder.append("0");
                    }
                    serialBuilder.append(cardSerial);
                    cardSerial = serialBuilder.toString();
                }
            }
            String response = userCard(serviceId, cardSerial, tran.getCardCode(), tran.getTransactionId().toString(), target);
            if (response == null || response.isEmpty()) {
                tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            } else {
                tran.setUseCardResponse(response);
                String[] msg = response.split("\\|");
                tran.setResponseStatus(msg[0]);
                if (msg[0].equals("00")) {
                    tran.setAmount(msg[4]);
                    tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                    tran.setUseCardResponse("00" + "|" + msg[1] + "|" + msg[4]);
                } else {
                    tran.setUseCardResponse(msg[0] + "|" + msg[1]);
                    tran.setResponseStatus(processTelcoCardService(new Integer(msg[0])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
        }
        tran.setResponseTime(new Date());
        return tran;
    }

    public Transaction checkCard(Transaction tran) {
        try {
            String serviceId = serviceIdMap.get(tran.getIssuer());
//         response:   ErrorCode|Description|TransactionID |CardAmount|Time
            String response = checkTran(serviceId, tran.getTransactionId().toString());
            if (response == null || response.isEmpty()) {
                tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
            } else {
                tran.setUseCardResponse(response);
                String[] msg = response.split("\\|");
                tran.setResponseStatus(msg[0]);
                if (msg[0].equals("00")) {
                    tran.setAmount(msg[3]);
                    tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                    tran.setUseCardResponse("00" + "|" + msg[1] + "|" + msg[3]);
                } else {
                    tran.setUseCardResponse(msg[0] + "|" + msg[1]);
                    tran.setResponseStatus(processTelcoCardService(new Integer(msg[0])));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
        }
        tran.setResponseTime(new Date());
        return tran;
    }

    public Boolean checkConnection() {
        boolean result = true;
        try {
            new URL(URL).openConnection().connect();
        } catch (Exception ex) {
            System.out.println("GATEProxy.checkConnection.telco : ERROR");
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    public static String userCard(String serviceId, String cardSerial, String cardPin, String partnerTransactionID, String target) {
        String response = "";
        try {
//            StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
//            prefixBuilder.append(prefix);
//            prefixBuilder.append("[userCard - MerchantID:");
//            prefixBuilder.append(MERCHANTID);
//            prefixBuilder.append("][target:");
//            prefixBuilder.append(target);
//            prefixBuilder.append("][cardSerial:");
//            prefixBuilder.append(cardSerial);
//            prefixBuilder.append("[cardPin:");
//            prefixBuilder.append(cardPin);
//            prefixBuilder.append("][FUNCTION:");
//            prefixBuilder.append(FUNCTION);
//            prefixBuilder.append("]");
//            System.out.println(prefixBuilder.toString());
            //Thuc hien tao chu ky theo tai lieu
            RequestData rq = new RequestData();
            rq.MerchantID = new Integer(MERCHANTID);
            rq.Username = target;
            rq.CardSerial = cardSerial;
            rq.CardPIN = cardPin;
            rq.FunctionName = FUNCTION_CARD_INPUT;
            rq.PartnerTransactionID = partnerTransactionID;
            String originalData = String.format("%d%s%s%s", rq.MerchantID, rq.Username, rq.CardSerial, rq.CardPIN);
            String signature = Excute(originalData);
            rq.Signature = signature;
            String xmlRequestData = toXML(rq);
//            System.out.println("-----xmlRequestData: \n" + xmlRequestData);
            response = service.ProcessRequest(serviceId, xmlRequestData);
//            prefixBuilder.append("[result:");
//            prefixBuilder.append(response);
//            prefixBuilder.append("]");
//            System.out.println(prefixBuilder.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConnectWebserviceException();
        }
        return response;
    }

    public static String checkTran(String serviceId, String partnerTransactionID) {
        String response = "";
        try {
            //Thuc hien tao chu ky theo tai lieu
            RequestData rq = new RequestData();
            rq.MerchantID = new Integer(MERCHANTID);
            rq.FunctionName = FUNCTION_CHECK_TRAN;
            rq.PartnerTransactionID = partnerTransactionID;
            String originalData = String.format("%d%s", rq.MerchantID, rq.PartnerTransactionID);
            String signature = Excute(originalData);
            rq.Signature = signature;
            String xmlRequestData = toXML(rq);
//            System.out.println("-----xmlRequestData: \n" + xmlRequestData);
            response = service.ProcessRequest(serviceId, xmlRequestData);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ConnectWebserviceException();
        }
        return response;
    }

    private String processTelcoCardService(Integer status) {
        if (status == null) {
            return WebParameter.GIAO_DICH_NGHI_VAN;
        }
        switch (status.intValue()) {
            case 1:
                return WebParameter.LOI_KET_NOI_PROVIDER;
            case 2:
                return WebParameter.LOI_KET_NOI_PROVIDER;
            case 3:
                return WebParameter.LOI_KET_NOI_PROVIDER;
            case 4:
                return WebParameter.LOI_KET_NOI_PROVIDER;
            case 5:
                return WebParameter.LOI_KET_NOI_PROVIDER;
            case 6:
                return WebParameter.LOI_KET_NOI_PROVIDER;
            case 7:
                return WebParameter.SERIAL_KHONG_HOP_LE;
            case 8:
                return WebParameter.THE_KHONG_TON_TAI;
            case 15:
                return WebParameter.THE_DA_DUOC_SU_DUNG;
            case 16:
                return WebParameter.THE_CHUA_DUOC_KICH_HOAT;
            case 17:
                return WebParameter.THE_KHONG_DUNG_DINH_DANG;
            case 18:
                return WebParameter.PARTNER_BI_KHOA;
            case 19:
                return WebParameter.THE_HET_HAN_SU_DUNG;
            case 20:
                return WebParameter.SAI_CHU_KY;
            case 60:
                return WebParameter.MA_GIAO_DICH_KHONG_TON_TAI;
            case 62:
                return WebParameter.MA_GIAO_DICH_KHONG_TON_TAI;
            case 81:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 82:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 83:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 84:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 85:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 86:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 87:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 97:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 98:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 99:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 100:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 101:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case 995:
                return WebParameter.LOI_GOI_HAM_PROVIDER;
            case -1:
                return WebParameter.THE_KHONG_DUNG_DINH_DANG;
            default:
                return WebParameter.LOI_KHONG_XAC_DINH;
        }
    }

    public static void main(String[] args) throws Exception {
//        String username = "homedirect";
//        String cardSerial = "TD00000000";
//        String cardPIN = "1111111111";
//        DateFormat dateFormat_ = new SimpleDateFormat("ddMMyyyyHHmmss");
//        String partnerTransactionID = dateFormat_.format(new Date());
//        String result = userCard("GATE", cardSerial, cardPIN, partnerTransactionID, username);
        System.out.println("url:" + FPTTelcoProxy.class.getResource(File.separator + "file" + File.separator + "FPT" + File.separator + "key"));

    }
}

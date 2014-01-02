package com.paydirect.voucher.proxy;

import com.vss.cardservice.dto.Transaction;
import com.vss.clientws.dto.Param;
import com.vss.clientws.util.CallService;
import java.util.Iterator;
import java.util.Map;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import com.paydirect.voucher.exception.MobiServiceException;
import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MobifoneProxy {

    private static String sessionValue;
    private static final String partner_id = "partner_id";
    private static final String transaction_id = "transaction_id";
    private static final String sessionID = "sessionID";
    private static final String cardID = "cardID";
    private static final String cardCode = "cardCode";
    private static final String cardSerial = "cardSerial";
    private static final String login = "login";
    private static final String password = "password";
    private static final String mobiKey;
    private static String partner_id_value;
    private static String login_value;
    private static String password_value;
    private static String wsdl;
    private static String prefix = "[MobifoneProxy]";
    private static final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private boolean isFirstTry = true;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle("wsconfig" + File.separator + "MobifoneProxy");
        mobiKey = bundle.getString("mobi_key").trim();
        partner_id_value = decrypt(bundle.getString("mobi_partnerId").trim());
        login_value = bundle.getString("mobi_login").trim();
        password_value = bundle.getString("mobi_password").trim();
        wsdl = bundle.getString("mobi_wsdl").trim();
    }

    private static String getSessionValue() {
        if (sessionValue == null) {
            login();
        }
        return sessionValue;
    }

    public Transaction useCard(Transaction tran) {
        try {
            String responseDescription = null, responseStatus = null;
            getSessionValue();

            if (sessionValue == null) {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
                return tran;
            }
            Map<String, String> map = useCard(tran.getTransactionId().toString(), sessionValue, tran.getCardCode(), tran.getCardSerial());

            if (map != null && !map.isEmpty()) {
                tran.setUseCardResponse(map.toString());
                tran.setResponseTime(new Date());
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    if (key.indexOf("description") > -1) {
                        responseDescription = map.get(key);
                    }
                    if (key.indexOf("response") > -1) {
                        responseStatus = map.get(key);
                    }
                }
                if (responseStatus == null || responseStatus.equals("")
                        || responseStatus.equals("-1") || responseStatus.equals("-2")
                        || responseStatus.equals("-3") || responseStatus.equals("-4")
                        || responseStatus.equals("-10") || responseStatus.equals("-12")) {
                    if (responseStatus.equals("-4")) {
                        tran.setResponseStatus(WebParameter.THE_CHUA_DUOC_KICH_HOAT);
                    } else if (responseStatus.equals("-1")) {
                        tran.setResponseStatus(WebParameter.THE_DA_DUOC_SU_DUNG);
                    } else {
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                    }
                } else if ((responseStatus.equals("-99") || responseStatus.equals("0")) && responseDescription != null && responseDescription.indexOf("Invalid session id") > -1) {
                    sessionValue = null;
                    if (isFirstTry) {
                        return useCard(tran);
                    } else {
                        tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
                    }
                } else if (responseStatus.equals("1") && responseDescription != null && responseDescription.indexOf(":") > -1) {
                    tran.setAmount(responseDescription.split(":")[1]);
                    tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                } else {
                    tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                }
            } else {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
        }
        return tran;
    }

    public Boolean checkConnection() {
        boolean result = true;
        try {
            new URL(wsdl).openConnection().connect();
        } catch (Exception ex) {
            System.out.println("MobifoneProxy.checkConnection : ERROR");
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    public static Map<String, String> useCard(String transactionid, String sessionId, String cardId, String _cardSerial) {
        try {
            CallService callService = new CallService(wsdl);
            Map map =
                    callService.getOutputParams("userCard", "java.lang.Object",
                    new Param[]{
                        new Param(partner_id, XMLType.XSD_STRING, ParameterMode.IN, partner_id_value, false),
                        new Param(transaction_id, XMLType.XSD_STRING, ParameterMode.IN, encrypt(transactionid), false),
                        new Param(sessionID, XMLType.XSD_STRING, ParameterMode.IN, encrypt(sessionId), false),
                        new Param(cardID, XMLType.XSD_STRING, ParameterMode.IN, encrypt(cardId), false),
                        new Param(cardCode, XMLType.XSD_STRING, ParameterMode.IN, encrypt(cardCode), false),
                        new Param(cardSerial, XMLType.XSD_STRING, ParameterMode.IN, encrypt(_cardSerial), false)
                    });
            Map<String, String> resultMap = new HashMap<String, String>();
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object value = map.get(key);
                if (value != null) {
//                    System.out.println(key + " _ " + decrypt(value.toString()));
                    resultMap.put(key.toString(), decrypt(value.toString()));
                }
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MobiServiceException();
        }
    }

    public static String login() {
        Map map;
        Iterator iterator;
        if (sessionValue == null) {
            try {
                StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
                prefixBuilder.append(prefix);
                prefixBuilder.append("[login : ");
                prefixBuilder.append(wsdl);
                prefixBuilder.append(" ]");
                System.out.println(prefixBuilder.toString());
                CallService callService = new CallService(wsdl);
                map = callService.getOutputParams("login", "java.lang.Object",
                        new Param[]{
                            new Param(partner_id, XMLType.XSD_STRING, ParameterMode.IN, partner_id_value, false),
                            new Param(login, XMLType.XSD_STRING, ParameterMode.IN, login_value, false),
                            new Param(password, XMLType.XSD_STRING, ParameterMode.IN, password_value, false)});
                iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    Object value = map.get(key);
                    if (value != null) {
                        System.out.println(key + " _ " + value);
                        if (key.toString().indexOf("session") > -1) {
                            sessionValue = decrypt(value.toString());
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new MobiServiceException(e);
            }
        }
        return sessionValue;
    }

    public static String keepSession() {
        if (sessionValue == null) {
            System.out.println("MobiSession = null -> login() ");
            return login();
        } else {
            System.out.println("Mobisession != null -> keep " + sessionValue);
            try {
                CallService callService = new CallService(wsdl);
                Map map =
                        callService.getOutputParams("keepAlive", "java.lang.Object",
                        new Param[]{
                            new Param(partner_id, XMLType.XSD_STRING, ParameterMode.IN, partner_id_value, false),
                            new Param(sessionID, XMLType.XSD_STRING, ParameterMode.IN, encrypt(sessionValue), false)
                        });
                Iterator iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    Object value = map.get(key);
                    if (value != null) {
                        String decryptValue = decrypt(value.toString());
                        System.out.println(key + " _ " + value);
                        if (key.toString().equals("responseStatus") && decryptValue.equals("1")) {
                            return sessionValue;
                        }
                    }
                }
                sessionValue = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public static Map logout(String sessionId) {
        try {
            StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
            prefixBuilder.append(prefix);
            prefixBuilder.append("[logout - sessionID:");
            prefixBuilder.append(sessionValue);
            prefixBuilder.append("]");
            System.out.println(prefixBuilder.toString());
            CallService callService = new CallService(wsdl);
            Map map =
                    callService.getOutputParams("logout", "java.lang.Object",
                    new Param[]{new Param(partner_id, XMLType.XSD_STRING, ParameterMode.IN, partner_id_value, false),
                        new Param(sessionID, XMLType.XSD_STRING, ParameterMode.IN, encrypt(sessionId), false)});
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object value = map.get(key);
                if (value != null) {
                    System.out.println(key + " _ " + decrypt(value.toString()));
                    map.put(key, decrypt(value.toString()));
                }
            }
            return map;
        } catch (Exception e) {
            throw new MobiServiceException();
        }
    }

    public static Map checkCard(String sessionid, String cardid) {
        try {
            StringBuilder prefixBuilder = new StringBuilder(dateFormat.format(new Date()));
            prefixBuilder.append(prefix);
            prefixBuilder.append("[checkCard - sessionID:");
            prefixBuilder.append(sessionID);
            prefixBuilder.append("][cardID:");
            prefixBuilder.append(cardid);
            prefixBuilder.append("]");
            System.out.println(prefixBuilder.toString());
            CallService callService = new CallService(wsdl);
            Map map =
                    callService.getOutputParams("checkCard", "java.lang.Object",
                    new Param[]{
                        new Param(partner_id, XMLType.XSD_STRING, ParameterMode.IN, partner_id_value, false),
                        new Param(sessionID, XMLType.XSD_STRING, ParameterMode.IN, sessionid, true),
                        new Param(cardID, XMLType.XSD_STRING, ParameterMode.IN, cardid, true),
                        new Param(cardCode, XMLType.XSD_STRING, ParameterMode.IN, cardCode, true),});
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                Object key = iterator.next();
                Object value = map.get(key);
                if (value != null) {
                    System.out.println(key + " _ " + decrypt(value.toString()));
                    map.put(key, decrypt(value.toString()));
                }
            }
            return map;
        } catch (Exception e) {
            throw new MobiServiceException(e);
        }
    }

    /**
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        try {
            return encrypt(mobiKey, data);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String data) {
        try {
            return decrypt(mobiKey, data);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String encrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("TripleDES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes(), 0, key.length());
        String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(
                0, 24);
        SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
        cipher.init(Cipher.ENCRYPT_MODE, keyspec);
        byte[] stringBytes = data.getBytes();
        byte[] raw = cipher.doFinal(stringBytes);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(raw);
    }

    /**
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    public static String decrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("TripleDES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes(), 0, key.length());
        String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(0, 24);
        SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
        cipher.init(Cipher.DECRYPT_MODE, keyspec);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] raw = decoder.decodeBuffer(data);
        byte[] stringBytes = cipher.doFinal(raw);
        return new String(stringBytes);
    }

    public static void main(String[] args) {
        try {
            String code = "993233235741";
            String serial = "012182000029925";
            System.out.println("MobiCardProxy.main : cardcode = " + code + " , cardserial = " + serial);
            String sessionId = login();

            System.out.println("SessionId = " + sessionId);
            useCard("test" + new Date().getTime(), sessionId, code, serial);
            logout(sessionId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

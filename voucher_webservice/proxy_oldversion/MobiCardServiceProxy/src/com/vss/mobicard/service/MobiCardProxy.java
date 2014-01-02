package com.vss.mobicard.service;

import com.vss.clientws.dto.Param;
import com.vss.clientws.util.CallService;
import java.util.Iterator;
import java.util.Map;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.encoding.XMLType;

import com.vss.mobicard.exception.MobiServiceException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class MobiCardProxy {

    static ResourceBundle bundle = ResourceBundle.getBundle("WSConfig");
//    private static CallService callService;
    static final String partner_id = "partner_id";
    static final String transaction_id = "transaction_id";
    static final String sessionID = "sessionID";
    static final String cardID = "cardID";
    static final String cardCode = "cardCode";
    static final String cardSerial = "cardSerial";
    static final String login = "login";
    static final String password = "password";
    static final String key_value = bundle.getString("mobi-key");
    static String partner_id_value = decrypt(bundle.getString("mobi-partner_id"));
    static String login_value = bundle.getString("mobi-login");
    static String password_value = bundle.getString("mobi-password");
    static String ws_url = bundle.getString("ws_url");

//    static {
//        callService = new CallService(bundle.getString("ws_url"));
//    }
    /**
     * <ul>request
     * <li>partnerId (use)</li>
     * <li>login (use)</li>
     * <li>password (use)</li>
     * </ul><ul>response
     * <li>sessionId</li>
     * <li>responseStatus</li>
     * <li>description</li></ul>
     */
    public static Map login() {
        try {
//            System.out.println("********** login : ");
//            System.out.println(bundle.getString("mobi-partner_id") + " _ " + partner_id_value);
//            System.out.println(bundle.getString("mobi-login") + " _ " + login_value);
//            System.out.println(bundle.getString("mobi-password") + " _ " + password_value);
            CallService callService = new CallService(ws_url);
            Map map = callService.getOutputParams("login", "java.lang.Object",
                    new Param[]{
                        new Param(partner_id, XMLType.XSD_STRING, ParameterMode.IN, partner_id_value, false),
                        new Param(login, XMLType.XSD_STRING, ParameterMode.IN, login_value, false),
                        new Param(password, XMLType.XSD_STRING, ParameterMode.IN, password_value, false)});
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
            e.printStackTrace();
            throw new MobiServiceException(e);
        }
    }

    /**
     * <ul>request
     * <li>partner_id (use)</li>
     * <li>sessionId (use)</li>
     * </ul><ul>response
     * <li>description</li>
     * <li>responseStatus</li></ul>
     */
    public static Map logout(String sessionId) {
        try {
            CallService callService = new CallService(ws_url);
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

    /**
     * <ul>request
     * <li>partner_id (not-use)</li>
     * <li>sessionId (use)</li>
     * <li>cardId (use)</li>
     * <li>cardCode (use)</li>
     * </ul><ul>response
     * <li>sessionId</li>
     * <li>responseStatus</li>
     * <li>description</li></ul>
     */
    public static Map checkCard(String sessionid, String cardid) {
        try {
            CallService callService = new CallService(ws_url);
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
     * <ul>request
     * <li>partner_id (not-use)</li>
     * <li>transaction_id (use)</li>
     * <li>sessionId (use)</li>
     * <li>cardId (use)</li>
     * <li>cardCode (not-use)</li>
     * </ul><ul>response
     * <li>sessionId</li>
     * <li>responseStatus</li>
     * <li>description </li></ul>
     */
    public static Map useCard(String transactionid, String sessionId, String cardId, String _cardSerial) {
        try {
            CallService callService = new CallService(ws_url);
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
            e.printStackTrace();
            throw new MobiServiceException();
        }
    }

    public static Map keepAlive(String sessionId) {
        try {
            System.out.println("---- MobiCardProxy.keepAlive");
            CallService callService = new CallService(ws_url);
            Map map =
                    callService.getOutputParams("keepAlive", "java.lang.Object",
                    new Param[]{
                        new Param(partner_id, XMLType.XSD_STRING, ParameterMode.IN, partner_id_value, false),
                        new Param(sessionID, XMLType.XSD_STRING, ParameterMode.IN, encrypt(sessionId), false)
                    });
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
            e.printStackTrace();
            throw new MobiServiceException();
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
            return encrypt(key_value, data);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public static String decrypt(String data) {
        try {
            return decrypt(key_value, data);
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

    /**
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static void main(String[] args) {
        try {
        String code = args[0];
        String serial = args[1];
        System.out.println("MobiCardProxy.main : cardcode = "+code+ " , cardserial = "+serial);
        Map<Object,String> map = login();
        String sessionId = null;
        Iterator iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    if (key.toString().indexOf("session") > -1) {
                        sessionId = map.get(key);
                    }
                }
        
        System.out.println("SessionId = "+sessionId);
        useCard("test" + new Date().getTime(), sessionId, args[0], args[1]);
        logout(sessionId);

//        String a = "test";
//            a = encrypt(a);
//            System.out.println(a);
//            System.out.println(decrypt(a));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

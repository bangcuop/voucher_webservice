/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vnptepay.proxy;

import com.hdpay.vnptepay.exception.CardSerialNotNullException;
import com.hdpay.vnptepay.exception.VNPTEPAYEncryptException;
import com.hdpay.vnptepay.exception.VNPTEPAYServiceException;
import com.hdpay.vnptepay.util.MD5;
import com.hdpay.vnptepay.util.RSAEncryption;
import com.hdpay.vnptepay.util.TripleDESEncryption;
import com.vss.clientws.dto.ChargeReponse;
import com.vss.clientws.dto.LoginResponse;
import com.vss.clientws.dto.MessageResponse;
import com.vss.clientws.dto.Param;
import com.vss.clientws.util.CallService;
import com.vss.clientws.util.ClassTypeMapping;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.rpc.ParameterMode;
import org.apache.axis.encoding.XMLType;

/**
 *
 * @author zannami
 */
public class vnptepayProxy {

    public static String VTC_COIN = "VTC";
    public static String FPT = "FPT";
    public static String VINAPHONE = "VNP";
    public static String MOBIPHONE = "VMS";

    private static CallService getService() {
        CallService service = new CallService();
        service.setEndPointAddress(CallService.getString("Webservice"));
        service.setRequiredLogin(false);
        service.setCallService(service.getCallService());
        return service;
    }
//    static CallService service = new CallService();
//
//    static {
//        service.setEndPointAddress(service.getString("Webservice"));
//        service.setRequiredLogin(false);
//        service.setCallService(service.getCallService());
//    }

    /**
     * 
     * @param md5Session
     * @return
     */
    public static MessageResponse logout(String sessionId) {
        CallService service = getService();
        try {
            Param[] p = new Param[]{
                new Param("username", null, ParameterMode.IN, CallService.getString("User"), false),
                new Param("partnerID", null, ParameterMode.IN, CallService.getString("Partner"), false),
                new Param("Md5sessionID", null, ParameterMode.IN, MD5.hash(sessionId), false)};
            ClassTypeMapping typeMapping = new ClassTypeMapping();
            typeMapping.setCls(MessageResponse.class);
            typeMapping.setLocalPart("MessageResponse");
            typeMapping.setNameSpaceURI("http://interfaces");
            service.registerTypeMapping(typeMapping);
            MessageResponse res = (MessageResponse) service.invokeMethod("logout", "com.vss.clientws.dto.MessageResponse", p);
//            MessageResponse res = (MessageResponse) service.invokeMethod("login", "com.vss.clientws.dto.MessageResponse", p);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new VNPTEPAYServiceException();
        }
    }

    /**
     * 
     * @return
     */
    public static LoginResponse login() {
        CallService service = getService();
        System.out.println("------------------->>>  vao login");
        System.out.println("------------------->>>  User:" + CallService.getString("User") );
        System.out.println("------------------->>>  Pass:" + CallService.getString("Pass") );
        System.out.println("------------------->>>  Partner:" + CallService.getString("Partner"));
        Param[] p = new Param[]{
            new Param("username", XMLType.XSD_STRING, ParameterMode.IN, CallService.getString("User"), false),
            new Param("password", XMLType.XSD_STRING, ParameterMode.IN, encrypt(CallService.getString("Pass")), false),
            new Param("partnerID", XMLType.XSD_STRING, ParameterMode.IN, CallService.getString("Partner"), false)};
        try {
            ClassTypeMapping typeMapping = new ClassTypeMapping();
            //Can 3 thuoc tinh
            //Doi tuong can merg: Da khai bao, extend MessageResponse, va co cac thuoc tinh giong ong tra lai
            //LocalPart:
            typeMapping.setCls(LoginResponse.class);
            typeMapping.setLocalPart("LoginResponse");
            typeMapping.setNameSpaceURI("http://interfaces");
            service.registerTypeMapping(typeMapping);
            LoginResponse res = (LoginResponse) service.invokeMethod("login", "com.vss.clientws.dto.LoginResponse", p);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new VNPTEPAYServiceException();
        }
    }

    public static ChargeReponse getTransactionStatus(String transId, String sessionId) {
        CallService service = getService();
        try {
            String partnerId = CallService.getString("Partner");
            String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
            transId = partnerId.toLowerCase() + "_" + date + "_" + transId;
            Param[] p = new Param[]{
                new Param("transid", XMLType.XSD_STRING, ParameterMode.IN, transId, false),
                new Param("username", XMLType.XSD_STRING, ParameterMode.IN, CallService.getString("User"), false),
                new Param("partnerID", XMLType.XSD_STRING, ParameterMode.IN, partnerId, false),
                new Param("Md5sessionID", XMLType.XSD_STRING, ParameterMode.IN, sessionId, false)};
            ClassTypeMapping typeMapping = new ClassTypeMapping();
            typeMapping.setCls(ChargeReponse.class);
            typeMapping.setLocalPart("ChangeResponse");
            typeMapping.setNameSpaceURI("http://interfaces");
            service.registerTypeMapping(typeMapping);
            ChargeReponse res = (ChargeReponse) service.invokeMethod("getTransactionStatus", "com.vss.clientws.dto.ChargeReponse", p);
            if (res.getStatus().equalsIgnoreCase("1")) {
                res.setResponseamount(TripleDESEncryption.decrypt(sessionId, res.getResponseamount()));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new VNPTEPAYServiceException();
        }
    }

    /**
     * 
     * @param issuer
     * @param cardSerial
     * @param cardCode
     * @param transId
     * @param target
     * @param md5Session
     */
    public static ChargeReponse cardCharging(String issuer, String cardSerial, String cardCode, String transId, String target, String sessionId, boolean fullDes) {
        /**
         * <wsdl:part name="transid" type="xsd:string">
        <wsdl:part name="username" type="xsd:string">
        <wsdl:part name="partnerID" type="xsd:string">
        <wsdl:part name="mpin" type="xsd:string">
        <wsdl:part name="target" type="xsd:string">
        <wsdl:part name="card_data" type="xsd:string">
         * mã thẻ:mã nhà cung cấp.
         * Seri của thẻ: mã thẻ: mệnh giá: mã nhà cung cấp.
        <wsdl:part name="md5sessionid" type="xsd:string">
         */
        if (issuer.equals(VTC_COIN) || issuer.equals(FPT)) {
            if (cardSerial == null || cardSerial.isEmpty()) {
                throw new CardSerialNotNullException();
            }
        }
        String cardData = "";
        if (fullDes) {
            cardData = cardSerial + ":" + cardCode + "::" + issuer;
        } else {
            cardData = cardCode + ":" + issuer;
        }
        try {
            CallService service = getService();
            String mpin = CallService.getString("Mpin");
//            String mpin = "aaaaa";
            String partnerId = CallService.getString("Partner");
            mpin = TripleDESEncryption.encrypt(sessionId, mpin);
            cardData = TripleDESEncryption.encrypt(sessionId, cardData);
            String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
            transId = partnerId.toLowerCase() + "_" + date + "_" + transId;
            Param[] p = new Param[]{
                new Param("transid", XMLType.XSD_STRING, ParameterMode.IN, transId, false),
                new Param("username", XMLType.XSD_STRING, ParameterMode.IN, CallService.getString("User"), false),
                new Param("partnerID", XMLType.XSD_STRING, ParameterMode.IN, partnerId, false),
                new Param("mpin", XMLType.XSD_STRING, ParameterMode.IN, mpin, false),
                new Param("target", XMLType.XSD_STRING, ParameterMode.IN, target, false),
                new Param("card_data", XMLType.XSD_STRING, ParameterMode.IN, cardData, false),
                new Param("Md5sessionID", XMLType.XSD_STRING, ParameterMode.IN, MD5.hash(sessionId), false)};
            ClassTypeMapping typeMapping = new ClassTypeMapping();
            typeMapping.setCls(ChargeReponse.class);
            typeMapping.setLocalPart("ChangeResponse");
            typeMapping.setNameSpaceURI("http://interfaces");
            service.registerTypeMapping(typeMapping);
            ChargeReponse res = (ChargeReponse) service.invokeMethod("cardCharging", "com.vss.clientws.dto.ChargeReponse", p);
//            ChargeReponse res = (ChargeReponse) service.invokeMethod("cardCharging", "com.hdpay.vnptepay.dto.ChargeReponse", p);
            if (res.getStatus().equalsIgnoreCase("1")) {
                res.setResponseamount(TripleDESEncryption.decrypt(sessionId, res.getResponseamount()));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            throw new VNPTEPAYServiceException();
        }
    }

    public static void main(String[] args) {
//        service.setEndPointAddress(service.getString("Webservice"));
//        service.setRequiredLogin(false);
//        service.setCallService(service.getCallService());


        try {
//            Param[] p = new Param[]{
//                new Param("username", null, ParameterMode.IN, service.getString("User"), false),
//                new Param("password", null, ParameterMode.IN, encrypt(service.getString("Pass")), false),
//                new Param("partnerID", null, ParameterMode.IN, service.getString("Partner"), false)};
//
//            ClassTypeMapping typeMapping = new ClassTypeMapping();
//            typeMapping.setCls(LoginResponse.class);
//            typeMapping.setLocalPart("LoginResponse");
//            typeMapping.setNameSpaceURI("http://interfaces");
//            service.registerTypeMapping(typeMapping);
//            Map map = service.invokeMethod("login", "com.hdpay.vnptepay.dto.LoginResponse", p);
//            Iterator iterator = map.keySet().iterator();
//            while (iterator.hasNext()) {
//                Object key = iterator.next();
//                System.out.print(key.toString() + "==" + map.get(key));
//            }
            LoginResponse res = login();
//            System.out.println(res != null ? res.getStatus() + "|" + res.getMessage() + "|" + RSADecrypt(res.getSessionid()) : "cannot convert");
//            String session = MD5.hash("a69d459f172b532dcde758ccf2777cdbbad9214de45e53e5");

            String session = res.getSessionid();
            System.out.println("----------------------------->>> session:" + session);
//            String session =  "a69d459f172b532dcde758ccf2777cdbbad9214de45e53e5";
           session = RSADecrypt(res.getSessionid());
            ChargeReponse msg = cardCharging(VINAPHONE, "12345678912", "123456789", "2", "abcdef", session, false);

            System.out.println(msg.getStatus() + "|" + msg.getMessage() + "|" + msg.getTransid() + "|" + msg.getResponseamount());
//            MessageResponse msg = logout(session);
//            System.out.println(msg.getStatus() + "|" + msg.getMessage());
//            System.out.println(res.getSessionid());
//            System.out.println(MD5.hash("a69d459f172b532dcde758ccf2777cdbbad9214de45e53e5"));
//            ChargeReponse msg = getTransactionStatus("6", MD5.hash(session));
//            System.out.println(msg != null ? msg.getStatus() + "|" + msg.getMessage() + "|" + msg.getTransid() : "cannot convert");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String RSADecrypt(String sessionid) {
        try {
            RSAEncryption rsa = new RSAEncryption();
            rsa.setPrivateKeyStr("MIICXAIBAAKBgQDFP9IRgVsnXO7SlA4aZkHoY96mG6/zo8DQ6dMEi5SiWpxusNljqjneOEk8gRMOL0Amxleq8TT5p0urY06ViR3LLt9N2fu1UZFzKS6Jj2sL9UfadCwK5Nm5Ij6URljFBuML3sZRHBl9oVaxjzz7tUbGK7Mz/VB/rN7+OMWWn/zphQIDAQABAoGAZg0d70ag1Z3ERhipr0jmoohBImnCv+giEzRWo8xfixwcQTyoTGM+61TttysEo9sYcrc9lkiTYvMGOFb5ym4VhihQ8L/ANu01uPmcKTupMzJ/AM//EiDdwIcG4fGFCb+Cf6QMOUTROqshGGm4iDpJp4hwfEkVCyAITQsjYIN11wECQQDmnsU1UPawIP4OmIcCTzhuWGcpyGjbpAeK77kGLalwwtW4Buc6rnn+913BjglJVNcaRHhq62TRRU6Nephg8J1FAkEA2vTlkG7eFqoKVf0lH9F5bUs4E0/2zv36lEUSYNCkEJYDl43h/oMgHEMrSJ0qvStmFDvFmBPhOFWk+4JaU/0/QQJBAOQmYEztqw71pMFFzhLP0rD/jhe851ebP8cIf5AILl+asJodYjYglBpUGBG69Z7xatfWsxZ7h8KsgRjgFs2YXM0CQCuEGo/fCPKWLuGyIwu01R48odNsxA+VeJS5OZLWRJUrS2F2iGDN7LbRPqv62ICqPGpvCrFT2iIZG2YFHTCuF0ECQCZdRotxAOhET1HNPfxm7aO25vGZkKGTO0DQdfhjHzZY/lkJou3ubmxMUyx7P3DB7h+Ghc/FRt4ewuj+6vGj0fA=");
//            rsa.setPrivateKeyStr(CallService.getString("privateKey"));
            rsa.setPadding(CallService.getString("padding"));
            return rsa.decrypt(sessionid);
        } catch (Exception e) {
            e.printStackTrace();
            throw new VNPTEPAYEncryptException();
        }
    }

    private static String encrypt(String password) {
        RSAEncryption encryptmethod = new RSAEncryption();
        encryptmethod.setPadding(CallService.getString("padding"));
        String publickey = CallService.getString("publickey");
        try {
            encryptmethod.setPublicKeyStr(publickey);
            return encryptmethod.encrypt(password);
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        throw new VNPTEPAYEncryptException();
    }
}


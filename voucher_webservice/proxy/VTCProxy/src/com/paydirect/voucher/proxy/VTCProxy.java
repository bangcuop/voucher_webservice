/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import com.hdpay.vtc.dto.CardRequest;
import com.hdpay.vtc.dto.RequestResponse;
import com.hdpay.vtc.exception.DecryptException;
import com.hdpay.vtc.exception.VTCServiceException;
import com.thoughtworks.xstream.XStream;
import com.vss.cardservice.dto.Transaction;
import java.io.File;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.MessageDigest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import webapi.card.vtconline.Card;
import webapi.card.vtconline.CardSoap;

/**
 *
 * @author zannami
 */
public class VTCProxy {

    public static ResourceBundle configure = ResourceBundle.getBundle("wsconfig" + File.separator + "VTCProxy");
    private static Class issuerServiceUtil;
    private static Method getRandomGameAccountMethod;
    private static Map<String, String> issuerMap;
    public static final String VCOIN = "VCOIN";
    public static final String USECARD = "UseCard";
    public CardSoap soap;
    public String wsURL;
    public String partnerKey;
    public String partnerId;
    private static VTCVcoinProxy vtcVcoinProxy;
    private static VTCTelcoProxy vtcTelcoProxy;
    public static Map<String, Boolean> checkConnectionMap = new HashMap<String, Boolean>();

    static {

        issuerMap = new HashMap<String, String>();
        try {
            String issuerSupport = configure.getString("VTC_telcoCode");
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
            URLClassLoader CLASSLOADER = (URLClassLoader) VTCProxy.class.getClassLoader();
            issuerServiceUtil = Class.forName("com.vss.cardservice.service.util.IssuerServiceUtil", true, CLASSLOADER);
            getRandomGameAccountMethod = issuerServiceUtil.getDeclaredMethod("getRandomGameAccount");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        RequestResponse response = useCard(wsURL, partnerKey, partnerId, "174863592046", "PM0000020100", "test homedirect");
//        System.out.println(response.getResponseStatus());
//        System.out.println(response.getDescription());
//    }
    private Transaction useVCoinCard(Transaction tran) {
        String cardSerial = tran.getCardSerial();
        if (cardSerial == null || !cardSerial.toUpperCase().startsWith("PM")) {
            tran.setResponseStatus(WebParameter.SERIAL_KHONG_HOP_LE);
        } else {
            String account = tran.getAccountId();
            RequestResponse response = null;
            try {
                response = getvtcVcoinProxy().checkCardStatus(cardSerial, account);
            } catch (DecryptException e) {
                tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                tran.setUseCardResponse("Check Card Status Response : " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
            }
            if (response != null) {
                tran = getResponseStatus(response, tran);
                if (tran.getResponseStatus().equals(WebParameter.GIAO_DICH_THANH_CONG)) {
                    try {
                        response = getvtcVcoinProxy().useCard(tran.getCardCode(), cardSerial, account);
                        tran.setResponseTime(new Date());
                        tran = getResponseStatus(response, tran);
                    } catch (DecryptException e) {
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        tran.setUseCardResponse("Use Card Response : " + e.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
                        tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
                    }
                }
            }
        }
        return tran;
    }

    public Transaction useCard(Transaction tran) {

        String account;
        try {
            account = (String) getRandomGameAccountMethod.invoke(issuerServiceUtil);
        } catch (Exception ex) {
            ex.printStackTrace();
            account = String.valueOf(tran.getTransactionId());
        }
        tran.setAccountId(account);
        String issuer = tran.getIssuer().toUpperCase();
        if (issuer.equals(VCOIN)) {
            return useVCoinCard(tran);
        } else {
            String provider = issuerMap.get(issuer);
            if (provider == null) {
                tran.setResponseStatus(WebParameter.MA_DICH_VU_KHONG_HOP_LE);
            } else {
                RequestResponse response = null;
                try {
                    response = getVtcTelcoProxy().useCard(provider, tran.getCardSerial(), tran.getCardCode(), Long.valueOf(tran.getTransactionId()), account);
                    tran = getResponseStatus(response, tran);
                } catch (DecryptException e) {
                    tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                    tran.setUseCardResponse("Use Card Response : " + e.getMessage());
                } catch (Exception e) {
                    tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
                }
            }
            return tran;

        }
    }

    public Boolean checkConnection() {
        checkConnectionMap.clear();
        CheckConnectionThread t1 = new CheckConnectionThread(getVtcTelcoProxy().wsURL);
        CheckConnectionThread t2 = new CheckConnectionThread(getvtcVcoinProxy().wsURL);
        t1.start();
        t2.start();
        int count = 0;
        try {
            while (checkConnectionMap.size() < 2 && count++ < 15) {
                Thread.sleep(1000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        t1.interrupt();
        t2.interrupt();
        boolean result = true;
        Boolean telcoResult = checkConnectionMap.get(getVtcTelcoProxy().wsURL);
        Boolean vcoinResult = checkConnectionMap.get(getvtcVcoinProxy().wsURL);
        if (telcoResult == null || !telcoResult) {
            System.out.println("VTCProxy.checkConnection : Error Telco");
            result = false;
        }
        if (vcoinResult == null || !vcoinResult) {
            System.out.println("VTCProxy.checkConnection : Error VCoin");
            result = false;
        }
        return result;
    }
//    public Transaction checkCard(Transaction tran) {
//        String cardSerial = tran.getCardSerial();
//        if (cardSerial == null || !cardSerial.toUpperCase().startsWith("PM")) {
//            tran.setResponseStatus(WebParameter.SERIAL_KHONG_HOP_LE);
//        } else {
//            String account = tran.getAccountId();
//            RequestResponse request = null;
//            try {
//                request = vtcVcoinProxy.checkCardStatus(cardSerial, account);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (request != null) {
//                tran = getResponseStatus(request, tran);
//                if (tran.getResponseStatus().equals(WebParameter.GIAO_DICH_THANH_CONG)) {
//                    try {
//                        request = vtcVcoinProxy.checkCardValue(tran.getCardCode(), cardSerial, account);
//                        tran = getResponseStatus(request, tran);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
//                    }
//                }
//            }
//        }
//        return tran;
//    }

    private Transaction getResponseStatus(RequestResponse response, Transaction tran) {
        if (response != null) {
            int status = response.getResponseStatus();
            tran.setUseCardResponse(String.valueOf(status));
            tran.setResponseTime(new Date());
            if (status % 1000 == 0) { // Thanh Cong?
                tran.setAmount(String.valueOf(status));
                tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
            } else {
                switch (status) {
                    case -1:
                        tran.setResponseStatus(WebParameter.THE_DA_DUOC_SU_DUNG);
                        break;
                    case -2:
                        tran.setResponseStatus(WebParameter.THE_DA_KHOA);
                        break;
                    case -3:
                        tran.setResponseStatus(WebParameter.THE_HET_HAN_SU_DUNG);
                        break;
                    case -4:
                        tran.setResponseStatus(WebParameter.THE_CHUA_DUOC_KICH_HOAT);
                        break;
                    case -6:
                        tran.setResponseStatus(WebParameter.MATHE_SERIAL_KHONG_KHOP);
                        break;
                    case -10:
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        break;
                    case -11:
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        break;
                    case -12:
                        tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        break;
                    case -98:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -99:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    default:
                        System.out.println("VTC : Khong xac dinh : " + response.getResponseStatus() + " _ " + response.getDescription());
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                }
            }
        }
        return tran;
    }

    protected RequestResponse submit(String command, String cardCode, String cardSerial, String description) {
        try {
//            System.out.println("VTCProxy.submit : " + wsURL + "_" + partnerId + "_" + partnerKey + "_" + cardCode);
//            System.out.println(partnerKey);
//            System.out.println(partnerId);
//            System.out.println(cardCode);
            CardRequest card = new CardRequest();
            card.setFunction(command);
            card.setCardCode(cardCode);
            card.setCardID(cardSerial);
            card.setDescription(description);
            XStream xml = new XStream();
            xml.alias("CardRequest", CardRequest.class);
            String requestData = xml.toXML(card);

            requestData = VTCEncrypt(partnerKey, requestData);

            String res = VTCDecrypt(partnerKey, soap.request(Integer.valueOf(partnerId), requestData));

            RequestResponse response = new RequestResponse();
            response.setXml(res);

            Pattern pStatus = Pattern.compile("<ResponseStatus>(.*)</ResponseStatus>");
            Matcher m = pStatus.matcher(res);

            if (m.find()) {
                try {
                    response.setResponseStatus(Integer.valueOf(m.group(1)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            Pattern pDesc = Pattern.compile("<Descripton>(.*)</Descripton>");
//            m = pDesc.matcher(res);
//            if (m.find()) {
//                response.setDescription(m.group(1));
//            }
//            System.out.println("VTC : ResponseStatus = " + response.getResponseStatus()+ " _ XML = "+response.getXml());
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new VTCServiceException();
        }
    }

    public static CardSoap getVTCServiceByWSDL(String wsdlLocation) {
        try {
            String namespaceURI = "VTCOnline.Card.WebAPI";
            String localPart = "card";
            QName q = new QName(namespaceURI, localPart);
            Card c = new Card(new URL(wsdlLocation), q);
            return c.getCardSoap();
        } catch (Exception e) {
            e.printStackTrace();
            throw new VTCServiceException();
        }
    }

    static String VTCEncrypt(String key, String data) {
        try {
            Cipher cipher = Cipher.getInstance("TripleDES");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(key.getBytes(), 0, key.length());
            String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(0,
                    24);
            SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");
            cipher.init(Cipher.ENCRYPT_MODE, keyspec);
            byte[] stringBytes = data.getBytes();
            byte[] raw = cipher.doFinal(stringBytes);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(raw);
            return base64;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DecryptException();
        }
    }

    static String VTCDecrypt(String key, String data) {
        try {
            Cipher cipher = Cipher.getInstance("TripleDES");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(key.getBytes(), 0, key.length());
            String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(0, 24);
            SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");

            cipher.init(Cipher.DECRYPT_MODE, keyspec);
            BASE64Decoder decoder = new BASE64Decoder();
//        System.out.println(data.length() + " _"+data);
            byte[] raw = decoder.decodeBuffer(data);
//        System.out.println(raw.length + "  _  "+raw.length%8);
            byte[] stringBytes = cipher.doFinal(raw);
            String result = new String(stringBytes);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DecryptException(data);
        }
    }

    public static VTCTelcoProxy getVtcTelcoProxy() {
        if (vtcTelcoProxy == null) {
            vtcTelcoProxy = new VTCTelcoProxy();
        }
        return vtcTelcoProxy;
    }

    public static VTCVcoinProxy getvtcVcoinProxy() {
        if (vtcVcoinProxy == null) {
            vtcVcoinProxy = new VTCVcoinProxy();
        }
        return vtcVcoinProxy;
    }
}

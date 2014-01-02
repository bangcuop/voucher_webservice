/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.proxy;

import com.hdpay.vtc.dto.CardRequest;
import webapi.card.vtconline.CardSoap;
import webapi.card.vtconline.Card;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author koziwa
 */
public class VTCTelcoProxy {

    public static ResourceBundle configure = ResourceBundle.getBundle("wsconfig" + File.separator + "VTCProxy");
    private static Class issuerServiceUtil;
    private static Method getRandomGameAccountMethod;
    private static Map<String, String> issuerMap;
    public static final String USECARD = "UseCard";
    public static final String STATUS_PATTERN = "<ResponseStatus>(.*)</ResponseStatus>";
    public static final String DESCRIPTION_PATTERN = "<Descripton>(.*)</Descripton>";
    private static CardSoap soap;
    private static String wsURL;
    private static String partnerKey;
    private static String partnerId;
    private static DateFormat df = new SimpleDateFormat("HH:mm:ss");

    static {
        wsURL = configure.getString("VTC_VMS_webservice");
        partnerKey = configure.getString("VTC_VMS_PartnerKey");
        partnerId = configure.getString("VTC_VMS_PartnerID");
        soap = getVTCServiceByWSDL(wsURL);

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
            URLClassLoader classLoader = (URLClassLoader) VTCTelcoProxy.class.getClassLoader();
            issuerServiceUtil = Class.forName("com.vss.cardservice.service.util.IssuerServiceUtil", true, classLoader);
            getRandomGameAccountMethod = issuerServiceUtil.getDeclaredMethod("getRandomGameAccount");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        String provider = issuerMap.get(issuer);
        if (provider == null) {
            tran.setResponseStatus(WebParameter.MA_DICH_VU_KHONG_HOP_LE);
        } else {
            RequestResponse response = null;
            try {
                response = submit(USECARD, tran.getCardCode(), tran.getCardSerial(), provider + "|" + tran.getTransactionId() + "|" + account);
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

    public Boolean checkConnection() {
        boolean result = true;
        Date start = new Date();
        try {
            new URL(wsURL).openConnection().connect();
        } catch (Exception ex) {
            Date end = new Date();
            System.out.println("VTCTelcoProxy.checkConnection : ERROR. Start : " + df.format(start) + " -  End : " + df.format(end));
            ex.printStackTrace();
            result = false;
        }
        return result;
    }

    private Transaction getResponseStatus(RequestResponse response, Transaction tran) {
        if (response != null) {
            int status = response.getResponseStatus();
            tran.setUseCardResponse(status + "|" + response.getDescription());
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
                    case -5:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -6:
                        tran.setResponseStatus(WebParameter.MATHE_SERIAL_KHONG_KHOP);
                        break;
                    case -8:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -9:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
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
                    case -13:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -14:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -15:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -16:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -98:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -99:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -999:
                        tran.setResponseStatus(WebParameter.LOI_GOI_HAM_PROVIDER);
                        break;
                    case -100:
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

            Pattern pStatus = Pattern.compile(STATUS_PATTERN);
            Matcher m = pStatus.matcher(res);

            if (m.find()) {
                try {
                    response.setResponseStatus(Integer.valueOf(m.group(1)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            Pattern pDesc = Pattern.compile(DESCRIPTION_PATTERN);
            m = pDesc.matcher(res);
            if (m.find()) {
                response.setDescription(m.group(1));
            }
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
//    public static void main(String[] args) {
////        String[][] vcoin = new String[][]{{"PM0000015076", "230815621092"}, {"PM0000015077", "596794015838"}, {"PM0000015078", "636790822792"}};
//        RequestResponse response = request(MOBIPHONE, "5841000080695", "10791130194736", 12345, String.valueOf(System.currentTimeMillis()));
//        System.out.println(response.getResponseStatus());
//        System.out.println(response.getDescription());
////        System.out.println(10 % 1000);
//    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.proxy;

import com.vss.clientws.dto.Param;
import com.vss.clientws.util.CallService;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import javax.xml.rpc.ParameterMode;

/**
 * @author zannami UPZ294452 1118.1465.4793
 *
 * 9192.2480.4976 003681000026314 1960.8650.0906 UPZ297866
 */
public class VoucherServiceProxy {
//    static final String nameSpaceURI = "http://pasolutions.vn/voucherservice";
//    static final String nameSpaceURI = "http://ahha.vn/voucherservice";

//    static final String nameSpaceURI = "http://ahha.vn/voucherservice";
    static final String nameSpaceURI = "http://paydirect.vn/voucherservice";

    public static void main(String[] args) {
        String str = null;
        String transRef = new SimpleDateFormat("MMyyyy").format(new Date()) + "_" + System.currentTimeMillis();
        if (args != null && args.length > 2) {
            str = useCard(args[0], args[1], args[2], transRef);
            System.out.println(str);
            return;
        }
        try {
//            str = getTransactionDetail("2166594");
//            str = useCard("vina", "AUB469745", "50161033078580", transRef);
            str = useCard("VCOIN", "PM0111555432", "700081212929", transRef);
            System.out.println(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CallService getService() {
        CallService service = new CallService();
        try {
            service.setEndPointAddress(WebServiceProxy.endpointURL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.setRequiredLogin(false);
        service.setCallService(service.getCallService());
        return service;
    }
    public static List<String> phone = new ArrayList();

    static {
        try {
//            String phones = conf.getString("phones");
            String phones = "84903255350";
            for (String s : phones.split(",")) {
                phone.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTransactionDetail(String transRef) {
        String method = "getTransactionDetail";

        try {
            Param[] p = new Param[]{
                new Param("transRef", null, ParameterMode.IN, transRef, false),
                new Param("partnerCode", null, ParameterMode.IN, WebServiceProxy.partnerCode, false),
                new Param("password", null, ParameterMode.IN, WebServiceProxy.password, false),
                new Param("signature", null, ParameterMode.IN, hashData(transRef + WebServiceProxy.partnerCode + WebServiceProxy.password + WebServiceProxy.secretKey), false)
            };
            CallService service = getService();
            return (String) service.invoke(method, nameSpaceURI, p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String useCard(String issuer, String cardSerial, String cardCode, String transRef) {
        String method = "useCard";
//        String transRef = new SimpleDateFormat("MMyyyy").format(new Date()) + "_" + System.currentTimeMillis();

        try {
            Param[] p = new Param[]{
                new Param("issuer", null, ParameterMode.IN, issuer, false),
                new Param("cardSerial", null, ParameterMode.IN, cardSerial, false),
                new Param("cardCode", null, ParameterMode.IN, cardCode, false),
                new Param("transRef", null, ParameterMode.IN, transRef, false),
                new Param("partnerCode", null, ParameterMode.IN, WebServiceProxy.partnerCode, false),
                new Param("password", null, ParameterMode.IN, WebServiceProxy.password, false),
                new Param("signature", null, ParameterMode.IN, hashData(issuer + cardCode + transRef + WebServiceProxy.partnerCode + WebServiceProxy.password + WebServiceProxy.secretKey), false)
            };
            CallService service = getService();
            return (String) service.invoke(method, nameSpaceURI, p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static String hashData(String data) {
        try {
            MessageDigest hash = MessageDigest.getInstance("MD5");
            hash.update(data.getBytes());

            byte[] temp = hash.digest();

            return convertToHex(temp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            int halfbyte = data[i] >>> 4 & 0xF;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) (48 + halfbyte));
                } else {
                    buf.append((char) (97 + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0xF;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }
}

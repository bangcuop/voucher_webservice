/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hdpay.vtc.proxy;

import com.hdpay.vtc.exception.VTCServiceException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ResourceBundle;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import webapi.card.vtconline.Card;
import webapi.card.vtconline.CardSoap;

/**
 *
 * @author koziwa
 */
public class WebServiceProxy {

    static ResourceBundle configure = ResourceBundle.getBundle("configure");

    static CardSoap getVTCServiceByWSDL(String wsdlLocation) {
        try {
            String namespaceURI = "VTCOnline.Card.WebAPI";
            String localPart = "card";
            QName q = new QName(namespaceURI, localPart);
            Card c = new Card(new URL(wsdlLocation), q);
            return c.getCardSoap();
        } catch (Exception e) {
            throw new VTCServiceException();
        }
    }

    static String VTCEncrypt(String key, String data) throws Exception {
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

    }

    static String VTCDecrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance("TripleDES");
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(key.getBytes(), 0, key.length());
        String keymd5 = new BigInteger(1, md5.digest()).toString(16).substring(0, 24);
        SecretKeySpec keyspec = new SecretKeySpec(keymd5.getBytes(), "TripleDES");

        cipher.init(Cipher.DECRYPT_MODE, keyspec);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] raw = decoder.decodeBuffer(data);
        byte[] stringBytes = cipher.doFinal(raw);
        String result = new String(stringBytes);
        return result;
    }
}

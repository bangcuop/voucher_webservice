/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.service.exception.*;
import java.util.*;

/**
 *
 * @author Huy Pham
 */
public class ValidateUtils {

    public static Map<String, Issuer> supportIssuer;

    static {
        supportIssuer = new HashMap<String, Issuer>();
        String strSupport = ServiceUtil.getString("support_issuer");
        for (String s : strSupport.split(",")) {
            Issuer issuer = new Issuer();
            issuer.setIssuer(s);
            try {
                String configure = ServiceUtil.getString(s.toLowerCase());
                String[] conf = configure.split(":");
                issuer.setValidCardCode(conf[0]);
                String[] lengthArray = conf[0].split(",");
                List<Integer> lengthList = new ArrayList<Integer>();
                if (!conf[0].isEmpty()) {
                    for (int i = 0; i < lengthArray.length; i++) {
                        lengthList.add(Integer.parseInt(lengthArray[i]));
                    }
                    issuer.setCodeLengthList(lengthList);
                }
                issuer.setCheckSerial(conf.length > 1);
                if (issuer.isCheckSerial()) {
                    if (!conf[0].isEmpty()) {
                        lengthArray = conf[1].split(",");
                        lengthList = new ArrayList<Integer>();
                        for (int i = 0; i < lengthArray.length; i++) {
                            lengthList.add(Integer.parseInt(lengthArray[i]));
                        }
                        issuer.setSerialLengthList(lengthList);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            supportIssuer.put(s, issuer);
        }
    }

    public static void validateFormat(String issuer, String cardCode, String cardSerial, String transRef) {
        if (issuer == null || issuer.isEmpty()) {
            throw new UnknownIssuerException();
        }

        if (cardCode == null || cardCode.isEmpty()) {
            throw new InvalidCardLengthException();
        }

        if ((transRef == null) || (transRef.isEmpty()) || (transRef.trim().length() > 30)) {
            throw new InvalidRequestRefException();
        }

        Issuer issuer1 = supportIssuer.get(issuer.toLowerCase());
        if (issuer1 == null) {
            throw new UnknownIssuerException();
        }

        validateCardCode(cardCode, issuer1);

        if (issuer1.isCheckSerial()) {
            validateCardSerial(cardSerial, issuer1);
        }
    }

    private static void validateCardCode(String cardCode, Issuer issuer) {
        if (issuer.getCodeLengthList().isEmpty()) {
            return;
        }

        if (cardCode == null || cardCode.isEmpty()) {
            throw new NumberFormatException();
        }

        try {
            Long.parseLong(cardCode);
        } catch (Exception ex) {
            throw new NumberFormatException();
        }

        if (!issuer.getCodeLengthList().contains(cardCode.length())) {
            throw new InvalidCardLengthException();
        }

    }

    private static void validateCardSerial(String cardSerial, Issuer issuer) {
        if (issuer.getSerialLengthList().isEmpty()) {
            return;
        }

        if (cardSerial == null || cardSerial.isEmpty()) {
            throw new NumberFormatException();
        }

        if (!issuer.getSerialLengthList().contains(cardSerial.length())) {
            throw new InvalidCardLengthException();
        }
    }
}

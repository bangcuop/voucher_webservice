/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.exception.CardServiceException;
import com.vss.cardservice.service.exception.InvalidCardLengthException;
import com.vss.cardservice.service.exception.InvalidCardSerialException;
import com.vss.cardservice.service.exception.InvalidSignatureException;
import com.vss.cardservice.service.exception.UnknownIssuerException;
import com.vss.cardservice.service.exception.UnknownPartnerException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Huy Pham
 */
public class ValidateUtils {

    public static String responseToVietpay(Transaction tran) {
        if (tran != null) {
            return tran.getResponseStatus() + "|" + tran.getTransRefId() + "|" + (tran.getAmount() == null ? "0" : tran.getAmount());
        }
        throw new CardServiceException();
    }

    public static void validateInput(String issuer, String cardCode, PartnerInfo partnerInfo, String cardSerial) {
        if (issuer == null || issuer.isEmpty()) {
            throw new UnknownIssuerException();
        }

        if (cardCode == null || cardCode.isEmpty()) {
            throw new InvalidCardLengthException();
        }

        if (partnerInfo == null) {
            throw new UnknownPartnerException();
        }
        Issuer issuer1 = null;
        try {
            issuer1 = ServiceUtil.getSupportIssuer().get(issuer.toLowerCase());
        } catch (Exception e) {
            issuer1 = null;
        }
        if (issuer1 == null) {
            throw new UnknownIssuerException();
        }

        if (issuer.equalsIgnoreCase("vt") && (cardSerial == null || cardSerial.isEmpty())) {
            throw new InvalidCardSerialException();
        }

//        String validCardCode = issuer1.getValidCardCode();

        /*------------ Do dai ma the ----------*/
        int validateCardCodeResult = validateCardCode(cardCode, issuer1);
        if (validateCardCodeResult == -1) {
            throw new NumberFormatException();
        } else if (validateCardCodeResult == -2) {
            throw new InvalidCardLengthException();
        }

        /*------------ Kiem tra serial ----------*/
        if (issuer1.isCheckSerial()) {
//            String validSerial = issuer1.getValidCardSerial();
            int validateCardSerialResult = validateCardSerial(cardSerial, issuer1);
            if (validateCardSerialResult == -1) {
                throw new NumberFormatException();
            } else if (validateCardSerialResult == -2) {
                throw new InvalidCardSerialException();
            }
        }
    }

    public static Transaction createTransaction(ITransactionService transactionService, Transaction tran, String partnerId, String partnerCode, String cardCode,
            String cardSerial, String issuer, String ip, String transRef) {
        try {
            if (tran == null) {
                tran = new Transaction();
            }
            tran.setPartnerId(partnerId);
            tran.setPartnerCode(partnerCode);
            tran.setCardCode(cardCode);
            tran.setCardSerial(cardSerial);
            tran.setRequestTime(new Date());
            tran.setIssuer(issuer);
            tran.setTransRefId(transRef);
            tran.setRequestIp(ip);

            //saving this request
            Long transactionId = transactionService.createTransaction(tran);
            tran.setTransactionId(String.valueOf(transactionId));
            return tran;
        } catch (Exception e) {

            throw new CardServiceDBException();
        }
    }

    public static Partner validateSignature(IPartnerService partnerService, PartnerInfo partnerInfo, String ip, String issuer, String cardCode, String transRef) throws InvalidSignatureException {
        Partner validPartner = partnerService.checkPartnerRequest(partnerInfo.getPartnerCode(), partnerInfo.getPassword(), ip);
        String secretKey = validPartner.getSecretKey();
        String signature = partnerInfo.getSignature();
        String validData = null;
        if (cardCode == null) {
            // signature for getTransactionDetail
            validData = ServiceUtil.hashData(transRef + partnerInfo.getPartnerCode() + partnerInfo.getPassword() + secretKey);
        } else {
            // signature for useCard
            if (ServiceUtil.PPCARD_GATEWAY_IP.indexOf(ip) == -1) {
                validData = ServiceUtil.hashData(issuer + cardCode + transRef + partnerInfo.getPartnerCode() + partnerInfo.getPassword() + secretKey);
            } else {
                validData = ServiceUtil.hashData(cardCode + transRef + partnerInfo.getPartnerCode() + partnerInfo.getPassword() + secretKey);
            }
        }
        if (!validData.equals(signature)) {
            throw new InvalidSignatureException();
        }
        return validPartner;
    }

    public static int validateCardCode(String cardCode, Issuer issuer) {
        if (issuer.getCodeLengthList().isEmpty()) {
            return 0;
        }

        if (cardCode == null || cardCode.isEmpty()) {
            return -1;
        }

        try {
            Long.parseLong(cardCode);
        } catch (Exception ex) {
            return -1;
        }

//        String[] cardCodeLengths = cardCodeLengthList.split(",");
//        for (String item : cardCodeLengths) {
//            StringBuilder partternString = new StringBuilder("\\d{");
//            partternString.append(item.trim());
//            partternString.append("}");
//            Pattern pattern = Pattern.compile(partternString.toString());
//            Matcher matcher = pattern.matcher(cardCode);
//            if (matcher.matches()) {
//                return 0;
//            }
//        }
        if (issuer.getCodeLengthList().contains(cardCode.length())) {
            return 0;
        }
        return -2;
    }

    public static int validateCardSerial(String cardSerial, Issuer issuer) {
        if (issuer.getSerialLengthList().isEmpty()) {
            return 0;
        }

        if (cardSerial == null || cardSerial.isEmpty()) {
            return -1;
        }

//        String[] cardSerialLengths = cardSerialLengthList.split(",");
//        for (String item : cardSerialLengths) {
////            StringBuilder partternString = new StringBuilder("\\d{");
////            partternString.append(item.trim());
////            partternString.append("}");
////            Pattern pattern = Pattern.compile(partternString.toString());
////            Matcher matcher = pattern.matcher(cardSerial);
////            if (matcher.matches()) {
////                return 0;
////            }
//            if (cardSerial.length() == Integer.parseInt(item)) {
//                return 0;
//            }
//        }
        if (issuer.getSerialLengthList().contains(cardSerial.length())) {
            return 0;
        }
        return -2;
    }

    public static String getCurrentDate() {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
            return format.format(date);
        } catch (Exception ex) {
            return ("------>>>");
        }
    }

    public static void main(String[] args) {
        validateInput("VINA", "123456739012", new PartnerInfo(), "AWE124");
//        System.out.println(validateCardSerial("QWER24124",));
    }
}

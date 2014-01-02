package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.api.IIssuerService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.message.util.LoggingUtil;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceUtil {

    public static Boolean mobiWSStatus = true;
    public static Boolean viettelWSStatus = true;
    public static Boolean vnptEpayWSStatus = true;
    public static Boolean vinaWSStatus = true;
    public static Boolean cardWSStatus = true;
    public static Boolean fgameWSStatus = true;
    public static Boolean vdcWSStatus = true;
    public static Boolean activeQueueRunning = false;
    public static String sendToPartner = getString("sendToPartner");
    public static String invokeToTelco = getString("invoke_to_telco");
    public static boolean lockPartnerPermission = false;
    public static int maxFail = 10;

    static {
        try {
            lockPartnerPermission = (getString("lockPartnerPermission") != null && getString("lockPartnerPermission").equals("true"));
            maxFail = Integer.parseInt(getBundle().getString("maxFail"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static ExecutorService pool = Executors.newCachedThreadPool();
    private static ResourceBundle bundle;
    public static ResourceBundle language;
    public static final ResourceBundle CARD_SERVICE = ResourceBundle.getBundle("cardService");
    public static Map issuerCollection = null;
    public static Map<String, Partner> partnerCollection = new HashMap<String, Partner>();
    public static Map<String, Issuer> supportIssuer = null;
    public static Map defaultCardId = null;
    /**
     * key = partnerCode + issuer + cardCode + transRef
     * value = transaction
     */
    public static Map<String, Transaction> transactionQueue = new HashMap<String, Transaction>();
    public static Long timeGet = null;
    public static List<String> listAccount = null;
    public static String PPCARD_GATEWAY_IP = getString("vp_ip");
    public static final String disconnectSubject = MailServiceUtil.getString("disconnectSubject");
    public static final String reconnectSubject = MailServiceUtil.getString("reconnectSubject");
    public static final String disconnectContent = MailServiceUtil.getString("disconnectContent");
    public static final String reconnectContent = MailServiceUtil.getString("reconnectContent");
    public static final String smsDisconnect = MailServiceUtil.getString("smsDisconnect");
    public static final String smsReconnect = MailServiceUtil.getString("smsReconnect");
    public static final String mailSubject = MailServiceUtil.getString("mail_alert_subject");
    public static final String smsContent = MailServiceUtil.getString("smsContent");

    private static ResourceBundle getBundle() {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("WSConfig");
        }
        return bundle;
    }

    public static String getString(String key) {
        try {
            return getBundle().getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDescription(String key) {
        if (language == null) {
            language = ResourceBundle.getBundle("language");
        }
        try {
            return language.getString(key);
        } catch (Exception e) {
        }
        return "";
    }

    public void setBundle(ResourceBundle bundle) {
        ServiceUtil.bundle = bundle;
    }

    public static Map<String, Issuer> getSupportIssuer() {
        if (supportIssuer == null) {
            supportIssuer = new HashMap<String, Issuer>();
            String strSupport = getString("support_issuer");
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
        return supportIssuer;
    }

    public static Map<String, String> getDefaultCardId() {
        if (defaultCardId == null) {
            defaultCardId = new HashMap();
            String defaultValue = getString("default_value");
            for (String s : defaultValue.split(",")) {
                String[] s1 = s.split(":");
                defaultCardId.put(s1[0], s1[1]);
            }
        }
        return defaultCardId;
    }

    public static List<String> getGameAccountList(IGameAccountService gameAccountService) {
        try {
            if ((listAccount == null) || (listAccount.isEmpty())) {
                listAccount = gameAccountService.getAccountList();
            }
            return listAccount;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }

    }

    public static Map getIssuerCollection(IIssuerService issuerService) {
        try {
            if ((issuerCollection == null) || (issuerCollection.isEmpty())) {
                issuerCollection = new HashMap();
                timeGet = Long.valueOf(System.currentTimeMillis());
                List<Issuer> listIssuer = issuerService.getListIssuer(Integer.valueOf(0));
                for (Issuer issuer : listIssuer) {
                    String issuerName = issuer.getIssuer().trim().toUpperCase();
                    String cardId = issuer.getCardId().trim();
                    String par = issuer.getPar().trim();
                    int issuerId = issuer.getIssuerId();
                    issuerCollection.put(issuerName, issuerId);
                    issuerCollection.put(issuerName + par, cardId);
                }
            } else {
                Long now = Long.valueOf(System.currentTimeMillis());
                Integer time_reload_issuer = Integer.valueOf(Integer.parseInt(getString("time_reload_issuer")));
                if ((time_reload_issuer == null) || (time_reload_issuer.intValue() <= 0)) {
                    time_reload_issuer = Integer.valueOf(1);
                }
                if (now.longValue() >= timeGet.longValue() + time_reload_issuer.intValue() * 1000 * 60 * 60) {
                    issuerCollection = null;
                    timeGet = null;
                    getIssuerCollection(issuerService);
                }
            }
            return issuerCollection;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
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

    public void logTransaction(Transaction tran, Exception ex) {
        String content = "";

        if (tran != null) {
            try {
                content = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()) + " " +
                        tran.getPartnerCode() + "|" +
                        tran.getTransactionId() + "|" +
                        tran.getIssuer() + "|" +
                        tran.getTransRefId() + "|" +
                        tran.getCardCode() + "|" +
                        tran.getUseCardResponse() + "|" +
                        tran.getResponseToPartner(false) + "\n";
                LoggingUtil.log(content, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MailServiceUtil.mailAlertTransaction(mailSubject, content);
//        if (ex != null) {
//            MailServiceUtil.mailAlertTransaction(mailSubject, content);
//        } else {
//            MailServiceUtil.mailAlertTransaction(mailSubject, content);
//        }
    }

    public void logTransactionExt(Transaction tran, boolean create) {
        String content = "";
        if (tran != null) {
            if (create) {
                content = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()) + " " + "[CREATE TRANSACTION]" + tran.getTransactionId() + "|" + tran.getIssuer() + "|" + tran.getTransRefId() + "|" + tran.getCardCode() + "|" + tran.getCheckCardResponse() + "|" + tran.getUseCardResponse() + "|" + tran.getResponseToPartner(false) + "\n";
            } else {
                content = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(new Date()) + " " + "[UPDATE TRANSACTION]" + tran.getTransactionId() + "|" + tran.getIssuer() + "|" + tran.getTransRefId() + "|" + tran.getCardCode() + "|" + tran.getCheckCardResponse() + "|" + tran.getUseCardResponse() + "|" + tran.getResponseToPartner(false) + "\n";
            }
            try {
                LoggingUtil.log(content, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MailServiceUtil.mailAlertTransaction(mailSubject, content);
    }

    public static void checkLockPartner(PartnerInfo partnerInfo) {
        if (!lockPartnerPermission) {
            return;
        }
        try {
            Partner p = partnerCollection.get(partnerInfo.getPartnerCode());
            if (p != null) {
                if (p.getFailedCount() >= maxFail) {
                    p.setIsLock("1");
                    p.setStartLockTime(System.currentTimeMillis());
                    com.vss.message.util.LoggingUtil.log("[WARN] LOCK partner " + p.getPartnerCode() +
                            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), "useCard_transaction");
                } else {
                    p.setFailedCount(p.getFailedCount() + 1);
                }
                com.vss.message.util.LoggingUtil.log("[WARN] failed count partner " + p.getPartnerCode() + "==" + p.getFailedCount() + " " +
                        new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()), "useCard_transaction");
            }
        } catch (Exception e) {
        }
    }
}

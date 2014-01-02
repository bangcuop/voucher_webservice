package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.message.util.LoggingUtil;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServiceUtil {

    public static Map<String, Partner> partnerCollection = new HashMap<String, Partner>();
    public static Map<Integer, Partner> providerCollection = new HashMap<Integer, Partner>();
    public static Map<String, Integer> issuerMap;
    public static Map<String, Transaction> transactionQueue = new HashMap<String, Transaction>();
    public static int lockDuration = Integer.parseInt(getString("lockDuration"));
    public static Long session_timeout = null;
    public static boolean lockPartnerPermission = false;
    public static int maxFail = 10;
    private static final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
    private static ResourceBundle bundle;
    public static final String serverIp = "[" + TransactionServiceUtil.getServerIp() + "]";
    public static final String disconnectSubject = serverIp + MailServiceUtil.getString("disconnectSubject");
    public static final String reconnectSubject = serverIp + MailServiceUtil.getString("reconnectSubject");
    public static final String disconnectContent = serverIp + MailServiceUtil.getString("disconnectContent");
    public static final String reconnectContent = serverIp + MailServiceUtil.getString("reconnectContent");
    public static final String smsDisconnect = serverIp + MailServiceUtil.getString("smsDisconnect");
    public static final String smsReconnect = serverIp + MailServiceUtil.getString("smsReconnect");
    public static final String mailSubject = serverIp + MailServiceUtil.getString("mail_alert_subject");
    public static final String smsContent = serverIp + MailServiceUtil.getString("smsContent");

    static {
        try {
            lockPartnerPermission = getString("lockPartnerPermission").equals("true");
            maxFail = Integer.parseInt(getBundle().getString("maxFail"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            session_timeout = Long.valueOf(getString("session_timeout"));
        } catch (Exception e) {
            session_timeout = 20000l;
        }
    }

    public static Integer getCardId(String issuer, String amount) {
        Integer cardId = issuerMap.get(issuer + amount);
        if (cardId == null) {
            throw new CardServiceDBException("Khong ton tai menh gia : " + issuer + " " + amount);
        }
        return cardId;
    }

    public static String getCurrentDate() {
        return df.format(new Date());
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
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MILLISECOND, lockDuration);
                    p.setUnlockTime(cal.getTimeInMillis());
                    LoggingUtil.log("[WARN] LOCK partner " + p.getPartnerCode() + " : " + df.format(new Date()) + " - " + df.format(cal.getTime()), "useCard_transaction");
                } else {
                    p.setFailedCount(p.getFailedCount() + 1);
                    LoggingUtil.log("[WARN] failed count partner " + p.getPartnerCode() + "==" + p.getFailedCount() + " " + df.format(new Date()), "useCard_transaction");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getResponseToPartner(Transaction tran, boolean getTransactionDetail) {
        String responseStatus = tran.getResponseStatus();
        String responseDescription = tran.getResponseDescription();
        if (!getTransactionDetail) {
            if (responseStatus != null && responseStatus.equals(WebParameter.GIAO_DICH_THANH_CONG.split("\\|")[0])) {
                return responseStatus + "|" + responseDescription + "|" + tran.getAmount();
            } else {
                return responseStatus + "|" + responseDescription;
            }
        } else {
            return (responseStatus == null ? WebParameter.LOI_KHONG_XAC_DINH : responseStatus) + "|"
                    + (tran.getTransRefId() == null ? "" : tran.getTransRefId()) + "|"
                    + (tran.getCardCode() == null ? "" : tran.getCardCode()) + "|"
                    + (tran.getAmount() == null ? "0" : tran.getAmount());
        }
    }

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
            e.printStackTrace();
            return "";
        }
    }

    public void setBundle(ResourceBundle bundle) {
        ServiceUtil.bundle = bundle;
    }

    public static void main(String[] args) {
//        System.out.println(hashData("dfg$3@"));
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

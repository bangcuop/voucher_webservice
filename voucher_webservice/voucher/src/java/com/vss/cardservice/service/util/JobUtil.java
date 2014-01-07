/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.api.IIssuerService;
import com.vss.cardservice.api.IPartnerService;
import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.job.CheckProviderConnectionThread;
import com.vss.cardservice.service.job.KeepProviderSessionThread;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.log4j.Logger;

/**
 *
 * @author thang.tranquyet
 */
public class JobUtil {

    private static IPartnerService partnerService;
    private static IIssuerService issuerService;
    private static IGameAccountService gameAccountService;
    private static ExecutorService checkConnectionPool;
    private static ExecutorService keepSessionPool;
    public static Map<Integer, Boolean> providerConnectionMap = new HashMap();
    public static Map<Integer, String> providerSessionMap = new HashMap();
    private static Map<Integer, String> lostConnectionTimeMap = new HashMap();
    private static final DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static boolean sendToPartner = ServiceUtil.getString("sendToPartner").equalsIgnoreCase("true");
    private static Logger logger = Logger.getLogger("JobUtil");

    public static void checkProviderConnection() throws Exception {
        checkConnectionPool = Executors.newCachedThreadPool();
        providerConnectionMap.clear();

        List<Partner> partnerList = partnerService.getPartnerList(null, true, null, false);
        Partner partner;
        Boolean checkConnectionResult;
        for (int i = 0; i < partnerList.size(); i++) {
            checkConnectionPool.execute(new CheckProviderConnectionThread(partnerList.get(i)));
        }
        Thread.sleep(30000);
        String lostConnectionTime;
        boolean wasConnected;
        for (int i = 0; i < partnerList.size(); i++) {
            partner = partnerList.get(i);
            String partnerCode = partner.getPartnerCode();
            checkConnectionResult = providerConnectionMap.get(partner.getPartnerId());
            if (checkConnectionResult == null) {
                checkConnectionResult = false;
            }
            lostConnectionTime = lostConnectionTimeMap.get(partner.getPartnerId());
            wasConnected = lostConnectionTime == null;
            if (!checkConnectionResult.equals(wasConnected)) {
                System.out.println(df.format(new Date()) + " : CheckProviderConnectionJob : " + partnerCode + " : OLD = " + (wasConnected) + " _ NEW = " + checkConnectionResult);
            }
            if (!checkConnectionResult) {
                if (wasConnected) {
                    lostConnectionTimeMap.put(partner.getPartnerId(), df.format(new Date()));
                    MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject.replaceAll("telco", partnerCode),
                            ServiceUtil.disconnectContent.replaceAll("telco", partnerCode),
                            ServiceUtil.smsDisconnect.replaceAll("telco", partnerCode), null, sendToPartner);
                }
            } else if (!wasConnected) { // khoi phuc ket noi
                lostConnectionTimeMap.remove(partner.getPartnerId());
                String now = df.format(new Date());
                MailServiceUtil.sendAlert(
                        ServiceUtil.reconnectSubject.replaceAll("telco", partnerCode),
                        ServiceUtil.reconnectContent.replaceAll("telco", partnerCode).replaceAll("startTime", lostConnectionTime).replaceAll("endTime", now),
                        ServiceUtil.smsReconnect.replaceAll("telco", partnerCode).replaceAll("startTime", lostConnectionTime).replaceAll("endTime", now), null, sendToPartner);
            }
            if (!partner.getIsConnected().equals(checkConnectionResult)) {
                partner.setIsConnected(checkConnectionResult);
                partnerService.updatePartner(partner);
            }
        }
        checkConnectionPool.shutdownNow();
    }

    public static void checkTransaction() throws Exception {
        /*
         * Lay danh sach transaction tuong ung voi nha cung cap co the goi lai
         * Duyet tung giao dich de goi sang tung nha cung cap do Neu giao dich
         * do thanh cong thi cap nhat lai giao dich do la thanh cong
         */
        List<Transaction> listTransactions = null;
        try {
            listTransactions = TransactionServiceUtil.getTransactionListForCheckTran();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (listTransactions != null && !listTransactions.isEmpty()) {
            logger.info("Check Transaction Job : size = " + listTransactions.size());
            for (Transaction transaction : listTransactions) {
                try {
                    StringBuilder sb = createCheckTransactionLog(transaction);
                    transaction = ClassLoaderUtil.checkTransaction(ServiceUtil.providerCollection.get(transaction.getProviderId()), transaction);

                    sb.append(" : new responseStatus=");
                    sb.append(transaction.getResponseStatus());
                    sb.append(" - checkCardResposne=");
                    sb.append(transaction.getCheckCardResponse());
                    transaction.setStatus(TransactionServiceUtil.getStatus(transaction.getResponseStatus()));
                    if (transaction.getStatus().equals(ITransactionService.UNIDENTIFIED_RESULT)) {
                        sb.append(" - Bo qua");
                    } else {
                        TransactionServiceUtil.updateTransaction(transaction, true);
                        sb.append(" - Cap nhat thanh cong");
                    }
                    logger.info(sb.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static StringBuilder createCheckTransactionLog(Transaction transaction) {
        StringBuilder sb = new StringBuilder(100);
        sb.append("Check card : transId = ");
        sb.append(transaction.getTransactionId());
        sb.append(" - provider=");
        sb.append(transaction.getPartnerCode());
        sb.append(" - telcoTransRefId=");
        sb.append(transaction.getTelcoTransRefId());
        sb.append(" - partnerId=");
        sb.append(transaction.getPartnerId());
        sb.append(" - cardCode=");
        sb.append(transaction.getCardCode());
        sb.append(" - cardSerial=");
        sb.append(transaction.getCardSerial());
        sb.append(" - responseStatus=");
        sb.append(transaction.getResponseStatus());
        sb.append(" - useCardResponse=");
        sb.append(transaction.getUseCardResponse());
        return sb;
    }

    public static void keepProviderSession() throws Exception {
        keepSessionPool = Executors.newCachedThreadPool();
        providerSessionMap.clear();
        List<Partner> partnerList = partnerService.getPartnerList(null, true, true, false);
        Partner partner;
        String keepSessionResult;
        for (int i = 0; i < partnerList.size(); i++) {
            keepSessionPool.execute(new KeepProviderSessionThread(partnerList.get(i)));
        }
        Thread.sleep(20000);
        for (int i = 0; i < partnerList.size(); i++) {
            partner = partnerList.get(i);
            String partnerCode = partner.getPartnerCode();
            keepSessionResult = providerSessionMap.get(partner.getPartnerId());

            if (keepSessionResult == null) {
                String content = ServiceUtil.serverIp + " Loi keep session toi nha cung cap " + partnerCode;
                MailServiceUtil.sendAlert(content, content, content, null, false);
            } else if (!partner.getSessionValue().equals(keepSessionResult)) {
                System.out.println(df.format(new Date()) + " : KeepProviderSessionJob : " + partnerCode + " : OLD = " + partner.getSessionValue() + " _ NEW = " + keepSessionResult);
                partnerService.updateSessionValue(partnerCode, keepSessionResult);
            }
        }
        keepSessionPool.shutdownNow();
    }

    public static void refreshDbCache() {
        refreshPartner();
        refreshIssuer();
//        gameAccountService.refreshGameService();
    }

    private static void refreshPartner() {
        try {
            List<Partner> partnerList = partnerService.getPartnerList(null, null, null);
            Partner partner, cachePartner;
            for (int i = 0; i < partnerList.size(); i++) {
                partner = partnerList.get(i);
                if (partner.getIsProvider()) {
                    ServiceUtil.providerCollection.put(partner.getPartnerId(), partner);
                } else {
                    cachePartner = ServiceUtil.partnerCollection.get(partner.getPartnerCode());
                    if (cachePartner == null) {
                        ServiceUtil.partnerCollection.put(partner.getPartnerCode(), partner);
                    } else {
                        cachePartner.setValidIps(partner.getValidIps());
                        cachePartner.setPassword(partner.getPassword());
                        cachePartner.setSecretKey(partner.getSecretKey());
                        if (partner.getIsLock().equals("1")) {
                            cachePartner.setUnlockTime(null);
                        }
                        cachePartner.setIsLock(partner.getIsLock());
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void refreshIssuer() {
        try {
            Map newIssuerMap = new HashMap();
            List<Issuer> listIssuer = issuerService.getListIssuer(Integer.valueOf(0));
            for (Issuer issuer : listIssuer) {
                String issuerName = issuer.getIssuer().trim().toUpperCase();
                Integer cardId = issuer.getCardId();
                Integer par = issuer.getPar();
                newIssuerMap.put(issuerName, issuer.getIssuerId());
                newIssuerMap.put(issuerName + par, cardId);
            }
            ServiceUtil.issuerMap = newIssuerMap;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /////////////
    public void setIssuerService(IIssuerService issuerService) {
        JobUtil.issuerService = issuerService;
    }

    public void setPartnerService(IPartnerService partnerService) {
        JobUtil.partnerService = partnerService;
    }

    public void setGameAccountService(IGameAccountService gameAccountService) {
        JobUtil.gameAccountService = gameAccountService;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.thread.ThreadManager;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.PartnerInfo;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.*;
import com.vss.cardservice.service.util.*;
import com.vss.cardservice.thread.AlertThread;
import com.vss.cardservice.thread.TransactionThread;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

/**
 *
 * @author thang.tranquyet
 */
public class VoucherServiceUtil {

    public static final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
    private static final Logger logger = Logger.getLogger("VoucherServiceUtil");
    private static String serverIp;

    public String useCard(String issuerCode, String cardSerial, String cardCode, String transRef, String partnerCode, String password, String signature, HttpServletRequest req) {
        Transaction tran = null;
        String exceptionLog = null;
        String response;
        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setPartnerCode(partnerCode);
        partnerInfo.setPassword(password);
        partnerInfo.setSignature(signature);
        Date now = new Date();
        String ip = getRequestIp(req);
        String logPrefix = createLogPrefix(partnerCode, issuerCode, cardCode, cardSerial, transRef);
        writeReceiveLog(logPrefix, password, ip, now);

        Partner provider = null;
        try {
            ValidateUtils.validateFormat(issuerCode, cardCode, cardSerial, transRef);
            Partner partner = PartnerServiceUtil.validateInfo(partnerInfo, ip, issuerCode, cardCode, transRef);
            issuerCode = issuerCode.toUpperCase();

            tran = new Transaction();
            tran.setPartnerId(partner.getPartnerId());
            tran.setPartnerCode(partnerCode);
            tran.setCardCode(cardCode);
            tran.setCardSerial(cardSerial);
            tran.setRequestTime(now);
            tran.setIssuer(issuerCode);
            tran.setTransRefId(transRef);
            tran.setRequestIp(ip);
            tran.setServerIp(getServerIp());
//        if (tran.getIssuer().equalsIgnoreCase("MOBI")) {
//            tran.setGameService(IssuerServiceUtil.getRandomGameService(partnerId));
//        }
            TransactionServiceUtil.checkExistTransaction(tran);
            provider = PartnerServiceUtil.getProcessPartner(issuerCode);
            
            tran.setCardId(String.valueOf(ServiceUtil.getCardId(tran.getIssuer(), "0")));
            if (provider != null) {
                tran.setProviderId(provider.getPartnerId());
            }
            tran = TransactionServiceUtil.insertTransaction(tran);

            writeInsertLog(logPrefix, tran.getTransactionId(), provider == null ? "" : provider.getPartnerCode());
            
            if (provider == null) {
                throw new IssuerConnectionException();
            }
            long start = System.currentTimeMillis();
            ThreadManager.execute(new TransactionThread(tran, provider));
//            String transKey = partnerInfo.getPartnerCode() + tran.getIssuer() + tran.getCardCode() + tran.getTransRefId();
            Integer transKey = tran.getTransactionId();
            while (ServiceUtil.transactionQueue.get(transKey) == null) {
                Thread.sleep(100);
                if (System.currentTimeMillis() - start >= ServiceUtil.session_timeout) {
                    ServiceUtil.transactionQueue.put(transKey, tran);
                    throw new ConnectionTimeoutException();
                }
            }
            tran = ServiceUtil.transactionQueue.get(transKey);
            ServiceUtil.transactionQueue.remove(transKey);

            response = tran.getResponseToPartner();
//            response = (response == null) || (response.isEmpty()) ? WebParameter.LOI_GOI_HAM_PROVIDER : response;
        } catch (Exception ex) {
            exceptionLog = ex.toString().replaceAll("com.vss.cardservice.service.exception.", "");
            try {
                if (tran != null) {
                    tran = ErrorUtil.processException(tran, ex);
                    TransactionServiceUtil.updateTransaction(tran, false);
                    response = tran.getResponseToPartner();
                } else {
                    response = ErrorUtil.captureResponseFromException(ex);
                }
            } catch (Exception e) {
                logger.error("Loi khong xac dinh : ", e);
                response = ErrorUtil.captureResponseFromException(e);
            }
        }
        String responseLog = createResponseLog(logPrefix, tran == null ? null : tran.getTransactionId(), exceptionLog, response);
        if (response.startsWith(WebParameter.LOI_GOI_HAM_PROVIDER)
                || response.startsWith(WebParameter.LOI_KET_NOI_PROVIDER)
                || response.startsWith(WebParameter.LOI_KHONG_XAC_DINH)
                || response.startsWith(WebParameter.CONNECTION_TIMEOUT)) {
            ThreadManager.execute(new AlertThread(ServiceUtil.mailSubject, responseLog));
            if (provider != null) {
                PartnerServiceUtil.checkLockIssuerPartner(new Issuer(ServiceUtil.issuerMap.get(issuerCode), issuerCode), provider);
            }
        }

        if (response.startsWith(WebParameter.THE_KHONG_TON_TAI)) {
            ServiceUtil.checkLockPartner(partnerInfo);
        } else if (response.startsWith(WebParameter.GIAO_DICH_THANH_CONG)) {
            ServiceUtil.partnerCollection.get(partnerInfo.getPartnerCode()).setFailedCount(0);
        }
        logger.warn(responseLog);
        return response;
    }

    private String createLogPrefix(String partnerCode, String issuerCode, String cardCode, String cardSerial, String transRef) {
        StringBuilder sb = new StringBuilder(150);
        sb.append("|partner:");
        sb.append(partnerCode);
        sb.append("|transRef:");
        sb.append(transRef);
        sb.append("|issuer:");
        sb.append(issuerCode);
        sb.append("|code:");
        sb.append(cardCode);
        sb.append("|serial:");
        sb.append(cardSerial);
        return sb.toString();
    }

    private void writeReceiveLog(String logPrefix, String password, String ip, Date now) {
        StringBuilder log = new StringBuilder(200);
        log.append(df.format(now));
        log.append("|RECEIVE");
        log.append(logPrefix);
        log.append("|password:");
        log.append(password);
        log.append("|ip:");
        log.append(ip);
        logger.warn(log.toString());
    }

    private void writeInsertLog(String logPrefix, Integer transactionId, String providerCode) {
        StringBuilder log = new StringBuilder(200);
        log.append(df.format(new Date()));
        log.append("| INSERT");
        log.append(logPrefix);
        log.append("|transactionId:");
        log.append(transactionId);
        log.append("|provider:");
        log.append(providerCode);
        logger.warn(log.toString());
    }

    private String createResponseLog(String logPrefix, Integer transactionId, String exceptionLog, String response) {
        StringBuilder responseLog = new StringBuilder(200);
        responseLog.append(df.format(new Date()));
        responseLog.append("|RESPOND");
        responseLog.append(logPrefix);
        if (transactionId != null) {
            responseLog.append("|transactionId:");
            responseLog.append(transactionId);
        }
        if (exceptionLog != null) {
            responseLog.append("|Exception: ");
            responseLog.append(exceptionLog);
        }
        responseLog.append("|Response: ");
        responseLog.append(response);
        return responseLog.toString();
    }

    public String getTransactionDetail(String transRef, String partnerCode, String password, String signature, HttpServletRequest req) {
        String ip = getRequestIp(req);
        StringBuilder log = new StringBuilder(200);
        log.append(df.format(new Date()));
        log.append("|GetTransactionDetail|partner:");
        log.append(partnerCode);
        log.append("|transRef:");
        log.append(transRef);
        log.append("|password:");
        log.append(password);
        log.append("|ip:");
        log.append(ip);

        PartnerInfo partnerInfo = new PartnerInfo();
        partnerInfo.setPartnerCode(partnerCode);
        partnerInfo.setPassword(password);
        partnerInfo.setSignature(signature);
        String result;
        try {
            Partner p = PartnerServiceUtil.validateInfo(partnerInfo, ip, null, null, transRef);
            Transaction tran = TransactionServiceUtil.getTransactionDetail(p.getPartnerId(), transRef);
            if (tran == null) {
                throw new TransRefNotExistException();
            }
//            return tran.getResponseToPartner(true);
            result = ServiceUtil.getResponseToPartner(tran, true);
        } catch (Exception e) {
            result = ErrorUtil.captureResponseFromException(e);
        }
        log.append("|Ket qua check : ");
        log.append(result);
        logger.warn(log.toString());
        return result;
    }
    //

    private static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("clientip_pay");
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getServerIp() {
        if (serverIp == null) {
            Enumeration<NetworkInterface> networkInterfaces = null;
            try {
                networkInterfaces = NetworkInterface.getNetworkInterfaces();
                while (networkInterfaces.hasMoreElements()) {
                    NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                    Enumeration<InetAddress> ipEnum = ni.getInetAddresses();
                    while (ipEnum.hasMoreElements()) {
                        String ip = ipEnum.nextElement().getHostAddress();
                        System.out.println("Server IP :" + ip);
                        if (ip.contains(".") && !ip.startsWith("127")) {
                            serverIp = ip;
                        }
//                        if (ip.getHostAddress().startsWith("202.160.125.")) {
//                            serverIp = ip.getHostAddress().replaceFirst("202.160.125.", "");
//                            break;
//                        }
//                        if (ip.getHostAddress().startsWith("10.26.")) {
//                            serverIp = ip.getHostAddress().replaceFirst("10.26.", "");
//                            break;
//                        }
                    }
                }

            } catch (SocketException e) {
                e.printStackTrace();
            }
        }
        return serverIp;
    }
}

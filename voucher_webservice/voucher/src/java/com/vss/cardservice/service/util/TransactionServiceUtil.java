/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Transaction;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;

/**
 *
 * @author thang.tranquyet
 */
public class TransactionServiceUtil {

    private static ITransactionService transactionService;
    private static String serverIp;

    public static Boolean updateTransaction(Transaction transaction) {
        return transactionService.updateTransaction(transaction);
    }

    public static Transaction createTransaction(Integer partnerId, String partnerCode, String cardCode, String cardSerial, String issuer, String ip, String transRef) throws Exception {
        Transaction tran = new Transaction();
        tran.setPartnerId(partnerId);
        tran.setPartnerCode(partnerCode);
        tran.setCardCode(cardCode);
        tran.setCardSerial(cardSerial);
        tran.setRequestTime(new Date());
        tran.setIssuer(issuer);
        tran.setTransRefId(transRef);
        tran.setRequestIp(ip);
        tran.setServerIp(getServerIp());
//        if (tran.getIssuer().equalsIgnoreCase("MOBI")) {
//            tran.setGameService(IssuerServiceUtil.getRandomGameService(partnerId));
//        }
        Long transactionId = transactionService.createTransaction(tran);
        tran.setTransactionId(transactionId.intValue());
        return tran;
    }

    public static Transaction getTransactionDetail(Integer partnerId, String transRef) {
        return transactionService.getTransactionDetail(partnerId, transRef);
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

    ///////
    public static ITransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(ITransactionService transactionService) {
        TransactionServiceUtil.transactionService = transactionService;
    }
}

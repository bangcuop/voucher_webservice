/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Transaction;
import java.util.List;

/**
 *
 * @author thang.tranquyet
 */
public class TransactionServiceUtil {

    private static ITransactionService transactionService;

    public static String getStatus(String responseStatus) {
        if (responseStatus == null
                || responseStatus.equals(WebParameter.CONNECTION_TIMEOUT)
                || responseStatus.equals(WebParameter.GIAO_DICH_NGHI_VAN)
                || responseStatus.equals(WebParameter.LOI_GOI_HAM_PROVIDER)
                || responseStatus.equals(WebParameter.LOI_KET_NOI_PROVIDER)
                || responseStatus.equals(WebParameter.LOI_KHONG_XAC_DINH)) {
            return ITransactionService.UNIDENTIFIED_RESULT;
        } else {
            return ITransactionService.CLEAR_RESULT;
        }
    }

    public static void updateTransaction(Transaction transaction, boolean callBackend) throws Exception {
        transactionService.updateTransaction(transaction, callBackend);
    }

    public static Transaction insertTransaction(Transaction transaction) throws Exception {
        Long transactionId = transactionService.insertTransaction(transaction);
        transaction.setTransactionId(transactionId.intValue());
        return transaction;
    }

    public static Transaction loadTransaction(Integer transactionId) throws Exception {
        return transactionService.loadTransaction(transactionId);
    }

    public static Transaction getTransactionDetail(Integer partnerId, String transRef) {
        return transactionService.getTransactionDetail(partnerId, transRef);
    }

    public static void checkExistTransaction(Transaction transaction) throws Exception {
        transactionService.checkExistTransaction(transaction);
    }

    public static List<Transaction> getTransactionListForCheckTran() throws Exception {
        return transactionService.getTransactionListForCheckTran();
    }

    ///////
    public static ITransactionService getTransactionService() {
        return transactionService;
    }

    public void setTransactionService(ITransactionService transactionService) {
        TransactionServiceUtil.transactionService = transactionService;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import java.util.concurrent.Executors;

/**
 *
 * @author zannami
 */
public class ThreadManager {

    static TransactionProcess transactionProcess;

    public void setTransactionProcess(TransactionProcess transactionProcess) {
        ThreadManager.transactionProcess = transactionProcess;
    }

    public static void execute(Transaction tran) {
        try {
            if (ServiceUtil.pool == null || ServiceUtil.pool.isTerminated() || ServiceUtil.pool.isShutdown()) {
                ServiceUtil.pool = Executors.newCachedThreadPool();
            }
            transactionProcess.setTran(tran);
            ServiceUtil.pool.execute(transactionProcess);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException(e);
        }
    }
}

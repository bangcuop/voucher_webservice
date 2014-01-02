/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author zannami
 */
public class ThreadManager {

//    static TransactionProcess transactionProcess;
//
//    public void setTransactionProcess(TransactionProcess transactionProcess) {
//        ThreadManager.transactionProcess = transactionProcess;
//    }
    public static ExecutorService pool = Executors.newCachedThreadPool();

    public static void execute(Transaction tran, Partner provider) {
        try {
            if (pool == null || pool.isTerminated() || pool.isShutdown()) {
                pool = Executors.newCachedThreadPool();
            }
            pool.execute(new TransactionProcess(tran, provider));
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException(e);
        }
    }
}

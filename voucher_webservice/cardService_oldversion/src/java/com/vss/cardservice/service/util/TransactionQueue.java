/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.Transaction;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author zannami
 */
public class TransactionQueue {

    public static Integer processCount = 0;
    public static final int MAX_PROCESS = 15000;
    public static Queue<Transaction> q = new LinkedList<Transaction>();
    public static boolean PROCESS_LIMIT = true;
    public static String response = "";

    public static void processTransaction(Transaction tran) {
        synchronized (processCount) {
            if (processCount == MAX_PROCESS) {
                q.add(tran);
                PROCESS_LIMIT = true;
            } else {
                processCount++;
                PROCESS_LIMIT = false;
                TransactionProcess process = new TransactionProcess();
                process.setTran(tran);
                process.start();
            }
        }
    }

    public static void retryProcessTransaction() {
        synchronized (processCount) {
            if (processCount < MAX_PROCESS) {
                Transaction tran = (Transaction) ((LinkedList) q).removeLast();
                processCount++;
                PROCESS_LIMIT = false;
                TransactionProcess process = new TransactionProcess();
                process.setTran(tran);
                process.start();
            }
        }
    }
//    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//        long timeout = 0;
//        processTransaction(new Transaction());
//        System.out.println("response1===" + response);
//        while (response.isEmpty()) {
//            timeout = System.currentTimeMillis() - start;
//            if (timeout >= 5000) {
//                response = "timeout";
//                break;
//            }
//        }
//        System.out.println("response2===" + response);
//    }

//    public static void main(String[] args) {
//        TransactionProcess[] process = new TransactionProcess[5];
//        ExecutorService exe = Executors.newCachedThreadPool();
//        for (int i = 0; i < process.length; i++) {
//            process[i] = new TransactionProcess();
//            System.out.println("put process " + i + " " + System.currentTimeMillis());
//            process[i].setProcessCount(i);
//            exe.submit(process[i]);
//        }
//    }
}

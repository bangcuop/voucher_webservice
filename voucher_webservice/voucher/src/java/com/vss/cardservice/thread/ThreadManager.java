/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.thread;

import com.vss.cardservice.service.exception.CardServiceException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author thang.tranquyet
 */
public class ThreadManager {

    public static ExecutorService pool = Executors.newCachedThreadPool();

    public static void execute(Thread thread) {
        try {
            if (pool == null || pool.isTerminated() || pool.isShutdown()) {
                pool = Executors.newCachedThreadPool();
            }
            pool.execute(thread);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException(e);
        }
    }
}

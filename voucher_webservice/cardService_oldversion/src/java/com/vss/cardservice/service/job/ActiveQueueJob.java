/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.TransactionQueue;

/**
 *
 * @author zannami
 */
public class ActiveQueueJob extends AbstractCronTriggerJob {

    @Override
    public void excute() {
        try {
            if (ServiceUtil.activeQueueRunning) {
                return;
            }
            ServiceUtil.activeQueueRunning = true;
            while (!TransactionQueue.q.isEmpty() && !TransactionQueue.PROCESS_LIMIT) {
                TransactionQueue.retryProcessTransaction();
            }
            ServiceUtil.activeQueueRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

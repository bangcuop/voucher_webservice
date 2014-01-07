/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.service.util.JobUtil;

/**
 *
 * @author liemnh
 */
public class CheckTransactionJob extends AbstractCronTriggerJob {

    public CheckTransactionJob() {
        super();
    }

    public void excute() {
        try {
            JobUtil.checkTransaction();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

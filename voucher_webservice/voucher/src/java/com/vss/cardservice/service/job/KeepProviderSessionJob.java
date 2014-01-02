/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.service.util.JobUtil;

/**
 *
 * @author zannami
 */
public class KeepProviderSessionJob extends AbstractCronTriggerJob {

    public KeepProviderSessionJob() {
        super();
//        excute();
    }

    public void excute() {
        try {
            JobUtil.keepProviderSession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

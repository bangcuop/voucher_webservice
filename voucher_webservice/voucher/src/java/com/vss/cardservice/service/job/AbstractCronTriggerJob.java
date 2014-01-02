/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobDetail;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;

/**
 *
 * @author zannami
 */
public abstract class AbstractCronTriggerJob extends CronTriggerBean {

    public AbstractCronTriggerJob() {
        try {
            String jobName = this.getClass().getName() + "@" + this.hashCode();
            MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
            jobDetail.setTargetObject(this);
            jobDetail.setTargetMethod("excute");
            jobDetail.setName(jobName);
            jobDetail.afterPropertiesSet();
            this.setJobDetail((JobDetail) jobDetail.getObject());
        } catch (Exception e) {
            Logger.getLogger(AbstractCronTriggerJob.class.getName()).log(Level.SEVERE, null, e);

        }
    }

    public abstract void excute();
}

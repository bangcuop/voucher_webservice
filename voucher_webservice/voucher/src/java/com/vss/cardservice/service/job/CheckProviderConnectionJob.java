/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.service.util.JobUtil;

/**
 *
 * @author thangtq
 */
public class CheckProviderConnectionJob extends AbstractCronTriggerJob {

    public CheckProviderConnectionJob() {
        super();
    }

    public void excute() {
        try {
            JobUtil.checkProviderConnection();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

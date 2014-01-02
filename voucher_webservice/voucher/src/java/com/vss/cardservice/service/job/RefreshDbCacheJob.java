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
public class RefreshDbCacheJob extends AbstractCronTriggerJob {

    public RefreshDbCacheJob() {
        super();
        JobUtil.refreshDbCache();
    }

    public void excute() {
        JobUtil.refreshDbCache();
    }
}

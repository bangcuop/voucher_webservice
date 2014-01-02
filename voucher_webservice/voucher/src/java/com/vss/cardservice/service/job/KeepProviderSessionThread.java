/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.service.util.ClassLoaderUtil;
import com.vss.cardservice.service.util.JobUtil;

/**
 *
 * @author zannami
 */
public class KeepProviderSessionThread extends Thread {

    private Partner partner;

    public KeepProviderSessionThread() {
        super();
    }

    public KeepProviderSessionThread(Partner partner) {
        super();
        this.partner = partner;
    }

    @Override
    public void run() {
        if (partner != null) {
//            System.out.println("TransactionProcess : "+System.identityHashCode(this));
            try {
                String result = ClassLoaderUtil.keepSession(partner);
                JobUtil.providerSessionMap.put(partner.getPartnerId(), result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.job;

import com.vss.cardservice.service.util.WebParameter;
import com.vss.mobicard.service.MobiCardProxy;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class KeepSessionJob extends AbstractCronTriggerJob {

    public KeepSessionJob() {
        super();
//        excute();
    }

    public void excute() {
        try {
            if (WebParameter.mobiSessionValue == null) {
                Map map = MobiCardProxy.login();
                if (map == null || map.isEmpty()) {
                    WebParameter.mobiSessionValue = null;
                    throw new Exception("Remote Service Exception");
                }
                Object session = "";
                Iterator iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    Object key = iterator.next();
                    if (key.toString().indexOf("session") > -1) {
                        session = map.get(key);
                    }
                }
                if (session == null || session.equals("")) {
                    WebParameter.mobiSessionValue = null;
                    throw new Exception("Remote Service Exception");
                }
                WebParameter.mobiSessionValue = session.toString();
            }
            else if (WebParameter.mobiSessionValue != null && !WebParameter.mobiSessionValue.isEmpty()) {
                MobiCardProxy.keepAlive(WebParameter.mobiSessionValue);
            }
        } catch (Exception e) {
            WebParameter.mobiSessionValue = null;
            e.printStackTrace();
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.aop;

import com.vss.cardservice.service.exception.RemoteServiceException;
import com.vss.cardservice.service.util.MailServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.mobicard.exception.MobiServiceException;
import java.net.ConnectException;
import java.util.Calendar;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 *
 * @author zannami
 */
public class WebServiceAop {

    private static Boolean alive = true;
    private static Calendar refreshTime = Calendar.getInstance();
    private static int timeOut; // seconds
    private static final RemoteServiceException remoteException = new RemoteServiceException("Remote Service Exception");

    public void setTimeOut(int timeOut) {
        WebServiceAop.timeOut = timeOut;
    }

    /**
     * Gui SMS khi mat ket noi hoac khoi phuc
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object checkStatus(ProceedingJoinPoint pjp) throws Throwable {
        synchronized (alive) {
            try {
                if (alive || Calendar.getInstance().after(refreshTime)) {
                    Object returnValue = pjp.proceed();
                    if (!alive) {
//                        MailServiceUtil.sendAlert(ServiceUtil.reconnectSubject, ServiceUtil.reconnectContent, ServiceUtil.smsReconnect, null, false);
                        alive = true;
                    }
                    return returnValue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof ConnectException || e instanceof InterruptedException || e instanceof MobiServiceException) {
                    alive = false;
//                    MailServiceUtil.sendAlert(ServiceUtil.disconnectSubject, ServiceUtil.disconnectContent, ServiceUtil.smsDisconnect, null, false);
                    throw remoteException;
                }
            }
        }
        throw remoteException;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.aop;

import com.vss.cardservice.service.exception.CardServiceDBException;
import java.util.Calendar;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 *
 * @author zannami
 */
public class MethodExecutionAop {

    private static Boolean execute = true;
    private static Calendar refreshTime = Calendar.getInstance();
    private static int timeOut; // seconds
    private static final CardServiceDBException dbException = new CardServiceDBException("Method Execution Exception");

    public void setTimeOut(int timeOut) {
        MethodExecutionAop.timeOut = timeOut;
    }

    public Object checkMethodExecution(ProceedingJoinPoint pjp) throws Throwable {
        synchronized (execute) {
            try {
                if (execute || Calendar.getInstance().after(refreshTime)) {
                    if (!execute) {
                        execute = true;
                    }
                    Object returnValue = pjp.proceed();
                    return returnValue;
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new CardServiceDBException(e);
            }
        }
        throw dbException;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.thread;

import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.ErrorUtil;
import com.vss.cardservice.service.util.ClassLoaderUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.TransactionServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author thangtq
 */
public class TransactionThread extends Thread {

    private static final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
    private static final Logger logger = Logger.getLogger("TransactionThread");
    private Transaction tran;
    private Partner provider;

    public TransactionThread() {
        super();
    }

    public TransactionThread(Transaction tran, Partner provider) {
        super();
        this.tran = tran;
        this.provider = provider;
    }

    @Override
    public void run() {
        if (tran != null) {
//            System.out.println("TransactionProcess : "+System.identityHashCode(this));
            try {
                tran = ClassLoaderUtil.useCard(provider, tran);
            } catch (Exception ex) {
                ex.printStackTrace();
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_PROVIDER);
            }
            tran.setResponseDescription(ErrorUtil.descriptionMap.get(tran.getResponseStatus()));
//            StringBuilder sb = new StringBuilder(100);
//            sb.append(tran.getPartnerCode());
//            sb.append(tran.getIssuer());
//            sb.append(tran.getCardCode());
//            sb.append(tran.getTransRefId());
//            String key = sb.toString();
            Integer key = tran.getTransactionId();
            if (ServiceUtil.transactionQueue.containsKey(key)) {
                ServiceUtil.transactionQueue.remove(key);
                writeResultLog();
            } else {
                tran.setResponseToPartner(ServiceUtil.getResponseToPartner(tran, false));
                ServiceUtil.transactionQueue.put(key, tran);
            }
            
            try {
                TransactionServiceUtil.updateTransaction(tran, false);
            } catch (Exception e) {
                logger.error("****** Co loi update transaction : transactionId = " + tran.getTransactionId(), e);
            }
        }
    }

    private void writeResultLog() {
        StringBuilder log = new StringBuilder(200);
        log.append(df.format(new Date()));
        log.append("| RESULT|partner:");
        log.append(tran.getPartnerCode());
        log.append("|transRef:");
        log.append(tran.getTransRefId());
        log.append("|issuer:");
        log.append(tran.getIssuer());
        log.append("|code:");
        log.append(tran.getCardCode());
        log.append("|serial:");
        log.append(tran.getCardSerial());
        log.append("|transactionId:");
        log.append(tran.getTransactionId());
        log.append("|late Result: ");
        log.append(ServiceUtil.getResponseToPartner(tran, false));
        logger.warn(log.toString());
    }
}

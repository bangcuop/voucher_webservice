/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import com.vss.cardservice.service.exception.ErrorUtil;

/**
 *
 * @author thangtq
 */
public class TransactionProcess extends Thread {

    private Transaction tran;
    private Partner provider;

    public TransactionProcess() {
        super();
    }

    public TransactionProcess(Transaction tran, Partner provider) {
        super();
        this.tran = tran;
        this.provider = provider;
    }

    @Override
    public void run() {
        if (tran != null) {
//            System.out.println("TransactionProcess : "+System.identityHashCode(this));
            try {
                String transKey = tran.getPartnerCode() + tran.getIssuer() + tran.getCardCode() + tran.getTransRefId();

                tran = ClassLoaderUtil.useCard(provider, tran);
                String responseStatus = tran.getResponseStatus();
                String responseDescription = ErrorUtil.descriptionMap.get(responseStatus);
                tran.setResponseDescription(responseDescription);
                tran.setResponseToPartner(ServiceUtil.getResponseToPartner(tran, false));
                ServiceUtil.transactionQueue.put(transKey, tran);
                TransactionServiceUtil.updateTransaction(tran);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CardServiceException();
            }
        }
    }
}

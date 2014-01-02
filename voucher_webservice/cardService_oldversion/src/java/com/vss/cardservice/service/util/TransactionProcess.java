/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;

/**
 *
 * @author zannami
 */
public class TransactionProcess extends Thread {

    private Transaction tran;
    private CardServiceLocalFactory cardServiceLocalFactory;
    private ITransactionService transactionService;

    public void setTransactionService(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public void setCardServiceLocalFactory(CardServiceLocalFactory cardServiceLocalFactory) {
        this.cardServiceLocalFactory = cardServiceLocalFactory;
    }

    public Transaction getTran() {
        return tran;
    }

    public void setTran(Transaction tran) {
        this.tran = tran;
    }

    @Override
    public void run() {
        if (tran != null) {
            try {
                String transKey = tran.getPartnerCode() + tran.getIssuer() + tran.getCardCode() + tran.getTransRefId();
                tran.setIssuer(tran.getIssuer().toUpperCase());
                if (ServiceUtil.invokeToTelco.equals("all")) {
                    tran = cardServiceLocalFactory.getCardService(tran.getIssuer().toUpperCase()).useCard(tran);
                } else {
                    tran = cardServiceLocalFactory.getCardService(ServiceUtil.invokeToTelco).useCard(tran);
                }
                ServiceUtil.transactionQueue.put(transKey, tran);
                transactionService.updateTransaction(tran);
            } catch (Exception e) {
                e.printStackTrace();
                throw new CardServiceException();
            }
//            finally {
//                TransactionQueue.processCount--;
//            }
        }
//        try {
//            if (processCount == 1) {
//                sleep(10000);
//            } else {
//                sleep(1000);
//            }
//            setProcessCount(2);
//        } catch (Exception e) {
//        }
//        System.out.println(processCount + "===" + System.currentTimeMillis());
    }
}

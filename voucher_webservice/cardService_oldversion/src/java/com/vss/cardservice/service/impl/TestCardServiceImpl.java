/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.util.WebParameter;

/**
 *
 * @author zannami
 */
public class TestCardServiceImpl extends Thread implements ICardService {

    public Transaction useCard(Transaction tran) {
        TestCardServiceImpl testThread = new TestCardServiceImpl();
        testThread.start();
        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO.split("@")[0]);
        tran.setResponseDescription(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO.split("@")[1]);
        return tran;
    }

    public void run() {
        while (true) {
            try {
                sleep(2000);
                break;
            } catch (Exception e) {
            }
        }
    }

    public Transaction checkCard(Transaction tran) {
        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO.split("@")[0]);
        tran.setResponseDescription(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO.split("@")[1]);
        return tran;
    }

    public String loginToTelco() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

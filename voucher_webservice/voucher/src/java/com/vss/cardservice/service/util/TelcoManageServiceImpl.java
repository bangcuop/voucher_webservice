/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.dto.Transaction;

/**
 * Manage transaction for mobi and vina
 * @author zannami
 */
public class TelcoManageServiceImpl implements ICardService {
    private void getTelcoServiceImpl(String issuer){
       
    }
    public Transaction useCard(Transaction tran) {
       throw new UnsupportedOperationException("Not supported yet.");
    }

    public Transaction checkCard(Transaction tran) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String loginToTelco() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

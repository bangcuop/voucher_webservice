package com.paydirect.voucher.proxy;

import com.vss.cardservice.dto.Transaction;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 2:56:15 PM
 */
public interface ICardService {

    public Transaction useCard(Transaction tran);

    public Transaction checkCard(Transaction tran);

    public String loginToTelco();
}

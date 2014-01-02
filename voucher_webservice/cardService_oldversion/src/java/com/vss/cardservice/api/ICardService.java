package com.vss.cardservice.api;

import com.vss.cardservice.dto.Transaction;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 2:56:15 PM
 */
public interface ICardService {

    Transaction useCard(Transaction tran);

    Transaction checkCard(Transaction tran);

    String loginToTelco();
}

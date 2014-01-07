package com.vss.cardservice.api;

import com.vss.cardservice.dto.Transaction;
import java.util.List;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 11:23:06 AM
 */
public interface ITransactionService {

    public static final String CREATE_TRANS = "1";
    public static final String UNIDENTIFIED_RESULT = "2";
    public static final String CLEAR_RESULT = "3";
    public static final String VALID_UPDATE = "4";
    public static final String INVALID_UPDATE = "5";

    public Long insertTransaction(Transaction trans) throws Exception;

    public void checkExistTransaction(Transaction transaction) throws Exception;

    public void updateTransaction(Transaction trans, boolean callBackend) throws Exception;

    public Transaction getTransactionDetail(Integer partnerId, String transRef);

    public Transaction loadTransaction(Integer transactionId) throws Exception;

    public List<Transaction> getTransactionListForCheckTran() throws Exception;
}

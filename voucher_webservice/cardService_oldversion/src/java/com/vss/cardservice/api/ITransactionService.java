package com.vss.cardservice.api;

import com.vss.cardservice.dto.ProviderParameter;
import com.vss.cardservice.dto.Transaction;
import java.util.List;

/**
 * 
 * @author zannami
 * 
 *         Jul 13, 2011 11:23:06 AM
 */
public interface ITransactionService {

    Long createTransaction(Transaction trans);

    Boolean updateTransaction(Transaction trans);

    Transaction getTransactionDetail(String parnerId, String transRef);

    Boolean checkTransaction(String cardCode, String cardSerial, String issuer);

    /**
     *
     * @param transType
     * @return
     * 0: loi
     * 1: thanh cong
     */
    List<Transaction> getListTransactionByDay(int transType, int issuerId, int start, int end);

    int countTransactionByDay(int transType, int issuerId);

    String providerUpdateTransaction(ProviderParameter providerParameter);

    /**
     * for transaction update failed
     */
    List<Transaction> getListTransactionFailed(int start, int end);
    int countTransactionFailed();
    void updateCardId(Transaction tran);
}

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

    public static final String CREATE_TRANS = "1";
    public static final String UNIDENTIFIED_RESULT = "2";
    public static final String CLEAR_RESULT = "3";
    public static final String VALID_UPDATE = "4";
    public static final String INVALID_UPDATE = "5";
    
    public Long createTransaction(Transaction trans) throws Exception;

    Boolean updateTransaction(Transaction trans);

    Transaction getTransactionDetail(Integer partnerId, String transRef);

////    Boolean checkTransaction(String cardCode, String cardSerial, String issuer);
//
//    /**
//     *
//     * @param transType
//     * @return
//     * 0: loi
//     * 1: thanh cong
//     */
//    List<Transaction> getListTransactionByDay(int transType, int issuerId, int start, int end);
//
//    int countTransactionByDay(int transType, int issuerId);

//    String providerUpdateTransaction(ProviderParameter providerParameter);

    /**
     * for transaction update failed
     */
//    List<Transaction> getListTransactionFailed(int start, int end);
//    int countTransactionFailed();
//    void updateCardId(Transaction tran);
}

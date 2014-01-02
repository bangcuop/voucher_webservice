package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.IProviderService;
import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Provider;
import com.vss.cardservice.dto.ProviderParameter;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.exception.InvalidCardCodeException;
import com.vss.cardservice.service.util.BaseService;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.message.util.LoggingUtil;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zannami
 *
 *         Jul 13, 2011 4:55:27 PM
 */
public class TransactionServiceImpl extends BaseService implements ITransactionService {

    private IProviderService providerService;

    public Long createTransaction(Transaction tran) {
        try {
            Integer count = (Integer) mysqlMap.queryForObject("transaction.checkTransaction", tran);
            if (count > 0) {
                return new Long(-1);
            }
            String issuer = tran.getIssuer();
            String cardId = ServiceUtil.getDefaultCardId().get(issuer.toLowerCase().trim());
            tran.setCardId(String.valueOf(cardId));
            return (Long) mysqlMap.insert("transaction.createTransaction", tran);
        } catch (Exception e) {
            throw new CardServiceDBException(e);
        }

    }

    public Boolean updateTransaction(Transaction tran) {
        try {
            /**
             * get cardId
             *
             */
            String amount = tran.getAmount() == null ? null : tran.getAmount().trim();
            String issuer = tran.getIssuer().trim();
            String cardId = null;
            if (ServiceUtil.issuerCollection != null && !ServiceUtil.issuerCollection.isEmpty()) {
                try {
                    boolean check = ServiceUtil.issuerCollection.containsKey(issuer.trim().toUpperCase() + amount);
                    if (check) {
                        cardId = String.valueOf(ServiceUtil.issuerCollection.get(issuer.trim().toUpperCase() + amount));
                    }
                } catch (Exception e) {
                }
            }
            String sql = "UPDATE transaction SET partnerId = partnerId ";
            if (tran.getStatus() == null) {
                tran.setStatus("1");
            }
            if (tran.getResponseTime() != null) {
                sql += ",responseTime = '" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "'";
            }
            if (tran.getResponseStatus() != null) {
                String responseStatus = tran.getResponseStatus();
                sql += ",responseStatus = '" + responseStatus + "'";
                if (responseStatus.equals("01") && cardId == null) {
                    LoggingUtil.log("[NOT_FOUND_CARDID]=$$" + issuer + amount + "$telcoResponse=$" +
                            tran.getUseCardResponse() + "$$", "useCard_transaction");
                }
            }
            if (tran.getResponseDescription() != null) {
                sql += ",responseDescription = '" + tran.getResponseDescription() + "'";
            }
            if (cardId != null) {
                sql += ",cardId = " + cardId + " ";
            }
            if (tran.getCheckCardResponse() != null) {
                sql += ",checkCardResponse ='" + tran.getCheckCardResponse() + "'";
            }
            if (tran.getUseCardResponse() != null) {
                if (tran.getUseCardResponse().length() > 100) {
                    tran.setUseCardResponse(tran.getUseCardResponse().substring(0,100));
                }
                sql += ",useCardResponse ='" + tran.getUseCardResponse() + "'";
                sql += ",status = " + tran.getStatus();
            }
            if (tran.getResponseToPartner(false) != null) {
                sql += ",responseToPartner ='" + tran.getResponseToPartner(false) + "'";
            }
            if (tran.getTelcoTransRefId() != null) {
                sql += ",telcoTransRefId ='" + tran.getTelcoTransRefId() + "'";
            }
            if (tran.getAccountId() != null) {
                sql += ",accountId ='" + tran.getAccountId() + "'";
            }
            if (tran.getCardSerial() != null) {
                sql += ",cardSerial ='" + tran.getCardSerial() + "'";
            }
            if(tran.getProviderId()!=null && tran.getProviderId() > 0){
                sql += ",providerId="+ tran.getProviderId();
            }
            sql += " WHERE transactionId =" + tran.getTransactionId();
            Map map = new HashMap();
            map.put("sql", sql);
            int count = mysqlMap.update("transaction.updateTransaction", map);
            return (count > 0);
        } catch (Exception e) {
            throw new CardServiceDBException(e);
        }
    }

    public Transaction getTransactionDetail(String parnerId, String transRef) {
        try {
            Map map = new HashMap();
            map.put("partnerId", parnerId);
            map.put("transRefId", transRef);
            return (Transaction) mysqlMap.queryForObject("transaction.getTransactionDetail", map);
        } catch (Exception e) {
            throw new CardServiceDBException(e);
        }
    }

    public Boolean checkTransaction(String cardCode, String cardSerial,  String issuer) {
        StringBuilder prefix_ = new StringBuilder("[");
        prefix_.append(getCurrentDate());
        prefix_.append("][TransactionServiceImpl][checkTransaction][cardCode:");
        prefix_.append(cardCode);
        prefix_.append("][issuer:");
        prefix_.append(issuer);
        prefix_.append("]");
//        System.out.println("------------------------>>>>" + prefix_.toString());
        LoggingUtil.log(prefix_.toString(), "useCard_transaction");
        Map map = new HashMap();
        map.put("cardCode", cardCode);
        map.put("cardSerial", cardSerial);
        map.put("name", issuer);
        List<Transaction> lst;
        try {
            lst = mysqlMap.queryForList("transaction.getStatusByCardCode", map);
            StringBuilder prefix = new StringBuilder(prefix_.toString());
            prefix_.append("][lst:");
            prefix_.append(lst.size());
            prefix_.append("]");
//            System.out.println("------------------------>>>>" + prefix.toString());
            LoggingUtil.log(prefix.toString(), "useCard_transaction");
        } catch (Exception ex) {
            return false;
        }

        if (lst != null) {
            for (Transaction tran : lst) {
                if (tran.getStatus() != null) {
                    if (tran.getStatus().equalsIgnoreCase("2") || tran.getStatus().equalsIgnoreCase("3")) {
                        prefix_.append("[Ma the da duoc su dung]");
                        throw new InvalidCardCodeException();
                    }
                }
            }
        }
        prefix_.append("[Ma the chua duoc su dung]");
        LoggingUtil.log(prefix_.toString(), "useCard_transaction");
        return false;
    }

    public static String getCurrentDate() {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");
            return format.format(date);
        } catch (Exception ex) {
            return ("------>>>");
        }
    }

    public List<Transaction> getListTransactionByDay(int transType, int issuerId, int start, int end) {
        try {
            Map map = new HashMap();
            map.put("transType", transType);
            map.put("issuerId", issuerId);
            map.put("start", start);
            map.put("end", end);
            return mysqlMap.queryForList("transaction.getListTransactionByDay", map);
        } catch (Exception e) {
            throw new CardServiceDBException(e);
        }
    }

    public int countTransactionByDay(int transType, int issuerId) {
        try {
            Map map = new HashMap();
            map.put("transType", transType);
            map.put("issuerId", issuerId);
            return (Integer) mysqlMap.queryForObject("transaction.countTransactionByDay", map);
        } catch (Exception e) {
            throw new CardServiceDBException(e);
        }
    }

    public String providerUpdateTransaction(ProviderParameter providerParameter) {
        try {
            String transationId = providerParameter.getTransactionId();
            if (transationId == null || transationId.isEmpty()) {
                return WebParameter.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
            }
            Integer transId;
            try {
                transId = Integer.parseInt(transationId);
            } catch (NumberFormatException ex) {
                return WebParameter.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
            }

            Transaction trans = loadTransaction(transId);
            if (trans == null) {
                return WebParameter.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
            }
            Integer providerId = trans.getProviderId();
            if (providerId == null) {
                return WebParameter.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
            }
            Provider provider = providerService.loadProvider(providerId);
            String ips = provider.getIps();
            String ip = providerParameter.getIpRequest();
            if (ips == null) {
                return WebParameter.INVALID_UPDATE + WebParameter.SAI_DIA_CHI_IP.split("\\|")[1];
            }
            String[] ipList = ips.split(";");
            for (int i = 0; i < ipList.length; i++) {
                String ipPartner = ipList[i];
                if (ip.equalsIgnoreCase(ipPartner)) {
                    break;
                }
                if (i == (ipList.length - 1) && !ipPartner.equalsIgnoreCase(ip)) {
                    return WebParameter.INVALID_UPDATE + WebParameter.SAI_DIA_CHI_IP.split("\\|")[1];
                }
            }
            String signature = ServiceUtil.hashData(providerParameter.getType() + transationId + providerParameter.getAmount() + provider.getSecretKey() + provider.getProviderId());
            if (!signature.equals(providerParameter.getSignature())) {
                return WebParameter.INVALID_UPDATE + WebParameter.SAI_CHU_KY.split("\\|")[1];

            }
            if (!trans.getStatus().equals(Transaction.CREATE_TRANS)) {
                return WebParameter.INVALID_UPDATE + WebParameter.GIAO_DICH_DA_XAC_THUC[1];
            }
            //tim cardId
            String issuerName = "";

            if (providerParameter.getType().intValue() == ProviderParameter.TELCO) {
                switch (providerParameter.getIssuerId().intValue()) {
                    case 1:
                        issuerName = Issuer.MOBIPHONE;
                        break;
                    case 2:
                        issuerName = Issuer.VINAPHONE;
                        break;
                    case 3:
                        issuerName = Issuer.VIETTEL;
                        break;
                }
            } else if (providerParameter.getType().intValue() == ProviderParameter.GATE) {
                issuerName = Issuer.GATE;
            } else if (providerParameter.getType().intValue() == ProviderParameter.VTC) {
                issuerName = Issuer.VTC_COIN;
            } else {
                return WebParameter.INVALID_UPDATE + WebParameter.MA_DICH_VU_KHONG_HOP_LE.split("\\|")[1];
            }
            Map map = new HashMap();
            map.put("issuerName", issuerName);
            map.put("amount", providerParameter.getAmount());
            Integer cardId = getCardIdFromIssuerNameAndAmunt(map);
            trans.setCardId(cardId.toString());
            if (providerParameter.getAmount() == 0) {
                trans.setStatus(Transaction.INVALID_UPDATE);
            } else {
                trans.setStatus(Transaction.VALID_UPDATE);
            }
            //update
            updateTransactionByProvider(trans);
        } catch (Exception ex) {
            ex.printStackTrace();
            return WebParameter.INVALID_UPDATE + WebParameter.LOI_KHONG_XAC_DINH.split("\\|")[1];
        }
        return WebParameter.VALID_UPDATE;
    }

    private Transaction loadTransaction(Integer transactionId) {
        try {
            return (Transaction) mysqlMap.queryForObject("transaction.loadTransaction", transactionId);
        } catch (SQLException ex) {
            throw new CardServiceDBException(ex);
        }
    }

    private Integer getCardIdFromIssuerNameAndAmunt(Map map) {
        try {
            return (Integer) mysqlMap.queryForObject("transaction.getCardIdFromIssuerNameAndAmunt", map);
        } catch (SQLException ex) {
            throw new CardServiceDBException(ex);
        }
    }

    private void updateTransactionByProvider(Transaction trans) {
        try {
            mysqlMap.update("transaction.updateTransactionByProvider", trans);
        } catch (SQLException ex) {
            throw new CardServiceDBException();
        }
    }

    public void setProviderService(IProviderService providerService) {
        this.providerService = providerService;
    }

    public List<Transaction> getListTransactionFailed(int start, int end) {
        try {
            Map map = new HashMap();
            map.put("start", start);
            map.put("end", end);
            return mysqlMap.queryForList("transaction.selectAllTransactionFailed", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public int countTransactionFailed() {
        try {
            Connection cnn = mysqlMap.getDataSource().getConnection();
            String sql = " select count(*) total from transaction t " +
                    " inner join card c " +
                    " on c.cardId = t.cardId " +
                    " and  t.responseStatus = '01' " +
                    " and c.par = 0 " +
                    " and date(requestTime) = date(CURRENT_TIMESTAMP) " +
                    " and substr(responseToPartner,24) REGEXP '^-?[0-9]+$' ";
            PreparedStatement stmt = cnn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int row = 0;
            while (rs.next()) {
                row = rs.getInt("total");
            }
            return row;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public void updateCardId(Transaction tran) {
        try {
            Integer cardId = (Integer) mysqlMap.queryForObject("card.getCardIdByIssuerAndPar", tran);
            tran.setCardId(String.valueOf(cardId));
            mysqlMap.update("transaction.updateCardId", tran);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }
}

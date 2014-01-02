package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.exception.DuplicatedRequestRefException;
import com.vss.cardservice.service.exception.InvalidCardCodeException;
import com.vss.cardservice.service.util.BaseService;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author zannami
 *
 * Jul 13, 2011 4:55:27 PM
 */
public class TransactionServiceImpl extends BaseService implements ITransactionService {

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    private IProviderService providerService;

    public Long createTransaction(Transaction tran) throws Exception {
        checkTransaction(tran);
        tran.setCardId(String.valueOf(ServiceUtil.getCardId(tran.getIssuer(), "0")));
        return (Long) mysqlMap.insert("transaction.createTransaction", tran);
    }

    private void checkTransaction(Transaction transaction) {
        Map map = new HashMap();
        map.put("cardCode", transaction.getCardCode());
        map.put("cardSerial", transaction.getCardSerial());
        map.put("issuerId", ServiceUtil.issuerMap.get(transaction.getIssuer()));
        map.put("partnerId", transaction.getPartnerId());
        map.put("transRefId", transaction.getTransRefId());
        Transaction tran;
        try {
            tran = (Transaction) mysqlMap.queryForObject("transaction.getStatusByCardCode", map);
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        if (tran != null) {
            if (tran.getPartnerId().equals(transaction.getPartnerId()) && tran.getTransRefId().equalsIgnoreCase(transaction.getTransRefId())) {
                throw new DuplicatedRequestRefException("[Trung ma giao dich]");
            } else {
                throw new InvalidCardCodeException("[Ma the da duoc su dung]");
            }
        }
    }

    private String getStatus(String responseStatus) {
        if (responseStatus == null
                || responseStatus.equals(WebParameter.CONNECTION_TIMEOUT)
                || responseStatus.equals(WebParameter.GIAO_DICH_NGHI_VAN)
                || responseStatus.equals(WebParameter.LOI_GOI_HAM_PROVIDER)
                || responseStatus.equals(WebParameter.LOI_KET_NOI_PROVIDER)
                || responseStatus.equals(WebParameter.LOI_KHONG_XAC_DINH)
                || responseStatus.equals(WebParameter.CONNECTION_TIMEOUT)
                || responseStatus.equals(WebParameter.CONNECTION_TIMEOUT)) {
            return UNIDENTIFIED_RESULT;
        } else {
            return CLEAR_RESULT;
        }
    }

    public Boolean updateTransaction(Transaction tran) {
        try {
            Integer cardId = null;
            String amount = tran.getAmount();
            if (amount != null) {
                try {
                    cardId = ServiceUtil.getCardId(tran.getIssuer().trim(), amount);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            StringBuilder sb = new StringBuilder("UPDATE transaction SET status = ");
            sb.append(getStatus(tran.getResponseStatus()));
            if (tran.getResponseTime() != null) {
                sb.append(",responseTime = '");
                sb.append(df.format(new Date()));
                sb.append("'");
            }
            if (tran.getResponseStatus() != null) {
                sb.append(",responseStatus = '");
                sb.append(tran.getResponseStatus());
                sb.append("'");
            }
            if (tran.getResponseDescription() != null) {
                sb.append(",responseDescription = '");
                sb.append(tran.getResponseDescription());
                sb.append("'");
            }
            if (cardId != null) {
                sb.append(",cardId = ");
                sb.append(cardId);
            }
            if (tran.getUseCardResponse() != null) {
                sb.append(",useCardResponse ='");
                sb.append(tran.getUseCardResponse().length() < 100 ? tran.getUseCardResponse() : tran.getUseCardResponse().substring(0, 100));
                sb.append("'");
            }
            if (tran.getResponseToPartner() != null) {
                sb.append(",responseToPartner ='");
                sb.append(tran.getResponseToPartner());
                sb.append("'");
            }
            if (tran.getTelcoTransRefId() != null) {
                sb.append(",telcoTransRefId ='");
                sb.append(tran.getTelcoTransRefId());
                sb.append("'");
            }
            if (tran.getAccountId() != null) {
                sb.append(",accountId ='");
                sb.append(tran.getAccountId());
                sb.append("'");
            }
            if (tran.getProviderId() != null && tran.getProviderId() > 0) {
                sb.append(",providerId=");
                sb.append(tran.getProviderId());
            }
            sb.append(" WHERE transactionId =");
            sb.append(tran.getTransactionId());
            int count = mysqlMap.update("transaction.updateTransaction", sb.toString());
            return (count > 0);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public Transaction getTransactionDetail(Integer partnerId, String transRef) {
        try {
            Map map = new HashMap();
            map.put("partnerId", partnerId);
            map.put("transRefId", transRef);
            return (Transaction) mysqlMap.queryForObject("transaction.getTransactionDetail", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }
//    public List<Transaction> getListTransactionByDay(int transType, int issuerId, int start, int end) {
//        try {
//            Map map = new HashMap();
//            map.put("transType", transType);
//            map.put("issuerId", issuerId);
//            map.put("start", start);
//            map.put("end", end);
//            return mysqlMap.queryForList("transaction.getListTransactionByDay", map);
//        } catch (Exception e) {
//            throw new CardServiceDBException(e);
//        }
//    }
//
//    public int countTransactionByDay(int transType, int issuerId) {
//        try {
//            Map map = new HashMap();
//            map.put("transType", transType);
//            map.put("issuerId", issuerId);
//            return (Integer) mysqlMap.queryForObject("transaction.countTransactionByDay", map);
//        } catch (Exception e) {
//            throw new CardServiceDBException(e);
//        }
//    }
//
//    public String providerUpdateTransaction(ProviderParameter providerParameter) {
//        try {
//            String transationId = providerParameter.getTransactionId();
//            if (transationId == null || transationId.isEmpty()) {
//                return ServiceUtil.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
//            }
//            Integer transId;
//            try {
//                transId = Integer.parseInt(transationId);
//            } catch (NumberFormatException ex) {
//                return ServiceUtil.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
//            }
//
//            Transaction trans = loadTransaction(transId);
//            if (trans == null) {
//                return ServiceUtil.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
//            }
//            Integer providerId = trans.getProviderId();
//            if (providerId == null) {
//                return ServiceUtil.INVALID_UPDATE + WebParameter.MA_GIAO_DICH_KHONG_TON_TAI.split("\\|")[1];
//            }
//            Provider provider = providerService.loadProvider(providerId);
//            String ips = provider.getIps();
//            String ip = providerParameter.getIpRequest();
//            if (ips == null) {
//                return ServiceUtil.INVALID_UPDATE + WebParameter.SAI_DIA_CHI_IP.split("\\|")[1];
//            }
//            String[] ipList = ips.split(";");
//            for (int i = 0; i < ipList.length; i++) {
//                String ipPartner = ipList[i];
//                if (ip.equalsIgnoreCase(ipPartner)) {
//                    break;
//                }
//                if (i == (ipList.length - 1) && !ipPartner.equalsIgnoreCase(ip)) {
//                    return ServiceUtil.INVALID_UPDATE + WebParameter.SAI_DIA_CHI_IP.split("\\|")[1];
//                }
//            }
//            String signature = ServiceUtil.hashData(providerParameter.getType() + transationId + providerParameter.getAmount() + provider.getSecretKey() + provider.getProviderId());
//            if (!signature.equals(providerParameter.getSignature())) {
//                return ServiceUtil.INVALID_UPDATE + WebParameter.SAI_CHU_KY.split("\\|")[1];
//
//            }
//            if (!trans.getStatus().equals(CREATE_TRANS)) {
//                return ServiceUtil.INVALID_UPDATE + ServiceUtil.GIAO_DICH_DA_XAC_THUC[1];
//            }
//            //tim cardId
//            String issuerName = "";
//
//            if (providerParameter.getType().intValue() == ProviderParameter.TELCO) {
//                switch (providerParameter.getIssuerId().intValue()) {
//                    case 1:
//                        issuerName = Issuer.MOBIPHONE;
//                        break;
//                    case 2:
//                        issuerName = Issuer.VINAPHONE;
//                        break;
//                    case 3:
//                        issuerName = Issuer.VIETTEL;
//                        break;
//                }
//            } else if (providerParameter.getType().intValue() == ProviderParameter.GATE) {
//                issuerName = Issuer.GATE;
//            } else if (providerParameter.getType().intValue() == ProviderParameter.VTC) {
//                issuerName = Issuer.VTC_COIN;
//            } else {
//                return ServiceUtil.INVALID_UPDATE + WebParameter.MA_DICH_VU_KHONG_HOP_LE.split("\\|")[1];
//            }
//            Map map = new HashMap();
//            map.put("issuerName", issuerName);
//            map.put("amount", providerParameter.getAmount());
//            Integer cardId = getCardIdFromIssuerNameAndAmunt(map);
//            trans.setCardId(cardId.toString());
//            if (providerParameter.getAmount() == 0) {
//                trans.setStatus(INVALID_UPDATE);
//            } else {
//                trans.setStatus(VALID_UPDATE);
//            }
//            //update
//            updateTransactionByProvider(trans);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return ServiceUtil.INVALID_UPDATE + WebParameter.LOI_KHONG_XAC_DINH.split("\\|")[1];
//        }
//        return ServiceUtil.VALID_UPDATE;
//    }
//    private Transaction loadTransaction(Integer transactionId) {
//        try {
//            return (Transaction) mysqlMap.queryForObject("transaction.loadTransaction", transactionId);
//        } catch (SQLException ex) {
//            throw new CardServiceDBException(ex);
//        }
//    }
//    private Integer getCardIdFromIssuerNameAndAmunt(Map map) {
//        try {
//            return (Integer) mysqlMap.queryForObject("transaction.getCardIdFromIssuerNameAndAmunt", map);
//        } catch (SQLException ex) {
//            throw new CardServiceDBException(ex);
//        }
//    }
//
//    private void updateTransactionByProvider(Transaction trans) {
//        try {
//            mysqlMap.update("transaction.updateTransactionByProvider", trans);
//        } catch (SQLException ex) {
//            throw new CardServiceDBException();
//        }
//    }
//    public void setProviderService(IProviderService providerService) {
//        this.providerService = providerService;
//    }
//    public List<Transaction> getListTransactionFailed(int start, int end) {
//        try {
//            Map map = new HashMap();
//            map.put("start", start);
//            map.put("end", end);
//            return mysqlMap.queryForList("transaction.selectAllTransactionFailed", map);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CardServiceDBException(e);
//        }
//    }
//
//    public int countTransactionFailed() {
//        try {
//            Connection cnn = mysqlMap.getDataSource().getConnection();
//            String sql = " select count(*) total from transaction t "
//                    + " inner join card c "
//                    + " on c.cardId = t.cardId "
//                    + " and  t.responseStatus = '01' "
//                    + " and c.par = 0 "
//                    + " and date(requestTime) = date(CURRENT_TIMESTAMP) "
//                    + " and substr(responseToPartner,24) REGEXP '^-?[0-9]+$' ";
//            PreparedStatement stmt = cnn.prepareStatement(sql);
//            ResultSet rs = stmt.executeQuery();
//            int row = 0;
//            while (rs.next()) {
//                row = rs.getInt("total");
//            }
//            return row;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CardServiceDBException(e);
//        }
//    }
//    public void updateCardId(Transaction tran) {
//        try {
//            Integer cardId = (Integer) mysqlMap.queryForObject("card.getCardIdByIssuerAndPar", tran);
//            tran.setCardId(String.valueOf(cardId));
//            mysqlMap.update("transaction.updateCardId", tran);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new CardServiceDBException(e);
//        }
//    }
}

package com.vss.cardservice.service.impl;

import com.elcom.vchgws.message.LoadResponse;
import com.elcom.vchgws.message.LoginResponse;
import com.vss.cardservice.api.ICardService;
import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.api.IIssuerService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.service.exception.CardServiceException;
import com.vss.cardservice.service.util.CardServiceLocalServiceUtil;
import com.vss.cardservice.service.util.MailServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.WebParameter;
import com.vss.message.util.LoggingUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import javax.xml.bind.JAXBElement;
import vcardserverproxy.VCardServerProxy;

/**
 * 
 * @author zannami
 * 
 *         Jul 13, 2011 4:42:11 PM
 */
public class VinaCardServiceImpl implements ICardService {

    IGameAccountService gameAccountService = CardServiceLocalServiceUtil.getGameAccountService();
    private SimpleDateFormat fm = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    IIssuerService issuerService = CardServiceLocalServiceUtil.getIssuerService();
    final String ISSUER_NAME = "VINA";

    public Transaction useCard(Transaction tran) {
        try {
            String sessionId = WebParameter.vinaSessionValue;
            if (sessionId == null) {
                sessionId = loginToTelco();
            }
            if (sessionId != null) {
                String account = "";
                try {
                    Random randomGenerator = new Random();
                    Integer accountId = randomGenerator.nextInt(238750);
                    account = ServiceUtil.getGameAccountList(gameAccountService).get(accountId);
                } catch (Exception e) {
                    e.printStackTrace();
                    account = String.valueOf(tran.getTransactionId());
                }
                if (account == null || account.isEmpty()) {
                    account = String.valueOf(tran.getTransactionId());
                }
                tran.setAccountId(account);
                LoadResponse load = VCardServerProxy.loadNew(sessionId, tran.getCardCode(), account, Integer.parseInt(tran.getTransactionId()), tran.getCardSerial());
                load.getDRemainAmount();
                Integer status = load.getStatus();
                JAXBElement<String> msg = load.getMessage();
                String transactionMessage;
                if (msg != null) {
                    transactionMessage = msg.getValue();
                } else {
                    transactionMessage = "";
                }
                LoggingUtil.log(fm.format(new Date()) + " [** VINA **] Usecard:" + tran.getCardCode() + "|status:" + status + "|msg:" + transactionMessage + "|resonseStatus:" + tran.getResponseStatus()+"|vinaSessionValue:" + WebParameter.vinaSessionValue, "useCard_transaction");
                if (load.getTransid() != null) {
                    tran.setTelcoTransRefId(String.valueOf(load.getTransid()));
                }
                if (transactionMessage.indexOf("username/pass") > -1) {
                    if (WebParameter.loginAndUpdate) {
                        try {
                            VCardServerProxy.logout(sessionId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    WebParameter.vinaSessionValue = null;
                    useCard(tran);
                }

                if (load.getDRemainAmount() == null || String.valueOf(load.getDRemainAmount()).equals("0.0")) {
                    if (transactionMessage.indexOf("Account cua ban da bi khoa") > 0 || transactionMessage.indexOf("SYSTEM OVERLOAD") > -1 || transactionMessage.indexOf("NOT_DISTRIBUTED") > -1 || transactionMessage.indexOf("Co loi trong qua trinh xu ly") > -1) {
                        tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
                        tran.setUseCardResponse(load.getTransid() + "|" + load.getStatus() + "|" + transactionMessage == null ? "" : transactionMessage);
                    } else {
//                        if (transactionMessage.indexOf("USED_BEFORE") > -1 || transactionMessage.indexOf("NO_SUCH_CARD_IN_DATABASE") > -1) {
//                            tran.setStatus("2"); // card invalid
//                            tran.setTelcoResponse(true);
//                        }
                        if (transactionMessage.indexOf("USED_BEFORE") > -1) {
                            tran.setResponseStatus(WebParameter.THE_DA_DUOC_SU_DUNG);
                            tran.setStatus("2"); // card invalid
                            tran.setTelcoResponse(true);
                        } else if (transactionMessage.indexOf("TERMINATED_BEFORE") > -1) {
                            tran.setResponseStatus(WebParameter.THE_HET_HAN_SU_DUNG);
                            tran.setStatus("2"); // card invalid
                            tran.setTelcoResponse(true);
                        } else {
                            tran.setResponseStatus(WebParameter.THE_KHONG_TON_TAI);
                        }
                        tran.setUseCardResponse(load.getTransid() + "|" + load.getStatus() + "|" + transactionMessage == null ? "" : transactionMessage);
                    }
                } else {
                    tran.setStatus("3");
                    tran.setAmount(String.valueOf(load.getDRemainAmount().intValue()));
                    tran.setUseCardResponse(load.getTransid() + "|" + load.getStatus() + "|" + load.getMessage().getValue() + "|" + load.getDRemainAmount());
                    tran.setResponseStatus(WebParameter.GIAO_DICH_THANH_CONG);
                    tran.setCardSerial(load.getSSerialNumber().getValue());
                }
            } else {
                tran.setResponseStatus(WebParameter.LOI_KET_NOI_SERVER);
            }
//            System.out.println("*** ResponseStatus : "+tran.getResponseStatus());
            int pos = tran.getResponseStatus().indexOf("|");
            if (pos != -1) {
                tran.setResponseStatus(tran.getResponseStatus().substring(0, pos));
            }
            if (tran.getUseCardResponse().length() > 100) {
                tran.setUseCardResponse(tran.getUseCardResponse().substring(0, 96) + "...");
            }
            tran.setResponseTime(new Date());
            return tran;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException(e);
        }
    }

    public Transaction checkCard(Transaction tran) {
        tran.setResponseStatus(WebParameter.DICH_VU_CHUA_DUOC_HO_TRO);
        return tran;
    }
//

    public String loginToTelco() {
        try {
            if (!WebParameter.loginAndUpdate) {
                Issuer issuer = issuerService.getIssuer(ISSUER_NAME);
                LoggingUtil.log(fm.format(new Date()) + " |Capture sessionValue from database: " + issuer.getSessionValue(), "useCard_transaction");
                WebParameter.vinaSessionValue = issuer.getSessionValue();
            } else {
                LoginResponse login = VCardServerProxy.login();
                int status = login.getStatus();
                if (status == 0) {
                    JAXBElement<String> sessionid = login.getSessionid();
                    WebParameter.vinaSessionValue = sessionid.getValue();
                    LoggingUtil.log(fm.format(new Date()) + " |Login to vinaphone with sessionid: " + WebParameter.vinaSessionValue, "useCard_transaction");
                    LoggingUtil.log(fm.format(new Date()) + " |WebParameter.vinaSessionValue:" + WebParameter.vinaSessionValue, "useCard_transaction");
                    if (WebParameter.vinaSessionValue != null) {
                        issuerService.updateSessionValue(ISSUER_NAME, WebParameter.vinaSessionValue);
                    }
                } else {
                    LoggingUtil.log(fm.format(new Date()) + " |Login false to vinaphone with message: " + login.getMessage().getValue(), "useCard_transaction");
                    MailServiceUtil.mailAlert(ServiceUtil.mailSubject, ServiceUtil.getString("login_error_content").replace("telco", "VINAPHONE"));
                    WebParameter.vinaSessionValue = null;
                }
            }
            return WebParameter.vinaSessionValue;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceException(e);
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.api.ITransactionService;
import com.vss.cardservice.dto.Partner;
import com.vss.cardservice.dto.Transaction;
import com.vss.cardservice.dto.UseCardInfo;
import com.vss.cardservice.service.util.ClassLoaderUtil;
import com.vss.cardservice.service.util.PartnerServiceUtil;
import com.vss.cardservice.service.util.ServiceUtil;
import com.vss.cardservice.service.util.TransactionServiceUtil;
import java.util.Date;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;

/**
 *
 * @author zannami
 *
 */
@Controller
@SessionAttributes
public class CardServiceController {

    private static Logger logger = Logger.getLogger("CardServiceController");

    @RequestMapping(value = "/useCard", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView useCard(@ModelAttribute("useCardInfo") UseCardInfo useCardInfo, BindingResult result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String responseToPartner = new VoucherServiceUtil().useCard(useCardInfo.getIssuer(), useCardInfo.getCardSerial(), useCardInfo.getCardCode(),
                useCardInfo.getTransRef(), useCardInfo.getPartnerCode(), useCardInfo.getPassword(), useCardInfo.getSignature(), request);
        useCardInfo.setResponseToPartner(responseToPartner);
        return new ModelAndView("useCardInfo", "command", useCardInfo);
    }

//    @RequestMapping("/useCardInfo")
//    public ModelAndView showUseCardForm(@ModelAttribute("useCardInfo") UseCardInfo useCardInfo) {
//        return new ModelAndView("useCardInfo", "command", useCardInfo);
//    }
    @RequestMapping(value = "/getTransactionDetail", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView getTransactionDetail(@ModelAttribute("useCardInfo") UseCardInfo useCardInfo, BindingResult result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String responseToPartner = new VoucherServiceUtil().getTransactionDetail(useCardInfo.getTransRef(), useCardInfo.getPartnerCode(),
                useCardInfo.getPassword(), useCardInfo.getSignature(), request);
        useCardInfo.setResponseToPartner(responseToPartner);
        return new ModelAndView("useCardInfo", "command", useCardInfo);
    }

    @RequestMapping(value = "/*/updateTransaction", method = {RequestMethod.POST, RequestMethod.GET})
    public ModelAndView updateTransaction(HttpServletRequest request) {
        Map<String, String[]> parameters = request.getParameterMap();
        String queryString = request.getQueryString();
        StringTokenizer st = new StringTokenizer(request.getServletPath(), "/");
        UseCardInfo useCardInfo = new UseCardInfo();
        StringBuilder sb = new StringBuilder(200);
        sb.append(VoucherServiceUtil.df.format(new Date()));
        sb.append("|Update transaction : ");
        sb.append(request.getRequestURL()).append("?").append(request.getQueryString()).append("  : ");
        try {
            Integer partnerId = Integer.parseInt(st.nextToken());
//            Partner partner = PartnerServiceUtil.loadPartnerById(partnerId);
            Partner partner = ServiceUtil.providerCollection.get(partnerId);
            if (partner != null) {
                Transaction transaction = ClassLoaderUtil.updateTransaction(partner, parameters);
                if (transaction.getTransactionId() == null) {
                    useCardInfo.setResponseToPartner(transaction.getCheckCardResponse());
                } else {
                    Transaction oldTransaction = TransactionServiceUtil.loadTransaction(transaction.getTransactionId());
                    if (oldTransaction == null) {
                        sb.append(" Giao dich khong ton tai");
                    } else if (!oldTransaction.getProviderId().equals(partnerId)) {
                        sb.append(" Giao dich cua provider ");
                        sb.append(oldTransaction.getProviderId());
                    } else if (oldTransaction.getStatus().equals(ITransactionService.CLEAR_RESULT)) {
                        sb.append("Giao dich cu da co ket qua ro rang");
                    } else {
                        transaction.setIssuerId(oldTransaction.getIssuerId());
                        transaction.setCheckCardResponse(queryString);
                        TransactionServiceUtil.updateTransaction(transaction, true);
                        useCardInfo.setResponseToPartner(transaction.getResponseToPartner());
                        sb.append("Cap nhat thanh cong");
                    }
                }
                sb.append(" : ");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        sb.append(useCardInfo.getResponseToPartner());
        logger.info(sb.toString());
        return new ModelAndView("useCardInfo", "useCardInfo", useCardInfo);
    }
}

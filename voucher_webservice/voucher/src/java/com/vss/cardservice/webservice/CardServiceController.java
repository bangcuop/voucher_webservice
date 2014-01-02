/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.dto.UseCardInfo;
import javax.servlet.http.HttpServletRequest;
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
}

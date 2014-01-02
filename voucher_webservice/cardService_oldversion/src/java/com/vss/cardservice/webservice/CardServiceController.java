/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.dto.UseCardInfo;
import java.text.SimpleDateFormat;
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

    VoucherService voucherService = new VoucherService();
    SimpleDateFormat fm = new SimpleDateFormat("yyyy/MM/dd");

    @RequestMapping(value = "/useCard", method = RequestMethod.POST)
    public ModelAndView useCard(@ModelAttribute("useCardInfo") UseCardInfo useCardInfo, BindingResult result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        voucherService.setReq(request);
        String responseToPartner = voucherService.useCard(useCardInfo.getIssuer(), useCardInfo.getCardSerial(), useCardInfo.getCardCode(),
                useCardInfo.getTransRef(), useCardInfo.getPartnerCode(), useCardInfo.getPassword(), useCardInfo.getSignature());
        useCardInfo.setResponseToPartner(responseToPartner);
        return new ModelAndView("useCardInfo", "command", useCardInfo);
    }

//    @RequestMapping("/useCardInfo")
//    public ModelAndView showUseCardForm(@ModelAttribute("useCardInfo") UseCardInfo useCardInfo) {
//        return new ModelAndView("useCardInfo", "command", useCardInfo);
//    }

    @RequestMapping(value = "/getTransactionDetail", method = RequestMethod.POST)
    public ModelAndView getTransactionDetail(@ModelAttribute("useCardInfo") UseCardInfo useCardInfo, BindingResult result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        voucherService.setReq(request);
        String responseToPartner = voucherService.getTransactionDetail(useCardInfo.getTransRef(), useCardInfo.getPartnerCode(),
                useCardInfo.getPassword(), useCardInfo.getSignature());
        useCardInfo.setResponseToPartner(responseToPartner);
        return new ModelAndView("useCardInfo", "command", useCardInfo);
    }
}

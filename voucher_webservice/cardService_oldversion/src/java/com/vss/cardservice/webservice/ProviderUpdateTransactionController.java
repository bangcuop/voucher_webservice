/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import com.vss.cardservice.dto.ProviderParameter;
import com.vss.cardservice.service.util.CardServiceLocalServiceUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author thibt
 */
@Controller
@SessionAttributes
public class ProviderUpdateTransactionController {

    @RequestMapping(value = "/providerUpdateTransaction", method = RequestMethod.POST)
    public ModelAndView providerUpdateTransaction(@ModelAttribute("ProviderParameter") ProviderParameter providerParameter, BindingResult result) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getRemoteAddr());
        providerParameter.setIpRequest(request.getRemoteAddr());
        String response = CardServiceLocalServiceUtil.getTransactionService().providerUpdateTransaction(providerParameter);
//        System.out.println(response);
        providerParameter.setUpdateTransResponse(response);
        return new ModelAndView("updateTransactionResponse", "command", providerParameter);
    }
}

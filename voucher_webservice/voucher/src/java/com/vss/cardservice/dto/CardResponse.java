/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.dto;

import com.vss.cardservice.service.util.WebParameter;

/**
 *
 * @author zannami
 */
public class CardResponse {

    String response;
    String partnerCode;
    String transRefId;

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getResponse() {
        if(response==null || response.isEmpty()){
            return WebParameter.LOI_GOI_HAM_PROVIDER;
        }
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getTransRefId() {
        return transRefId;
    }

    public void setTransRefId(String transRefId) {
        this.transRefId = transRefId;
    }
}

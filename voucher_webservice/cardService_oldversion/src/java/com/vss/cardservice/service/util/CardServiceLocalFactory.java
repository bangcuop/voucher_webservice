/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.ICardService;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class CardServiceLocalFactory {

    private Map<String, ICardService> mapService;

    public ICardService getCardService(String serviceLocal) {
        if(ServiceUtil.invokeToTelco==null || ServiceUtil.invokeToTelco.isEmpty()){
            ServiceUtil.invokeToTelco = "all";
        }
        if (ServiceUtil.invokeToTelco.equals("all")) {
            return (ICardService) mapService.get(serviceLocal);
        } else {
            return (ICardService) mapService.get(ServiceUtil.invokeToTelco);
        }
    }

    public Map<String, ICardService> getMapService() {
        return mapService;
    }

    public void setMapService(Map<String, ICardService> mapService) {
        this.mapService = mapService;
    }
}

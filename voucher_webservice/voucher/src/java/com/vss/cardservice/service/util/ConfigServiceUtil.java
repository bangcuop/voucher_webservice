/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.api.IConfigService;

/**
 *
 * @author liemnh
 */
public class ConfigServiceUtil {

    private static IConfigService configService;

    public IConfigService getConfigService() {
        return configService;
    }

    public void setConfigService(IConfigService configService) {
        ConfigServiceUtil.configService = configService;
    }

    public static String getValueFromKey(String key) throws Exception {
        return configService.getValueFromKey(key);
    }
}

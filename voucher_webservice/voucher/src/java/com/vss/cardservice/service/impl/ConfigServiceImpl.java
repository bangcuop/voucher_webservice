/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.IConfigService;
import com.vss.cardservice.service.util.BaseService;

/**
 *
 * @author liemnh
 */
public class ConfigServiceImpl extends BaseService implements IConfigService {

    public String getValueFromKey(String key) throws Exception {
        return (String) mysqlMap.queryForObject("Config.getValueFromKey", key);
    }
}

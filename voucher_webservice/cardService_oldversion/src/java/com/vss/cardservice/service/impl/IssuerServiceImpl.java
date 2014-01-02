/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.IIssuerService;
import com.vss.cardservice.dto.Issuer;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.util.BaseService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class IssuerServiceImpl extends BaseService implements IIssuerService {

    public List<Issuer> getListIssuer(Integer issuerId) {
        try {
            Map map = new HashMap();
            if (issuerId > 0) {
                map.put("issuerid", issuerId);
            }
            return mysqlMap.queryForList("issuer.getListIssuer", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public void updateSessionValue(String name, String sessionValue) {
        try {
            Map map = new HashMap();
            if (name != null) {
                map.put("name", name);
            }
            if (sessionValue != null) {
                map.put("sessionValue", sessionValue);
            }
            mysqlMap.update("issuer.updateSessionValue", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public Issuer getIssuer(String name) {
        try {
            Map map = new HashMap();
            if (name != null) {
                map.put("name", name);
            }
            return (Issuer) mysqlMap.queryForObject("issuer.getListIssuer", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }
}

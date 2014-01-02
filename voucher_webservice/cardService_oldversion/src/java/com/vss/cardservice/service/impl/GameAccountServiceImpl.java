/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.util.BaseService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class GameAccountServiceImpl extends BaseService implements IGameAccountService {

    public String getAccountName(int accountId) {
        Map map = new HashMap();
        try {
            map.put("accountId", accountId);
            return (String)mysqlMap.queryForObject("Account.getAccountById", map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }

    public List<String> getAccountList() {
        try {
            return mysqlMap.queryForList("Account.getAccountList");
        } catch (Exception e) {
            e.printStackTrace();
            throw new CardServiceDBException(e);
        }
    }
}

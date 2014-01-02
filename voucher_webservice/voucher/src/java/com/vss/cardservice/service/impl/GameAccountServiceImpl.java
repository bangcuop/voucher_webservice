/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.impl;

import com.vss.cardservice.api.IGameAccountService;
import com.vss.cardservice.dto.GameService;
import com.vss.cardservice.service.exception.CardServiceDBException;
import com.vss.cardservice.service.util.BaseService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author zannami
 */
public class GameAccountServiceImpl extends BaseService implements IGameAccountService {

    private Map<Integer, List<String>> gameServiceMap = new HashMap<Integer, List<String>>();

    public String getAccountName(int accountId) {
        Map map = new HashMap();
        try {
            map.put("accountId", accountId);
            return (String) mysqlMap.queryForObject("Account.getAccountById", map);
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

    public String getRandomGameService(Integer partnerId) {
        List<String> serviceList = gameServiceMap.get(partnerId);
        if (serviceList == null) {
            return "paydirect";
        } else {
            return serviceList.get(new Long(Math.round(serviceList.size() * Math.random())).intValue());
        }
    }

    public void refreshGameService() {
        try {
            List<GameService> list = mysqlMap.queryForList("Account.getGameServiceList");
            if (list.isEmpty()) {
                return;
            }
            List<String> gameNameList = new ArrayList<String>();
            int i = 0;
            int partnerId = 0;
            GameService gameService = list.get(0);
            gameNameList.add(gameService.getGameName());

            while (++i < list.size()) {
                gameService = list.get(i);
                if (!gameService.getPartnerId().equals(partnerId)) {
                    gameServiceMap.put(partnerId, gameNameList);
                    partnerId = gameService.getPartnerId();
                    gameNameList = new ArrayList();
                } 
                gameNameList.add(gameService.getGameName());
            }
            gameServiceMap.put(partnerId, gameNameList);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

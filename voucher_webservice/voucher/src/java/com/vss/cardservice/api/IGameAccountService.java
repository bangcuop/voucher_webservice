/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.api;

import java.util.List;

/**
 *
 * @author zannami
 */
public interface IGameAccountService {

    public String getAccountName(int accountId);

    public List<String> getAccountList();

    public void refreshGameService();

    public String getRandomGameService(Integer partnerId);
}

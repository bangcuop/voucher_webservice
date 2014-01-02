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
    String getAccountName(int accountId);
    List<String> getAccountList();

}

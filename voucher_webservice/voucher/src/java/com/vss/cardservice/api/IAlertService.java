/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.api;

/**
 *
 * @author zannami
 */
public interface IAlertService extends IMailService {

    void sendAlertMessage(String description, String message) throws Exception;

    void sendAlertMessages(String description[], String message) throws Exception;
}

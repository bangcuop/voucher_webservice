/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author zannami
 */
public class CardServiceDBException extends RuntimeException {

    public CardServiceDBException() {
        super();
    }

    public CardServiceDBException(String msg) {
        super(msg);
    }

    public CardServiceDBException(Exception ex) {
        super(ex);
    }
}

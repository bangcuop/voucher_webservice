/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paydirect.voucher.exception;

/**
 *
 * @author zannami
 */
public class CardServiceException extends RuntimeException {

    public CardServiceException() {
        super();
    }

    public CardServiceException(String msg) {
        super(msg);
    }

    public CardServiceException(Exception ex) {
        super(ex);
    }
}

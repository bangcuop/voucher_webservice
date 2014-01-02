/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author zannami
 */
public class PromotionServiceException extends RuntimeException {

    public PromotionServiceException() {
        super();
    }

    public PromotionServiceException(String msg) {
        super(msg);
    }

    public PromotionServiceException(Exception ex) {
        super(ex);
    }
}

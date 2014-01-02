/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.viettel.exception;

/**
 *
 * @author zannami
 */
public class ViettelServiceException extends RuntimeException {

    public ViettelServiceException(Exception ex) {
        super(ex);
    }
}

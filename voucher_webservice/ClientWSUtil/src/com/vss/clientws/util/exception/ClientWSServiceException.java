/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.util.exception;

/**
 *
 * @author zannami
 */
public class ClientWSServiceException extends RuntimeException {

    public ClientWSServiceException() {
        super();
    }

    public ClientWSServiceException(String msg) {
        super(msg);
    }

    public ClientWSServiceException(Exception ex) {
        super(ex);
    }
}

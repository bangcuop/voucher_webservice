/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.vss.cardservice.service.exception;

/**
 *
 * @author zannami
 */
public class RemoteServiceException extends RuntimeException {

    public RemoteServiceException() {
        super();
    }

    public RemoteServiceException(String msg) {
        super(msg);
    }

    public RemoteServiceException(Exception ex) {
        super(ex);
    }

}

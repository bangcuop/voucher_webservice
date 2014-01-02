/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author thibt
 */
public class ProccessFGameDtu {

    public static void throwExceptionFromErrorCode(String errorCode) throws Exception {
        System.out.println("throwExceptionFromErrorCode--------" + errorCode);
        switch (Integer.valueOf(errorCode)) {
            case 0:
                throw new ConnectionTimeoutException();
            case 1:
                throw new CardProcessingException();
            case 2:
                throw new UnknownIssuerException();
            case 3:
                throw new NumberFormatException();
            case 4:
                throw new IssuerProcessingException();
            case 5:
                throw new InvalidCardCodeException();
            case 6:
                throw new InvalidCardCodeException();
            case 7:
                throw new InvalidCardCodeException();
            case 8:
                throw new InvalidCardCodeException();
            case 9:
                throw new IssuerProcessingException();
            case 11:
                throw new CardProcessingException();
            case 99:
                throw new CardProcessingException();
            default:
                break;
        }
    }
}

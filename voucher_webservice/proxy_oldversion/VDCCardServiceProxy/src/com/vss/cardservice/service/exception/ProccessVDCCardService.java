/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.exception;

/**
 *
 * @author thibt
 */
public class ProccessVDCCardService {

    public static void throwExceptionFromErrorCode(int errorCode) throws Exception {
        System.out.println("throwExceptionFromErrorCode--------" + errorCode);
        switch (errorCode) {
            case -1://The da su dung
                throw new InvalidCardCodeException();
            case -2:    //the da khoa
                throw new LockedCardCodeException();
            case -3://the het han su dung
                throw new CardCodeExpiredException();
            case -4://The chua duoc kick hoat
                throw new CardCodeNotActiveException();
            case -10:
                throw new NumberFormatException();
            case -12:
                throw new InvalidCardCodeException();
            case 0:
                throw new TelcoSystemException();
            case -99:
                throw new TelcoSystemException();
            case 3:
                throw new TelcoSystemException();
            case 4:
                throw new InvalidCardCodeException();
            case 5:
                throw new TooManyErrorException();
            case 7:
                throw new TelcoSystemException();
            case 8://Giao dich nghi van
                throw new TransactionWaitUpdateException();
            case 9:
                throw new IssuerProcessingException();
            case 10:
                throw new InvalidFormatException();
            default:
                break;
        }
    }
}

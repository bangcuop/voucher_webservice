package com.homedirect.voucher.exception;

import java.io.PrintStream;

public class ProccessVDCCardService
{
  public static void throwExceptionFromErrorCode(int errorCode)
    throws Exception
  {
    System.out.println("throwExceptionFromErrorCode--------" + errorCode);
    switch (errorCode) {
    case -1:
      throw new InvalidCardCodeException();
    case -2:
      throw new LockedCardCodeException();
    case -3:
      throw new CardCodeExpiredException();
    case -4:
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
    case 8:
      throw new TransactionWaitUpdateException();
    case 9:
      throw new IssuerProcessingException();
    case 10:
      throw new InvalidFormatException();
    }
  }
}
package com.homedirect.voucher.exception;

public class CardServiceDBException extends RuntimeException
{
  public CardServiceDBException()
  {
  }

  public CardServiceDBException(String msg)
  {
    super(msg);
  }

  public CardServiceDBException(Exception ex) {
    super(ex);
  }
}
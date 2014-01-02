package com.paydirect.voucher.exception;

public class CardServiceException extends RuntimeException
{
  public CardServiceException()
  {
  }

  public CardServiceException(String msg)
  {
    super(msg);
  }

  public CardServiceException(Exception ex) {
    super(ex);
  }
}
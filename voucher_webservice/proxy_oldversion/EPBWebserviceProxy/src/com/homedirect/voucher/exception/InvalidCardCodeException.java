package com.homedirect.voucher.exception;

public class InvalidCardCodeException extends RuntimeException
{
  public InvalidCardCodeException()
  {
  }

  public InvalidCardCodeException(String msg)
  {
    super(msg);
  }
}
package com.homedirect.voucher.exception;

public class InvalidCardLengthException extends RuntimeException
{
  public InvalidCardLengthException()
  {
  }

  public InvalidCardLengthException(String msg)
  {
    super(msg);
  }
}
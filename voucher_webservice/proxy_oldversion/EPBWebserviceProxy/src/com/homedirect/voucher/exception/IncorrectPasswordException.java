package com.homedirect.voucher.exception;

public class IncorrectPasswordException extends RuntimeException
{
  public IncorrectPasswordException()
  {
  }

  public IncorrectPasswordException(String msg)
  {
    super(msg);
  }
}
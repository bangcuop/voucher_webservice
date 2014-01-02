package com.homedirect.voucher.exception;

public class UnknownIssuerException extends RuntimeException
{
  public UnknownIssuerException()
  {
  }

  public UnknownIssuerException(String msg)
  {
    super(msg);
  }
}
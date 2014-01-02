package com.homedirect.voucher.exception;

public class IssuerConnectionException extends RuntimeException
{
  public IssuerConnectionException()
  {
  }

  public IssuerConnectionException(String msg)
  {
    super(msg);
  }
}
package com.homedirect.voucher.exception;

public class IssuerBusyException extends RuntimeException
{
  public IssuerBusyException()
  {
  }

  public IssuerBusyException(String msg)
  {
    super(msg);
  }
}
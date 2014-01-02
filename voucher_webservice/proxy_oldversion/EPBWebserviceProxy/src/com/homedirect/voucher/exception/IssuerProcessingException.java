package com.homedirect.voucher.exception;

public class IssuerProcessingException extends RuntimeException
{
  public IssuerProcessingException()
  {
  }

  public IssuerProcessingException(String msg)
  {
    super(msg);
  }
}
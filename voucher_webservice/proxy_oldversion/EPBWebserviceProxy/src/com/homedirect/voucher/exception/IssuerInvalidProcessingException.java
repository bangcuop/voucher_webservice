package com.homedirect.voucher.exception;

public class IssuerInvalidProcessingException extends RuntimeException
{
  public IssuerInvalidProcessingException()
  {
  }

  public IssuerInvalidProcessingException(String msg)
  {
    super(msg);
  }
}
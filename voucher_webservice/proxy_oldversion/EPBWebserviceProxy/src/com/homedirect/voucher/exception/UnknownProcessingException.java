package com.homedirect.voucher.exception;

public class UnknownProcessingException extends RuntimeException
{
  public UnknownProcessingException()
  {
  }

  public UnknownProcessingException(String msg)
  {
    super(msg);
  }
}
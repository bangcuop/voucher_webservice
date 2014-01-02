package com.homedirect.voucher.exception;

public class InvalidSignatureException extends RuntimeException
{
  public InvalidSignatureException()
  {
  }

  public InvalidSignatureException(String msg)
  {
    super(msg);
  }
}
package com.homedirect.voucher.exception;

public class InvalidRequestRefException extends RuntimeException
{
  public InvalidRequestRefException()
  {
  }

  public InvalidRequestRefException(String msg)
  {
    super(msg);
  }
}
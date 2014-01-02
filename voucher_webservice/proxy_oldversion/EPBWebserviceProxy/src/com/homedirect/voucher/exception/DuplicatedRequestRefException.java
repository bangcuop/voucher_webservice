package com.homedirect.voucher.exception;

public class DuplicatedRequestRefException extends RuntimeException
{
  public DuplicatedRequestRefException()
  {
  }

  public DuplicatedRequestRefException(String msg)
  {
    super(msg);
  }
}
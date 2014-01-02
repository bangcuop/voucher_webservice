package com.homedirect.voucher.exception;

public class InvalidIpException extends RuntimeException
{
  public InvalidIpException()
  {
  }

  public InvalidIpException(String msg)
  {
    super(msg);
  }
}
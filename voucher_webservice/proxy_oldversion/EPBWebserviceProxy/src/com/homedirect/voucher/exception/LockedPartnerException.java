package com.homedirect.voucher.exception;

public class LockedPartnerException extends RuntimeException
{
  public LockedPartnerException()
  {
  }

  public LockedPartnerException(String msg)
  {
    super(msg);
  }
}
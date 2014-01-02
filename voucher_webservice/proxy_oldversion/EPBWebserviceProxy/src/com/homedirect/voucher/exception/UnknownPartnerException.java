package com.homedirect.voucher.exception;

public class UnknownPartnerException extends RuntimeException
{
  public UnknownPartnerException()
  {
  }

  public UnknownPartnerException(String msg)
  {
    super(msg);
  }

  public UnknownPartnerException(Exception ex) {
    super(ex);
  }
}
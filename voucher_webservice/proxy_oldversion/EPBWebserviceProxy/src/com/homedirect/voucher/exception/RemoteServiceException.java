package com.homedirect.voucher.exception;

public class RemoteServiceException extends RuntimeException
{
  public RemoteServiceException()
  {
  }

  public RemoteServiceException(String msg)
  {
    super(msg);
  }

  public RemoteServiceException(Exception ex) {
    super(ex);
  }
}
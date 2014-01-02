package com.homedirect.voucher.exception;

public class MaintenanceException extends RuntimeException
{
  public MaintenanceException()
  {
  }

  public MaintenanceException(String msg)
  {
    super(msg);
  }
}
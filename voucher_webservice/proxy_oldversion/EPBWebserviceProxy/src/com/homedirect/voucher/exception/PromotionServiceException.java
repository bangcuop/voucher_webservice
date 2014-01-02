package com.homedirect.voucher.exception;

public class PromotionServiceException extends RuntimeException
{
  public PromotionServiceException()
  {
  }

  public PromotionServiceException(String msg)
  {
    super(msg);
  }

  public PromotionServiceException(Exception ex) {
    super(ex);
  }
}
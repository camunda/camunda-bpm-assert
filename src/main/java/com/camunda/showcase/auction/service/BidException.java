package com.camunda.showcase.auction.service;

import javax.ejb.ApplicationException;

@ApplicationException
public class BidException extends RuntimeException {

  public BidException() {
    super();
  }

  public BidException(String message, Throwable cause) {
    super(message, cause);
  }

  public BidException(String message) {
    super(message);
  }

  public BidException(Throwable cause) {
    super(cause);
  }

}

package com.camunda.showcase.auction.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Bid {

  @Id
  @GeneratedValue
  private Long id;
  
  private String bidderName;
  
  private int amount;
  
  @ManyToOne
  private Auction auction;
  
  public Bid() { }

  public Bid(int amount, String bidderName) {
    this.amount = amount;
    this.bidderName = bidderName;
  }

  public String getBidderName() {
    return bidderName;
  }

  public void setBidderName(String bidderName) {
    this.bidderName = bidderName;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public Long getId() {
    return id;
  }
  
  public Auction getAuction() {
    return auction;
  }
  
  public void setAuction(Auction auction) {
    this.auction = auction;
  }
}

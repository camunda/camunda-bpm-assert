package com.camunda.showcase.auction.service;

import com.camunda.showcase.auction.domain.Auction;

/*
 * @author Nico Rehwaldt <nico.rehwaldt@camunda.com>
 */
public interface TwitterPublishService {
    	public void publishAuction(Auction auction);
}

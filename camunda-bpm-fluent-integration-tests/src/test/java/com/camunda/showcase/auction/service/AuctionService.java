package com.camunda.showcase.auction.service;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.domain.Bid;

import java.util.List;

/*
 * @author Nico Rehwaldt <nico.rehwaldt@camunda.com>
 */
public interface AuctionService {

    public Long createAuction(Auction auction);
    public void authorizeAuction(String taskId, boolean authorized);
    public Auction findAuctionByTaskId(String taskId);
    public List<Auction> getActiveAuctions();
    public Bid findHighestBid(long auctionId);
    public Long locateHighestBidId(long auctionId);
    public Long placeBid(Long auctionId, int bidAmount, String bidderName);
}

package com.camunda.showcase.auction.service;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.domain.Bid;
import com.camunda.showcase.auction.repository.AuctionRepository;
import com.camunda.showcase.auction.repository.BidRepository;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AuctionServiceImpl implements AuctionService {

  @Inject
  private AuctionRepository auctionRepository;

  @Inject
  private BidRepository bidRepository;

  @Inject
  private ProcessEngine processEngine;

  public void authorizeAuction(String taskId, boolean authorized) {
    Auction auction = findAuctionByTaskId(taskId);

    auction.setAuthorized(authorized);

    auctionRepository.saveUpdate(auction);

    // complete task
    processEngine.getTaskService().complete(taskId);
  }

  public Auction findAuctionByTaskId(String taskId) {
    Task task = processEngine.getTaskService()
        .createTaskQuery()
        .taskId(taskId)
        .singleResult();

    String processInstanceId = task.getProcessInstanceId();

    long auctionId = (Long) processEngine.getRuntimeService()
        .getVariable(processInstanceId, "auctionId");

    return auctionRepository.findById(auctionId);
  }

  public List<Auction> getActiveAuctions() {
    return auctionRepository.findAllActive();
  }

  public Long createAuction(Auction auction) {

    // add in repository
    auctionRepository.saveAndFlush(auction);

    // associate with process
    Map<String, Object> processVariables = new HashMap<String, Object>();
    processVariables.put("auctionId", auction.getId());

    // start process
    processEngine
      .getRuntimeService()
      .startProcessInstanceByKey("auction-process", processVariables);

    return auction.getId();
  }

  public Bid findHighestBid(long auctionId) {
    return auctionRepository.findHighestBidByAuctionId(auctionId);
  }

  public Long locateHighestBidId(long auctionId) {
    Bid bid = auctionRepository.findHighestBidByAuctionId(auctionId);
    if (bid != null) {
      return bid.getId();
    } else {
      return null;
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRED)
  public Long placeBid(Long auctionId, int bidAmount, String bidderName) {

    Auction auction = auctionRepository.findById(auctionId);
    if (auction == null) {
      throw new BidException("Auction not found");
    }

    if (auction.getEndTime().before(new Date())) {
      throw new BidException("Auction already closed");
    }

    Bid highestBid = findHighestBid(auctionId);

    if (highestBid != null && highestBid.getAmount() > bidAmount) {
      throw new BidException("Bid is to small, highest bid currently is " + highestBid.getAmount());
    }

    Bid bid = new Bid(bidAmount, bidderName);
    bid.setAuction(auction);

    bidRepository.saveAndFlush(bid);

    return bid.getId();
  }
}

package com.camunda.showcase.auction.service;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.repository.AuctionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@ApplicationScoped
public class TwitterPublishService {

	@Inject
	private AuctionRepository auctionRepository;
	
	/**
	 * Publish auction with the given name on twitter
	 * 
	 * @param auctionName
	 */
	public void publishAuction(Auction auction) {
		
		// twitter now (!)

		System.out.println("###############\n\n");
		System.out.println("Tweeting now:\n\n");
		System.out.println("Auction <<" + auction.getName() + ">>");
		System.out.println("starts now!");
		System.out.println("ends at: " + auction.getEndTime());
		System.out.println("\n\n###############");
	}
}

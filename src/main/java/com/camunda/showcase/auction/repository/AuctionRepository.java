package com.camunda.showcase.auction.repository;

import com.camunda.showcase.auction.domain.Auction;
import com.camunda.showcase.auction.domain.Bid;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Stateless
public class AuctionRepository {

  @PersistenceContext
  private EntityManager em;
	
	/**
	 * Find by id
	 * 
	 * @param id
	 * @return
	 */
	public Auction findById(long id) {
	  return em.find(Auction.class, id);
	}
	
	/**
	 * Find by id with bids
	 * 
	 * @param id
	 * @return
	 */
	public Auction findByIdFetchBids(long id) {
	  try {
  	  return em.createQuery("SELECT a FROM Auction a LEFT JOIN FETCH a.bids WHERE a.id = :id", Auction.class)
  	           .setParameter("id", id)
  	           .getSingleResult();
	  } catch (NoResultException e) {
	    return null;
	  }
	}
	
  /**
   * Find all
   * 
   * @return
   */
  public List<Auction> findAll() {
    return em.createQuery("SELECT a FROM Auction a", Auction.class).getResultList();
  }
  
	/**
	 * 
	 * @param auction
	 */
	public void saveAndFlush(Auction auction) {
	  
	  if (!em.contains(auction)) {
	    em.persist(auction);
	  }

    em.flush();
	}
	
	public Auction saveUpdate(Auction auction) {
	  return em.merge(auction);
	}
	
  /**
   * Remove all auctions
   * 
   */
  public void deleteAll() {
    List<Auction> list = em.createQuery("SELECT a FROM Auction a", Auction.class).getResultList();
    for (Auction auction : list) {
      em.remove(auction);
    }
  }

  public List<Auction> findAllActive() {
    return em.createQuery("SELECT a FROM Auction a WHERE a.authorized IS TRUE AND a.endTime > :now", Auction.class)
        .setParameter("now", new Date())
        .getResultList();
  }

  public Bid findHighestBidByAuctionId(long auctionId) {
    try {
      
      return em.createQuery("SELECT b FROM Bid b WHERE b.auction.id = :auctionId ORDER BY b.amount DESC", Bid.class)
          .setParameter("auctionId", auctionId)
          .setFirstResult(0)
          .setMaxResults(1)
          .getSingleResult();
      
    } catch (NoResultException e) {
      return null;
    }
  }
}

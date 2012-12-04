package com.camunda.showcase.auction.repository;

import com.camunda.showcase.auction.domain.Bid;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class BidRepository {

  @PersistenceContext
  private EntityManager em;
	
	/**
	 * Find by id
	 * 
	 * @param id
	 * @return
	 */
	public Bid findById(long id) {
	  return em.find(Bid.class, id);
	}
	
  /**
   * Find all
   * 
   * @return
   */
  public List<Bid> findAll() {
    return em.createQuery("SELECT b FROM Bid b", Bid.class).getResultList();
  }
  
	/**
	 * 
	 * @param bid
	 */
	public void saveAndFlush(Bid bid) {
	  
	  if (!em.contains(bid)) {
	    em.persist(bid);
	  }

    em.flush();
	}
	
	public Bid saveUpdate(Bid bid) {
	  return em.merge(bid);
	}
	
  /**
   * Remove all bids
   * 
   */
  public void deleteAll() {
    List<Bid> list = em.createQuery("SELECT a FROM Bid a", Bid.class).getResultList();
    for (Bid bid : list) {
      em.remove(bid);
    }
  }
}

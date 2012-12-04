package com.camunda.showcase.auction.domain;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Auction implements Serializable {

  public static interface CREATE { };

  @Id
  @GeneratedValue
	private Long id;

  @NotNull
  @Size(min=5, max=20, groups={ CREATE.class })
	private String name;

  @NotNull
  @Size(min=5, max=20, groups={ CREATE.class })
	private String description;

  @NotNull
  @Future(groups={ CREATE.class })
  @Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	private boolean authorized;

	@OneToMany(cascade={CascadeType.ALL}, mappedBy="auction")
	private List<Bid> bids = new ArrayList<Bid>();

	public Auction() { }

	public Auction(String name, String description, Date endTime) {
		this.name = name;
		this.description = description;
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public boolean isAuthorized() {
		return authorized;
	}

	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Bid> getBids() {
    return bids;
  }

	public void setBids(List<Bid> bids) {
    this.bids = bids;
  }

	private static final DateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

  public String getEndTimeIso() {
    return ISO_DATE_FORMAT.format(getEndTime());
  }
}

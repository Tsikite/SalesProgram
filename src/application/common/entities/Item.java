package application.common.entities;

import java.util.logging.Logger;

import application.Program;
import application.managers.log.LoggerManager;

public class Item {
	
	//**************************
	private int id;
	private String name;
	private int bidInitPrice;
	private int auctionInitPrice;
	//**************************
	private Logger logger;
	//**************************
	public Item(String name, int auctionInitPrice, int bidInitPrice, int id) {
		super();
		this.setName(name);
		this.setAuctionInitPrice(auctionInitPrice);
		this.setBidInitPrice(bidInitPrice);
		this.setId(id);
		logger = LoggerManager.getInstance().getItemLogger(name);
		logger.info(id + " " + name + "Item created");
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBidInitPrice() {
		return bidInitPrice;
	}

	public void setBidInitPrice(int bidInitPrice) {
		this.bidInitPrice = bidInitPrice;
	}

	public int getAuctionInitPrice() {
		return auctionInitPrice;
	}

	public void setAuctionInitPrice(int auctionInitPrice) {
		this.auctionInitPrice = auctionInitPrice;
	}
	
	@Override
	public String toString() {

		return "\nName: " + name + " Bid starting price: " + bidInitPrice + " Auction starting price: " + auctionInitPrice;
	}
}

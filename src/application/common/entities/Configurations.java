package application.common.entities;

// This class is a Singleton.

public class Configurations {

	private static Configurations instance = null;

	private int maxAuctionAtATime;
	private int auctionTimeExtend;
	private int saleTime;
        private String dataBaseUrl;
	protected Configurations() {
		// No instantiation is allowed.
	}

	public static Configurations getInstance() {

		if (instance == null) {
			instance = new Configurations();
		}

		return instance;
	}

	public int getMaxAuctionAtATime() {
		return maxAuctionAtATime;
	}

	public void setMaxAuctionAtATime(int maxAuctionAtATime) {
		this.maxAuctionAtATime = maxAuctionAtATime;
	}

	public int getAuctionTimeExtend() {
		return auctionTimeExtend;
	}

	public void setAuctionTimeExtend(int auctionTimeExtend) {
		this.auctionTimeExtend = auctionTimeExtend;
	}

	public int getSaleTime() {
		return saleTime;
	}

	public void setSaleTime(int saleTime) {
		this.saleTime = saleTime;
	}
        
	public String getDataBaseUrl() {
		return dataBaseUrl;
	}

	public void setDataBaseUrl(String dataBaseUrl) {
		this.dataBaseUrl = dataBaseUrl;
	}


	@Override
	public String toString() {
		return "\nMax Auctions at a time: " + maxAuctionAtATime
				+ "\nAuction time extend: " + auctionTimeExtend
				+ "\nBid sale time: " + saleTime 
                                + "\nData base url: " + dataBaseUrl;
	}

}

/*
 * public class Configurations {
 * 
 * private int maxAuctionAtATime; private int auctionTimeExtend; private int
 * saleTime;
 * 
 * 
 * public int getMaxAuctionAtATime() { return maxAuctionAtATime; } public void
 * setMaxAuctionAtATime(int maxAuctionAtATime) { this.maxAuctionAtATime =
 * maxAuctionAtATime; } public int getAuctionTimeExtend() { return
 * auctionTimeExtend; } public void setAuctionTimeExtend(int auctionTimeExtend)
 * { this.auctionTimeExtend = auctionTimeExtend; } public int getSaleTime() {
 * return saleTime; } public void setSaleTime(int saleTime) { this.saleTime =
 * saleTime; } }
 */

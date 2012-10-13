package application.common.timerTasks;

import java.util.TimerTask;

import application.common.entities.AuctionRoom;
import application.managers.data.DataManager;

public class SaleExtend extends TimerTask {

	private AuctionRoom theSale;
	// This method starts when sale time is over 
	// Its only purpose is to set a new timer according to the auction extend time
	@Override
	public void run() {
		
		// Reseting the timer
		theSale.reSchedualTimer(DataManager.configuration.getAuctionTimeExtend()*1000);
		theSale.turnOnTimeExtend();
	}
	
	public SaleExtend(AuctionRoom theSale) {
		this.theSale = theSale;
	}

}

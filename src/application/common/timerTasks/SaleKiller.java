package application.common.timerTasks;

import java.util.TimerTask;

import application.Program;
import application.common.entities.SaleRoom;

public class SaleKiller extends TimerTask {

	private SaleRoom theSale;
	// This method starts when sale time is over 
	// Its only purpose is to notify the sale that its time is over by deactivating it.
	@Override
	public void run() {
		
		// Time's up - Sale is over.
		theSale.deactivateRoom();
		this.theSale.stopTimer();
                theSale.freeLatch();
	}
	
	public SaleKiller(SaleRoom theSale) {
		this.theSale = theSale;
	}

}

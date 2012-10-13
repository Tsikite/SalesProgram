package application.logging.filters;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class AuctionFilter implements Filter {

	String auctionRoomName;

	public AuctionFilter(String _auctionRoomName) {
		auctionRoomName = _auctionRoomName;
	}

	@Override
	public boolean isLoggable(LogRecord record) {

		if (record.getMessage().contains(auctionRoomName)) {
			
			return true;
		}
		return false;
	}
}

package application.logging.filters;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class BidFilter implements Filter {

	String bidRoomName;

	public BidFilter(String _bidRoomName) {
		bidRoomName = _bidRoomName;
	}

	@Override
	public boolean isLoggable(LogRecord record) {

		if (record.getMessage().contains(bidRoomName)) {
			
			return true;
		}
		return false;
	}

}

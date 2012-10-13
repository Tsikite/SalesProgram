package application.managers.log;

import java.util.logging.Logger;

public interface ILoggerManaager {

	Logger getItemLogger(String itemName);

	Logger getUserLogger(String userName);

	Logger getAuctionRoomLogger( String bidRoom);

	Logger getBidRoomLogger(String auctionRoom);

	Logger getProgramLogger();
}
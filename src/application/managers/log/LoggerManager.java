package application.managers.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import application.logging.filters.AuctionFilter;
import application.logging.filters.BidFilter;
import application.logging.filters.ItemFilter;
import application.logging.filters.ProgramFilter;
import application.logging.filters.UserFilter;
import application.logging.formators.LoggerFomattor;

public class LoggerManager implements ILoggerManaager {

	private static Logger logger;

	private LoggerManager() {
		Init();
	}

	private static class LoggerManagerManagerHolder {
		private final static ILoggerManaager INSTANCE = new LoggerManager();
	}

	public static ILoggerManaager getInstance() {
		return LoggerManagerManagerHolder.INSTANCE;
	}

	public Logger getLogger(){
		return logger;
	}
	
	private void Init() {
		logger = Logger.getLogger("Logger");
		FileHandler theFileHandler = null;
		try {
			theFileHandler = new FileHandler("Program.txt", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (theFileHandler != null) {
			theFileHandler.setFormatter(new LoggerFomattor());
			theFileHandler.setFilter(new ProgramFilter());
			logger.addHandler(theFileHandler);
		}
	}

	public Logger getProgramLogger() {
		return logger;
	}

	@Override
	public Logger getItemLogger(String itemName) {
		FileHandler theFileHandler = null;
		try {
			theFileHandler = new FileHandler(itemName + ".txt", true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (theFileHandler != null) {
			theFileHandler.setFormatter(new LoggerFomattor());
			theFileHandler.setFilter(new ItemFilter(itemName));
			logger.addHandler(theFileHandler);
		}
		return logger;
	}

	@Override
	public Logger getUserLogger(String userName) {
		FileHandler theFileHandler = null;
		try {
			theFileHandler = new FileHandler(userName + ".txt", true);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (theFileHandler != null) {
			theFileHandler.setFormatter(new LoggerFomattor());
			theFileHandler.setFilter(new UserFilter(userName));
			logger.addHandler(theFileHandler);
		}
		return logger;
	}

	@Override
	public Logger getAuctionRoomLogger(String auctionRoom) {
		FileHandler theFileHandler = null;
		try {
			theFileHandler = new FileHandler(auctionRoom + ".txt", true);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (theFileHandler != null) {
			theFileHandler.setFormatter(new LoggerFomattor());
			theFileHandler.setFilter(new AuctionFilter(auctionRoom));
			logger.addHandler(theFileHandler);
		}
		return logger;
	}

	@Override
	public Logger getBidRoomLogger(String bidRoom) {
		FileHandler theFileHandler = null;
		try {
			theFileHandler = new FileHandler(bidRoom + ".txt", true);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (theFileHandler != null) {
			theFileHandler.setFormatter(new LoggerFomattor());
			theFileHandler.setFilter(new BidFilter(bidRoom));
			logger.addHandler(theFileHandler);
		}
		return logger;
	}

}

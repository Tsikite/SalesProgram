package application.logging.filters;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class UserFilter implements Filter {

	String userName;

	public UserFilter(String _userName) {
		userName = _userName;
	}

	@Override
	public boolean isLoggable(LogRecord record) {

		if (record.getMessage().contains(userName)) {
			
			return true;
		}
		return false;
	}
}

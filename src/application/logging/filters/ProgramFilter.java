package application.logging.filters;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ProgramFilter implements Filter {

	String tag = "Program:";

	public ProgramFilter() {
	}

	@Override
	public boolean isLoggable(LogRecord record) {

		if (record.getMessage().contains(tag)) {
			
			return true;
		}
		return false;
	}
}

package application.logging.filters;

import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ItemFilter implements Filter {

	String itemName;

	public ItemFilter(String _itemName) {
		itemName = _itemName;
	}

	@Override
	public boolean isLoggable(LogRecord record) {

		if (record.getMessage().contains(itemName)) {
			
			return true;
		}
		return false;
	}

}

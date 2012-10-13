package application.common.exceptions;

import application.common.utils.TagNames;

public class ItemsReadException extends Exception {
	
	public ItemsReadException(String message) {
		super(TagNames.T_EXCEPTION_ITEMS_READ + message);
	}
}

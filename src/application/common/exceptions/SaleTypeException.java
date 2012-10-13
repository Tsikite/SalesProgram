package application.common.exceptions;

import application.common.utils.TagNames;

public class SaleTypeException extends Exception {
	public SaleTypeException(String message) {
		super(TagNames.T_EXCEPTION_SALE_TYPE + message);
	}
}

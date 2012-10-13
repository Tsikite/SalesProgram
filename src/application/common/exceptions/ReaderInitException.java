package application.common.exceptions;

import application.common.utils.TagNames;

/**
 * ReaderInitException - class that represent a failure in the reader init function.
 *
 */
public class ReaderInitException extends Exception{

	public ReaderInitException(String message) {
		super(TagNames.T_EXCEPTION_READER_INIT+message);
		
	}

}

package application.common.exceptions;

import application.common.utils.TagNames;

public class ConfigurationReadException extends Exception {

	public ConfigurationReadException(String message) {
		super(TagNames.T_EXCEPTION_CONFIGURATION_READ+message);
	}

}

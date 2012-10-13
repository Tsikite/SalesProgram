package application.common.exceptions;

import application.common.utils.TagNames;

public class UsersReadException extends Exception {

	public UsersReadException(String message) {
		super(TagNames.T_EXCEPTION_USERS_READ + message);
	}

}

package com.ibm.ro.users.exception;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1213445553322423421L;

	public UserNotFoundException(String message) {
		super("User Not Found : " + message);
	}

}

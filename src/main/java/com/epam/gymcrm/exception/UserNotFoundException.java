package com.epam.gymcrm.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message){
		super(message);
	}
}

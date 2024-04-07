package com.epam.gymcrm.exception;

public class LoginBlockedException extends RuntimeException{
	public LoginBlockedException(String message) {
		super(message);
	}
}

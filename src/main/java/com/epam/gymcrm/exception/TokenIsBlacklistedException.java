package com.epam.gymcrm.exception;

public class TokenIsBlacklistedException extends Exception{
	public TokenIsBlacklistedException(String message) {
		super(message);
	}
}

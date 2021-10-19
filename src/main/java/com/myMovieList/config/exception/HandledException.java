package com.myMovieList.config.exception;

public class HandledException extends Exception {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMessage;

	public HandledException(String errorMessage, int errorCode) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
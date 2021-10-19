package com.myMovieList.config.exception;

public class HandledResponse {

	private int code;
	private String message;

	public HandledResponse(String message, int code) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}

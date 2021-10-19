package com.myMovieList.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class HandledControllerAdvice {
	
    @ExceptionHandler(HandledException.class)
    @ResponseBody
    public ResponseEntity<HandledResponse> handleException(HandledException se) {
    	HandledResponse response = new HandledResponse(se.getErrorMessage(), se.getErrorCode());
    	return new ResponseEntity<HandledResponse>(response, HttpStatus.valueOf(se.getErrorCode()));
    }
}
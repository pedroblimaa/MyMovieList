package com.myMovieList.config.exception;

import javax.validation.ConstraintViolationException;

import com.myMovieList.config.dto.ErrorHandleDto;

import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AppExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorHandleDto> handle(ConstraintViolationException exception) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorHandleDto(exception.getMessage(), 400));
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorHandleDto> typeHandle(MethodArgumentTypeMismatchException exception) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorHandleDto(exception.getName() + ": invalid data type", 400));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorHandleDto> typeHandle(MethodArgumentNotValidException exception) {

		String message = "";

		for (FieldError error : exception.getFieldErrors()) {
			message += error.getField() + " " + error.getDefaultMessage();
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorHandleDto(message, 400));
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ResponseEntity<ErrorHandleDto> typeHandle(MissingServletRequestParameterException exception) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorHandleDto(exception.getMessage(), 400));
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorHandleDto> typeHandle(HttpMessageNotReadableException exception) {

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorHandleDto("Invalid data format", 400));
	}

	@ExceptionHandler(JDBCConnectionException.class)
	public ResponseEntity<ErrorHandleDto> typeHandle(JDBCConnectionException exception) {

		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ErrorHandleDto("Error with database connection", 502));
	}

}

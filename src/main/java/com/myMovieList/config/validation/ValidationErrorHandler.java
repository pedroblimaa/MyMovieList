package com.myMovieList.config.validation;

import javax.validation.ConstraintViolationException;

import com.myMovieList.config.dto.ErrorHandleDto;

import org.springframework.data.mongodb.UncategorizedMongoDbException;
import org.springframework.http.HttpStatus;
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
public class ValidationErrorHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorHandleDto handle(ConstraintViolationException exception) {

		return new ErrorHandleDto(exception.getMessage(), 400);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ErrorHandleDto typeHandle(MethodArgumentTypeMismatchException exception) {

		return new ErrorHandleDto(exception.getName() + ": invalid data type", 400);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorHandleDto typeHandle(MethodArgumentNotValidException exception) {

		String message = "";

		for (FieldError error : exception.getFieldErrors()) {
			message += error.getField() + " " + error.getDefaultMessage();
		}

		return new ErrorHandleDto(message, 400);
	}

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ErrorHandleDto typeHandle(MissingServletRequestParameterException exception) {

		return new ErrorHandleDto(exception.getMessage(), 400);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorHandleDto typeHandle(HttpMessageNotReadableException exception) {

		return new ErrorHandleDto("Invalid data format", 400);
	}

	@ExceptionHandler(UncategorizedMongoDbException.class)
	public ErrorHandleDto typeHandle(UncategorizedMongoDbException exception) {

		return new ErrorHandleDto("Database is not connected", 503);
	}
}

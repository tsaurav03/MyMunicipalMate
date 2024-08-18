package com.app.mmm.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleMuncipleCooprationException(ResourceNotFoundException exception,
			WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));

		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
	public ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception
			, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), exception.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(details, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
}

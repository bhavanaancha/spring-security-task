package com.epam.reportingservice.exceptionhandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

import feign.FeignException;

@RestControllerAdvice
public class ApplicationExceptionHandler {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
			WebRequest request) {
		List<String> error = new ArrayList<>();
		e.getAllErrors().forEach(err -> error.add(err.getDefaultMessage()));
		return new ExceptionResponse(new Date().toString(), HttpStatus.BAD_REQUEST.toString(), error.toString(),
				request.getDescription(false));

	}
//	@ExceptionHandler(ReportException.class)
//	@ResponseStatus(HttpStatus.OK)
//	public ExceptionResponse handleQuestionNotFoundException(LibraryException e, WebRequest request) {
//		return new ExceptionResponse(new Date().toString(), HttpStatus.OK.toString(), e.getMessage(),
//				request.getDescription(false));
//	}
	@ExceptionHandler(HttpClientErrorException.class)
	@ResponseStatus(HttpStatus.OK)
	public ExceptionResponse handleHttpClientException(HttpClientErrorException e, WebRequest request) {
		return new ExceptionResponse(new Date().toString(), HttpStatus.OK.toString(), e.getMessage(),
				request.getDescription(false));
	}
	@ExceptionHandler(FeignException.class)
	@ResponseStatus(HttpStatus.OK)
	public ExceptionResponse handleFeignException(FeignException e, WebRequest request) {
		return new ExceptionResponse(new Date().toString(), HttpStatus.OK.toString(), e.getMessage(),
				request.getDescription(false));
	}

}

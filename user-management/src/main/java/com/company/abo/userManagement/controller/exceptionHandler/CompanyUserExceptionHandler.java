package com.company.abo.userManagement.controller.exceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.company.abo.userManagement.exception.CompanyUserNotFoundException;
import com.company.abo.userManagement.exception.EmailAlreadyExistsException;

@ControllerAdvice
public class CompanyUserExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(CompanyUserNotFoundException.class)
	public ResponseEntity<CompanyUserErrorResponse> handleNotFoundException(Exception exception, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.NOT_FOUND;
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(exception, request, httpStatus); 	
		
		return new ResponseEntity<CompanyUserErrorResponse>(errorResponse, httpStatus);	
	}
	
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<CompanyUserErrorResponse> handleConstraintViolationException(Exception exception, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(exception, request, httpStatus); 	
		
		return new ResponseEntity<CompanyUserErrorResponse>(errorResponse, httpStatus);	
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(exception, request, httpStatus); 	
		
		final ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorResponse, httpStatus);
		
		return responseEntity;
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(exception, request, httpStatus); 
			
		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
		}
		
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(exception, request, status); 
		
		return new ResponseEntity<Object>(errorResponse, status);
	}
	
	private static CompanyUserErrorResponse createCompanyUserErrorResponse(final Exception exception, final WebRequest webRequest, final HttpStatus httpStatus) {
		final CompanyUserErrorResponse errorResponse = new CompanyUserErrorResponse();
		
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(httpStatus.value());
		errorResponse.setPath(getRequestUri(webRequest));
		errorResponse.setMessage("Error(s) occured ");
	
		final Set<String> errors = new LinkedHashSet<>();
		errors.add(getErrorMessage(exception));
		errorResponse.setErrors(errors);
		
		return errorResponse;
	}
	
	private static String getRequestUri(final WebRequest webRequest) {
		final String uri = ((ServletWebRequest) webRequest).getRequest().getRequestURL().toString();
		
		return uri;
	}
	
	
	private static String getErrorMessage(final Exception exception) {
		String errorMessage = exception.getMessage();
		if(exception instanceof HttpRequestMethodNotSupportedException) {
			errorMessage += " - Valid methods are : " + 
			Arrays.toString(((HttpRequestMethodNotSupportedException) exception).getSupportedMethods());
		}
		return errorMessage;
	}

}

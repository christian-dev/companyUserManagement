package com.company.abo.userManagement.controller.exceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

/**
 * Class to handle the exception / error on the API call
 * It maps every error/exception with the status and custom response error message
 * 
 * @see ResponseEntityExceptionHandler
 * 
 * @author ABO
 *
 */
@ControllerAdvice
public class CompanyUserExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * @see ResponseEntityExceptionHandler#handleNotFoundException
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(CompanyUserNotFoundException.class)
	public ResponseEntity<CompanyUserErrorResponse> handleNotFoundException(Exception exception, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.NOT_FOUND;
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(getErrorMessage(exception), request, httpStatus); 	
		
		return new ResponseEntity<CompanyUserErrorResponse>(errorResponse, httpStatus);	
	}
	
	/**
	 * @see ResponseEntityExceptionHandler#handleConstraintViolationException
	 * @param exception
	 * @param request
	 * @return
	 */
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<CompanyUserErrorResponse> handleConstraintViolationException(Exception exception, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(getErrorMessage(exception), request, httpStatus); 	
		
		return new ResponseEntity<CompanyUserErrorResponse>(errorResponse, httpStatus);	
	}
	
	/**
	 * @see ResponseEntityExceptionHandler#handleMethodArgumentNotValid
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
		
		BindingResult bindingResult = exception.getBindingResult();
		
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		Object errors = "";
		if(fieldErrors != null) {
			errors = fieldErrors.stream().map(fieldError -> fieldError.getDefaultMessage()).collect(Collectors.toSet());
		}
		
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(errors, request, httpStatus); 	
		
		final ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorResponse, httpStatus);
		
		return responseEntity;
	}
	
	/**
	 * @see ResponseEntityExceptionHandler#handleHttpMessageNotReadable
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		final HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(getErrorMessage(exception), request, httpStatus); 
			
		return new ResponseEntity<Object>(errorResponse, httpStatus);
	}
	
	/**
	 * @see ResponseEntityExceptionHandler#handleExceptionInternal
	 */
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception exception, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status)) {
			request.setAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE, exception, WebRequest.SCOPE_REQUEST);
		}
		
		final CompanyUserErrorResponse errorResponse = createCompanyUserErrorResponse(getErrorMessage(exception), request, status); 
		
		return new ResponseEntity<Object>(errorResponse, status);
	}
	
	/**
	 * Create a custom error message for the response to a caller
	 * @param errors
	 * @param webRequest
	 * @param httpStatus
	 * @return
	 */
	private static CompanyUserErrorResponse createCompanyUserErrorResponse(final Object errors, final WebRequest webRequest, final HttpStatus httpStatus) {
		final CompanyUserErrorResponse errorResponse = new CompanyUserErrorResponse();
		
		errorResponse.setTimestamp(LocalDateTime.now());
		errorResponse.setStatus(httpStatus.value());
		errorResponse.setPath(getRequestUri(webRequest));
		errorResponse.setMessage("Error(s) occured ");
	
		errorResponse.setErrors(errors);
		
		return errorResponse;
	}
	
	
	/**
	 * Get the URI that identify the request resource
	 * @param webRequest
	 * @return
	 */
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

package com.company.abo.userManagement.controller.exceptionHandler;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * Class to handle the response error information to be sent to API caller
 * @author ABO
 *
 */
@Data
public class CompanyUserErrorResponse implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3667739973697021046L;

	/**
	 * Date of the current call
	 */
	private LocalDateTime timestamp;
	
	/**
	 * HTTP request status
	 */
	private int status;
	
	/**
	 * Error information. It can be a list of error, an exception message, an exception, ...
	 */
	private Object errors;
	
	/**
	 * Global message for the error
	 */
	private String message;
	
	/**
	 * Path identifying the request
	 */
	private String path;
	
}

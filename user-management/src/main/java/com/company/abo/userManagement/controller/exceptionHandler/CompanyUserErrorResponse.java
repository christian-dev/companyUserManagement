package com.company.abo.userManagement.controller.exceptionHandler;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Data;

@Data
public class CompanyUserErrorResponse implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3667739973697021046L;

	private LocalDateTime timestamp;
	
	private int status;
	
	private Set<String> errors;
	
	private String message;
	
	private String path;
	
}

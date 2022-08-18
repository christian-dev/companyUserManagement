package com.company.abo.userManagement.exception;

public class CompanyUserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5011995948550542020L;
	
	public CompanyUserNotFoundException() {
		super("The company user is not found");
	}
	
	public CompanyUserNotFoundException(Long userId) {
		super(String.format("The company user with id %s not found", userId));
	}
	
	public CompanyUserNotFoundException(String email) {
		super(String.format("The company user with email %s not found", email));
	}
}

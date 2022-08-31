package com.company.abo.userManagement.exception;

/**
 * Runtime exception thrown on email already used by another user in the application
 * @author ABO
 *
 */
public class EmailAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5011995948550542020L;
	
	public EmailAlreadyExistsException(String email) {
		super(String.format("The email %s already exists", email));
	}
}

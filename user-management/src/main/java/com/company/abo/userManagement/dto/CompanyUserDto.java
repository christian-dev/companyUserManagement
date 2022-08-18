package com.company.abo.userManagement.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.company.abo.userManagement.dto.validator.birthdate.BirthDateConstraint;
import com.company.abo.userManagement.dto.validator.countryOfResidence.CountryOfResidenceConstraint;
import com.company.abo.userManagement.dto.validator.gender.GenderConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1171217282526998948L;
	
	private Long userId;
	
	@NotEmpty(message="The first name must not be empty")
	@NotBlank(message="The first name must not be empty")
	@Size(min = 2, max = 50, message = "The first name must be betwen 2 and 50 characters")
	private String firstName;
	
	@NotEmpty(message="The last name must not be empty")
	@NotBlank(message="The last name must not be empty")
	@Size(min = 2, max = 50, message = "The last name must be betwen 2 and 50 characters")
	private String lastName;
	
	@NotEmpty(message="The birth date must not be empty")
	@NotBlank(message="The birth date must not be empty")
	@Size(min = 10, max = 10, message = "The birth date must be with 10 characters")
	@BirthDateConstraint
	private String birthdate;
	
	@NotEmpty(message="The country of residence must not be empty")
	@NotBlank(message="The country of residence must not be empty")
	@CountryOfResidenceConstraint
	private String countryOfResidence;
	
	private String phoneNumber;
	
	@NotEmpty(message="The email must not be empty")
	@Email(message="Invalid email")
	private String email;
	
	@NotEmpty(message="The gender must not be empty")
	@GenderConstraint
	private String gender;
	
	private Timestamp creationDate;
	
	private Timestamp updateDate;

}

package com.company.abo.userManagement.dto;

import java.io.Serializable;

import javax.validation.GroupSequence;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.company.abo.userManagement.dto.validator.birthdate.BirthDateConstraint;
import com.company.abo.userManagement.dto.validator.countryOfResidence.CountryOfResidenceConstraint;
import com.company.abo.userManagement.dto.validator.gender.GenderConstraint;
import com.company.abo.userManagement.dto.validator.group.CompanyUserConstraintGroup;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class that describe an user.
 * Object of this class is serialized and exchanged with the API caller
 * @author ABO
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@GroupSequence(value={
		CompanyUserDto.class, 
		CompanyUserConstraintGroup.class})
public class CompanyUserDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1171217282526998948L;
	
	private Long userId;
	
	@NotNull(message="The first name must not be null")
	@NotEmpty(message="The first name must not be empty")
	@NotBlank(message="The first name must not be empty")
	@Size(min = 2, max = 50, message = "The first name must be between 2 and 50 characters")
	private String firstName;
	
	@NotNull(message="The last name must not be null")
	@NotEmpty(message="The last name must not be empty")
	@NotBlank(message="The last name must not be empty")
	@Size(min = 2, max = 50, message = "The last name must be between 2 and 50 characters")
	private String lastName;
	
	@NotNull(message="The birth date must not be null")
	@NotEmpty(message="The birth date must not be empty")
	@NotBlank(message="The birth date must not be empty")
	@Size(min = 10, max = 10, message = "The birth date must be with 10 characters")
	@BirthDateConstraint(groups=CompanyUserConstraintGroup.class)
	private String birthdate;
	
	@NotNull(message="The country of residence must not be null")
	@NotEmpty(message="The country of residence must not be empty")
	@NotBlank(message="The country of residence must not be empty")
	@CountryOfResidenceConstraint(groups=CompanyUserConstraintGroup.class)
	private String countryOfResidence;
	
	private String phoneNumber;
	
	@NotNull(message="The email must not be null")
	@NotEmpty(message="The email must not be empty")
	@Email(message="Invalid email")
	private String email;
	
	@NotNull(message="The gender must not be null")
	@NotEmpty(message="The gender must not be empty")
	@GenderConstraint(groups=CompanyUserConstraintGroup.class)
	private String gender;
	
	private String creationDate;
	
	private String updateDate;

}

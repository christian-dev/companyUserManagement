package com.company.abo.userManagement.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.model.CompanyUser;


@TestConfiguration
public class CompanyUserMapperTestConfiguration {
	
	@Bean
	public CompanyUserMapper companyUserMapper() {
		return Mappers.getMapper(CompanyUserMapper.class);
	}
	
	
	public static CompanyUserDto referenceCompanyUserDto(final Long userId) {
		return newCompanyUserDto (
				userId, 
				"John",
				"Doe",
				"18/08/1985",
				"FRANCE",
				"+33667771245",
				"john@doe.fr",
				"M", 
				null,
				null);
	}

	public static CompanyUser referenceCompanyUser(final Long userId) {
		return newCompanyUser (
				userId,
				"John",
				"Doe",
				LocalDate.of(1985, 8, 18),
				"FRANCE",
				"+33667771245",
				"john@doe.fr"
				,"M"
				,null,
				null);
		
	}
	
	public static CompanyUser newCompanyUser(
			Long userId, 
			String firstName, 
			String lastName,
			LocalDate birthdate,
			String countryOfResidence,
			String phoneNumber,
			String email,
			String gender, 
			Timestamp creationDate,
			Timestamp updateDate) {
		CompanyUser companyUser = new CompanyUser();

		companyUser.setUserId(userId);
		companyUser.setFirstName(firstName);
		companyUser.setLastName(lastName);
		companyUser.setBirthdate(birthdate);
		companyUser.setCountryOfResidence(countryOfResidence);
		companyUser.setPhoneNumber(phoneNumber);
		companyUser.setEmail(email);
		companyUser.setGender(gender);
		
		companyUser.setCreationDate(creationDate);
		companyUser.setUpdateDate(updateDate);
		
		return companyUser;
	}
	
	public static CompanyUserDto newCompanyUserDto(
			Long userId, 
			String firstName, 
			String lastName,
			String birthdate,
			String countryOfResidence,
			String phoneNumber,
			String email,
			String gender, 
			Timestamp creationDate,
			Timestamp updateDate) {
		return new CompanyUserDto(userId, firstName, lastName, birthdate, countryOfResidence, phoneNumber, email, gender, creationDate, updateDate);
	}
}

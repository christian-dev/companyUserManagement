package com.company.abo.userManagement.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
				"30/07/2022 11:30:03", 
				"29/08/2022 16:25:16");
	}

	public static CompanyUser referenceCompanyUser(final Long userId) {
		return newCompanyUser (
				userId,
				"John",
				"Doe",
				LocalDate.of(1985, 8, 18),
				"FRANCE",
				"+33667771245",
				"john@doe.fr",
				"M",
				LocalDateTime.of(2022,7,30,11,30,3), 
				LocalDateTime.of(2022,8,29,16,25,16));
		
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
			LocalDateTime creationDate,
			LocalDateTime updateDate) {
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
			String creationDate,
			String updateDate) {
		return new CompanyUserDto(userId, firstName, lastName, birthdate, countryOfResidence, phoneNumber, email, gender, creationDate, updateDate);
	}
}

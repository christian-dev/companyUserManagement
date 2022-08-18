package com.company.abo.userManagement.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.company.abo.userManagement.mapper.CompanyUserMapperTestConfiguration.*;

import com.company.abo.userManagement.common.AppDateFormatterTestConfiguration;
import com.company.abo.userManagement.config.CompanyAppProperties;
import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.model.CompanyUser;

@ExtendWith(SpringExtension.class)
@Import({CompanyUserMapperTestConfiguration.class, AppDateFormatterTestConfiguration.class})

public class CompanyUserMapperTest {
	
	@MockBean
	private CompanyAppProperties companyAppProperties;
	
	@Autowired
	private CompanyUserMapper companyUserMapper;	
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		when(companyAppProperties.getDateFormatPattern()).thenReturn("dd/MM/yyyy");
	}
	

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void givenCompanyUserEntityToDtoCompanyUser_whenMaps_thenCorrect() {
		final CompanyUser companyUser = CompanyUserMapperTestConfiguration.referenceCompanyUser(1L);
		final CompanyUserDto companyUserDto = CompanyUserMapperTestConfiguration.referenceCompanyUserDto(1L);
		final CompanyUserDto mappedCompanyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		
		assertEqualsCompanyUser(companyUserDto, mappedCompanyUserDto, "userId",
				"firstName",
				"lastName",
				"birthdate",
				"countryOfResidence",
				"phoneNumber",
				"email",
				"gender",
				"creationDate",
				"updateDate");
	}

	@Test
	public void givenCompanyUserDtoToCompanyUserEntity_whenMaps_thenCorrect() {
		final CompanyUser companyUser = CompanyUserMapperTestConfiguration.referenceCompanyUser(1L);
		final CompanyUserDto companyUserDto = CompanyUserMapperTestConfiguration.referenceCompanyUserDto(1L);
		final CompanyUser mappedCompanyUser = companyUserMapper.companyUserDtoToEntity(companyUserDto);

		assertEqualsCompanyUser(companyUser, mappedCompanyUser, "userId",
				"firstName",
				"lastName",
				"birthdate",
				"countryOfResidence",
				"phoneNumber",
				"email",
				"gender",
				"creationDate",
				"updateDate");
	}

	@Test
	public void testCompanyUserEntityToDtoListOfCompanyUser() {
		assertNotNull(companyUserMapper);
		
		List<CompanyUser> companyUsers = new ArrayList<>();
		companyUsers.add(newCompanyUser (1L, "John", "Doe", LocalDate.of(1985, 8, 18), "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (2L, "Marc", "Simple", LocalDate.of(2000, 7, 11), "FRANCE", "+33685967265", "marc@simple.com" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (3L, "Ahmed", "CHOUIA", LocalDate.of(1970, 5, 10), "FRANCE", "+33667776789", "ahmed@chouia.com" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (4L, "Françoise", "NGANDA", LocalDate.of(1999, 1, 3), "FRANCE", "+33669991245", "francoise@nganda.com" ,"F" ,null, null));
		companyUsers.add(newCompanyUser (5L, "patricia", "PARKER", LocalDate.of(1983, 9, 12), "FRANCE", "+33668881245", "patrica@parker.com" ,"F" ,null, null));
		
		List<CompanyUserDto> companyUserDtos = companyUserMapper.companyUserEntityToDto(companyUsers);
		for(int i = 0; i < companyUserDtos.size(); i++) {

			assertEqualsCompanyUser(companyUserDtos.get(i), companyUserMapper.companyUserEntityToDto(companyUsers.get(i)), 
					"userId",
					"firstName",
					"lastName",
					"birthdate",
					"countryOfResidence",
					"phoneNumber",
					"email",
					"gender",
					"creationDate",
					"updateDate");
		}
	
	}	

	@Test
	public void testCompanyUserDtoToEntityListOfCompanyUserDto() {
		assertNotNull(companyUserMapper);
		
		List<CompanyUserDto> companyUserDtos = new ArrayList<>();
		companyUserDtos.add(newCompanyUserDto (1L, "John", "Doe", "18/08/1985", "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null));
		companyUserDtos.add(newCompanyUserDto (2L, "Marc", "Simple", "11/07/2000", "FRANCE", "+33685967265", "marc@simple.com" ,"M" ,null, null));
		companyUserDtos.add(newCompanyUserDto (3L, "Ahmed", "CHOUIA", "10/05/1970", "FRANCE", "+33667776789", "ahmed@chouia.com" ,"M" ,null, null));
		companyUserDtos.add(newCompanyUserDto (4L, "Françoise", "NGANDA", "03/01/1999", "FRANCE", "+33669991245", "francoise@nganda.com" ,"F" ,null, null));
		companyUserDtos.add(newCompanyUserDto (4L, "patricia", "PARKER", "03/01/1999", "FRANCE", "+33668881245", "patrica@parker.com" ,"F" ,null, null));
		
		List<CompanyUser> companyUsers = companyUserMapper.companyUserDtoToEntity(companyUserDtos);
		for(int i = 0; i < companyUsers.size(); i++) {

			assertEqualsCompanyUser(companyUsers.get(i), companyUserMapper.companyUserDtoToEntity(companyUserDtos.get(i)), 
					"userId",
					"firstName",
					"lastName",
					"birthdate",
					"countryOfResidence",
					"phoneNumber",
					"email",
					"gender",
					"creationDate",
					"updateDate");
		}
	
	}

	@Test
	public void givenCompanyUserDto_whenReplacePartialValues_thenCorrect() {
		
		assertNotNull(companyUserMapper);
		
		final CompanyUserDto referenceCompanyUserDto = CompanyUserMapperTestConfiguration.referenceCompanyUserDto(1L);
		final CompanyUserDto companyUserDto = CompanyUserMapperTestConfiguration.referenceCompanyUserDto(1L);
		Map<String, String> map = new HashMap<>();
		map.put("email", "johnny@doe.fr");
		map.put("countryOfResidence", "ENGLAND");
		companyUserMapper.copyMapValuestoCompanyUserDto(map, companyUserDto);
		
		assertEqualsCompanyUser(companyUserDto, referenceCompanyUserDto, "userId",
				"firstName",
				"lastName",
				"birthdate",
				//"countryOfResidence", -- countryOfResidence replaced 
				"phoneNumber",
				//"email", -- Email have been replaced
				"gender",
				"creationDate",
				"updateDate");
		
		assertEquals(companyUserDto.getCountryOfResidence(), "ENGLAND");
		assertEquals(referenceCompanyUserDto.getCountryOfResidence(), "FRANCE");
		
		assertEquals(companyUserDto.getEmail(), "johnny@doe.fr");
		assertEquals(referenceCompanyUserDto.getEmail(), "john@doe.fr");
	}
	
	private void assertEqualsCompanyUser(final CompanyUserDto user1, final CompanyUserDto user2, String ...propertyNames) {
		
		for(String propertyName : propertyNames) {
			switch(propertyName) {
			case "userId":
				assertEquals(user1.getUserId(), user2.getUserId());
				continue;
			case "firstName":
				assertEquals(user1.getFirstName(), user2.getFirstName());
				continue;
			case "lastName":
				assertEquals(user1.getLastName(), user2.getLastName());
				continue;
			case "birthdate":
				assertEquals(user1.getBirthdate(), user2.getBirthdate());
				continue;
			case "countryOfResidence":
				assertEquals(user1.getCountryOfResidence(), user2.getCountryOfResidence());
				continue;
			case "phoneNumber":
				assertEquals(user1.getPhoneNumber(), user2.getPhoneNumber());
				continue;
			case "email":
				assertEquals(user1.getEmail(), user2.getEmail());
				continue;
			case "gender":
				assertEquals(user1.getGender(), user2.getGender());
				continue;
			case "creationDate":
				assertEquals(user1.getCreationDate(), user2.getCreationDate());
				continue;
			case "updateDate":
				assertEquals(user1.getUpdateDate(), user2.getUpdateDate());
				continue;
			}
		}
	}
	
	private void assertEqualsCompanyUser(final CompanyUser user1, final CompanyUser user2, String ...propertyNames) {
		
		for(String propertyName : propertyNames) {
			switch(propertyName) {
			case "userId":
				assertEquals(user1.getUserId(), user2.getUserId());
			case "firstName":
				assertEquals(user1.getFirstName(), user2.getFirstName());					
			case "lastName":
				assertEquals(user1.getLastName(), user2.getLastName());
			case "birthdate":
				assertEquals(user1.getBirthdate(), user2.getBirthdate());
			case "countryOfResidence":
				assertEquals(user1.getCountryOfResidence(), user2.getCountryOfResidence());
			case "phoneNumber":
				assertEquals(user1.getPhoneNumber(), user2.getPhoneNumber());
			case "email":
				assertEquals(user1.getEmail(), user2.getEmail());
			case "gender":
				assertEquals(user1.getGender(), user2.getGender());
			case "creationDate":
				assertEquals(user1.getCreationDate(), user2.getCreationDate());
			case "updateDate":
				assertEquals(user1.getUpdateDate(), user2.getUpdateDate());
			}
		}
	}
}

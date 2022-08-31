package com.company.abo.userManagement.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
	
	@Autowired
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
		when(companyAppProperties.getDateTimeFormatPattern()).thenReturn("dd/MM/yyyy HH:mm:ss");
	}
	

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void givenCompanyUserEntityToDtoCompanyUser_whenMaps_thenCorrect() {
		final CompanyUser companyUser = CompanyUserMapperTestConfiguration.referenceCompanyUser(1L);
		final CompanyUserDto companyUserDto = CompanyUserMapperTestConfiguration.referenceCompanyUserDto(1L);
		final CompanyUserDto mappedCompanyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		
		assertEquals(companyUserDto, mappedCompanyUserDto);
	}

	@Test
	public void givenCompanyUserDtoToCompanyUserEntity_whenMaps_thenCorrect() {
		final CompanyUser companyUser = CompanyUserMapperTestConfiguration.referenceCompanyUser(1L);
		final CompanyUserDto companyUserDto = CompanyUserMapperTestConfiguration.referenceCompanyUserDto(1L);
		final CompanyUser mappedCompanyUser = companyUserMapper.companyUserDtoToEntity(companyUserDto);

		assertEquals(companyUser, mappedCompanyUser);
	}

	@Test
	public void testCompanyUserEntityToDtoListOfCompanyUser() {
		assertNotNull(companyUserMapper);
		
		final List<CompanyUser> companyUsers = new ArrayList<>();
		companyUsers.add(newCompanyUser (1L, "John", "Doe", LocalDate.of(1985, 8, 18), "FRANCE", "+33667771245", "john@doe.fr", "M", LocalDateTime.of(2022,7,31,11,05,30), LocalDateTime.of(2022,8,30,17,03,50)));
		companyUsers.add(newCompanyUser (2L, "Marc", "Simple", LocalDate.of(2000, 7, 11), "FRANCE", "+33685967265", "marc@simple.com", "M" , LocalDateTime.of(2022,7,31,11,05,30), LocalDateTime.of(2022,8,30,17,03,50)));
		companyUsers.add(newCompanyUser (3L, "Ahmed", "CHOUIA", LocalDate.of(1970, 5, 10), "FRANCE", "+33667776789", "ahmed@chouia.com", "M" , LocalDateTime.of(2022,7,31,11,05,30), LocalDateTime.of(2022,8,30,17,03,50)));
		companyUsers.add(newCompanyUser (4L, "Françoise", "NGANDA", LocalDate.of(1999, 1, 3), "FRANCE", "+33669991245", "francoise@nganda.com", "F", LocalDateTime.of(2022,7,31,11,05,30), LocalDateTime.of(2022,8,30,17,03,50)));
		companyUsers.add(newCompanyUser (5L, "patricia", "PARKER", LocalDate.of(1983, 9, 12), "FRANCE", "+33668881245", "patrica@parker.com", "F", LocalDateTime.of(2022,7,31,11,05,30), LocalDateTime.of(2022,8,30,17,03,50)));
		
		final List<CompanyUserDto> companyUserDtos = companyUserMapper.companyUserEntityToDto(companyUsers);
		for(int i = 0; i < companyUserDtos.size(); i++) {

			assertEquals(companyUserMapper.companyUserEntityToDto(companyUsers.get(i)), companyUserDtos.get(i));
		}
	
	}	

	@Test
	public void testCompanyUserDtoToEntityListOfCompanyUserDto() {
		assertNotNull(companyUserMapper);
		
		List<CompanyUserDto> companyUserDtos = new ArrayList<>();
		companyUserDtos.add(newCompanyUserDto (1L, "John", "Doe", "18/08/1985", "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,"30/07/2022 11:30:03", "29/08/2022 16:25:16"));
		companyUserDtos.add(newCompanyUserDto (2L, "Marc", "Simple", "11/07/2000", "FRANCE", "+33685967265", "marc@simple.com", "M", "30/07/2022 11:30:03", "29/08/2022 16:25:16"));
		companyUserDtos.add(newCompanyUserDto (3L, "Ahmed", "CHOUIA", "10/05/1970", "FRANCE", "+33667776789", "ahmed@chouia.com" ,"M", "30/07/2022 11:30:03", "29/08/2022 16:25:16"));
		companyUserDtos.add(newCompanyUserDto (4L, "Françoise", "NGANDA", "03/01/1999", "FRANCE", "+33669991245", "francoise@nganda.com" ,"F", "30/07/2022 11:30:03", "29/08/2022 16:25:16"));
		companyUserDtos.add(newCompanyUserDto (5L, "patricia", "PARKER", "03/01/1999", "FRANCE", "+33668881245", "patrica@parker.com" ,"F", "30/07/2022 11:30:03", "29/08/2022 16:25:16"));
		
		List<CompanyUser> companyUsers = companyUserMapper.companyUserDtoToEntity(companyUserDtos);
		for(int i = 0; i < companyUsers.size(); i++) {
			
			assertEquals(companyUsers.get(i), companyUserMapper.companyUserDtoToEntity(companyUserDtos.get(i)));
			
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
		
		assertEquals("ENGLAND", companyUserDto.getCountryOfResidence());
		assertEquals("FRANCE", referenceCompanyUserDto.getCountryOfResidence());
		
		assertEquals("johnny@doe.fr", companyUserDto.getEmail());
		assertEquals("john@doe.fr", referenceCompanyUserDto.getEmail());
		
		assertEquals(referenceCompanyUserDto.getUserId(), companyUserDto.getUserId());
		assertEquals(referenceCompanyUserDto.getBirthdate(), companyUserDto.getBirthdate());
		assertEquals(referenceCompanyUserDto.getFirstName(), companyUserDto.getFirstName());
		assertEquals(referenceCompanyUserDto.getLastName(), companyUserDto.getLastName());
		assertEquals(referenceCompanyUserDto.getGender(), companyUserDto.getGender());
		assertEquals(referenceCompanyUserDto.getPhoneNumber(), companyUserDto.getPhoneNumber());
		
	}
	
	
}

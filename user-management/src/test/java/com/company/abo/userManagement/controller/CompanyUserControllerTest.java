package com.company.abo.userManagement.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.company.abo.userManagement.controller.exceptionHandler.CompanyUserErrorResponse;
import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.model.CompanyUser;
import com.company.abo.userManagement.repository.CompanyUserRepository;

import static com.company.abo.userManagement.mapper.CompanyUserMapperTestConfiguration.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyUserControllerTest {
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private CompanyUserRepository companyUserRepository;
	
	private String url;
	@BeforeAll
	public void setUpBeforeClass() throws Exception {
		assertNotNull(companyUserRepository);

		final List<CompanyUser> companyUsers = new ArrayList<>();
		companyUsers.add(newCompanyUser (null, "John", "Doe", LocalDate.of(1985, 8, 18), "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (null, "Marc", "Simple", LocalDate.of(2000, 7, 11), "FRANCE", "+33685967265", "marc@simple.com" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (null, "Ahmed", "CHOUIA", LocalDate.of(1970, 5, 10), "FRANCE", "+33667776789", "ahmed@chouia.com" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (null, "Françoise", "NGANDA", LocalDate.of(1999, 1, 3), "FRANCE", "+33669991245", "francoise@nganda.com" ,"F" ,null, null));
		companyUsers.add(newCompanyUser (null, "Patricia", "PARKER", LocalDate.of(1983, 9, 12), "FRANCE", "+33668881245", "patrica@parker.com" ,"F" ,null, null));
		companyUserRepository.saveAll(companyUsers);
		
		this.url = "http://localhost:" + port + "/" + "api/v1/users/";
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	public void given_user_id_getDetails_then_failsOnUserNotExists() {
		final Long userId = 10L;
		ResponseEntity<CompanyUserDto> responseEntity = restTemplate.getForEntity(this.url + "/" + userId, CompanyUserDto.class);
	
		assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void given_valid_user_id_getDetails_then_success() {
		final Long userId = 1L;
		ResponseEntity<CompanyUserDto> responseEntity = restTemplate.getForEntity(this.url + "/" + userId, CompanyUserDto.class);
		CompanyUserDto companyUser = responseEntity.getBody();
		assertEquals(companyUser.getEmail(), "john@doe.fr");
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void register_invalid_CompanyUser_InvalidEmail_should_failed() {
		CompanyUserDto companyUserDto = newCompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "FRANCE", "+33667771245", "Priscillia_email" ,"F" ,null, null);
		ResponseEntity<CompanyUserErrorResponse> responseEntity = restTemplate.postForEntity(this.url + "/" + "register", companyUserDto, CompanyUserErrorResponse.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void register_invalid_CompanyUser_emailAlreadyUsed_should_failed() {
		CompanyUserDto companyUserDto = newCompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "FRANCE", "+33667771245", "john@doe.fr" ,"F" ,null, null);
		ResponseEntity<CompanyUserErrorResponse> responseEntity = restTemplate.postForEntity(this.url + "/" + "register", companyUserDto, CompanyUserErrorResponse.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void register_invalid_CompanyUser_non_adult_should_failed() {
		CompanyUserDto companyUserDto = newCompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2020", "FRANCE", "+33667771245", "priscillia@dumas.fr" ,"F" ,null, null);
		ResponseEntity<CompanyUserErrorResponse> responseEntity = restTemplate.postForEntity(this.url + "/" + "register", companyUserDto, CompanyUserErrorResponse.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void register_invalid_CompanyUser_non_French_resident_should_failed() {
		CompanyUserDto companyUserDto = newCompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "ENGLAND", "+33667771245", "priscillia@dumas.fr" ,"F" ,null, null);
		ResponseEntity<CompanyUserErrorResponse> responseEntity = restTemplate.postForEntity(this.url + "/" + "register", companyUserDto, CompanyUserErrorResponse.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void register_invalid_CompanyUser_not_valid_gender_should_failed() {
		CompanyUserDto companyUserDto = newCompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "FRANCE", "+33667771245", "priscillia@dumas.fr" ,"A" ,null, null);
		ResponseEntity<CompanyUserErrorResponse> responseEntity = restTemplate.postForEntity(this.url + "/" + "register", companyUserDto, CompanyUserErrorResponse.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
	}
	
	@Test
	public void register_valid_CompanyUser_should_sucess() {
		CompanyUserDto companyUserDto = newCompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "FRANCE", "+33668881245", "priscillia@dumas.fr" ,"F" ,null, null);
		ResponseEntity<CompanyUserDto> responseEntity = restTemplate.postForEntity(this.url + "/" + "register", companyUserDto, CompanyUserDto.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
		companyUserDto = responseEntity.getBody();
		companyUserRepository.deleteById(companyUserDto.getUserId()); //Clean
	}
	
}

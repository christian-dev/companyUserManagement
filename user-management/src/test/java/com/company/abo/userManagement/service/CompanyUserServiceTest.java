package com.company.abo.userManagement.service;

import static com.company.abo.userManagement.mapper.CompanyUserMapperTestConfiguration.*;
import static org.junit.jupiter.api.Assertions.*;

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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.exception.CompanyUserNotFoundException;
import com.company.abo.userManagement.exception.EmailAlreadyExistsException;
import com.company.abo.userManagement.mapper.CompanyUserMapper;
import com.company.abo.userManagement.model.CompanyUser;
import com.company.abo.userManagement.repository.CompanyUserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyUserServiceTest {
	@Autowired
	private CompanyUserRepository companyUserRepository;
	
	@Autowired
	private CompanyUserMapper companyUserMapper;
	
	@Autowired
	private CompanyUserService companyUserService;

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

	}

	@AfterAll
	public void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
	
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void given_CompanyUserDto_when_createCompanyUser_then_failsOnEmailAreadyExists() {
		assertNotNull(companyUserService);

		final CompanyUserDto companyUserDto = newCompanyUserDto (null, "Johnny", "Doel", "10/05/1980", "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null);
		assertThrows(EmailAlreadyExistsException.class,  
				() -> {
					companyUserService.createCompanyUser(companyUserDto);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_createCompanyUser_and_emailNotAreadyExists_then_success() {
		assertNotNull(companyUserService);

		CompanyUserDto companyUserDto = newCompanyUserDto (null, "Johnny", "Doel", "10/05/1980", "FRANCE", "+33667771245", "johnny@doe.fr" ,"M" ,null, null);
	
		companyUserDto = companyUserService.createCompanyUser(companyUserDto);
		
		assertNotNull(companyUserDto.getUserId());
		companyUserRepository.deleteById(companyUserDto.getUserId());

	}
	
	@Test
	public void given_CompanyUserDto_when_updateCompanyUser_then_failsWhenIdNotExists() {
		assertNotNull(companyUserService);

		
		final CompanyUserDto companyUserDto = newCompanyUserDto (10L, "John", "Doe", "10/05/1980", "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null);
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.updateCompanyUser(10L, companyUserDto);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_updateCompanyUser_then_failsOnNewEmailAlreadyUsedByOtherUser() {
		assertNotNull(companyUserService);

		final CompanyUserDto companyUserDto = newCompanyUserDto (1L, "John", "Doe", "10/05/1980", "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,null, null);
		
		assertThrows(EmailAlreadyExistsException.class,  
				() -> {
					companyUserService.updateCompanyUser(1L, companyUserDto);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_updateCompanyUser_then_sucess() {
		assertNotNull(companyUserService);

		final CompanyUserDto companyUserDto = newCompanyUserDto (1L, "John", "Doe", "10/05/1980", "FRANCE", "+33612345678", "johnny@doe.com" ,"M" ,null, null);
		
		companyUserService.updateCompanyUser(1L, companyUserDto);
		
		companyUserDto.setEmail("john@doe.fr");
		
		companyUserService.updateCompanyUser(1L, companyUserDto);
		
	}
	
	@Test
	public void given_CompanyUserDto_when_patchCompanyUser_then_failsWhenIdNotExists() {
		assertNotNull(companyUserService);

		final Map<String, String> valueMap = new HashMap<>();
		valueMap.put("email", "john10@doe.fr");
		valueMap.put("phoneNumber", "+33667771245");
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.patchCompanyUser(10L, valueMap);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_patchCompanyUser_then_failsWhenEmailAlreadUsed() {
		assertNotNull(companyUserService);

		final Map<String, String> valueMap = new HashMap<>();
		valueMap.put("email", "marc@simple.com");
		valueMap.put("phoneNumber", "+33660001245");
		assertThrows(EmailAlreadyExistsException.class,  
				() -> {
					companyUserService.patchCompanyUser(1L, valueMap);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_patchCompanyUser_then_sucess() {
		assertNotNull(companyUserService);

		final Map<String, String> valueMap = new HashMap<>();
		valueMap.put("email", "john10@doe.fr");
		valueMap.put("phoneNumber", "+33660001245");
		
		companyUserService.patchCompanyUser(1L, valueMap);
		
		valueMap.put("email", "john@doe.fr");
		companyUserService.patchCompanyUser(1L, valueMap);
	}
	
	@Test
	public void given_CompanyUserDto_when_deleteCompanyUser_then_failsWhenIdNotExists() {
		assertNotNull(companyUserService);

		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.deleteCompanyUser(10L);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_deleteCompanyUser_then_success() {
		assertNotNull(companyUserService);
		CompanyUser companyUser = newCompanyUser (null, "Samantha", "LULOUA", LocalDate.of(1960, 6, 30), "FRANCE", "+33667771245", "samantha@luloua.fr" ,"F" ,null, null);
		CompanyUserDto companyUserDto = companyUserMapper.companyUserEntityToDto(companyUserRepository.save(companyUser)); 
		final Long userId = companyUserDto.getUserId();
		assertNotNull(userId);
		
		companyUserService.deleteCompanyUser(userId);
		
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.getCompanyUserDetails(userId);
				});
		
	}
	

	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_by_id_then_failsWhenIdNotExists() {
		assertNotNull(companyUserService);
		
		final Long userId = 10L;
		
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.getCompanyUserDetails(userId);
				});

	}
	
	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_then_sucess() {
		assertNotNull(companyUserService);
		
		final Long userId = 1L;
		
		CompanyUserDto companyUserDto = companyUserService.getCompanyUserDetails(userId);
		assertEquals(companyUserDto.getEmail(), "john@doe.fr");

	}
	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_by_name_then_sucess() {
		assertNotNull(companyUserService);
		
		final String firstName = "John";
		final String lastName = "Doe";

		List<CompanyUserDto> companyUserDtos = companyUserService.getCompanyUserDetails(firstName, lastName);
		assertEquals(companyUserDtos.size(), 1);
		assertEquals(companyUserDtos.get(0).getEmail(), "john@doe.fr");

	}
	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_by_emailthen_sucess() {
		assertNotNull(companyUserService);
		
		final String email = "john@doe.fr";
		
		CompanyUserDto companyUserDto = companyUserService.getCompanyUserDetails(email);
		assertEquals(companyUserDto.getUserId(), 1L);
		
	}

}



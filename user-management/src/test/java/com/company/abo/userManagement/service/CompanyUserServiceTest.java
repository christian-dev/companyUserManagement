package com.company.abo.userManagement.service;

import static com.company.abo.userManagement.mapper.CompanyUserMapperTestConfiguration.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.company.abo.userManagement.config.CompanyAppProperties;
import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.exception.CompanyUserNotFoundException;
import com.company.abo.userManagement.exception.EmailAlreadyExistsException;
import com.company.abo.userManagement.mapper.CompanyUserMapper;
import com.company.abo.userManagement.model.CompanyUser;
import com.company.abo.userManagement.repository.CompanyUserRepository;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(CompanyUserServiceTestConfiguration.class)
public class CompanyUserServiceTest {
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Autowired
	private CompanyUserRepository companyUserRepository;
	
	@Autowired
	private CompanyUserMapper companyUserMapper;
	
	@Autowired
	private CompanyUserService companyUserService;

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		assertNotNull(companyUserService);
		when(companyAppProperties.getDateFormatPattern()).thenReturn("dd/MM/yyyy");
		when(companyAppProperties.getDateTimeFormatPattern()).thenReturn("dd/MM/yyyy HH:mm:ss");
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void given_CompanyUserDto_when_createCompanyUser_then_failsOnEmailAreadyExists() {
		
		
		final LocalDate birthDate = LocalDate.of(1980, 9, 29); 
		final LocalDateTime creationDate = LocalDateTime.of(2022, 8, 29, 15, 50, 9);
		final LocalDateTime updateDate = LocalDateTime.of(2022, 8, 30, 16, 17, 20);
		
		final CompanyUserDto companyUserDto = newCompanyUserDto (null, "Johnny", "Doel", "10/05/1980", "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null);
		final CompanyUser existingCompanyUser = newCompanyUser (1L, "John", "Doe", birthDate, "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,creationDate, updateDate);
		
		final Optional<CompanyUser> optionalExistingCompanyUser = Optional.of(existingCompanyUser);
		when(companyUserRepository.findByEmail("john@doe.fr")).thenReturn(optionalExistingCompanyUser);
		assertThrows(EmailAlreadyExistsException.class,  
				() -> {
					companyUserService.createCompanyUser(companyUserDto);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_createCompanyUser_and_emailNotAreadyExists_then_success() {
		
		
		final LocalDate birthDate = LocalDate.of(1980, 9, 29); 
		final LocalDateTime creationDate = LocalDateTime.of(2022, 8, 29, 15, 50, 9);
		final LocalDateTime updateDate = LocalDateTime.of(2022, 8, 30, 16, 17, 20);
		
		
		CompanyUserDto companyUserDto = newCompanyUserDto (null, "John", "Doe", "29/09/1980", "FRANCE", "+33667771245", "john@doe.fr"  ,"M" ,null, null);
		final CompanyUser createdCompanyUser = newCompanyUser (1L, "John", "Doe", birthDate, "FRANCE", "+33667771245", "john@doe.fr", "M", creationDate, updateDate); 
		when(companyUserRepository.findByEmail("john@doe.fr")).thenReturn(Optional.empty());
		when(companyUserRepository.save(companyUserMapper.companyUserDtoToEntity(companyUserDto))).thenReturn(createdCompanyUser);
		companyUserDto = companyUserService.createCompanyUser(companyUserDto);
		
		assertNotNull(companyUserDto.getUserId());

	}
	
	@Test
	public void given_CompanyUserDto_when_updateCompanyUser_then_failsWhenIdNotExists() {
		
		final Long userId = 10L;
		when(companyUserRepository.findById(userId)).thenReturn(Optional.empty());
		
		final CompanyUserDto companyUserDto = newCompanyUserDto (userId, "John", "Doe", "10/05/1980", "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null);
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.updateCompanyUser(10L, companyUserDto);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_updateCompanyUser_then_failsOnNewEmailAlreadyUsedByOtherUser() {
		
		final Long userId = 1L;
		final CompanyUserDto companyUserDto = newCompanyUserDto (1L, "John", "Doe", "10/05/1980", "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,null, null);
		final Optional<CompanyUser> optionalCompanyUser = Optional.of(companyUserMapper.companyUserDtoToEntity(companyUserDto));
		
		final LocalDate birthDate = LocalDate.of(1980, 9, 29); 
		final LocalDateTime creationDate = LocalDateTime.of(2022, 8, 29, 15, 50, 9);
		final LocalDateTime updateDate = LocalDateTime.of(2022, 8, 30, 16, 17, 20);
		final CompanyUser existingCompanyUser = newCompanyUser (5L, "Marc", "Simple", birthDate, "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,creationDate, updateDate);
		
		final Optional<CompanyUser> otherExistingCompanyUser = Optional.of(existingCompanyUser);
		final String emailAlreadyUsed = "marc@simple.com";
		when(companyUserRepository.existsById(userId)).thenReturn(Boolean.TRUE);
		when(companyUserRepository.findById(userId)).thenReturn(optionalCompanyUser);
		when(companyUserRepository.findByEmail(emailAlreadyUsed)).thenReturn(otherExistingCompanyUser);
		
		assertThrows(EmailAlreadyExistsException.class,  
				() -> {
					companyUserService.updateCompanyUser(1L, companyUserDto);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_updateCompanyUser_then_sucess() {
		
		final Long userId = 1L;
		final CompanyUserDto companyUserDto = newCompanyUserDto (userId, "John", "Doe", "10/05/1980", "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,null, null);
		final Optional<CompanyUser> optionalCompanyUser = Optional.of(companyUserMapper.companyUserDtoToEntity(companyUserDto));
		companyUserDto.setEmail("john@doe.fr");
		
		when(companyUserRepository.findByEmail("john@doe.com")).thenReturn(Optional.empty());
		when(companyUserRepository.findById(userId)).thenReturn(optionalCompanyUser);
		when(companyUserRepository.existsById(userId)).thenReturn(Boolean.TRUE);
		
		companyUserService.updateCompanyUser(1L, companyUserDto);
		
	}
	
	@Test
	public void given_CompanyUserDto_when_patchCompanyUser_then_failsWhenIdNotExists() {
		
		final Long userId = 10L;
		
		final Map<String, String> valueMap = new HashMap<>();
		valueMap.put("email", "john10@doe.fr");
		valueMap.put("phoneNumber", "+33667771245");
		
		when(companyUserRepository.findById(userId)).thenReturn(Optional.empty());
		
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.patchCompanyUser(userId, valueMap);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_patchCompanyUser_then_failsWhenEmailAlreadUsed() {
		final Long userId = 1L;
		final CompanyUserDto companyUserDto = newCompanyUserDto (1L, "John", "Doe", "10/05/1980", "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,null, null);
		final Optional<CompanyUser> optionalCompanyUser = Optional.of(companyUserMapper.companyUserDtoToEntity(companyUserDto));
		
		final LocalDate birthDate = LocalDate.of(1980, 9, 29); 
		final LocalDateTime creationDate = LocalDateTime.of(2022, 8, 29, 15, 50, 9);
		final LocalDateTime updateDate = LocalDateTime.of(2022, 8, 30, 16, 17, 20);
		final CompanyUser existingCompanyUser = newCompanyUser (5L, "Marc", "Simple", birthDate, "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,creationDate, updateDate);
		
		final Optional<CompanyUser> otherExistingCompanyUser = Optional.of(existingCompanyUser);
		final String emailAlreadyUsed = "marc@simple.com";
		when(companyUserRepository.existsById(userId)).thenReturn(Boolean.TRUE);
		when(companyUserRepository.findById(userId)).thenReturn(optionalCompanyUser);
		when(companyUserRepository.findByEmail(emailAlreadyUsed)).thenReturn(otherExistingCompanyUser);
		
		final Map<String, String> valueMap = new HashMap<>();
		valueMap.put("email", emailAlreadyUsed);
		valueMap.put("phoneNumber", "+33660001245");
		assertThrows(EmailAlreadyExistsException.class,  
				() -> {
					companyUserService.patchCompanyUser(userId, valueMap);
				});

	}
	
	@Test
	public void given_CompanyUserDto_when_patchCompanyUser_then_success() {
		
			
		final Long userId = 1L;
		final CompanyUserDto companyUserDto = newCompanyUserDto (userId, "John", "Doe", "10/05/1980", "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,null, null);
		final Optional<CompanyUser> optionalCompanyUser = Optional.of(companyUserMapper.companyUserDtoToEntity(companyUserDto));
		companyUserDto.setEmail("john@doe.fr");
		
		when(companyUserRepository.findByEmail("john@doe.com")).thenReturn(Optional.empty());
		when(companyUserRepository.findById(userId)).thenReturn(optionalCompanyUser);
		when(companyUserRepository.existsById(userId)).thenReturn(Boolean.TRUE);
		when(companyUserRepository.save(any(CompanyUser.class))).thenAnswer(s -> s.getArgument(0));
		
		final Map<String, String> valueMap = new HashMap<>();
		valueMap.put("email", "john10@doe.fr");
		valueMap.put("phoneNumber", "+33660001245");
		
		final CompanyUserDto patchedCompanyUserDto = companyUserService.patchCompanyUser(userId, valueMap);
		assertEquals(patchedCompanyUserDto.getEmail(), "john10@doe.fr");
		assertEquals(patchedCompanyUserDto.getPhoneNumber(), "+33660001245");
		
	}
	
	@Test
	public void given_CompanyUserDto_when_deleteCompanyUser_then_failsWhenIdNotExists() {
		
		final Long userId = 10L;
		when(companyUserRepository.findById(userId)).thenReturn(Optional.empty());
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.deleteCompanyUser(userId);
				});

	}
	
	@Test
	public void given_existing_CompanyUserDto_when_deleteCompanyUser_then_success() {
		
		final Long userId = 25L;
		final CompanyUser companyUser = newCompanyUser (userId, "Samantha", "LULOUA", LocalDate.of(1960, 6, 30), "FRANCE", "+33667771245", "samantha@luloua.fr" ,"F" ,null, null);
		
		when(companyUserRepository.findById(userId)).thenReturn(Optional.of(companyUser));
		
		final Map<String, Object> deleteResult = companyUserService.deleteCompanyUser(userId);
		
		assertEquals(deleteResult.get("userId"), userId);
		assertEquals(deleteResult.get("deleted"), Boolean.TRUE);
	}
	

	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_by_id_then_failsWhenIdNotExists() {
		
		
		final Long userId = 10L;
		when(companyUserRepository.findById(userId)).thenReturn(Optional.empty());
		
		assertThrows(CompanyUserNotFoundException.class,  
				() -> {
					companyUserService.getCompanyUserDetails(userId);
				});

	}
	
	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_then_sucess() {
		
		
		final Long userId = 5L;
		final LocalDate birthDate = LocalDate.of(1980, 9, 29); 
		final LocalDateTime creationDate = LocalDateTime.of(2022, 8, 29, 15, 50, 9);
		final LocalDateTime updateDate = LocalDateTime.of(2022, 8, 30, 16, 17, 20);
		final CompanyUser existingCompanyUser = newCompanyUser (userId, "Marc", "Simple", birthDate, "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,creationDate, updateDate);
		when(companyUserRepository.findById(userId)).thenReturn(Optional.of(existingCompanyUser));
		
		CompanyUserDto companyUserDto = companyUserService.getCompanyUserDetails(userId);
		assertEquals(userId, companyUserDto.getUserId());
		assertEquals("marc@simple.com", companyUserDto.getEmail());
		assertEquals("Marc", companyUserDto.getFirstName());
		assertEquals("Simple", companyUserDto.getLastName());
		assertEquals("29/09/1980", companyUserDto.getBirthdate());
		assertEquals("FRANCE", companyUserDto.getCountryOfResidence());
		assertEquals("+33667771245", companyUserDto.getPhoneNumber());
		assertEquals("M", companyUserDto.getGender());
	}
	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_by_name_then_sucess() {
		
		final Long userId = 5L;
		final LocalDate birthDate = LocalDate.of(1980, 9, 29); 
		final LocalDateTime creationDate = LocalDateTime.of(2022, 8, 29, 15, 50, 9);
		final LocalDateTime updateDate = LocalDateTime.of(2022, 8, 30, 16, 17, 20);
		final CompanyUser existingCompanyUser = newCompanyUser (userId, "Marc", "Simple", birthDate, "FRANCE", "+33667771245", "marc@simple.com" ,"M" ,creationDate, updateDate);
		
		final List<CompanyUser> foundUserList = new ArrayList<>();
		foundUserList.add(existingCompanyUser);
		final String firstName = "Marc";
		final String lastName = "Simple";
		when(companyUserRepository.findByFirstNameAndLastName(firstName, lastName)).thenReturn(foundUserList);
		
		List<CompanyUserDto> companyUserDtos = companyUserService.getCompanyUserDetails(firstName, lastName);
		assertEquals(1, companyUserDtos.size());
		final CompanyUserDto companyUserDto = companyUserDtos.get(0);
		
		assertEquals(userId, companyUserDto.getUserId());
		assertEquals("marc@simple.com", companyUserDto.getEmail());
		assertEquals("Marc", companyUserDto.getFirstName());
		assertEquals("Simple", companyUserDto.getLastName());
		assertEquals("29/09/1980", companyUserDto.getBirthdate());
		assertEquals("FRANCE", companyUserDto.getCountryOfResidence());
		assertEquals("+33667771245", companyUserDto.getPhoneNumber());
		assertEquals("M", companyUserDto.getGender());

	}
	
	@Test
	public void given_CompanyUserDto_when_getCompanyUserDetails_by_email_then_sucess() {
		
		final Long userId = 5L;
		final String email = "marc@simple.com";
		final LocalDate birthDate = LocalDate.of(1980, 9, 29); 
		final LocalDateTime creationDate = LocalDateTime.of(2022, 8, 29, 15, 50, 9);
		final LocalDateTime updateDate = LocalDateTime.of(2022, 8, 30, 16, 17, 20);
		final CompanyUser existingCompanyUser = newCompanyUser (userId, "Marc", "Simple", birthDate, "FRANCE", "+33667771245", email ,"M" ,creationDate, updateDate);
		
		when(companyUserRepository.findByEmail(email)).thenReturn(Optional.of(existingCompanyUser));
		
		CompanyUserDto companyUserDto = companyUserService.getCompanyUserDetails(email);
		assertEquals(userId, companyUserDto.getUserId());
		assertEquals(email, companyUserDto.getEmail());
		assertEquals("Marc", companyUserDto.getFirstName());
		assertEquals("Simple", companyUserDto.getLastName());
		assertEquals("29/09/1980", companyUserDto.getBirthdate());
		assertEquals("FRANCE", companyUserDto.getCountryOfResidence());
		assertEquals("+33667771245", companyUserDto.getPhoneNumber());
		assertEquals("M", companyUserDto.getGender());
		
	}

}



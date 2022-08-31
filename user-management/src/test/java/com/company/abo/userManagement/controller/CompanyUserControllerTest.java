package com.company.abo.userManagement.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.company.abo.userManagement.config.CompanyAppProperties;
import com.company.abo.userManagement.controller.exceptionHandler.CompanyUserExceptionHandler;
import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.exception.CompanyUserNotFoundException;
import com.company.abo.userManagement.exception.EmailAlreadyExistsException;
import com.company.abo.userManagement.service.CompanyUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@Import(CompanyUserControllerTestConfig.class)
@WebAppConfiguration
public class CompanyUserControllerTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private CompanyUserService companyUserService;
	
	@Autowired
	private CompanyUserController companyUserController;
	
	@Autowired
	private CompanyUserExceptionHandler companyUserExceptionHandler;
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Autowired
	private LocalValidatorFactoryBean localValidatorFactoryBean;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		when(companyAppProperties.getDateFormatPattern()).thenReturn("dd/MM/yyyy");
		when(companyAppProperties.getDateTimeFormatPattern()).thenReturn("dd/MM/yyyy HH:mm:ss");
		
		final List<String> validCountryOfResidences = new ArrayList<>();
		validCountryOfResidences.add("FRANCE"); 
		
		final List<String> validGenders = new ArrayList<>();
		validGenders.add("M"); 
		validGenders.add("F");
 
 		when(companyAppProperties.getValidCountryOfResidences()).thenReturn(validCountryOfResidences);
 		when(companyAppProperties.getAdulteAge()).thenReturn(18);
 		when(companyAppProperties.getValidGenders()).thenReturn(validGenders);
		
		this.mockMvc = MockMvcBuilders
				.standaloneSetup(companyUserController)
				.setValidator(localValidatorFactoryBean)
				.setControllerAdvice(companyUserExceptionHandler)
				.build();
		
	}
	
	@Test
	public void given_WebAppContext_check_controller() {
		ServletContext servletContext = webApplicationContext.getServletContext();
		
		assertNotNull(servletContext);
		assertTrue(servletContext instanceof MockServletContext);
		assertNotNull(webApplicationContext.getBean(CompanyUserController.class));
		assertNotNull(webApplicationContext.getBean(CompanyUserExceptionHandler.class));
	}
	
	@Test
	public void given_user_id_getDetails_then_failsOnUserNotExists() throws Exception {
		final Long userId = 10L;
		when(companyUserService.getCompanyUserDetails(anyLong())).thenThrow(CompanyUserNotFoundException.class);
		this.mockMvc.perform(get("/users/" + userId))
			.andExpect(status().isNotFound())
			.andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyUserNotFoundException));		
	}
	
	@Test
	public void given_existing_user_id_getDetails_then_success() throws Exception {
		final Long userId = 10L;
		final String firstName = "John";
		final String lastName = "Doe";
		final String birthdate = "17/09/2000";
		final String countryOfResidence = "FRANCE";
		final String phoneNumber = "+33689567412";
		final String email = "john@doe.com";
		final String gender = "M";
		final String creationDate = "13/05/2000 11:15:09"; 
		final String updateDate = "30/08/2022 09:52:11";
		final CompanyUserDto companyUserDto = new CompanyUserDto(
				userId, firstName, lastName, birthdate, countryOfResidence, phoneNumber, email, gender, creationDate, updateDate);
		
		when(companyUserService.getCompanyUserDetails(userId)).thenReturn(companyUserDto);
		
		
		this.mockMvc.perform(get("/users/" + userId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("userId").value(userId))
		.andExpect(jsonPath("firstName").value(firstName))
		.andExpect(jsonPath("lastName").value(lastName))
		.andExpect(jsonPath("birthdate").value(birthdate))
		.andExpect(jsonPath("phoneNumber").value(phoneNumber))
		.andExpect(jsonPath("email").value(email))
		.andExpect(jsonPath("countryOfResidence").value(countryOfResidence))
		.andExpect(jsonPath("gender").value(gender))
		.andExpect(jsonPath("creationDate").value(creationDate))
		.andExpect(jsonPath("updateDate").value(updateDate));
		
	}

	
	@Test
	public void register_invalid_CompanyUser_InvalidEmail_should_failed() throws Exception {

		final Long userId = null;
		final String firstName = "John";
		final String lastName = "Doe";
		final String birthdate = "17/09/2000";
		final String countryOfResidence = "FRANCE";
		final String phoneNumber = "+33689567412";
		final String email = "john.doe.com";
		final String gender = "M";
		
		final CompanyUserDto companyUserDto = new CompanyUserDto(
				userId, firstName, lastName, birthdate, countryOfResidence, phoneNumber, email, gender, null, null);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		final String jsonContent = objectWriter.writeValueAsString(companyUserDto);
		final URI uri = UriComponentsBuilder.fromUriString("/users/").build().encode().toUri();
		
		mockMvc.perform(post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent))
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void register_invalid_CompanyUser_emailAlreadyUsed_should_failed() throws Exception{
		when(companyUserService.createCompanyUser(any(CompanyUserDto.class))).thenThrow(EmailAlreadyExistsException.class);
		
		final Long userId = null;
		final String firstName = "John";
		final String lastName = "Doe";
		final String birthdate = "17/09/2000";
		final String countryOfResidence = "FRANCE";
		final String phoneNumber = "+33689567412";
		final String email = "john@doe.com";
		final String gender = "M";
		
		final CompanyUserDto companyUserDto = new CompanyUserDto(
				userId, firstName, lastName, birthdate, countryOfResidence, phoneNumber, email, gender, null, null);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		final String jsonContent = objectWriter.writeValueAsString(companyUserDto);
		final URI uri = UriComponentsBuilder.fromUriString("/users/").build().encode().toUri();
		
		mockMvc.perform(post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent))
				.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void register_invalid_CompanyUser_non_adult_should_failed() throws Exception{
		
 		final CompanyUserDto companyUserDto = new CompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2018", "FRANCE", "+33667771245", "priscillia@dumas.fr" ,"F" ,null, null);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		final String jsonContent = objectWriter.writeValueAsString(companyUserDto);
		final URI uri = UriComponentsBuilder.fromUriString("/users/").build().encode().toUri();
		
		mockMvc.perform(post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent))
				.andExpect(status().isBadRequest())
				.andReturn().getResolvedException();
		
	}
	
	@Test
	public void register_invalid_CompanyUser_non_French_resident_should_failed() throws Exception{
		CompanyUserDto companyUserDto = new CompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "ENGLAND", "+33667771245", "priscillia@dumas.fr" ,"F" ,null, null);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		final String jsonContent = objectWriter.writeValueAsString(companyUserDto);
		final URI uri = UriComponentsBuilder.fromUriString("/users/").build().encode().toUri();
		
		mockMvc.perform(post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void register_invalid_CompanyUser_not_valid_gender_should_failed() throws Exception {
		CompanyUserDto companyUserDto = new CompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "FRANCE", "+33667771245", "priscillia@dumas.fr" ,"A" ,null, null);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		final String jsonContent = objectWriter.writeValueAsString(companyUserDto);
		final URI uri = UriComponentsBuilder.fromUriString("/users/").build().encode().toUri();
		
		mockMvc.perform(post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void register_valid_CompanyUser_should_sucess() throws Exception {
	
		final CompanyUserDto companyUserDto = new CompanyUserDto (null, "Priscillia", "DUMAS", "17/10/2000", "FRANCE", "+33668881245", "priscillia@dumas.fr" ,"F" ,null, null);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
		final String jsonContent = objectWriter.writeValueAsString(companyUserDto);
		final URI uri = UriComponentsBuilder.fromUriString("/users/").build().encode().toUri();
		
		mockMvc.perform(post(uri)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent))
				.andExpect(status().isCreated());
	}
	
	
}

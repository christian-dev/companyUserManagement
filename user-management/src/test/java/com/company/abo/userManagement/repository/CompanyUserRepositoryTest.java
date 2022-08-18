package com.company.abo.userManagement.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.company.abo.userManagement.model.CompanyUser;

import static com.company.abo.userManagement.mapper.CompanyUserMapperTestConfiguration.*;


@ExtendWith(SpringExtension.class)
//@Import({CompanyUserMapperTestConfiguration.class, AppDateFormatterTestConfiguration.class})
@DataJpaTest
public class CompanyUserRepositoryTest {
	
	@Autowired 
	private CompanyUserRepository companyUserRepository;
	
	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	public void setUp() throws Exception {
		List<CompanyUser> companyUsers = new ArrayList<>();
		companyUsers.add(newCompanyUser (null, "John", "Doe", LocalDate.of(1985, 8, 18), "FRANCE", "+33667771245", "john@doe.fr" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (null, "Marc", "Simple", LocalDate.of(2000, 7, 11), "FRANCE", "+33685967265", "marc@simple.com" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (null, "Ahmed", "CHOUIA", LocalDate.of(1970, 5, 10), "FRANCE", "+33667776789", "ahmed@chouia.com" ,"M" ,null, null));
		companyUsers.add(newCompanyUser (null, "Françoise", "NGANDA", LocalDate.of(1999, 1, 3), "FRANCE", "+33669991245", "francoise@nganda.com" ,"F" ,null, null));
		companyUsers.add(newCompanyUser (null, "Patricia", "PARKER", LocalDate.of(1983, 9, 12), "FRANCE", "+33668881245", "patrica@parker.com" ,"F" ,null, null));
		companyUserRepository.saveAll(companyUsers);
	
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void testFindAll() {
		assertNotNull(companyUserRepository);
			
		List<CompanyUser> allCompanyUsers = companyUserRepository.findAll();
		assertTrue(allCompanyUsers.size() == 5);
		
		assertNotNull(allCompanyUsers.get(0).getUserId());
		assertNotNull(allCompanyUsers.get(1).getUserId());
		assertNotNull(allCompanyUsers.get(2).getUserId());
		assertNotNull(allCompanyUsers.get(3).getUserId());
		assertNotNull(allCompanyUsers.get(4).getUserId());
	}
	
	@Test
	public void testFindAllByFirstName() {
		assertNotNull(companyUserRepository);
		
			
		List<CompanyUser> allCompanyUsers = companyUserRepository.findAllByFirstName("Patricia");
		assertTrue(allCompanyUsers.size() == 1);
		assertEquals(allCompanyUsers.get(0).getEmail(), "patrica@parker.com");
	}

	@Test
	public void testFindAllByLastName() {
		assertNotNull(companyUserRepository);
		
			
		List<CompanyUser> allCompanyUsers = companyUserRepository.findAllByLastName("Doe");
		assertTrue(allCompanyUsers.size() == 1);
		assertEquals(allCompanyUsers.get(0).getEmail(), "john@doe.fr");
	}
	
	@Test
	public void testFindAllByGender() {
		assertNotNull(companyUserRepository);
		
			
		List<CompanyUser> allCompanyUsers = companyUserRepository.findAllByGender("F");
		assertTrue(allCompanyUsers.size() == 2);
		
		allCompanyUsers = companyUserRepository.findAllByGender("M");
		assertTrue(allCompanyUsers.size() == 3);
	}
	
	@Test
	public void testFindAllByPhoneNumber() {
		assertNotNull(companyUserRepository);
		
			
		List<CompanyUser> allCompanyUsers = companyUserRepository.findAllByPhoneNumber("+33669991245");
		assertTrue(allCompanyUsers.size() == 1);
		assertEquals(allCompanyUsers.get(0).getEmail(), "francoise@nganda.com");
	}
	
	@Test
	public void testFindByEmail() {
		assertNotNull(companyUserRepository);
					
		final Optional<CompanyUser> companyUser = companyUserRepository.findByEmail("john@doe.fr");
		
		assertTrue(companyUser.isPresent());
		assertEquals(companyUser.get().getFirstName(), "John");
		assertEquals(companyUser.get().getLastName(), "Doe");
	}
	
	
	@Test
	public void testFindAllByFirstNameAndLastName() {
		assertNotNull(companyUserRepository);
				
		List<CompanyUser> allCompanyUsers = companyUserRepository.findByFirstNameAndLastName("Patricia", "PARKER");
		assertTrue(allCompanyUsers.size() == 1);
		assertEquals(allCompanyUsers.get(0).getEmail(), "patrica@parker.com");
	}
	
	
}

package com.company.abo.userManagement.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

import java.time.LocalDate;


import com.company.abo.userManagement.config.CompanyAppProperties;



@ExtendWith(SpringExtension.class)
@Import(AppDateFormatterTestConfiguration.class)
public class AppDateFormatterTest {
	
	@MockBean
	private CompanyAppProperties companyAppProperties;
	
	@Autowired
	private AppDateFormatter appDateFormatter;
	
	@BeforeEach
	public void setUp() {
		
		when(companyAppProperties.getDateFormatPattern()).thenReturn("dd/MM/yyyy");
	}
	
	@Test
	public void testParseBirthdate() {
		assertNotNull(appDateFormatter);
		String sBirthdate = "18/08/2000";
		LocalDate birthDate = appDateFormatter.parseBirthdate(sBirthdate);
		assertEquals(birthDate.getYear(), 2000);
		assertEquals(birthDate.getMonthValue(), 8);
		assertEquals(birthDate.getDayOfMonth(), 18);
	}

	@Test
	void testFormatBirthdate() {
		assertNotNull(appDateFormatter);
		LocalDate birthdate = LocalDate.of(2000, 8, 18);
		String sBirthdate = appDateFormatter.formatBirthdate(birthdate);
		assertEquals(sBirthdate, "18/08/2000");
	}

}

package com.company.abo.userManagement.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.company.abo.userManagement.config.CompanyAppProperties;


@ExtendWith(SpringExtension.class)
@Import(AppDateFormatterTestConfiguration.class)
public class AppDateFormatterTest {
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Autowired
	private AppDateFormatter appDateFormatter;
	
	@BeforeEach
	public void setUp() {
		when(companyAppProperties.getDateFormatPattern()).thenReturn("dd/MM/yyyy");
		when(companyAppProperties.getDateTimeFormatPattern()).thenReturn("dd/MM/yyyy HH:mm:ss");
	}
	
	@Test
	public void testParseDate() {
		assertNotNull(appDateFormatter);
		String sBirthdate = "18/08/2000";
		LocalDate birthDate = appDateFormatter.parseDate(sBirthdate);
		assertEquals(2000, birthDate.getYear());
		assertEquals(8, birthDate.getMonthValue());
		assertEquals(18, birthDate.getDayOfMonth());
	}

	@Test
	void testFormatDate() {
		assertNotNull(appDateFormatter);
		LocalDate date = LocalDate.of(2000, 8, 18);
		String sBirthdate = appDateFormatter.formatDate(date);
		assertEquals("18/08/2000", sBirthdate);
	}
	
	
	@Test
	public void testParseDateTime() {
		assertNotNull(appDateFormatter);
		String sDateTime = "18/08/2000 11:56:07";
		LocalDateTime dateTime = appDateFormatter.parseDateTime(sDateTime);
		assertEquals(2000, dateTime.getYear());
		assertEquals(8, dateTime.getMonthValue());
		assertEquals(18, dateTime.getDayOfMonth());
	}

	@Test
	void testFormatDateTime() {
		assertNotNull(appDateFormatter);
		LocalDateTime dateTime = LocalDateTime.of(2000, 8, 18, 11, 56, 7);
		String sDateTime = appDateFormatter.formatDateTime(dateTime);
		assertEquals("18/08/2000 11:56:07", sDateTime);
	}
	
}

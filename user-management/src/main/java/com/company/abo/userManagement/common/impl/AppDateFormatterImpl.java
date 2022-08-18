package com.company.abo.userManagement.common.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.company.abo.userManagement.common.AppDateFormatter;
import com.company.abo.userManagement.config.CompanyAppProperties;

@Component
public class AppDateFormatterImpl implements AppDateFormatter{
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Override
	public LocalDate parseBirthdate(final String sBirthdate) {
		final String dateFormatPattern = companyAppProperties.getDateFormatPattern();
		
		final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(dateFormatPattern);
		final LocalDate birthdate = LocalDate.parse(sBirthdate, dateTimeFormatter);
		
		return birthdate;
		
	}
	
	@Override
	public String formatBirthdate(final LocalDate birthdate) {
	
		final String dateFormatPattern = companyAppProperties.getDateFormatPattern();
		
		final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(dateFormatPattern);
		String sBirthdate = dateTimeFormatter.format(birthdate);
		return sBirthdate;
		
	}
}

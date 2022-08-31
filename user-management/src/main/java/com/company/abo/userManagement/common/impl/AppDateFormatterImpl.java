package com.company.abo.userManagement.common.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.company.abo.userManagement.common.AppDateFormatter;
import com.company.abo.userManagement.config.CompanyAppProperties;

import lombok.RequiredArgsConstructor;

/**
 * An implementation of the interface AppDateFormatter
 * @see AppDateFormatter
 * @author ABO
 *
 */
@Component
@RequiredArgsConstructor
public class AppDateFormatterImpl implements AppDateFormatter{
	
	private final CompanyAppProperties companyAppProperties;
	
	/**
	 * @see AppDateFormatter#parseDate
	 */
	@Override
	public LocalDate parseDate(final String sBirthdate) {
		if(sBirthdate == null) {
			return null;
		}
		final String dateFormatPattern = companyAppProperties.getDateFormatPattern();
		
		final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(dateFormatPattern);
		final LocalDate birthdate = LocalDate.parse(sBirthdate, dateTimeFormatter);
		
		return birthdate;
		
	}
	

	/**
	 * @see AppDateFormatter#formatDate
	 */
	@Override
	public String formatDate(final LocalDate birthdate) {
		if(birthdate == null) {
			return null;
		}
		final String dateFormatPattern = companyAppProperties.getDateFormatPattern();
		
		final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(dateFormatPattern);
		String sBirthdate = dateTimeFormatter.format(birthdate);
		return sBirthdate;
		
	}
	

	/**
	 * @see AppDateFormatter#parseDateTime
	 */
	@Override
	public LocalDateTime parseDateTime(final String sDate) {
		if(sDate == null) {
			return null;
		}
		final String dateFormatPattern = companyAppProperties.getDateTimeFormatPattern();
		
		final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(dateFormatPattern);
		LocalDateTime dateTime = LocalDateTime.parse(sDate, dateTimeFormatter);
		
		return dateTime;
		
	}

	/**
	 * @see AppDateFormatter#formatDateTime
	 */
	@Override
	public String formatDateTime(final LocalDateTime dateTime) {
		if(dateTime == null) {
			return null;
		}
		final String dateFormatPattern = companyAppProperties.getDateTimeFormatPattern();
		
		final DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(dateFormatPattern);
		final String sDateTime = dateTimeFormatter.format(dateTime);
		
		return sDateTime;
		
	}

}

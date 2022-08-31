package com.company.abo.userManagement.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Handle the date and time format and parse
 * @author ABO
 *
 */
public interface AppDateFormatter {
	
	/**
	 * Parse String value to LocalDate object
	 * @param sDate
	 * @return LocalDate object
	 */
	LocalDate parseDate(String sDate);
	
	/**
	 * Format the Local date object to string
	 * @param date
	 * @return String value
	 */
	String formatDate(LocalDate localdate); 
	
	/**
	 * Parse String value to Local Date time object
	 * @param sDate
	 * @return LocalDate object
	 */
	LocalDateTime parseDateTime(String sDate);
	
	/**
	 * Format the Local date time object to string
	 * @param date
	 * @return String value
	 */
	String formatDateTime(LocalDateTime date);
	
}

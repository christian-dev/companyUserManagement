package com.company.abo.userManagement.common;

import java.time.LocalDate;

public interface AppDateFormatter {

	LocalDate parseBirthdate(final String sBirthdate);
	
	String formatBirthdate(final LocalDate birthdate); 
}

package com.company.abo.userManagement.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

/**
 * Handle application properties
 * @author ABO
 *
 */
@Component
@ConfigurationProperties(prefix = "app.company")
@Getter
public class CompanyAppProperties {
	/**
	 * List of valid country of residence. For example FRANCE
	 */
	@Value("${app.company.countryOfResidence.validList}")
	private List<String> validCountryOfResidences;
	
	/**
	 * List of valid genders. for example : M, F
	 */
	@Value("${app.company.gender.validList}")
	private List<String> validGenders;
	
	/**
	 * Adult age.For exampe age 18  
	 */
	@Value("${app.company.aduleAge}")
	private int adulteAge;
	
	/**
	 * Pattern for a date. For example : dd/MM/yyyy
	 */
	@Value("${app.company.dateFormatPattern}")
	private String dateFormatPattern;
	
	/**
	 * Pattern for a date time: For example dd/MM/yyyy HH:mm:ss
	 */
	@Value("${app.company.dateTimeFormatPattern}")
	private String dateTimeFormatPattern;
}

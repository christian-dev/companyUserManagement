package com.company.abo.userManagement.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@ConfigurationProperties(prefix = "app.company")
@Getter
public class CompanyAppProperties {
	
	@Value("${app.company.countryOfResidence.validList}")
	private List<String> validCountryOfResidences;
	
	@Value("${app.company.gender.validList}")
	private List<String> validGenders;
	
	
	@Value("${app.company.aduleAge}")
	private int adulteAge;
	
	@Value("${app.company.dateFormatPattern}")
	private String dateFormatPattern;
}

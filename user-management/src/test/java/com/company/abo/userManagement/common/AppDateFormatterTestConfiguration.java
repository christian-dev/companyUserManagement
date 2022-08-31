package com.company.abo.userManagement.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.company.abo.userManagement.common.impl.AppDateFormatterImpl;
import com.company.abo.userManagement.config.CompanyAppProperties;

@TestConfiguration
public class AppDateFormatterTestConfiguration {
	
	@MockBean
	private CompanyAppProperties companyAppProperties;
	
	@Bean
	public AppDateFormatter appDateFormatter() {
		return new AppDateFormatterImpl(companyAppProperties);
	}
	
}

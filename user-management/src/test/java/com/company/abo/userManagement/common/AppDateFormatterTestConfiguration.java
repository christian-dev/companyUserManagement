package com.company.abo.userManagement.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.company.abo.userManagement.common.impl.AppDateFormatterImpl;

@TestConfiguration
public class AppDateFormatterTestConfiguration {
	
	@Bean
	public AppDateFormatter appDateFormatter() {
		return new AppDateFormatterImpl();
	}
}

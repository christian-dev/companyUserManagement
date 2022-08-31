package com.company.abo.userManagement.service;

import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

import com.company.abo.userManagement.common.AppDateFormatter;
import com.company.abo.userManagement.common.impl.AppDateFormatterImpl;
import com.company.abo.userManagement.config.CompanyAppProperties;
import com.company.abo.userManagement.mapper.CompanyUserMapper;
import com.company.abo.userManagement.repository.CompanyUserRepository;
import com.company.abo.userManagement.service.impl.CompanyUserServiceImpl;

@TestConfiguration
public class CompanyUserServiceTestConfiguration {
	
	@MockBean
	private CompanyAppProperties companyAppProperties;
	
	@MockBean
	private CompanyUserRepository companyUserRepository;
	
	@Bean
	public CompanyUserMapper companyUserMapper() {
		return Mappers.getMapper(CompanyUserMapper.class);
	}
	
	@Bean
	public CompanyUserService companyUserService() {
		return new CompanyUserServiceImpl(companyUserRepository, companyUserMapper());
	}
	
	@Bean
	public AppDateFormatter appDateFormatter() {
		AppDateFormatter appDateFormatter = new AppDateFormatterImpl(companyAppProperties);
		return appDateFormatter;
	}
}

package com.company.abo.userManagement.controller;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.support.SpringWebConstraintValidatorFactory;
import org.springframework.web.context.WebApplicationContext;

import com.company.abo.userManagement.common.AppDateFormatter;
import com.company.abo.userManagement.common.impl.AppDateFormatterImpl;
import com.company.abo.userManagement.config.CompanyAppProperties;
import com.company.abo.userManagement.controller.exceptionHandler.CompanyUserExceptionHandler;
import com.company.abo.userManagement.dto.validator.birthdate.BirthDateValidator;
import com.company.abo.userManagement.dto.validator.countryOfResidence.CountryOfResidenceValidator;
import com.company.abo.userManagement.dto.validator.gender.GenderValidator;
import com.company.abo.userManagement.service.CompanyUserService;

@TestConfiguration
public class CompanyUserControllerTestConfig {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@MockBean
	private CompanyUserService companyUserService;
	
	@MockBean
	private CompanyAppProperties companyAppProperties;
	
	@Bean
	public CompanyUserController companyUserController() {
		return new CompanyUserController(companyUserService);
	}
	
	@Bean
	public CompanyUserExceptionHandler companyUserExceptionHandler() {
		return new CompanyUserExceptionHandler();
	}
	
	@Bean
	public LocalValidatorFactoryBean localValidatorFactoryBean() {
		LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
		localValidatorFactoryBean.setApplicationContext(webApplicationContext);
		localValidatorFactoryBean.setConstraintValidatorFactory(constraintValidatorFactory());
		localValidatorFactoryBean.setProviderClass(HibernateValidator.class);
		return localValidatorFactoryBean;
	}
	
	@Bean
	public ConstraintValidatorFactory constraintValidatorFactory() {
		ConstraintValidatorFactory constraintValidatorFactory = new SpringWebConstraintValidatorFactory() {
		
			@Override
			public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
				T constraintValidator = super.getInstance(key);
				if(constraintValidator instanceof BirthDateValidator) {
					final BirthDateValidator birthDateValidator = (BirthDateValidator) constraintValidator;
					birthDateValidator.setCompanyAppProperties(companyAppProperties);
					birthDateValidator.setDateFormatter(appDateFormatter());
				}
				else if(constraintValidator instanceof GenderValidator) {
					GenderValidator genderValidator = (GenderValidator) constraintValidator;
					genderValidator.setCompanyAppProperties(companyAppProperties);
				}
				else if(constraintValidator instanceof CountryOfResidenceValidator) {
					CountryOfResidenceValidator countryOfResidenceValidator = (CountryOfResidenceValidator) constraintValidator;
					countryOfResidenceValidator.setCompanyAppProperties(companyAppProperties);
				}
				return constraintValidator;
			}

			@Override
			protected WebApplicationContext getWebApplicationContext() {
				return webApplicationContext;
			}
			
		};
		
		return constraintValidatorFactory;
	}
	
	@Bean
	public AppDateFormatter appDateFormatter() {
		AppDateFormatter appDateFormatter = new AppDateFormatterImpl(companyAppProperties);
		return appDateFormatter;
	}
}

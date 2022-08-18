package com.company.abo.userManagement.dto.validator.birthdate;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.abo.userManagement.common.AppDateFormatter;
import com.company.abo.userManagement.config.CompanyAppProperties;

@Component
public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, String> {
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Autowired
	private AppDateFormatter dateFormatter;
	
	@Override
	public boolean isValid(String sBirthdate, ConstraintValidatorContext context) {
		LocalDate curentDate = LocalDate.now();
		
		final LocalDate birthdate = dateFormatter.parseBirthdate(sBirthdate);
		
		final int age = Period.between(birthdate, curentDate).getYears();
		final int aduleAge = companyAppProperties.getAdulteAge();
		
		return age >= aduleAge;
	}
	
}

package com.company.abo.userManagement.dto.validator.countryOfResidence;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.abo.userManagement.config.CompanyAppProperties;

@Component
public class CountryOfResidenceValidator implements ConstraintValidator<CountryOfResidenceConstraint, String> {
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Override
	public boolean isValid(String countryOfResidence, ConstraintValidatorContext context) {
		final List<String> validCountryOfResidences = companyAppProperties.getValidCountryOfResidences();
		return validCountryOfResidences.contains(countryOfResidence);
	}
	
}

package com.company.abo.userManagement.dto.validator.gender;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.abo.userManagement.config.CompanyAppProperties;

@Component
public class GenderValidator implements ConstraintValidator<GenderConstraint, String> {
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Override
	public boolean isValid(String gender, ConstraintValidatorContext context) {
		final List<String> validGenders = companyAppProperties.getValidGenders();
		return validGenders.contains(gender);
	}
	
}

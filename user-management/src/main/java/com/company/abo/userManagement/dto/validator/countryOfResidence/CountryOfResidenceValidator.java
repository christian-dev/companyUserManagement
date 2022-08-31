package com.company.abo.userManagement.dto.validator.countryOfResidence;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.abo.userManagement.config.CompanyAppProperties;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Validate the country of residence of an user
 * For examle FRANCE
 * @author ABO
 *
 */
@Component
@NoArgsConstructor
@Setter
public class CountryOfResidenceValidator implements ConstraintValidator<CountryOfResidenceConstraint, String> {
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	/**
	 * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String countryOfResidence, ConstraintValidatorContext context) {
		final List<String> validCountryOfResidences = companyAppProperties.getValidCountryOfResidences();
		return validCountryOfResidences.contains(countryOfResidence);
	}
	
}

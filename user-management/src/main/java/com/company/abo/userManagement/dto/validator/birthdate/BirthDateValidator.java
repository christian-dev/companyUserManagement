package com.company.abo.userManagement.dto.validator.birthdate;

import java.time.LocalDate;
import java.time.Period;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.company.abo.userManagement.common.AppDateFormatter;
import com.company.abo.userManagement.config.CompanyAppProperties;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Validation of the adult age. For example adule must have age > 18
 * @author ABO
 *
 */
@Component
@NoArgsConstructor
@Setter
public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, String> {
	
	@Autowired
	private CompanyAppProperties companyAppProperties;
	
	@Autowired
	private AppDateFormatter dateFormatter;
	
	/**
	 * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
	 */
	@Override
	public boolean isValid(String sBirthdate, ConstraintValidatorContext context) {
		LocalDate curentDate = LocalDate.now();
		
		final LocalDate birthdate = dateFormatter.parseDate(sBirthdate);
		
		final int age = Period.between(birthdate, curentDate).getYears();
		final int aduleAge = companyAppProperties.getAdulteAge();
		
		return (age >= aduleAge);
	}
	
}

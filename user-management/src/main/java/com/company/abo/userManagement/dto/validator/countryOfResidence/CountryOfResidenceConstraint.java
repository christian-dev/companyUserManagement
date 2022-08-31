package com.company.abo.userManagement.dto.validator.countryOfResidence;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation to valiate the country of residence of an user
 * For example FRANCE
 * @author ABO
 *
 */
@Documented
@Constraint(validatedBy=CountryOfResidenceValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CountryOfResidenceConstraint {
	String message() default "Only French residents are allowed to create an account";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

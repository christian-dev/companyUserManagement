package com.company.abo.userManagement.dto.validator.birthdate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotaton for check the BirthDate. 
 * For example the adulemust have age >= 18
 * @author ABO
 *
 */
@Documented
@Constraint(validatedBy=BirthDateValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateConstraint {
	String message() default "Only adults are allowed to create an account";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

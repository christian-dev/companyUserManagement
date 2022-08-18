package com.company.abo.userManagement.dto.validator.gender;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy=GenderValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface GenderConstraint {
	String message() default "The gender value must be 'M' or 'F'";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

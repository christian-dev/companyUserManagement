package com.company.abo.userManagement.dto.validator.group;

/**
 * Class to group the validation constraints
 * The application validate the DTO object sequentially
 * Avoid all validation each time
 * For example : 
 * Level 1 : Default validation (NotNull, NotEmpty, ....)
 * Level 2 : Application validation (coutry of residence, gender, birthdate, ...)
 * @author ABO
 *
 */
public interface CompanyUserConstraintGroup {

}

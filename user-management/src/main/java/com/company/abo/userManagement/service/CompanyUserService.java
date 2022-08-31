package com.company.abo.userManagement.service;

import java.util.List;
import java.util.Map;

import com.company.abo.userManagement.dto.CompanyUserDto;

/**
 * Service class layer for the user application 
 * @author ABO
 *
 */
public interface CompanyUserService {
	
	/**
	 * resgister an user
	 * @param companyUserDto
	 * @return
	 */
	CompanyUserDto createCompanyUser(CompanyUserDto companyUserDto);
	
	/**
	 * Update an already existing user
	 * @param userId
	 * @param companyUserDto
	 * @return
	 */
	CompanyUserDto updateCompanyUser(Long userId,  CompanyUserDto companyUserDto);
	
	/**
	 * Delete an existing user
	 * @param userId
	 * @return
	 */
	Map<String, Object> deleteCompanyUser(Long userId);
	
	/**
	 * Partial update of an already existing user
	 * @param userId
	 * @param valueMap
	 * @return
	 */
	CompanyUserDto patchCompanyUser(Long userId, Map<String, String> valueMap);
	
	/**
	 * Find user by userId
	 * @param userId
	 * @return
	 */
	CompanyUserDto getCompanyUserDetails(Long userId);
	
	/**
	 * Find user by email
	 * @param email
	 * @return
	 */
	CompanyUserDto getCompanyUserDetails(String email);
	
	/**
	 * Find all users
	 * @return
	 */
	List<CompanyUserDto> getCompanyUserDetails();
	
	/**
	 * Find all users by firstname and lastname
	 * @param firstname
	 * @param lastname
	 * @return
	 */
	List<CompanyUserDto> getCompanyUserDetails(String firstname, String lastname);
}

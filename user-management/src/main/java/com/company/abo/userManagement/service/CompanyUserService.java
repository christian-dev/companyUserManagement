package com.company.abo.userManagement.service;

import java.util.List;
import java.util.Map;

import com.company.abo.userManagement.dto.CompanyUserDto;

public interface CompanyUserService {
	
	CompanyUserDto createCompanyUser(CompanyUserDto companyUserDto);
	
	CompanyUserDto updateCompanyUser(Long userId,  CompanyUserDto companyUserDto);
	
	String deleteCompanyUser(Long userId);
	
	CompanyUserDto patchCompanyUser(Long userId, Map<String, String> valueMap);
	
	CompanyUserDto getCompanyUserDetails(Long userId);
	
	CompanyUserDto getCompanyUserDetails(String email);
	
	List<CompanyUserDto> getCompanyUserDetails();
	
	List<CompanyUserDto> getCompanyUserDetails(String firstname, String lastname);
}

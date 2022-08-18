package com.company.abo.userManagement.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.abo.userManagement.aop.LogExecutionContext;
import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.exception.CompanyUserNotFoundException;
import com.company.abo.userManagement.exception.EmailAlreadyExistsException;
import com.company.abo.userManagement.mapper.CompanyUserMapper;
import com.company.abo.userManagement.model.CompanyUser;
import com.company.abo.userManagement.repository.CompanyUserRepository;
import com.company.abo.userManagement.service.CompanyUserService;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {
	
	@Autowired
	private CompanyUserRepository companyUserRepository;
	
	@Autowired
	private CompanyUserMapper companyUserMapper;
	
	@Transactional
	@LogExecutionContext
	@Override
	public CompanyUserDto createCompanyUser(final CompanyUserDto companyUserDto) {
		
		final String email = companyUserDto.getEmail();
		
		companyUserRepository.findByEmail(email).ifPresent(s -> {throw new EmailAlreadyExistsException(email);});
				
		CompanyUser companyUser = companyUserMapper.companyUserDtoToEntity(companyUserDto);
		
		companyUser = companyUserRepository.save(companyUser);
		
		return companyUserMapper.companyUserEntityToDto(companyUser);
	}
	
	@LogExecutionContext
	@Override
	public CompanyUserDto updateCompanyUser(final Long userId, final CompanyUserDto companyUserDto) {
		return update(userId, companyUserDto);
	}

	@LogExecutionContext
	@Override
	public CompanyUserDto patchCompanyUser(final Long userId, final Map<String, String> map) {
		CompanyUser companyUser = companyUserRepository.findById(userId).
				orElseThrow(() -> new CompanyUserNotFoundException(userId));
		CompanyUserDto companyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		
		companyUserMapper.copyMapValuestoCompanyUserDto(map, companyUserDto);
		return update(userId, companyUserDto);
	}
	
	@LogExecutionContext
	@Override
	public String deleteCompanyUser(final Long userId) {
		final Optional<CompanyUser> existingCompanyUser = companyUserRepository.findById(userId);
		final CompanyUser companyUser = existingCompanyUser.orElseThrow(() -> new CompanyUserNotFoundException(userId));
		
		companyUserRepository.delete(companyUser);
		return String.format("The User with id %s is deleted ", userId);
	}
	
	@LogExecutionContext
	@Override
	public CompanyUserDto getCompanyUserDetails(final Long userId) {
		final CompanyUser companyUser = companyUserRepository.findById(userId).
			orElseThrow(() -> new CompanyUserNotFoundException(userId));
		final CompanyUserDto companyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		return companyUserDto;
	}

	@LogExecutionContext
	@Override
	public CompanyUserDto getCompanyUserDetails(String email) {
		final CompanyUser companyUser = companyUserRepository.findByEmail(email).orElseThrow(() -> new CompanyUserNotFoundException(email));
		final CompanyUserDto companyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		return companyUserDto;
	}

	@LogExecutionContext
	@Override
	public List<CompanyUserDto> getCompanyUserDetails(String firstname, String lastname) {
		final List<CompanyUser> companyUsers = companyUserRepository.findByFirstNameAndLastName(firstname, lastname);
		final List<CompanyUserDto> companyUserDtos = companyUserMapper.companyUserEntityToDto(companyUsers);
		return companyUserDtos;
	}
	
	@LogExecutionContext
	@Override
	public List<CompanyUserDto> getCompanyUserDetails() {
		final List<CompanyUser> companyUsers = companyUserRepository.findAll();
		final List<CompanyUserDto> companyUserDtos = companyUserMapper.companyUserEntityToDto(companyUsers);
		return companyUserDtos;
	}
	
	private CompanyUserDto update(final Long userId, final CompanyUserDto companyUserDto) {
		
		if(userId == null) {
			throw new CompanyUserNotFoundException();
		}
		
		final boolean existsById = companyUserRepository.existsById(userId);
		if(!existsById) {			
			throw new CompanyUserNotFoundException();
		}

		final String email = companyUserDto.getEmail();
		final Optional<CompanyUser> otherCompanyUserWithEmail = companyUserRepository.findByEmail(email);
		
		if(otherCompanyUserWithEmail.isPresent() && !otherCompanyUserWithEmail.get().getUserId().equals(userId)) {
			throw new EmailAlreadyExistsException(email);
		}
		
		companyUserDto.setUserId(userId);
		CompanyUser companyUser =  companyUserMapper.companyUserDtoToEntity(companyUserDto);
		
		companyUser = companyUserRepository.save(companyUser);
		
		return companyUserMapper.companyUserEntityToDto(companyUser);
	}
	
}

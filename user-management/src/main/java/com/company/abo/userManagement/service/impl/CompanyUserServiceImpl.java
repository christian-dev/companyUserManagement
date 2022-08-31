package com.company.abo.userManagement.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import lombok.RequiredArgsConstructor;

/**
 * Implementation of the service layer CompanyUserService
 * @see CompanyUserService
 * @author ABO
 *
 */
@Service
@RequiredArgsConstructor
public class CompanyUserServiceImpl implements CompanyUserService {
	
	private final CompanyUserRepository companyUserRepository;
	
	private final CompanyUserMapper companyUserMapper;
	
	/**
	 * @see CompanyUserService#createCompanyUser(CompanyUserDto)
	 */
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
	
	/**
	 * @see CompanyUserService#updateCompanyUser(Long, CompanyUserDto)
	 * 
	 */
	@LogExecutionContext
	@Override
	public CompanyUserDto updateCompanyUser(final Long userId, final CompanyUserDto companyUserDto) {
		return update(userId, companyUserDto);
	}

	/**
	 * @see CompanyUserService#patchCompanyUser(Long, Map)
	 */
	@LogExecutionContext
	@Override
	public CompanyUserDto patchCompanyUser(final Long userId, final Map<String, String> map) {
		CompanyUser companyUser = companyUserRepository.findById(userId).
				orElseThrow(() -> new CompanyUserNotFoundException(userId));
		CompanyUserDto companyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		
		companyUserMapper.copyMapValuestoCompanyUserDto(map, companyUserDto);
		return update(userId, companyUserDto);
	}
	
	/**
	 * @see CompanyUserService#deleteCompanyUser(Long)
	 */
	@LogExecutionContext
	@Override
	public Map<String, Object> deleteCompanyUser(final Long userId) {
		final Optional<CompanyUser> existingCompanyUser = companyUserRepository.findById(userId);
		final CompanyUser companyUser = existingCompanyUser.orElseThrow(() -> new CompanyUserNotFoundException(userId));
		
		companyUserRepository.delete(companyUser);
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("userId", userId);
		result.put("deleted", true);
		return result;
	}
	
	/**
	 * @see CompanyUserService#getCompanyUserDetails(Long)
	 */
	@LogExecutionContext
	@Override
	public CompanyUserDto getCompanyUserDetails(final Long userId) {
		final CompanyUser companyUser = companyUserRepository.findById(userId).
			orElseThrow(() -> new CompanyUserNotFoundException(userId));
		final CompanyUserDto companyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		return companyUserDto;
	}
	
	/**
	 * @see CompanyUserService#getCompanyUserDetails(String)
	 */
	@LogExecutionContext
	@Override
	public CompanyUserDto getCompanyUserDetails(String email) {
		final CompanyUser companyUser = companyUserRepository.findByEmail(email).orElseThrow(() -> new CompanyUserNotFoundException(email));
		final CompanyUserDto companyUserDto = companyUserMapper.companyUserEntityToDto(companyUser);
		return companyUserDto;
	}
	
	/**
	 * @see CompanyUserService#getCompanyUserDetails(String, String)
	 */
	@LogExecutionContext
	@Override
	public List<CompanyUserDto> getCompanyUserDetails(String firstname, String lastname) {
		final List<CompanyUser> companyUsers = companyUserRepository.findByFirstNameAndLastName(firstname, lastname);
		final List<CompanyUserDto> companyUserDtos = companyUserMapper.companyUserEntityToDto(companyUsers);
		return companyUserDtos;
	}
	
	/**
	 * @see CompanyUserService#getCompanyUserDetails()
	 */
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
		
		final CompanyUser existingCompanyUser = companyUserRepository.findById(userId).
				orElseThrow(() -> new CompanyUserNotFoundException(userId));

		final String email = companyUserDto.getEmail();
		final Optional<CompanyUser> otherCompanyUserWithEmail = companyUserRepository.findByEmail(email);
		
		if(otherCompanyUserWithEmail.isPresent() && !otherCompanyUserWithEmail.get().getUserId().equals(userId)) {
			throw new EmailAlreadyExistsException(email);
		}
		
		companyUserDto.setUserId(userId);
		CompanyUser companyUser =  companyUserMapper.companyUserDtoToEntity(companyUserDto);
		companyUser.setCreationDate(existingCompanyUser.getCreationDate());
		
		companyUser = companyUserRepository.save(companyUser);
		
		return companyUserMapper.companyUserEntityToDto(companyUser);
	}
	
}

package com.company.abo.userManagement.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.service.CompanyUserService;

import lombok.RequiredArgsConstructor;

/**
 * Rest controller class for the application
 * @author ABO
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/users")
public class CompanyUserController {

	private final CompanyUserService companyUserService;
	
	/**
	 * Register a new User.
	 * This user must not already exist or must not have an email already used
	 * @param companyUserDto
	 * @return
	 */
	@PostMapping(value="/")
	@ResponseBody
	public ResponseEntity<?> registerCompanyUser(@RequestBody @Valid CompanyUserDto companyUserDto) {
		companyUserDto = companyUserService.createCompanyUser(companyUserDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(companyUserDto);
	}
	
	/**
	 * Update an already registered user
	 * The new email must not be used by another user
	 * @param userId
	 * @param companyUserDto
	 * @return
	 */
	@PutMapping(value="/{userId}")
	@ResponseBody
	public CompanyUserDto updateUser(@PathVariable Long userId, @RequestBody @Valid CompanyUserDto companyUserDto) {
		return companyUserService.updateCompanyUser(userId, companyUserDto);
	}
	
	/**
	 * Partial update an existing user
	 * The new email must not be used by another user
	 * @param userId
	 * @param valueMap
	 * @return
	 */
	@PatchMapping(value="/{userId}")
	@ResponseBody
	public CompanyUserDto patchUser(@PathVariable Long userId, @RequestBody Map<String, String> valueMap) {
		return companyUserService.patchCompanyUser(userId, valueMap);
	}
	
	/**
	 * Delete an existing user.
	 * User must exist in the application
	 * @param userId
	 * @return
	 */
	@DeleteMapping(value="/{userId}")
	@ResponseBody
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		Map<String, Object> deleteInfos = companyUserService.deleteCompanyUser(userId);
		return ResponseEntity.ok(deleteInfos);
	}
	
	/**
	 * Get detailed information about an user
	 * The user must already be registered
	 * @param userId
	 * @return 
	 */
	@GetMapping("/{userId}")
	@ResponseBody
	public CompanyUserDto getCompanyUserDetails(@PathVariable Long userId) {
		return companyUserService.getCompanyUserDetails(userId);
	}
	
	/**
	 * Get all registerd users in the application
	 * @return
	 */
	@GetMapping("/")
	@ResponseBody
	public List<CompanyUserDto> getAllCompanyUsers() {
		return companyUserService.getCompanyUserDetails();
	}
	
	/**
	 * Get a detailed information about an user identified by its email
	 * @param email
	 * @return
	 */
	@GetMapping(value="/detailsByEmail")
	@ResponseBody
	public CompanyUserDto getCompanyUserDetails(@RequestParam String email) {
		return companyUserService.getCompanyUserDetails(email);
	}
	
	/**
	 * Get the list of users with the firstname and the lastname
	 * @param firstname
	 * @param lastname
	 * @return
	 */
	@GetMapping(value="/detailsByFirstnameAndLastname")
	@ResponseBody
	public List<CompanyUserDto> getCompanyUserDetails(@RequestParam String firstname, @RequestParam String lastname) {
		return companyUserService.getCompanyUserDetails(firstname, lastname);
	}

}

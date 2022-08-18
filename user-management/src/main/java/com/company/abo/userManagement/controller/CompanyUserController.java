package com.company.abo.userManagement.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.service.CompanyUserService;

@RestController
@RequestMapping(value="/users")
public class CompanyUserController {

	@Autowired
	private CompanyUserService companyUserService;
	
	@RequestMapping(method=RequestMethod.POST, value="/register")
	@ResponseBody
	//@PreAuthorize
	public ResponseEntity<?> registerCompanyUser(@RequestBody @Valid CompanyUserDto companyUserDto) {
		companyUserDto = companyUserService.createCompanyUser(companyUserDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(companyUserDto);
	}
	
	@PutMapping(value="/update/{userId}")
	@ResponseBody
	//@PreAuthorize
	public CompanyUserDto updateUser(@PathVariable Long userId, @RequestBody @Valid CompanyUserDto companyUserDto) {
		return companyUserService.updateCompanyUser(userId, companyUserDto);
	}
	
	@PatchMapping(value="/patch/{userId}")
	@ResponseBody
	//@PreAuthorize
	public CompanyUserDto patchUser(@PathVariable Long userId, @RequestBody Map<String, String> valueMap) {
		return companyUserService.patchCompanyUser(userId, valueMap);
	}
	
	@DeleteMapping(value="/delete/{userId}")
	@ResponseBody
	//@PreAuthorize
	public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
		String resultDelete = companyUserService.deleteCompanyUser(userId);
		return ResponseEntity.ok(resultDelete);
	}
	
	@GetMapping("/{userId}")
	@ResponseBody
	//@PreAuthorize
	public CompanyUserDto getCompanyUserDetails(@PathVariable Long userId) {
		return companyUserService.getCompanyUserDetails(userId);
	}
	
	@GetMapping("/getAll")
	@ResponseBody
	//@PreAuthorize
	public List<CompanyUserDto> getAllCompanyUsers() {
		return companyUserService.getCompanyUserDetails();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/detailsByEmail")
	@ResponseBody
	//@PreAuthorize
	public CompanyUserDto getCompanyUserDetails(@RequestParam String email) {
		return companyUserService.getCompanyUserDetails(email);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/detailsByFirstnameAndLastname")
	@ResponseBody
	//@PreAuthorize
	public List<CompanyUserDto> getCompanyUserDetails(@RequestParam String firstname, @RequestParam String lastname) {
		return companyUserService.getCompanyUserDetails(firstname, lastname);
	}

}

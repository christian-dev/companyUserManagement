package com.company.abo.userManagement.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.abo.userManagement.model.CompanyUser;

@Repository
public interface CompanyUserRepository extends CrudRepository<CompanyUser, Long> {
	
	List<CompanyUser> findAll();
	
	List<CompanyUser> findAllByFirstName(String firstName);
	
	List<CompanyUser> findAllByLastName(String lastName);
	
	List<CompanyUser> findAllByGender(String gender);
	
	List<CompanyUser> findAllByPhoneNumber(String phoneNumber);
	
	List<CompanyUser> findAllByCreationDate(Date creationDate);
	
	Optional<CompanyUser> findByEmail(String email);
	
	List<CompanyUser> findByFirstNameAndLastName(String firstName, String lastName);
}

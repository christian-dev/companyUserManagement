package com.company.abo.userManagement.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.abo.userManagement.model.CompanyUser;

/**
 * Repository class for database operation 
 * @author ABO
 *
 */
@Repository
public interface CompanyUserRepository extends CrudRepository<CompanyUser, Long> {
	
	/**
	 * Find all users
	 */
	List<CompanyUser> findAll();
	
	/**
	 * Find all user by firstname
	 * @param firstName
	 * @return
	 */
	List<CompanyUser> findAllByFirstName(String firstName);
	
	/**
	 * Find all users by last name
	 * @param lastName
	 * @return
	 */
	List<CompanyUser> findAllByLastName(String lastName);
	
	/**
	 * Find all users by gender
	 * @param gender
	 * @return
	 */
	List<CompanyUser> findAllByGender(String gender);
	
	/**
	 * Find all users by phone number
	 * @param phoneNumber
	 * @return
	 */
	List<CompanyUser> findAllByPhoneNumber(String phoneNumber);
	
	/**
	 * Find all users by creation date
	 * @param creationDate
	 * @return
	 */
	List<CompanyUser> findAllByCreationDate(Date creationDate);
	
	/**
	 * Find user by email
	 * @param email
	 * @return
	 */
	Optional<CompanyUser> findByEmail(String email);
	
	/**
	 * Find all users by firstname and lastname
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	List<CompanyUser> findByFirstNameAndLastName(String firstName, String lastName);
}

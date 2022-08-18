package com.company.abo.userManagement.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "COMPANY_USER")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of= {"userId"})
public class CompanyUser implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5750822526218364103L;
	
	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@Basic
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Basic
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Basic
	@Column(name = "BIRTH_DATE")
	private LocalDate birthdate;
	
	@Basic
	@Column(name = "COUNTRY_OF_RESIDENCE")
	private String countryOfResidence;
	
	@Basic
	@Column(name = "PHONE_NUMBER")
	private String phoneNumber;
	
	@Basic
	@Column(name = "EMAIL", unique=true)
	private String email;
	
	@Basic
	@Column(name = "GENDER")
	private String gender;
	
	@Basic
	@Column(name = "CREATION_DATE")
	@CreationTimestamp
	private Timestamp creationDate;
	
	@Basic
	@Column(name = "UPDATE_DATE")
	@UpdateTimestamp
	private Timestamp updateDate;
	
}

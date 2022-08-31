package com.company.abo.userManagement.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.abo.userManagement.common.AppDateFormatter;
import com.company.abo.userManagement.dto.CompanyUserDto;
import com.company.abo.userManagement.model.CompanyUser;

/**
 * Class to map entity to DTO and DTO to entity for the user
 * @author ABO
 *
 */
@Mapper(
		  componentModel = "spring",
	        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
	        nullValueCheckStrategy=NullValueCheckStrategy.ON_IMPLICIT_CONVERSION,
	        uses = AppDateFormatter.class,
	        injectionStrategy = InjectionStrategy.CONSTRUCTOR
	       
)
public abstract class CompanyUserMapper {
	
	@Autowired
	protected AppDateFormatter appDateFormatter;
	
	/**
	 * Map CompanyUser to CompanyUserDto
	 * @param companyUser
	 * @return
	 */
	@Mapping(source="birthdate", target="birthdate", qualifiedByName = "formatDate")
	@Mapping(source="creationDate", target="creationDate", qualifiedByName = "formatDateTime")
	@Mapping(source="updateDate", target="updateDate", qualifiedByName = "formatDateTime")
	public abstract CompanyUserDto companyUserEntityToDto(CompanyUser companyUser);

	/**
	 * Map the companyUserDto to CompanyUser
	 * @param companyUserDto list
	 * @return
	 */
	@Mapping(source="birthdate", target="birthdate", qualifiedByName = "parseDate")
	@Mapping(source="creationDate", target="creationDate", qualifiedByName = "parseDateTime")
	@Mapping(source="updateDate", target="updateDate", qualifiedByName = "parseDateTime")
	public abstract CompanyUser companyUserDtoToEntity(CompanyUserDto companyUserDto);
	
	/**
	 * Map CompanyUser list  to CompanyUserDto list
	 * @param companyUser
	 * @return
	 */
	public abstract List<CompanyUserDto> companyUserEntityToDto(List<CompanyUser> companyUser);
	
	/**
	 * Map the companyUserDto list to CompanyUser list
	 * @param companyUserDto list
	 * @return
	 */
	public abstract List<CompanyUser> companyUserDtoToEntity(List<CompanyUserDto> companyUserDto);
	
	/**
	 * Copy value in a map to DTO object
	 * @param map
	 * @param companyUserDto
	 */
	@Mapping(target="creationDate", ignore=true)
	@Mapping(target="updateDate", ignore=true)
	public abstract void copyMapValuestoCompanyUserDto(Map<String, String> map, @MappingTarget CompanyUserDto companyUserDto);
	
	/**
	 * @see AppDateFormatter#formatDate(LocalDate)
	 * @param date
	 * @return
	 */
	@Named("formatDate")
	protected String formatDate(LocalDate date) {
		return appDateFormatter.formatDate(date);		
	}
	
	/**
	 * @see AppDateFormatter#parseDate(String)
	 * @param sDate
	 * @return
	 */
	@Named("parseDate")
	protected LocalDate parseDate(String sDate) {
		return appDateFormatter.parseDate(sDate);	
	}
		
	/**
	 * @see AppDateFormatter#formatDateTime(LocalDateTime)
	 * @param dateTime
	 * @return
	 */
	@Named("formatDateTime")
	protected String formatDateTime(LocalDateTime dateTime) {
		return appDateFormatter.formatDateTime(dateTime);
	}
	
	/**
	 * @see AppDateFormatter#parseDateTime(String)
	 * @param sDate
	 * @return
	 */
	@Named("parseDateTime")
	protected LocalDateTime parseDateTime(String sDate) {
		return appDateFormatter.parseDateTime(sDate);	
	}
}

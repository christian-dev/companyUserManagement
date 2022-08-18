package com.company.abo.userManagement.mapper;

import java.time.LocalDate;
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
	
	@Mapping(source="birthdate", target="birthdate", qualifiedByName = "formatBirthdate")
	public abstract CompanyUserDto companyUserEntityToDto(CompanyUser companyUser);

	@Mapping(source="birthdate", target="birthdate", qualifiedByName = "parseBirthdate")
	public abstract CompanyUser companyUserDtoToEntity(CompanyUserDto companyUserDto);
	
	public abstract List<CompanyUserDto> companyUserEntityToDto(List<CompanyUser> companyUser);
	
	public abstract List<CompanyUser> companyUserDtoToEntity(List<CompanyUserDto> companyUserDto);
	

	@Mapping(target="creationDate", ignore=true)
	@Mapping(target="updateDate", ignore=true)
	public abstract void copyMapValuestoCompanyUserDto(Map<String, String> map, @MappingTarget CompanyUserDto companyUserDto);
	
	@Named("formatBirthdate")
	protected String formatBirthdate(LocalDate birthdate) {
		return appDateFormatter.formatBirthdate(birthdate);
		
	}
	
	@Named("parseBirthdate")
	protected LocalDate parseBirthdate(String sBirthdate) {
		return appDateFormatter.parseBirthdate(sBirthdate);
		
	}
}

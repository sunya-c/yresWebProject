package com.sunya.yresWebProject.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.daos.DaoPersinfo;
import com.sunya.yresWebProject.models.DataPersInfo;
import com.sunya.yresWebProject.models.ModelPersinfo;

@Service
public class ServicePersonalInformation
{
	@Autowired
	private DaoPersinfo dao;

	@Autowired
	@Qualifier("frontDate")
	private DateTimeFormatter dateFormat;
	
	@Autowired
	@Qualifier("serverTimeZone")
	private TimeZone tzone;


	/**
	 * Map data from {@code PojoPersonalInformation} to {@code DataPersInfo}.
	 */
	public void sPersInfo(DataPersInfo dataPersInfo)
	{
		ModelPersinfo model = dao.getPersinfoMinimal(1);
		
		dataPersInfo.setFirstname(model.getFirstname());
		dataPersInfo.setLastname(model.getLastname());
		dataPersInfo.setDateOfBirth(model.getDateOfBirth());
		LocalDate dateOfBirth = LocalDate.parse(model.getDateOfBirth(), dateFormat);
		LocalDateTime now = LocalDateTime.now().minus(tzone.getRawOffset(), ChronoUnit.MILLIS).plusHours(7);
		Period age = Period.between(dateOfBirth, now.toLocalDate());
		dataPersInfo.setAge(age.getYears());
		dataPersInfo.setGender(model.getGender());
		dataPersInfo.setNationality(model.getNationality());
		dataPersInfo.setDrivingLicense(model.getDrivingLicense());
		dataPersInfo.setEngineeringLicense(model.getEngineeringLicense());
		dataPersInfo.setListLanguage(model.getLanguages());
		dataPersInfo.setListProgrammingLanguage(model.getProgrammingLanguages());
		dataPersInfo.setAddress(model.getAddress()); // Skipped. Confidential info.
		dataPersInfo.setPhoneNumber(model.getPhoneNumber());
		dataPersInfo.setEmail(model.getEmail());
		dataPersInfo.setLineId(model.getLineid());
		dataPersInfo.setListEducation(model.getEducations());
		dataPersInfo.setListEnglishTest(model.getEnglishTests());
	}


	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

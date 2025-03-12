package com.sunya.yresWebProject.services;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.models.DataPersInfo;
import com.sunya.yresWebProject.pojos.PojoPersonalInformation;

@Service
public class ServicePersonalInformation
{
	@Autowired
	@Qualifier("frontDate")
	private DateTimeFormatter dateFormat;
	
	public DataPersInfo sPersInfo()
	{
		PojoPersonalInformation pInfo = new PojoPersonalInformation();
		pInfo.setupPojoPersonalInformation("sunyapong");
		
		DataPersInfo myInfo = new DataPersInfo();
		
		myInfo.setFirstname(pInfo.getFirstname());
		myInfo.setLastname(pInfo.getLastname());
		myInfo.setDateOfBirth(pInfo.getDateOfBirth().format(dateFormat));
		myInfo.setAge(pInfo.getAge());
		myInfo.setGender(pInfo.getGender());
		myInfo.setNationality(pInfo.getNationality());
		
		String tempYesNo = null;
		if (pInfo.getDrivingLicense())
			tempYesNo = "Yes";
		else
			tempYesNo = "No";
		myInfo.setDrivingLicense(tempYesNo);
		
		tempYesNo = null;
		if (pInfo.getEngineeringLicense())
			tempYesNo = "Yes";
		else
			tempYesNo = "No";
		myInfo.setEngineeringLicense(tempYesNo);
		
		myInfo.setListLanguage(pInfo.getListLanguage());
		myInfo.setListProgrammingLanguage(pInfo.getListProgrammingLanguage());
		myInfo.setAddress(pInfo.getAddress()); // Skipped. Confidential info.
		myInfo.setPhoneNumber(pInfo.getPhoneNumber());
		myInfo.setEmail(pInfo.getEmail());
		myInfo.setLineId(pInfo.getLineId());
		myInfo.setListEducation(pInfo.getListEducation());
		myInfo.setListEnglishTest(pInfo.getListEnglishTest());
		
		return myInfo;
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

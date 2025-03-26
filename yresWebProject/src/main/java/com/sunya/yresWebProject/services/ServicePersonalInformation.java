package com.sunya.yresWebProject.services;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.models.DataPersInfo;
import com.sunya.yresWebProject.pojos.PojoPersonalInformation;

@Service
public class ServicePersonalInformation
{
	@Autowired
	@Qualifier("frontDate")
	private DateTimeFormatter dateFormat;


	/**
	 * Map data from {@code PojoPersonalInformation} to {@code DataPersInfo}.
	 */
	public void sPersInfo(DataPersInfo dataPersInfo)
	{
		PojoPersonalInformation pInfo = YresWebProjectApplication.context.getBean(PojoPersonalInformation.class);
		pInfo.setupPojoPersonalInformation("sunyapong");

		dataPersInfo.setFirstname(pInfo.getFirstname());
		dataPersInfo.setLastname(pInfo.getLastname());
		dataPersInfo.setDateOfBirth(pInfo.getDateOfBirth().format(dateFormat));
		dataPersInfo.setAge(pInfo.getAge());
		dataPersInfo.setGender(pInfo.getGender());
		dataPersInfo.setNationality(pInfo.getNationality());

		String tempYesNo = null;
		if (pInfo.getDrivingLicense())
			tempYesNo = "Yes";
		else
			tempYesNo = "No";
		dataPersInfo.setDrivingLicense(tempYesNo);

		tempYesNo = null;
		if (pInfo.getEngineeringLicense())
			tempYesNo = "Yes";
		else
			tempYesNo = "No";
		dataPersInfo.setEngineeringLicense(tempYesNo);

		dataPersInfo.setListLanguage(pInfo.getListLanguage());
		dataPersInfo.setListProgrammingLanguage(pInfo.getListProgrammingLanguage());
		dataPersInfo.setAddress(pInfo.getAddress()); // Skipped. Confidential info.
		dataPersInfo.setPhoneNumber(pInfo.getPhoneNumber());
		dataPersInfo.setEmail(pInfo.getEmail());
		dataPersInfo.setLineId(pInfo.getLineId());
		dataPersInfo.setListEducation(pInfo.getListEducation());
		dataPersInfo.setListEnglishTest(pInfo.getListEnglishTest());
	}


	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

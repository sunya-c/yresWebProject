package com.sunya.yresWebProject.services;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.pojos.PojoPersonalInformation;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class ServicePersonalInformation
{
	@Autowired
	@Qualifier("frontDate")
	private DateTimeFormatter dateFormat;
	
	public void sPersInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PojoPersonalInformation pInfo = new PojoPersonalInformation();
		pInfo.setupPojoPersonalInformation("sunyapong");
		
		request.setAttribute("firstName", 	pInfo.getFirstname());
		request.setAttribute("lastName", 	pInfo.getLastname());
		request.setAttribute("dateOfBirth", pInfo.getDateOfBirth().format(dateFormat));
		request.setAttribute("age", 		pInfo.getAge());
		request.setAttribute("gender", 		pInfo.getGender());
		request.setAttribute("nationality", pInfo.getNationality());
		
		String tempYesNo = null;
		if (pInfo.getDrivingLicense())
			tempYesNo = "Yes";
		else
			tempYesNo = "No";
		request.setAttribute("drivingLicense",		tempYesNo);
		
		tempYesNo = null;
		if (pInfo.getEngineeringLicense())
			tempYesNo = "Yes";
		else
			tempYesNo = "No";
		request.setAttribute("engineeringLicense",  tempYesNo);
		
		request.setAttribute("listLanguage",		pInfo.getListLanguage());
		request.setAttribute("listProgrammingLanguage", pInfo.getListProgrammingLanguage());
		request.setAttribute("address", 	pInfo.getAddress()); // Skipped. Confidential info.
		request.setAttribute("phoneNumber", pInfo.getPhoneNumber());
		request.setAttribute("email", 		pInfo.getEmail());
		request.setAttribute("lineId", 		pInfo.getLineId());
		request.setAttribute("listEducation", 		pInfo.getListEducation());
		request.setAttribute("listEnghlishTest", 	pInfo.getListEnglishTest());
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

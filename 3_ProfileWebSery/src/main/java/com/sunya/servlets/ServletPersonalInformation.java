package com.sunya.servlets;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.sunya.pojos.PojoPersonalInformation;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class ServletPersonalInformation
{
	public void sPersInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PojoPersonalInformation pInfo = new PojoPersonalInformation("sunyapong");
		
		request.setAttribute("firstName", 	pInfo.getFirstname());
		request.setAttribute("lastName", 	pInfo.getLastname());
		
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd MMM yyyy");
		request.setAttribute("dateOfBirth", pInfo.getDateOfBirth().format(formatDate));
		
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
		
		HttpSession session = request.getSession();
		session.setAttribute("fromServlet", toString());
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

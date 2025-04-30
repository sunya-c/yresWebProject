package com.sunya.yresWebProject.models;

import java.util.ArrayList;

import org.springframework.web.util.HtmlUtils;

/**
 * A class created for viewing purpose. Use this class to display dynamic data
 * on <strong>PersonalInformation page</strong>.
 */
public class DataPersInfo
{
	private String firstname;
	private String lastname;
	private String dateOfBirth;
	private int age;
	private String gender;
	private String nationality;
	private String drivingLicense;
	private String engineeringLicense;
	
	private ArrayList<ModelPersinfo.Language> listLanguage = new ArrayList<>();  // { {Language1, Proficiency1}, {...2, ...2} }
	
	private ArrayList<ModelPersinfo.Language> listProgrammingLanguage = new ArrayList<>();  // { {Language1, Proficiency1}, {...2, ...2} }
	
	private String address;
	private String phoneNumber;
	private String email;
	private String lineId;
	
	private ArrayList<ModelPersinfo.Education> listEducation = new ArrayList<>();  // {Period, Title, SchoolName, Details}
	
	// Pattern for TU-GET
	private ArrayList<ModelPersinfo.EnglishTest> listEnglishTest = new ArrayList<>();  // {TestName, score1, score2, score3, scoreTotal}
	
	
	
	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = (firstname==null)? null : HtmlUtils.htmlEscape(firstname);
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = (lastname==null)? null : HtmlUtils.htmlEscape(lastname);
	}

	public String getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth)
	{
		this.dateOfBirth = (dateOfBirth==null)? null : HtmlUtils.htmlEscape(dateOfBirth);
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String gender)
	{
		this.gender = (gender==null)? null : HtmlUtils.htmlEscape(gender);
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = (nationality==null)? null : HtmlUtils.htmlEscape(nationality);
	}

	public String getDrivingLicense()
	{
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense)
	{
		this.drivingLicense = (drivingLicense==null)? null : HtmlUtils.htmlEscape(drivingLicense);
	}

	public String getEngineeringLicense()
	{
		return engineeringLicense;
	}

	public void setEngineeringLicense(String engineeringLicense)
	{
		this.engineeringLicense = (engineeringLicense==null)? null : HtmlUtils.htmlEscape(engineeringLicense);
	}

	public ArrayList<ModelPersinfo.Language> getListLanguage()
	{
		return listLanguage;
	}

	public void setListLanguage(ArrayList<ModelPersinfo.Language> listLanguage)
	{
		if (listLanguage!=null)
		{
			listLanguage.forEach(lang -> {
				lang.setLanguage((lang.getLanguage()==null)? null : HtmlUtils.htmlEscape(lang.getLanguage()));
				lang.setProficiency((lang.getProficiency()==null)? null : HtmlUtils.htmlEscape(lang.getProficiency()));
			});
		}
		this.listLanguage = listLanguage;
	}

	public ArrayList<ModelPersinfo.Language> getListProgrammingLanguage()
	{
		return listProgrammingLanguage;
	}

	public void setListProgrammingLanguage(ArrayList<ModelPersinfo.Language> listProgrammingLanguage)
	{
		if (listProgrammingLanguage!=null)
		{
			listProgrammingLanguage.forEach(progLang -> {
				progLang.setLanguage((progLang.getLanguage()==null)? null : HtmlUtils.htmlEscape(progLang.getLanguage()));
				progLang.setProficiency((progLang.getProficiency()==null)? null : HtmlUtils.htmlEscape(progLang.getProficiency()));
			});
		}
		this.listProgrammingLanguage = listProgrammingLanguage;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = (address==null)? null : HtmlUtils.htmlEscape(address);
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = (phoneNumber==null)? null : HtmlUtils.htmlEscape(phoneNumber);
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = (email==null)? null : HtmlUtils.htmlEscape(email);
	}

	public String getLineId()
	{
		return lineId;
	}

	public void setLineId(String lineId)
	{
		this.lineId = (lineId==null)? null : HtmlUtils.htmlEscape(lineId);
	}

	public ArrayList<ModelPersinfo.Education> getListEducation()
	{
		return listEducation;
	}

	public void setListEducation(ArrayList<ModelPersinfo.Education> listEducation)
	{
		if (listEducation!=null)
		{
			listEducation.forEach(edu -> {
				edu.setLine1((edu.getLine1()==null)? null : HtmlUtils.htmlEscape(edu.getLine1()));
				edu.setLine2((edu.getLine2()==null)? null : HtmlUtils.htmlEscape(edu.getLine2()));
				edu.setLine3((edu.getLine3()==null)? null : HtmlUtils.htmlEscape(edu.getLine3()));
				edu.setLine4((edu.getLine4()==null)? null : HtmlUtils.htmlEscape(edu.getLine4()));
			});
		}
		this.listEducation = listEducation;
	}

	public ArrayList<ModelPersinfo.EnglishTest> getListEnglishTest()
	{
		return listEnglishTest;
	}

	public void setListEnglishTest(ArrayList<ModelPersinfo.EnglishTest> listEnglishTest)
	{
		if (listEnglishTest!=null)
		{
			listEnglishTest.forEach(test -> {
				test.setTestName((test.getTestName()==null)? null : HtmlUtils.htmlEscape(test.getTestName()));
				test.setScore1((test.getScore1()==null)? null : HtmlUtils.htmlEscape(test.getScore1()));
				test.setScore2((test.getScore2()==null)? null : HtmlUtils.htmlEscape(test.getScore2()));
				test.setScore3((test.getScore3()==null)? null : HtmlUtils.htmlEscape(test.getScore3()));
				test.setScore4((test.getScore4()==null)? null : HtmlUtils.htmlEscape(test.getScore4()));
			});
		}
		this.listEnglishTest = listEnglishTest;
	}
}

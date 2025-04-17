package com.sunya.yresWebProject.models;

import java.util.ArrayList;

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
		this.firstname = firstname;
	}

	public String getLastname()
	{
		return lastname;
	}

	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}

	public String getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
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
		this.gender = gender;
	}

	public String getNationality()
	{
		return nationality;
	}

	public void setNationality(String nationality)
	{
		this.nationality = nationality;
	}

	public String getDrivingLicense()
	{
		return drivingLicense;
	}

	public void setDrivingLicense(String drivingLicense)
	{
		this.drivingLicense = drivingLicense;
	}

	public String getEngineeringLicense()
	{
		return engineeringLicense;
	}

	public void setEngineeringLicense(String engineeringLicense)
	{
		this.engineeringLicense = engineeringLicense;
	}

	public ArrayList<ModelPersinfo.Language> getListLanguage()
	{
		return listLanguage;
	}

	public void setListLanguage(ArrayList<ModelPersinfo.Language> listLanguage)
	{
		this.listLanguage = listLanguage;
	}

	public ArrayList<ModelPersinfo.Language> getListProgrammingLanguage()
	{
		return listProgrammingLanguage;
	}

	public void setListProgrammingLanguage(ArrayList<ModelPersinfo.Language> listProgrammingLanguage)
	{
		this.listProgrammingLanguage = listProgrammingLanguage;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getLineId()
	{
		return lineId;
	}

	public void setLineId(String lineId)
	{
		this.lineId = lineId;
	}

	public ArrayList<ModelPersinfo.Education> getListEducation()
	{
		return listEducation;
	}

	public void setListEducation(ArrayList<ModelPersinfo.Education> listEducation)
	{
		this.listEducation = listEducation;
	}

	public ArrayList<ModelPersinfo.EnglishTest> getListEnglishTest()
	{
		return listEnglishTest;
	}

	public void setListEnglishTest(ArrayList<ModelPersinfo.EnglishTest> listEnglishTest)
	{
		this.listEnglishTest = listEnglishTest;
	}
}

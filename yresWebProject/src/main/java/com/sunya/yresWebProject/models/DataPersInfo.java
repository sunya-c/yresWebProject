package com.sunya.yresWebProject.models;

import java.util.ArrayList;

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
	
	private ArrayList<String[]> listLanguage = new ArrayList<>();  // { {Language1, Proficiency1}, {...2, ...2} }
	
	private ArrayList<String[]> listProgrammingLanguage = new ArrayList<>();  // { {Language1, Proficiency1}, {...2, ...2} }
	
	private String address;
	private String phoneNumber;
	private String email;
	private String lineId;
	
	private ArrayList<String[]> listEducation = new ArrayList<>();  // {Period, Title, SchoolName, Details}
	
	// Pattern for TU-GET
	private ArrayList<String[]> listEnglishTest = new ArrayList<>();  // {TestName, score1, score2, score3, scoreTotal}
	
	
	
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

	public ArrayList<String[]> getListLanguage()
	{
		return listLanguage;
	}

	public void setListLanguage(ArrayList<String[]> listLanguage)
	{
		this.listLanguage = listLanguage;
	}

	public ArrayList<String[]> getListProgrammingLanguage()
	{
		return listProgrammingLanguage;
	}

	public void setListProgrammingLanguage(ArrayList<String[]> listProgrammingLanguage)
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

	public ArrayList<String[]> getListEducation()
	{
		return listEducation;
	}

	public void setListEducation(ArrayList<String[]> listEducation)
	{
		this.listEducation = listEducation;
	}

	public ArrayList<String[]> getListEnglishTest()
	{
		return listEnglishTest;
	}

	public void setListEnglishTest(ArrayList<String[]> listEnglishTest)
	{
		this.listEnglishTest = listEnglishTest;
	}
}

package com.sunya.yresWebProject.models;

import java.util.ArrayList;

public class ModelPersinfo
{
	private int id;
	private String firstname;
	private String lastname;
	private String dateOfBirth;
	private String gender;
	private String nationality;
	private String drivingLicense;
	private String engineeringLicense;
	private ArrayList<Language> languages;
	private ArrayList<Language> programmingLanguages;
	private String phoneNumber;
	private String email;
	private String lineid;
	
	// still not in use
	private String address;
	private ArrayList<Education> educations;
	private ArrayList<EnglishTest> englishTests;
	
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
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
	public ArrayList<Language> getLanguages()
	{
		return languages;
	}
	public void addLanguage(String language, String proficiency)
	{
		Language lang = new Language();
		lang.setLanguage(language);
		lang.setProficiency(proficiency);
		if (this.languages==null)
			this.languages = new ArrayList<>();
		this.languages.add(lang);
	}
	public ArrayList<Language> getProgrammingLanguages()
	{
		return programmingLanguages;
	}
	public void addProgrammingLanguage(String programmingLanguage, String proficiency)
	{
		Language lang = new Language();
		lang.setLanguage(programmingLanguage);
		lang.setProficiency(proficiency);
		if (this.programmingLanguages==null)
			this.programmingLanguages = new ArrayList<>();
		this.programmingLanguages.add(lang);
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
	public String getLineid()
	{
		return lineid;
	}
	public void setLineid(String lineid)
	{
		this.lineid = lineid;
	}
	
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public ArrayList<Education> getEducations()
	{
		return educations;
	}
	public void addEducation(String line1, String line2, String line3, String line4)
	{
		Education edu = new Education();
		edu.setLine1(line1);
		edu.setLine2(line2);
		edu.setLine3(line3);
		edu.setLine4(line4);
		if (this.educations==null)
			this.educations = new ArrayList<>();
		this.educations.add(edu);
	}
	public ArrayList<EnglishTest> getEnglishTests()
	{
		return englishTests;
	}
	public void addEnglishTest(String testName, String score1, String score2, String score3, String score4)
	{
		EnglishTest eng = new EnglishTest();
		eng.setTestName(testName);
		eng.setScore1(score1);
		eng.setScore2(score2);
		eng.setScore3(score3);
		eng.setScore4(score4);
		if (this.englishTests==null)
			this.englishTests = new ArrayList<>();
		this.englishTests.add(eng);
	}


	// Models for internal usage
	public class Language
	{
		private String language;
		private String proficiency;
		
		public String getLanguage()
		{
			return language;
		}
		public void setLanguage(String language)
		{
			this.language = language;
		}
		public String getProficiency()
		{
			return proficiency;
		}
		public void setProficiency(String proficiency)
		{
			this.proficiency = proficiency;
		}
	}
	public class EnglishTest
	{
		private String testName;
		private String score1;
		private String score2;
		private String score3;
		private String score4;
		
		public String getTestName()
		{
			return testName;
		}
		public void setTestName(String testName)
		{
			this.testName = testName;
		}
		public String getScore1()
		{
			return score1;
		}
		public void setScore1(String score1)
		{
			this.score1 = score1;
		}
		public String getScore2()
		{
			return score2;
		}
		public void setScore2(String score2)
		{
			this.score2 = score2;
		}
		public String getScore3()
		{
			return score3;
		}
		public void setScore3(String score3)
		{
			this.score3 = score3;
		}
		public String getScore4()
		{
			return score4;
		}
		public void setScore4(String score4)
		{
			this.score4 = score4;
		}
	}
	public class Education
	{
		private String line1;
		private String line2;
		private String line3;
		private String line4;
		
		public String getLine1()
		{
			return line1;
		}
		public void setLine1(String line1)
		{
			this.line1 = line1;
		}
		public String getLine2()
		{
			return line2;
		}
		public void setLine2(String line2)
		{
			this.line2 = line2;
		}
		public String getLine3()
		{
			return line3;
		}
		public void setLine3(String line3)
		{
			this.line3 = line3;
		}
		public String getLine4()
		{
			return line4;
		}
		public void setLine4(String line4)
		{
			this.line4 = line4;
		}
	}
}

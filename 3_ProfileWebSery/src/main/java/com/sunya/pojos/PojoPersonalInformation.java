package com.sunya.pojos;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.sunya.exceptions.NegativeAgeException;

public class PojoPersonalInformation
{
	private String firstname;
	private String lastname;
	private LocalDate dateOfBirth;
	private int age;
	private String gender;
	private String nationality;
	private boolean drivingLicense;
	private boolean engineeringLicense;
	private ArrayList<String[]> listLanguage = new ArrayList<>();  // { {Language1, Proficiency1}, {...2, ...2} }
	private ArrayList<String[]> listProgrammingLanguage = new ArrayList<>();  // { {Language1, Proficiency1}, {...2, ...2} }
	private String address;
	private String phoneNumber;
	private String email;
	private String lineId;
	
	private ArrayList<String[]> listEducation = new ArrayList<>();  // {Period, Title, SchoolName, Details}
	
	// Pattern for TU-GET
	private ArrayList<String[]> listEnglishTest = new ArrayList<>();  // {TestName, score1, score2, score3, scoreTotal}
	
	
	
	// Constructor
	public PojoPersonalInformation()
	{
	}
	public PojoPersonalInformation(String sunyapong)
	{
		if ( sunyapong.equals("sunyapong") )
		{
			setFirstname("Sunyapong");
			setLastname("Prabhapunpilai");

			try
			{
				LocalDate dateOfBirth = LocalDate.of(2000, 2, 24);
				setDateOfBirth(dateOfBirth);
			}
			catch (NegativeAgeException e)
			{
				System.err.println(">>> Exception 001.1 !!! <<<");
				System.err.println(e);
			}
			
			setGender("Male");
			setNationality("Thai");
			setDrivingLicense(true);
			setEngineeringLicense(true);
			
			
			addLanguageToList("Thai",
							"Native proficiency");
			addLanguageToList("English",
							"Conversational proficiency");
			

			addProgrammingLanguageToList("Java",
										"Core Java, Servlet, JSP, JDBC, Spring");
			addProgrammingLanguageToList("SQL",
										"Basic syntax and concepts");
			addProgrammingLanguageToList("Python",
										"Basic syntax and concepts");
			addProgrammingLanguageToList("Matlab",
										"Basic syntax and concepts");
			
			
			setAddress("159/69 PlaiBang Sub-district, BangKrui, Nonthaburi 11130");
			setPhoneNumber("0860232448");
			setEmail("sunyapong.c@gmail.com");
			setLineId("c1234.");
			
			
			addEducationToList("2012-2017",
								"Secondary school, High school",
								"Potisarn Pittayakorn School",
								"Science-Math Program (English Program)");
			addEducationToList("2018-2022",
								"Bachelor Degree",
								"Thammasat University",
								"Faculty of Engineering, TEPE, Civil Engineering (International Program), GPA: 3.33 (Second-class honor)");
			
			
			addEnglishTestToList("TU-GET",
								"Structure:\t"  + "150",
								"Vocabulary:\t" + "180",
								"Reading:\t"    + "450",
								"Total:\t"      + "780");
		}
	}
	// end -- Constructor

	
	
	// Getter & Setter
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

	public LocalDate getDateOfBirth()
	{
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) throws NegativeAgeException
	{
		this.dateOfBirth = dateOfBirth;
		
		Period period = Period.between(dateOfBirth, LocalDate.now());
		
		if ( period.getYears() >= 0 )
		{
			int age = period.getYears();
			setAge(age);
		}
		else
			throw new NegativeAgeException("The age is negative. Please give a valid age.");
	}

	public int getAge()
	{
		return age;
	}

	// Don't allow to set the age manually
	private void setAge(int age)
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

	public boolean getDrivingLicense()
	{
		return drivingLicense;
	}

	public void setDrivingLicense(boolean drivingLicense)
	{
		this.drivingLicense = drivingLicense;
	}

	public boolean getEngineeringLicense()
	{
		return engineeringLicense;
	}

	public void setEngineeringLicense(boolean engineeringLicense)
	{
		this.engineeringLicense = engineeringLicense;
	}

	// Language
	public ArrayList<String[]> getListLanguage()
	{
		return listLanguage;
	}

	public void setListLangauge(ArrayList<String[]> listLanguage)
	{
		this.listLanguage = listLanguage;
	}
	
	public void addLanguageToList(String language, String proficiency)
	{
		String[] toBeAddedToList = new String[2];
		toBeAddedToList[0] = language;
		toBeAddedToList[1] = proficiency;
		listLanguage.add(toBeAddedToList);
	}
	
	public void removeLanguageFromList(int removingIndex)
	{
		listLanguage.remove(removingIndex);
	}
	// end -- Language

	// Programming Language
	public ArrayList<String[]> getListProgrammingLanguage()
	{
		return listProgrammingLanguage;
	}

	public void setListProgramingLanguage(ArrayList<String[]> listProgrammingLanguage)
	{
		this.listProgrammingLanguage = listProgrammingLanguage;
	}
	
	public void addProgrammingLanguageToList(String programmingLanguage, String proficiency)
	{
		String[] toBeAddedToList = new String[2];
		toBeAddedToList[0] = programmingLanguage;
		toBeAddedToList[1] = proficiency;
		listProgrammingLanguage.add(toBeAddedToList);
	}
	
	public void removeProgrammingLanguageFromList(int removingIndex)
	{
		listProgrammingLanguage.remove(removingIndex);
	}
	// end -- Programming Language

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

	// Education
	public ArrayList<String[]> getListEducation()
	{
		return listEducation;
	}

	public void setListEducation(ArrayList<String[]> listEducation)
	{
		this.listEducation = listEducation;
	}
	
	public void addEducationToList(String period,
									String title,
									String schoolName,
									String details)// {Period, Title, SchoolName, Details}
	{
		String[] toBeAddedToList = new String[4];
		toBeAddedToList[0] = period;
		toBeAddedToList[1] = title;
		toBeAddedToList[2] = schoolName;
		toBeAddedToList[3] = details;
		listEducation.add(toBeAddedToList);
	}
	
	public void removeEducationFromList(int removingIndex)
	{
		listEducation.remove(removingIndex);
	}
	// end -- Education

	// English Test
	public ArrayList<String[]> getListEnglishTest()
	{
		return listEnglishTest;
	}

	public void setListEnglishTest(ArrayList<String[]> listEnglishTest)
	{
		this.listEnglishTest = listEnglishTest;
	}
	
	public void addEnglishTestToList(String testName,
									String score1,
									String score2,
									String score3,
									String scoreTotal)// {TestName, score1, score2, score3, scoreTotal}
	{
		String[] toBeAddedToList = new String[5];
		toBeAddedToList[0] = testName;
		toBeAddedToList[1] = score1;
		toBeAddedToList[2] = score2;
		toBeAddedToList[3] = score3;
		toBeAddedToList[4] = scoreTotal;
		listEnglishTest.add(toBeAddedToList);
	}
	
	public void removeEnglishTestFromList(int removingIndex)
	{
		listEnglishTest.remove(removingIndex);
	}
	// end -- English Test
	
	// end -- Getter & Setter

}

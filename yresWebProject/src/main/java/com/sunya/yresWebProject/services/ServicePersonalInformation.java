package com.sunya.yresWebProject.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.daos.DaoCodeExperience;
import com.sunya.yresWebProject.daos.DaoPersinfo;
import com.sunya.yresWebProject.models.DataPersInfo;
import com.sunya.yresWebProject.models.DataPersInfo.DataCodeExperience;
import com.sunya.yresWebProject.models.DataPersInfo.DataCodeExperienceDetail;
import com.sunya.yresWebProject.models.DataPersInfo.DataCodeExperienceTechnology;
import com.sunya.yresWebProject.models.ModelCodeExperience;
import com.sunya.yresWebProject.models.ModelPersinfo;

@Service
public class ServicePersonalInformation
{
	@Autowired
	private DaoPersinfo daoPersInfo;
	@Autowired
	private DaoCodeExperience daoCodeExperience;
	

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
		ModelPersinfo model = daoPersInfo.getPersinfoMinimal(1);
		
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
		
		setCodeExperience(dataPersInfo);
	}
	
	private void setCodeExperience(DataPersInfo dataPersInfo)
	{
		List<ModelCodeExperience> availabelCodeExps = daoCodeExperience.getCodeExperienceModels();
		
		for (ModelCodeExperience codeExp : availabelCodeExps)
		{
			DataCodeExperience dataCodeExp = dataPersInfo.new DataCodeExperience();
			ModelCodeExperience modelCodeExp = daoCodeExperience.getModelCodeExperience(codeExp.getExpId());
			
			dataCodeExp.setExpId(String.valueOf(modelCodeExp.getExpId()));
			dataCodeExp.setTitle(modelCodeExp.getTitle());
			dataCodeExp.setFromMonth(modelCodeExp.getFromMonth().format(DateTimeFormatter.ofPattern("yyyy MMM")));
			dataCodeExp.setToMonth(modelCodeExp.getToMonth()==null? null : modelCodeExp.getToMonth().format(DateTimeFormatter.ofPattern("yyyy MMM")));
			dataCodeExp.setLink(modelCodeExp.getLink());
			
			dataCodeExp.setDetails(
				modelCodeExp.getDetails().stream().map(
					detail -> {
						DataCodeExperienceDetail dt = dataPersInfo.new DataCodeExperienceDetail();
						dt.setDetailId(String.valueOf(detail.getDetailId()));
						dt.setDetail(detail.getDetail());
						return dt;
					}
				).collect(Collectors.toCollection(LinkedList::new))
			);
			
			LinkedList<DataCodeExperienceTechnology> technologies = modelCodeExp.getTechnologies().stream().map(
				technology -> {
					DataCodeExperienceTechnology tech = dataPersInfo.new DataCodeExperienceTechnology();
					tech.setType(technology.getType());
					tech.setTechId(String.valueOf(technology.getTechId()));
					tech.setTechnology(technology.getTechnology());
					return tech;
				}
			).collect(Collectors.toCollection(LinkedList::new));
			
			dataCodeExp.setFrontendTechnologies(
				technologies.stream().filter(tech -> "FRONTEND".equals(tech.getType()))
				.collect(Collectors.toCollection(LinkedList::new))
			);
			
			dataCodeExp.setBackendTechnologies(
				technologies.stream().filter(tech -> "BACKEND".equals(tech.getType()))
				.collect(Collectors.toCollection(LinkedList::new))
			);
			
			dataPersInfo.getCodeExperiences().add(dataCodeExp);
		}
	}


	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

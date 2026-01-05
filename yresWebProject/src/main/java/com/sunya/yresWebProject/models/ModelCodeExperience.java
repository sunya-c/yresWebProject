package com.sunya.yresWebProject.models;

import java.time.LocalDate;
import java.util.List;

public class ModelCodeExperience
{
	private int expId;
	private String title;
	private LocalDate fromMonth;
	private LocalDate toMonth;
	private String link;
	private List<ModelCodeExperienceDetail> details;
	private List<ModelCodeExperienceTechnology> technologies;
	
	public int getExpId()
	{
		return expId;
	}
	public void setExpId(int expId)
	{
		this.expId = expId;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public LocalDate getFromMonth()
	{
		return fromMonth;
	}
	public void setFromMonth(LocalDate fromMonth)
	{
		this.fromMonth = fromMonth;
	}
	public LocalDate getToMonth()
	{
		return toMonth;
	}
	public void setToMonth(LocalDate toMonth)
	{
		this.toMonth = toMonth;
	}
	public String getLink()
	{
		return link;
	}
	public void setLink(String link)
	{
		this.link = link;
	}
	public List<ModelCodeExperienceDetail> getDetails()
	{
		return details;
	}
	public void setDetails(List<ModelCodeExperienceDetail> details)
	{
		this.details = details;
	}
	public List<ModelCodeExperienceTechnology> getTechnologies()
	{
		return technologies;
	}
	public void setTechnologies(List<ModelCodeExperienceTechnology> technologies)
	{
		this.technologies = technologies;
	}
}

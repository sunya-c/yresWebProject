package com.sunya.yresWebProject.models;

import java.util.Objects;

public class ModelCodeExperienceTechnology
{
	private int expId;
	private String type;
	private int techId;
	private String technology;

	@Override
	public int hashCode()
	{
		return Objects.hash(expId, techId, type);
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this==obj)
			return true;
		if (obj==null)
			return false;
		if (getClass()!=obj.getClass())
			return false;
		ModelCodeExperienceTechnology other = (ModelCodeExperienceTechnology)obj;
		return expId==other.expId && techId==other.techId && Objects.equals(type, other.type);
	}
	
	public int getExpId()
	{
		return expId;
	}
	public void setExpId(int expId)
	{
		this.expId = expId;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public int getTechId()
	{
		return techId;
	}
	public void setTechId(int techId)
	{
		this.techId = techId;
	}
	public String getTechnology()
	{
		return technology;
	}
	public void setTechnology(String technology)
	{
		this.technology = technology;
	}
}

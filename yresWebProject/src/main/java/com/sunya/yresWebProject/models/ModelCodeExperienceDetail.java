package com.sunya.yresWebProject.models;

import java.util.Objects;

public class ModelCodeExperienceDetail
{
	private int expId;
	private int detailId;
	private String detail;
	

	
	@Override
	public int hashCode()
	{
		return Objects.hash(detailId, expId);
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
		ModelCodeExperienceDetail other = (ModelCodeExperienceDetail)obj;
		return detailId==other.detailId && expId==other.expId;
	}
	
	public int getExpId()
	{
		return expId;
	}
	public void setExpId(int expId)
	{
		this.expId = expId;
	}
	public int getDetailId()
	{
		return detailId;
	}
	public void setDetailId(int detailId)
	{
		this.detailId = detailId;
	}
	public String getDetail()
	{
		return detail;
	}
	public void setDetail(String detail)
	{
		this.detail = detail;
	}
}

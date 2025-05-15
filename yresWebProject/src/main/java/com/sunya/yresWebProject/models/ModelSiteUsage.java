package com.sunya.yresWebProject.models;

import com.sunya.yresWebProject.daos.PageUsageinfo;

/**
 * This class is a model for database. This class can be use when interacting
 * with <strong>usageinfo</strong> table.
 */
public class ModelSiteUsage
{
	private String refNumber;
	private String ip;
	private String dateTime;
	
	/**
	 * #0 usageCreateAccount<br>
	 * #1 usageError<br>
	 * #2 usageFeedback<br>
	 * #3 usageLogin<br>
	 * #4 usagePersInfo<br>
	 * #5 usageRedirecting<br>
	 * #6 usageUnderConstruction<br>
	 * #7 usageWelcome<br>
	 * #8 usageResumeDownload<br>
	 * #9 usageRestApi<br>
	 * #10 usageWebHistory<br>
	 * #11 usageAdminPanel<br>
	 * #12 usageAccountInfo<br>
	 */
	private int[] usages = new int[PageUsageinfo.numberOfPages];
	
	
	public String getRefNumber() {
		return refNumber;
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	public int getUsageValue(PageUsageinfo whichPage) {
		return usages[whichPage.getColumnOrder()];
	}
	public void setUsageValue(int usageValue, PageUsageinfo whichPage) {
		this.usages[whichPage.getColumnOrder()] = usageValue;
	}
	
	public int[] getUsages() {
		return usages;
	}
	public void setUsages(int[] usages) {
		this.usages = usages;
	}
	
}

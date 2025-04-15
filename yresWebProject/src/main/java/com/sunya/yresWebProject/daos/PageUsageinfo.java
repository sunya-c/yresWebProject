package com.sunya.yresWebProject.daos;

/**
 * This enum represents number-of-usage columns in usageinfo table. This class
 * is meant to be used whenever you want to refer to a number-of-usage column in
 * usageinfo table, quality-of-life-wise.
 */
public enum PageUsageinfo
{
	PAGE_CREATEACCOUNT		(0, "page_createaccount"),
	PAGE_ERROR				(1, "page_error"),
	PAGE_FEEDBACK			(2, "page_feedback"),
	PAGE_LOGIN				(3, "page_login"),
	PAGE_PERSINFO			(4, "page_persinfo"),
	PAGE_REDIRECTING		(5, "page_redirecting"),
	PAGE_UNDERCONSTRUCTION	(6, "page_underconstruction"),
	PAGE_WELCOME			(7, "page_welcome"),
	RESUME_DOWNLOAD			(8, "resume_download"),
	PAGE_RESTAPI			(9, "page_restapi");


	public final static int numberOfPages = 10;

	private int columnOrder;
	private String columnName;


	private PageUsageinfo(int columnOrder, String columnName)
	{
		this.columnOrder = columnOrder;
		this.columnName = columnName;
	}


	public int getColumnOrder()
	{
		return columnOrder;
	}


	public String getColumnName()
	{
		return columnName;
	}
}

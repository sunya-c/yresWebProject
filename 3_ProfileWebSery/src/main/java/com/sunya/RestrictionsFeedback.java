package com.sunya;

public class RestrictionsFeedback
{
	ErrorMessageSetterFeedback errSetter;
	String reportTitle;
	String reportDetail;

	// Constructor
	public RestrictionsFeedback(
			ErrorMessageSetterFeedback errSetter,
			String reportTitle,
			String reportDetail)
	{
		this.errSetter = errSetter;
		this.reportTitle = reportTitle;
		this.reportDetail = reportDetail;
	}
	// end -- Constructor
	
	
	public boolean checkRestriction()
	{
		boolean validInfo = true;
		if (reportTitle.isBlank())
		{
			errSetter.setReportTitleErr(ErrMsg.EMPTY_ERR);
			validInfo = false;
		}
		if (reportDetail.isBlank())
		{
			errSetter.setReportDetailErr(ErrMsg.EMPTY_ERR);
			validInfo = false;
		}
		
		return validInfo;
	}

}

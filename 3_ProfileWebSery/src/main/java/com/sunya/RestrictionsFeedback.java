package com.sunya;

public class RestrictionsFeedback
{
	ErrorMessageSetterFeedback errSetter;
	String feedbackTitle;
	String feedbackDetail;
	String feedbackErrorMessage;

	// Constructor
	public RestrictionsFeedback(
			ErrorMessageSetterFeedback errSetter,
			String feedbackTitle,
			String feedbackDetail,
			String feedbackErrorMessage)
	{
		this.errSetter = errSetter;
		this.feedbackTitle = feedbackTitle;
		this.feedbackDetail = feedbackDetail;
		this.feedbackErrorMessage = feedbackErrorMessage;
	}
	// end -- Constructor
	
	
	public boolean checkRestriction()
	{
		boolean validInfo = true;
		if (!feedbackTitle.isBlank())
		{
			if ( !(feedbackTitle.length() <= 200) )
			{
				errSetter.setFeedbackTitleErr(ErrMsg.FEEDBACK_TITLE_LENGTH);
				validInfo = false;
			}
		}
		else
		{
			errSetter.setFeedbackTitleErr(ErrMsg.FEEDBACK_FIELD_EMPTY);
			validInfo = false;
		}
		
		if (!feedbackDetail.isBlank())
		{
			if ( !(feedbackDetail.length() <= 4000) )
			{
				errSetter.setFeedbackDetailErr(ErrMsg.FEEDBACK_DETAIL_LENGTH);
				validInfo = false;
			}
		}
		else
		{
			errSetter.setFeedbackDetailErr(ErrMsg.FEEDBACK_FIELD_EMPTY);
			validInfo = false;
		}

		if ( !(feedbackErrorMessage.length() <= 2000) )
		{
			errSetter.setFeedbackErrorMessageErr(ErrMsg.FEEDBACK_ERRORMESSAGE_LENGTH);
			validInfo = false;
		}
		
		return validInfo;
	}

}

package com.sunya.yresWebProject.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.sunya.yresWebProject.models.FormFeedback;

import jakarta.servlet.ServletException;

/**
 * Call {@code setupRestrictionFeedback(title, detail, errorMessage)} first before {@code checkRestriction()}
 */
@Component
@Scope("prototype")
public class RestrictionsFeedback
{
	@Autowired
	private ErrorMessageSetterFeedback errSetter;
	private String feedbackTitle;
	private String feedbackDetail;
	private String feedbackErrorMessage;
	
	
	
	
	private void setupRestrictionFeedback(FormFeedback formFb)
	{
		this.feedbackTitle = formFb.getFeedbackTitle();
		this.feedbackDetail = formFb.getFeedbackDetail();
		this.feedbackErrorMessage = formFb.getFeedbackErrorMessage();
	}
	
	
	
	
	/**
	 * Check whether the given feedback information comply with the restriction.
	 * Set up the feedbackTitle, feedbackDetail, and feedbackErrorMessage before calling this method.
	 * 
	 * @return <strong>true</strong> ~ if the given feedback information comply with ALL the restriction.<br>
	 *         <strong>false</strong> ~ if at least one of them doesn't meet the restriction. 
	 *         							The error messages, containing the detail, will be set to the session by {@code ErrorMessageSetterFeedback.java} object
	 * @throws ServletException
	 */
	public boolean checkRestriction(FormFeedback formFb)
	{
		setupRestrictionFeedback(formFb);
		
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

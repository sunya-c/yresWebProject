package com.sunya.yresWebProject.models;

/**
 * A class created for mapping values come with the request object. Use this
 * class to map <strong>Feedback</strong>-form values.
 */
public class FormFeedback
{
	private String feedbackTitle;
	private String feedbackDetail;
	private String feedbackErrorMessage;
	
	
	
	public String getFeedbackTitle()
	{
		return feedbackTitle;
	}
	public void setFeedbackTitle(String feedbackTitle)
	{
		this.feedbackTitle = feedbackTitle;
	}
	public String getFeedbackDetail()
	{
		return feedbackDetail;
	}
	public void setFeedbackDetail(String feedbackDetail)
	{
		this.feedbackDetail = feedbackDetail;
	}
	public String getFeedbackErrorMessage()
	{
		return feedbackErrorMessage;
	}
	public void setFeedbackErrorMessage(String feedbackErrorMessage)
	{
		this.feedbackErrorMessage = feedbackErrorMessage;
	}
}

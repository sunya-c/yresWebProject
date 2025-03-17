package com.sunya.yresWebProject.models;

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

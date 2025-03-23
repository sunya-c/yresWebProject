package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.sunya.yresWebProject.Url;
import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.controllers.ControllerRedirecting;
import com.sunya.yresWebProject.daos.DaoFeedback;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.DataFeedback;
import com.sunya.yresWebProject.models.FormFeedback;
import com.sunya.yresWebProject.models.ModelFeedback;
import com.sunya.yresWebProject.restrictions.RestrictionsFeedback;

@Service
public class ServiceFeedback
{
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoFeedback dao;
	@Autowired
	private RestrictionsFeedback restriction;
	
	public String sFeedback(FormFeedback formFb, DataFeedback dataFeedback, String codeFeedback) throws Exception
	{
		dataFeedback.setTitlePreTyped(formFb.getFeedbackTitle());
		dataFeedback.setDetailPreTyped(formFb.getFeedbackDetail());
		
		if (restriction.checkRestriction(formFb, dataFeedback))
		{
			String username;
			synchronized (sm.getKeyHolder().getKeyLogin())
			{
				username = sm.getSessionLogin().getUsername();
			}
			
			ModelFeedback model = new ModelFeedback();
			model.setUsername(username);
			model.setFeedbackTitile(formFb.getFeedbackTitle());
			model.setFeedbackDetail(formFb.getFeedbackDetail());
			model.setFeedbackErrorMessage(formFb.getFeedbackErrorMessage());
			
			try
			{
				String refNumber = dao.submitFeedback(model);
			}
			catch (SomethingWentWrongException e)
			{
				return Url.feedback+"?"
			+((formFb.getFeedbackErrorMessage().isBlank()) ? "" : sm.FEEDBACK_ERRORMESSAGE_PRETYPED+"="+formFb.getFeedbackErrorMessage()+"&")
			+"code="+codeFeedback;
			}
			
			
			String code = sm.getSessionRedirecting().generateCode();
			
			return Url.redirecting+"?message=Thank you for reaching out!&destinationPage=Home page&code="+code;
		}
		else
		{
			return Url.feedback+"?"
		+((formFb.getFeedbackErrorMessage().isBlank()) ? "" : sm.FEEDBACK_ERRORMESSAGE_PRETYPED+"="+formFb.getFeedbackErrorMessage()+"&")
		+"code="+codeFeedback;
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

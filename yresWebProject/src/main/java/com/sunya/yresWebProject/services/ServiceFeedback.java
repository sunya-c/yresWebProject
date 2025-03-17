package com.sunya.yresWebProject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunya.yresWebProject.YresWebProjectApplication;
import com.sunya.yresWebProject.daos.DaoFeedback;
import com.sunya.yresWebProject.exceptions.SomethingWentWrongException;
import com.sunya.yresWebProject.managers.SessionManager;
import com.sunya.yresWebProject.models.FormFeedback;
import com.sunya.yresWebProject.models.ModelFeedback;
import com.sunya.yresWebProject.restrictions.RestrictionsFeedback;

import jakarta.servlet.http.HttpSession;

@Service
public class ServiceFeedback
{
	@Autowired
	private HttpSession session;
	@Autowired
	private SessionManager sm;
	@Autowired
	private DaoFeedback dao;
	
	public String sFeedback(FormFeedback formFb) throws Exception
	{
		sm.removeFeedbackErr();
		sm.removeFeedbackPreTyped();
		
		session.setAttribute(sm.FEEDBACK_TITLE_PRETYPED, formFb.getFeedbackTitle());
		session.setAttribute(sm.FEEDBACK_DETAIL_PRETYPED, formFb.getFeedbackDetail());
		
		RestrictionsFeedback restriction = YresWebProjectApplication.context
									.getBean(RestrictionsFeedback.class);
		
		if (restriction.checkRestriction(formFb))
		{
			
			String username = (String)session.getAttribute(sm.LOGIN_USERNAME);
			
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
				session.setAttribute(sm.FROM_SERVLET, toString());
				return "feedback?"+sm.FEEDBACK_ERRORMESSAGE_PRETYPED+"="+formFb.getFeedbackErrorMessage();
			}
			
			session.setAttribute(sm.REDIRECT_MESSAGE, "Thank you for reaching out!");
			session.setAttribute(sm.REDIRECT_DESTINATION, "Home page");
			
			sm.removeFeedbackPreTyped();
			
			session.setAttribute(sm.FROM_SERVLET, toString());
			return "redirecting";
		}
		else
		{
			session.setAttribute(sm.FROM_SERVLET, toString());
			return "feedback?"+sm.FEEDBACK_ERRORMESSAGE_PRETYPED+"="+formFb.getFeedbackErrorMessage();
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

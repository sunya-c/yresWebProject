package com.sunya.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.sunya.config.InternalConfig;
import com.sunya.daos.DaoFeedback;
import com.sunya.managers.SessionManager;
import com.sunya.restrictions.ErrorMessageSetterFeedback;
import com.sunya.restrictions.RestrictionsFeedback;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
	@Autowired
	private RestrictionsFeedback restriction;
	
	public String sFeedback(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String feedbackTitle = request.getParameter("reportTitle");
		String feedbackDetail = request.getParameter("reportDetail");
		String feedbackErrorMessage = request.getParameter("errorMessage");
		
		sm.removeFeedbackErr();
		
		restriction.setupRestrictionFeedback(feedbackTitle, feedbackDetail, feedbackErrorMessage);
		
		if (restriction.checkRestriction())
		{
			String username = (String) session.getAttribute(sm.LOGIN_USERNAME);
			dao.submitFeedback(username, feedbackTitle, feedbackDetail, feedbackErrorMessage);
			
			session.setAttribute(sm.REDIRECT_MESSAGE, "Thank you for reaching out!");
			session.setAttribute(sm.REDIRECT_DESTINATION, "Home page");
			
			sm.removeFeedbackPreTyped();
			
			session.setAttribute("fromServlet", toString());
			return "redirecting";
		}
		else
		{
			session.setAttribute(sm.FEEDBACK_TITLE_PRETYPED, feedbackTitle);
			session.setAttribute(sm.FEEDBACK_DETAIL_PRETYPED, feedbackDetail);
			
			session.setAttribute("fromServlet", toString());
			return "feedback?"+sm.FEEDBACK_ERRORMESSAGE_PRETYPED+"="+feedbackErrorMessage;
		}
	}
	
	@Override
	public String toString()
	{
		return this.getClass().getName();
	}

}

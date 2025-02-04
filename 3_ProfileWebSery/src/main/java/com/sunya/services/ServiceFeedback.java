package com.sunya.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

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
	public String sFeedback(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String feedbackTitle = request.getParameter("reportTitle");
		String feedbackDetail = request.getParameter("reportDetail");
		String feedbackErrorMessage = request.getParameter("errorMessage");
		
		HttpSession session = request.getSession();
		SessionManager sm = new SessionManager(session);
		sm.removeFeedbackErr();
		
		ErrorMessageSetterFeedback errSetter = new ErrorMessageSetterFeedback(session);  //TODO: try to put this inside restriction class so that this line can be removed.
		RestrictionsFeedback restriction = new RestrictionsFeedback(errSetter, feedbackTitle, feedbackDetail, feedbackErrorMessage);
		
		if (restriction.checkRestriction())
		{
			DaoFeedback dao = new DaoFeedback();
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

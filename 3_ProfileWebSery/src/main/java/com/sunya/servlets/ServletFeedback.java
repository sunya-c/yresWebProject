package com.sunya.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.sunya.ErrMsg;
import com.sunya.ErrorMessageSetterFeedback;
import com.sunya.RestrictionsFeedback;
import com.sunya.SessionManager;
import com.sunya.daos.DaoFeedback;


@WebServlet("/ServletFeedback")
public class ServletFeedback extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String feedbackTitle = request.getParameter("reportTitle");
		String feedbackDetail = request.getParameter("reportDetail");
		String feedbackErrorMessage = request.getParameter("errorMessage");
		
		HttpSession session = request.getSession();
		SessionManager sm = new SessionManager(session);
		sm.removeFeedbackErr();
		
		ErrorMessageSetterFeedback errSetter = new ErrorMessageSetterFeedback(session);
		RestrictionsFeedback restriction = new RestrictionsFeedback(errSetter, feedbackTitle, feedbackDetail, feedbackErrorMessage);
		
		if (restriction.checkRestriction())
		{
			DaoFeedback dao = new DaoFeedback();
			String username = (String) session.getAttribute(sm.LOGIN_USERNAME);
			dao.submitFeedback(username, feedbackTitle, feedbackDetail, feedbackErrorMessage);
			
			session.setAttribute("message", "Thank you for reaching out!");
			session.setAttribute("destinationPage", "\"Home page\"");
			
			sm.removeFeedbackPreTyped();
			
			session.setAttribute("fromServlet", getServletName());
			RequestDispatcher rd = request.getRequestDispatcher("RedirectingPage.jsp");
			rd.forward(request, response);
		}
		else
		{
			session.setAttribute(sm.FEEDBACK_TITLE_PRETYPED, feedbackTitle);
			session.setAttribute(sm.FEEDBACK_DETAIL_PRETYPED, feedbackDetail);
			
			session.setAttribute("fromServlet", getServletName());
			response.sendRedirect("FeedbackPage.jsp?"+sm.FEEDBACK_ERRORMESSAGE_PRETYPED+"="+feedbackErrorMessage);
		}


		
	}

}

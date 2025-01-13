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


@WebServlet("/ServletFeedback")
public class ServletFeedback extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		String reportTitle = request.getParameter("reportTitle");
		String reportDetail = request.getParameter("reportDetail");
		String errorMessage = request.getParameter("errorMessage");
		
		HttpSession session = request.getSession();
		SessionManager sm = new SessionManager(session);
		sm.removeFeedbackErr();
		
		ErrorMessageSetterFeedback errSetter = new ErrorMessageSetterFeedback(session);
		RestrictionsFeedback restriction = new RestrictionsFeedback(errSetter, reportTitle, reportDetail);
		
		if (restriction.checkRestriction())
		{
			// TODO: dao save report to database
			
			session.setAttribute("message", "Thank you for reaching out!");
			session.setAttribute("destinationPage", "\"Home page\"");
			
			sm.removeFeedback();
			
			session.setAttribute("fromServlet", getServletName());
			RequestDispatcher rd = request.getRequestDispatcher("RedirectingPage.jsp");
			rd.forward(request, response);
		}
		else
		{
			session.setAttribute("preTypedReportTitle", reportTitle);
			session.setAttribute("preTypedReportDetail", reportDetail);
			
			session.setAttribute("fromServlet", getServletName());
			response.sendRedirect("FeedbackPage.jsp?preTypedErrMessage="+errorMessage);
		}


		
	}

}

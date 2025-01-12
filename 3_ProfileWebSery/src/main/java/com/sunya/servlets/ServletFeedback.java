package com.sunya.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Servlet implementation class ServletFeedback
 */
@WebServlet("/ServletFeedback")
public class ServletFeedback extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		HttpSession session = request.getSession();
		session.setAttribute("fromServlet", "UnderConstruction");

		response.sendRedirect("UnderConstructionPage.jsp");
	}

}

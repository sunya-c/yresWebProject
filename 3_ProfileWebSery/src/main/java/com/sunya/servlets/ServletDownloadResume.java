package com.sunya.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/ServletDownloadResume")
public class ServletDownloadResume extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException
	{
		HttpSession session = request.getSession();
		
		FileInputStream inStream = null;
		OutputStream outStream = null;
		try
		{
			// Path to the file to be downloaded
			String filePath = getServletContext().getRealPath("/outBox/resumeC6_pdf.pdf");
			File downloadFile = new File(filePath);
			inStream = new FileInputStream(downloadFile);

			// Get MIME type of the file
			String mimeType = getServletContext().getMimeType(filePath);
			if (mimeType == null)
			{
				// Set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			System.out.println(mimeType);

			// Modify response
			response.setContentType(mimeType);
			response.setContentLength((int) downloadFile.length());

			// Set headers for the response
			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=\"ResumeC.pdf";
			response.setHeader(headerKey, headerValue);

			// Get output stream of the response
			outStream = response.getOutputStream();
			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			// Write bytes read from the input stream into the output stream
			while ((bytesRead = inStream.read(buffer)) != -1)
			{
				outStream.write(buffer, 0, bytesRead);
			}
		}
		catch (IOException e)
		{
			session.setAttribute("errorDescription", "java.io.IOException: "
					+ "Something went wrong.");
			session.setAttribute("fromServlet", getServletName());
		}
		finally
		{
			try
			{
				inStream.close();
				outStream.close();
			}
			catch (IOException e)
			{
				session.setAttribute("errorDescription", "java.io.IOException: "
						+ "Something went wrong.");
				session.setAttribute("fromServlet", getServletName());
			}
			catch (NullPointerException ne)
			{
				session.setAttribute("errorDescription", "java.lang.NullPointerException: "
						+ "Something went wrong.");
				session.setAttribute("fromServlet", getServletName());
			}
			
			try
			{
				response.sendRedirect("ErrorPage.jsp");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
		}

	}

}

<%@page import="com.sunya.PrintError"%>
<%@page import="com.sunya.FromServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<%
	FromServlet fs = new FromServlet(application, "ServletFeedback", session);
	if (fs.isFromServlet())
	{
		System.out.println("fromServlet and context matched.");
		session.setAttribute("fromServlet", null);
	}
	else
	{
		String errText = "fromServlet mismatch";
		PrintError.println(errText);
		System.err.println("fromServlet attribute : " + fs.getFromServletAttribute());
		System.err.println("ServletContext        : " + fs.getFullServletName());
		session.setAttribute("fromServlet", null);

		RequestDispatcher rd = request.getRequestDispatcher("ServletFeedback");
		rd.forward(request, response);
	}
	%>

</body>
</html>
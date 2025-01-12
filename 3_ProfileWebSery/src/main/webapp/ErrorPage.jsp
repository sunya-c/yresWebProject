<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<%
if (session.getAttribute("fromServlet") == null)
	response.sendRedirect("WelcomePage.jsp");
else
	session.setAttribute("fromServlet", null);
%>
<br>
&nbsp;----- ERROR -----
<br>
<br>
&nbsp;>>>&nbsp;${errorDescription}
<br>
<br>
&nbsp;If the error persists, please report via bug report button.
</body>
</html>
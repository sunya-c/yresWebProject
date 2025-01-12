<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>

<!--
	TODO
	Declarative
	final String ERR1
	String errText
	-->

<%
	if (
			(session.getAttribute("loggedIn") != null) &&
			((boolean) session.getAttribute("loggedIn") == true)
			)
	{
		response.sendRedirect("WelcomePage.jsp");
	}
	// try catch ClassCastException e
	// when loggedIn is not null and not boolean
%>

	<form action = "ServletLogin", method = "post">
	
		Username : <input type = "text" name = "username" placeholder="Enter your username" value="${preTypeUsername}"> ${wrongUsername} <br>
		Password : <input type = "text" name = "password" placeholder="Enter your password"> ${wrongPassword} <br>
		<input type = submit value = "Login">
		
	</form>
	
	<br><br><br>
	
	<form action = "ServletPersonalInformation", method = "get">
		<input type = submit value = "Personal information">
	</form>
	
	<br>
	
	<form action = "ServletDownloadResume", method = "get">
		<input type = submit value = "Download Resume">
	</form>
</body>

</html>
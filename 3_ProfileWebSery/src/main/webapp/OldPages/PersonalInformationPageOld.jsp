<%@page import="com.sunya.FromServlet"%>
<%@page import="com.sunya.PrintError"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Personal Information</title>
</head>

<body>

<!-- Prevent the back button -->
<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
	response.setHeader("Pragma", "no-cache"); // http 1.0
	response.setHeader("Expires", "0"); // Proxies
%>

<!-- Forcing to access this page by Servlet -->
<!-- Check if this page is called by its servlet -->
<%
	FromServlet fs = new FromServlet(application, "ServletPersonalInformation", session);

	if (fs.isFromServlet())
		{
		System.out.println("fromServlet and context matched.");
		session.setAttribute("fromServlet", null);
		}
	else
	{
		// TODO something might be wrong. Reset the session!!!
		String errText = "fromServlet mismatch";
		PrintError.println(errText);
		System.err.println("fromServlet attribute : " + fs.getFromServletAttribute());
		System.err.println("ServletContext        : " + fs.getFullServletName());
		
		session.setAttribute("fromServlet", null);
		response.sendRedirect("WelcomePage.jsp");
	}
%>

<!-- Page content -->
<pre>
	Personal Information <br>
	
	Name :                  ${firstName} ${lastName} <br>
	Date of Birth :         ${dateOfBirth} <br>
	Age :                   ${age} <br>
	Gender :                ${gender} <br>
	Nationality :           ${nationality} <br>
	Driving license :       ${drivingLicense} <br>
	Engineering license :   ${engineeringLicense} </pre>

<% request.setAttribute("counter", 0); %>

<br>

<!-- Language -->
<c:forEach items="${listLanguage}" var="language">
	<c:forEach items="${language}" var="langAndProf">
		<c:if test="${counter % 2 == 0}">
			<c:if test="${counter == 0}">
				<pre>	Language :              ${langAndProf}</pre>
			</c:if>
			<c:if test="${counter != 0}">
				<pre>	                        ${langAndProf}</pre>
			</c:if>
		</c:if>
		<c:if test="${counter % 2 == 1}">
			<pre>                                (${langAndProf})</pre>
		</c:if>
		<%
			int count = (int) request.getAttribute("counter");
			count += 1;
			request.setAttribute("counter", count);
		%>
	</c:forEach>
</c:forEach>
<% request.setAttribute("counter", 0); %>

<br>

<!-- Programming Language -->
<c:forEach items="${listProgrammingLanguage}" var="programmingLanguage">
	<c:forEach items="${programmingLanguage}" var="langAndProf">
		<c:if test="${counter % 2 == 0}">
			<c:if test="${counter == 0}">
				<pre>	Programming language :  ${langAndProf}</pre>
			</c:if>
			<c:if test="${counter != 0}">
				<pre>	                        ${langAndProf}</pre>
			</c:if>
		</c:if>
		<c:if test="${counter % 2 == 1}">
			<pre>                                (${langAndProf})</pre>
		</c:if>
		<%
			int count = (int) request.getAttribute("counter");
			count += 1;
			request.setAttribute("counter", count);
		%>
	</c:forEach>
</c:forEach>
<% request.setAttribute("counter", 0); %>

<br>

<pre>
	Contacts <br>
	
	Phone number :          ${phoneNumber} <br>
	Email :                 ${email} <br>
	Line ID :               ${lineId} </pre>


</body>

</html>
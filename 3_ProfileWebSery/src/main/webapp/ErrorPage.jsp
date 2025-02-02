<%@page import="com.sunya.managers.SessionManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error!!!</title>
<%
	String cssVersion;
	if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
		cssVersion = System.getProperty("SERY_CSS_VERSION");
	else
		cssVersion = System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="css/ErrorPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="i3ptqv">

	<%
	if (session.getAttribute("fromServlet") == null)
		response.sendRedirect("Home");
	else
		session.setAttribute("fromServlet", null);
	%>

	<div id="i6jq1w">
		<div id="i3sbnu">
			----- ERROR -----<br />
		</div>
		<div id="iakzlo">>>> ${errorDescription}</div>
		<form method="get" action="feedback" id="ijo1ol">
			<div id="i3wdhj">If the error persists, please report via bug
				report button.</div>
			<button type="submit" id="iwacim">Give feedback / bug report</button>
			<input type="hidden" name="preTypedFeedbackErrorMessage" value="${errorDescription}">
		</form>
	</div>
	<%
	SessionManager sm = new SessionManager(session);
	sm.removeErrorPage();
	%>
</body>
</html>
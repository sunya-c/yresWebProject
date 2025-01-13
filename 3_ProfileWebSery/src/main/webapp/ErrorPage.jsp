<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Error!!!</title>
<link rel="stylesheet" href="css/ErrorPageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="i3ptqv">

	<%
	if (session.getAttribute("fromServlet") == null)
		response.sendRedirect("WelcomePage.jsp");
	else
		session.setAttribute("fromServlet", null);
	%>

	<div id="i6jq1w">
		<div id="i3sbnu">
			----- ERROR -----<br />
		</div>
		<div id="iakzlo">>>> ${errorDescription}</div>
		<div id="i3wdhj">If the error persists, please report via bug
			report button.</div>
	</div>
	<form method="get" action="FeedbackPage.jsp" id="ijo1ol">
		<button type="submit" id="iwacim">Give feedback / bug report</button>
		<input type="hidden" name="preTypedErrMessage" value="${errorDescription}">
	</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres - error</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/ErrorPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="i3ptqv">
	<div id="i6jq1w">
		<div id="i3sbnu">----- ERROR -----</div>
		<div id="iakzlo">>>> ${dataError.errorDescription}</div>
		<form method="get" action="/feedback" id="ijo1ol">
			<div id="i3wdhj">If the error persists, please report via bug
				report button.</div>
			<button type="submit" id="iwacim">Give feedback / bug report</button>
			<input type="hidden" name="preTypedFeedbackErrorMessage"
				value="${dataError.errorDescription}">
		</form>
	</div>
</body>
</html>
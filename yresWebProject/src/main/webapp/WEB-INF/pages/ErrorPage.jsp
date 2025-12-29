<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isErrorPage="true"%>-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres - error</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<!--<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>-->
<!--<link rel="stylesheet"
	href="/resources/css/ErrorPageCss.css<%= cssVersion %>" />-->
<link rel="stylesheet"
	href="/resources/css/ErrorPageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body>
	<div id="wrapper">
		<div id="title" class="bold">----- ERROR -----</div>
		<div id="description">>>> ${dataError.errorDescription}</div>
		<form method="get" action="/feedback">
			<span class="text-color-dim">If the error persists, please report via bug report button.</span>
			<button type="submit">Give feedback / bug report</button>
			<input type="hidden" name="preTypedFeedbackErrorMessage"
				value="${dataError.errorDescription}">
		</form>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Redirecting</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="/resources/css/RedirectingPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
<script type="text/javascript">
	// Redirect to ServletRedirecting after a delay
	setTimeout(function() {
		window.location.href = "${dataRedirecting.destinationUrl}";
	}, 4500); // 4500 milliseconds = 4.5 seconds
</script>
</head>
<body>
	<div id="wrapper">
		<div id="message" class="bold">${dataRedirecting.message}</div>
		<div id="destination">
			Moving you to <span class="bold italic">${dataRedirecting.destinationPage}</span>...
		</div>
	</div>
</body>
</html>
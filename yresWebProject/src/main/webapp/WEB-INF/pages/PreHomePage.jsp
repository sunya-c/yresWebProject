<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Loading...</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<!--<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>-->
<link rel="stylesheet"
	href="/resources/css/PreHomePageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
<script>
	window.onload = function() {
		// Redirect to ServletRedirecting after a delay
		setTimeout(() => {
			window.location.href = "/Home${botParam}";
		}, 800);
	}
</script>
</head>
<body>
	<div id="wrapper">
		<div id="loading">
			Loading. . .
		</div>
	</div>
</body>
</html>

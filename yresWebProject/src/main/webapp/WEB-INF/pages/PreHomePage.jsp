<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Loading...</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/PreHomePageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
<script type="text/javascript">
	// Redirect to ServletRedirecting after a delay
	setTimeout(function() {
		window.location.href = "/Home"; <!-- TODO: change to el for dynamic redirecting -->
	}, 2000); // 2000 milliseconds = 2.0 seconds
</script>
</head>
<body id="i0wps4">
	<div id="ij9j5j">
		<div id="ibamvk">
			Loading. . .
		</div>
	</div>
</body>
</html>

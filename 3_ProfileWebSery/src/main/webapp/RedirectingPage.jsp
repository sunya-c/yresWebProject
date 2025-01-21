<%@page import="com.sunya.PrintError"%>
<%@page import="com.sunya.FromServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Redirecting</title>
<%
	String cssVersion;
	if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
		cssVersion = System.getProperty("SERY_CSS_VERSION");
	else
		cssVersion = System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="css/RedirectingPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
<script type="text/javascript">
	// Redirect to ServletRedirecting after a delay
	setTimeout(function() {
		window.location.href = "WelcomePage.jsp";
	}, 5000); // 5000 milliseconds = 5 seconds
</script>
</head>
<body id="ieu1zk">

	<%
	if (session.getAttribute("fromServlet") != null)
	{
		System.out.println("fromServlet and context matched.");
		session.setAttribute("fromServlet", null);
	}
	else
	{
		String errText = "fromServlet mismatch";
		PrintError.println(errText);
		session.setAttribute("fromServlet", null);

		response.sendRedirect("WelcomePage.jsp");
	}
	%>
	
	<div id="ifn70r">
		<div id="i20y3j">${message}</div>
		<div id="iz2qet">Moving you back to ${destinationPage}...</div>
	</div>
</body>
</html>
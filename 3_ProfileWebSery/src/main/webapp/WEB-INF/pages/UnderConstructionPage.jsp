<%@page import="com.sunya.FromServlet"%>
<%@page import="com.sunya.PrintError"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres - under construction</title>
<link rel="icon" href="resources/pics/Icon.png" type="image/png">
<%
	String cssVersion;
	if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
		cssVersion = System.getProperty("SERY_CSS_VERSION");
	else
		cssVersion = System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="resources/css/UnderConstructionPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap" rel="stylesheet" >
</head>

<body id="ie993">
	
	<!-- Forcing to access this page by Servlet -->
	<!-- Check if this page is called by its servlet -->
	<%-- <%
		FromServlet fs = new FromServlet(application, "UnderConstruction", session);
	
		if (fs.isFromServlet())
			{
			System.out.println("fromServlet and context matched.");
			session.setAttribute("fromServlet", null);
			}
		else
		{
			String errText = "fromServlet mismatch";
			PrintError.println(errText);
			System.err.println("fromServlet attribute : " + fs.getFromServletAttribute());
			System.err.println("ServletContext        : " + fs.getFullServletName());
			session.setAttribute("fromServlet", null);
			
			response.sendRedirect("Home");
		}
	%> --%>

	<div id="i3p1i">
		<div id="ikk4r">
			Under Construction...<br />
			<br />Check back soon!!!
		</div>
	</div>

</body>
</html>





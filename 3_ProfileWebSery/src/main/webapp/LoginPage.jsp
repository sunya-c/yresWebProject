<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres home</title>
<link rel="stylesheet" href="css/LoginPageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap" rel="stylesheet" >
</head>

<body id="i5xm">

	<%
	session.setAttribute("fromPage", "LoginPage.jsp");
	%>

	<!--
	TODO
	Declarative
	final String ERR1
	String errText
	-->

	<%
	if ((session.getAttribute("loggedIn") != null) && ((boolean) session.getAttribute("loggedIn") == true))
	{
		response.sendRedirect("WelcomePage.jsp");
	}
	// try catch ClassCastException e
	// when loggedIn is not null and not boolean
	%>

	<div id="i5dg">
		<form method="get" id="iq2wzk" action="WelcomePage.jsp">
			<button type="submit" id="ix3vku">Home</button>
		</form>
		<div id="iao9b">
			<form method="post" id="iipx" action="ServletLogin">
				<div id="i2sh">
					<label id="i9zl">Username<br /></label><input type="text"
						id="ikq8l" placeholder="Enter your username" name="username" value="${preTypedUsername}" /><br>
					<label id="irep4">${wrongUsername}<br /></label>
				</div>
				<div id="i1uz3">
					<label id="ianeo">Password<br /></label><input type="password"
						id="in4rx" placeholder="Enter your password" name="password" /><br>
					<label id="igiel">${wrongPassword}<br /></label>
				</div>
				<button type="submit" id="ikmqp">Log in</button>
			</form>
			<form method="get" id="i8bga" action="CreateAccountPage.jsp">
				<button type="submit" id="i3xat">Create an account</button>
			</form>
		</div>
		<form method="get" action="FeedbackPage.jsp" id="i9ojw1">
			<button type="submit" id="i9l8gi">Give feedback / bug report</button>
		</form>
	</div>
	<div id="imxfh">
		<form method="get" id="i0ydh" action="PersonalInformationPage.jsp">
			<button type="submit" id="i6m6h">Personal Information</button>
			<div id="illyu">What are you looking for?</div>
		</form>
		<form method="get" id="iuoax" action="ServletDownloadResume">
			<button type="submit" id="i9ami">Download Resume</button>
		</form>
	</div>
	<%
	session.setAttribute("trial1", System.getenv("SERY_DB_URL"));
	session.setAttribute("trial2", System.getenv("SERY_DB_UNAME"));
	%>

	test Environment variables : ${trial1}<br>
	test Environment variables : ${trial2}
</body>
</html>





<%@page import="com.sunya.daos.DaoWebdatainfo"%>
<%@page import="com.sunya.managers.SessionManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres home</title>
<%
	String cssVersion;
	if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
		cssVersion = System.getProperty("SERY_CSS_VERSION");
	else
		cssVersion = System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="css/LoginPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="i5xm">

	<%
	SessionManager sm = new SessionManager(session);
	session.setAttribute(sm.LOGIN_FROMPAGE, "LoginPage.jsp");
	%>

	<%
	DaoWebdatainfo dao = new DaoWebdatainfo();
	if (session.getAttribute(sm.WEB_NOTE1) == null || ((String)session.getAttribute(sm.WEB_NOTE1)).isEmpty())
	{
		session.setAttribute(sm.WEB_NOTE1, dao.getWebinfo(sm.WEB_NOTE1));
	}
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

	<div id="ilt5zk">
		<div id="i99fq4">Important : </div>
		<div id="i1hupq">${WEB_NOTE1}</div>
	</div>
	<div id="i5dg">
		<form method="get" id="iq2wzk" action="WelcomePage.jsp">
			<button type="submit" id="ix3vku">Home</button>
		</form>
		<div id="iao9b">
			<form method="post" id="iipx" action="ServletLogin">
				<div id="i2sh">
					<label id="i9zl">Username<br /></label><input type="text"
						id="ikq8l" placeholder="Enter your username" name="username"
						value="${preTypedUsername}" /><label id="irep4"><br>${wrongUsername}<br /></label>
				</div>
				<div id="i1uz3">
					<label id="ianeo">Password<br /></label><input type="password"
						id="in4rx" placeholder="Enter your password" name="password" /><label
						id="igiel"><br>${wrongPassword}<br /></label>
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
		<div id="ip7596">
			Website's source code:  <a id="ir0137"
				href="https://github.com/sunya-c/yresWebProject">https://github.com/sunya-c/yresWebProject</a>
		</div>
	</div>

	<%
	session.setAttribute("trial1",
			!(System.getProperty("SERY_DB_URL") == null || System.getProperty("SERY_DB_URL").isBlank()));
	session.setAttribute("trial2",
			!(System.getProperty("SERY_DB_UNAME") == null || System.getProperty("SERY_DB_UNAME").isBlank()));
	%>

	test Environment variable1 : ${trial1}
	<br> test Environment variable2 : ${trial2}<br>
	version : v0.7
</body>
</html>





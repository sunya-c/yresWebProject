<%@page import="com.sunya.managers.SessionManager"%>
<%@page import="com.sunya.daos.DaoWebdatainfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres home</title>
<link rel="icon" href="resources/pics/Icon.png" type="image/png">
<%
	String cssVersion;
	if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
		cssVersion = System.getProperty("SERY_CSS_VERSION");
	else
		cssVersion = System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="resources/css/WelcomePageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="icnopa">

	<div id="iy0w9r">
		<div id="i4j7hg">Important : </div>
		<div id="i7vw6u">
			${WEB_NOTE1}<br />
		</div>
	</div>
	<div id="ij1hw">
		<form method="get" id="ikz7hf" action="Home">
			<button type="submit" id="ifffxi">Home</button>
		</form>
		<div id="iqzgf4">
			<form method="post" action="sLogout" id="i6yg8g">
				<label id="iplzo3">Welcome ${username},<br /></label><label
					id="iv9349">You're logged in<br /></label>
				<button type="submit" id="i8e392">Log out</button>
			</form>
		</div>
		<form method="get" action="feedback" id="ixkbtg">
			<button type="submit" id="i71s98">Give feedback / bug report</button>
		</form>
	</div>
	<div id="ihdmp2">
		<form method="get" id="ijk4nn" action="personalInformation">
			<button type="submit" id="io9tx">Personal Information</button>
			<div id="i6t9u">What are you looking for?</div>
		</form>
		<form method="get" id="i4s965" action="sDownloadResume">
			<button type="submit" id="itj0iw">Download Resume</button>
		</form>
		<div id="ig39tr">
			Website's source code:  <a
				href="https://github.com/sunya-c/yresWebProject" id="iff0ag">https://github.com/sunya-c/yresWebProject</a>
		</div>
	</div>

</body>
</html>
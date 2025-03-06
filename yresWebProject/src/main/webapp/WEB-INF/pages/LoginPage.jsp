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
<link rel="stylesheet" href="resources/css/LoginPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="i5xm">

	<div id="ilt5zk">
		<div id="i99fq4">Important : </div>
		<div id="i1hupq">${WEB_NOTE1}</div>
	</div>
	<div id="i5dg">
		<form method="get" id="iq2wzk" action="Home">
			<button type="submit" id="ix3vku">Home</button>
		</form>
		<div id="iao9b">
			<form method="post" id="iipx" action="sLogin">
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
			<form method="get" id="i8bga" action="createAccount">
				<button type="submit" id="i3xat">Create an account</button>
			</form>
		</div>
		<form method="get" action="feedback" id="i9ojw1">
			<button type="submit" id="i9l8gi">Give feedback / bug report</button>
		</form>
	</div>
	<div id="imxfh">
		<form method="get" id="i0ydh" action="personalInformation">
			<button type="submit" id="i6m6h">Personal Information</button>
			<div id="illyu">What are you looking for?</div>
		</form>
		<form method="get" id="iuoax" action="sDownloadResume">
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
	<%
	session.setAttribute("trial1",
			!(System.getenv("SERY_DB_URL") == null || System.getenv("SERY_DB_URL").isBlank()));
	session.setAttribute("trial2",
			!(System.getenv("SERY_DB_UNAME") == null || System.getenv("SERY_DB_UNAME").isBlank()));
	%>

	test Environment variable1 : ${trial1}
	<br> test Environment variable2 : ${trial2}<br>
	version : v1.02 Spring-based
</body>
</html>





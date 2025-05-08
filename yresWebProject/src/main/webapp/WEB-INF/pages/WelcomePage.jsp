<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres home</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/WelcomePageCss.css<%=cssVersion%>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="icnopa">
	<div id="iy0w9r">
		<div id="i4j7hg">Important : </div>
		<div id="i7vw6u">${sessionWeb.webNote1}</div>
	</div>
	<div id="ij1hw">
		<form method="get" id="ikz7hf" action="/Home">
			<button type="submit" id="ifffxi">Home</button>
		</form>
		<div id="iqzgf4">
			<form method="post" action="/sLogout" id="i6yg8g">
				<label id="iplzo3">Welcome <span id="iy2ne2">${sessionLogin.username}</span>,<br /></label><label
					id="iv9349">You're logged in<br /></label>
				<button type="submit" id="i8e392">Log out</button>
			</form>
		</div>
		<form method="get" action="/feedback" id="ixkbtg">
			<button type="submit" id="i71s98">Give feedback / bug report</button>
		</form>
	</div>
	<div id="i9w8ag">
		<div id="i9lqo2">
			<div id="iybpdh">What are you looking for?</div>
		</div>
		<form method="get" action="/personalInformation" id="i7xlhl">
			<button type="submit" id="iitwbc">Personal Information</button>
		</form>
		<form method="get" action="/sDownloadResume" id="i3po9k">
			<button type="submit" id="ib81jz">Download Resume</button>
			<div id="idkq7h">
				Last update: <span id="i9b28cj">${sessionWeb.resumeDate}</span>
			</div>
		</form>
		<form method="get" action="/webHistory" id="i7yq1c">
			<button type="submit" id="i5fj2f">About this website</button>
		</form>
		<form method="get" action="/restApi" id="iwwaxj">
			<button type="submit" id="idu7vx">Try REST api</button>
		</form>
		<div id="iiawfa">
			Website's source code:  <a
				href="https://github.com/sunya-c/yresWebProject" id="inqnnz">https://github.com/sunya-c/yresWebProject</a>
		</div>
		<form method="get" action="/accountInfo" id="ithezd">
			<button type="submit" id="ifuk9u">Account info</button>
		</form>
		<form method="get" action="/adminPanel" id="i2vuok">
			<button type="submit" id="ia0qth">Admin panel</button>
			<div id="i32dz7">${dataWelcome.adminPanelButtonErr}</div>
		</form>
	</div>
</body>
</html>
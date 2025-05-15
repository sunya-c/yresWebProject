<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres - Account info</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/AccountInfoPageCss.css<%=cssVersion%>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
</head>
<body id="i96scr-2-2">
	<div id="ie2uro-2-2">
		<form method="get" action="/Home" id="i9m7kf-2-2">
			<button type="submit" id="irgx3m-2-2">Home</button>
		</form>
		<div id="iyfwz7-2-2">
			<form method="post" action="/sLogout" id="ii3nc3-2-2">
				<label id="i1l84x-2-2">Welcome <span id="ifv1ph-2-2">${sessionLogin.username}</span>,<br /></label><label
					id="ilinpi-2-2">You're logged in<br /></label>
				<button type="submit" id="iwuy1p-2-2">Log out</button>
			</form>
		</div>
		<form method="get" action="/feedback" id="igi9b8-2-2">
			<button type="submit" id="i0hicj-3-2">Give feedback / bug
				report</button>
		</form>
	</div>
	<div id="i1xzpl-2-2">
		<div id="infkrv-2-2">Account Management</div>
		<div id="i1uy3zf-2">This page allows you to edit your action
			information.</div>
		<div id="i00rjm-2-2">
			<form method="post" action="/accountInfo/sChangePassword"
				id="i0g592g-2">
				<label id="iizqjb8-2">Change password :</label><br />
				<input type="password" name="currentPassword"
					placeholder="Current password" id="iahdn4h-2" /><br />
				<label id="ikeo7ur-2">${dataAccountInfo.currentPasswordErr}</label><br />
				<input type="password" name="password1"
					placeholder="New password" id="ix1mpeu-2" /><br />
				<label id="iga05mf-2">${dataAccountInfo.password1Err}</label><br />
				<input type="password" name="password2"
					placeholder="Confirm new password" id="i52w35i-2" /><br />
				<label id="ik0b94j-2">${dataAccountInfo.password2Err}</label><br />
				<button type="submit" id="igi5nyu-2">Confirm</button>
			</form>
		</div>
	</div>
</body>
</html>
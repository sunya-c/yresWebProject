<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Yres - create account</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/CreateAccountPageCss.css<%=cssVersion%>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="ihjsk6">
	<div id="i1h2wx">
		<form method="get" action="/Home" id="idtzab">
			<button type="submit" id="i1ckeh">Home</button>
		</form>
		<form method="get" action="/feedback" id="in9x4b">
			<button type="submit" id="ikzg0o">Give feedback / bug report</button>
		</form>
	</div>
	<div id="ia2ryx">
		<div id="i82jpw">Creating an account</div>
		<div id="itqzxp">
			<form method="post" action="/sCreateAccount" id="ijtey8">
				<div id="ip4cc6">
					<label id="iyd4c4">Username<br /></label><input type="text"
						placeholder="Create your username" name="username"
						value="${dataCreateAccount.usernamePreTyped}" id="ir4udv" /><label
						id="iy009g"><br>${dataCreateAccount.usernameErr}<br /></label>
				</div>
				<div id="i69h82">
					<label id="inwehq">Password<br /></label><input type="password"
						placeholder="Create your password" name="password1" id="icug3i" /><label
						id="iq5yjh"><br>${dataCreateAccount.password1Err}<br /></label>
				</div>
				<div id="ir3u0x">
					<label id="ibtkzi">Confirm Password<br /></label><input
						type="password" placeholder="Repeat your password"
						name="password2" id="i5yupc" /><label id="ib1ixt"><br>${dataCreateAccount.password2Err}<br /></label>
				</div>
				<button type="submit" id="ih73op">Create account</button>
			</form>
		</div>
	</div>
</body>
</html>
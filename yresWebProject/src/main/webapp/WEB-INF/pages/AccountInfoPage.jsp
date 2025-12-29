<!--<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres - Account info</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<!--<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>-->
<!--<link rel="stylesheet"
	href="/resources/css/AccountInfoPageCss.css<%=cssVersion%>" />-->
<link rel="stylesheet"
	href="/resources/css/AccountInfoPageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Roboto:regular,italic&display=swap"
	rel="stylesheet">
<link
	href="https://fonts.googleapis.com/css?family=Inter:regular,italic&display=swap"
	rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Manrope:wght@200..800&family=YouTube+Sans:wght@300..900&display=swap" rel="stylesheet">
<script src="/resources/javascript/NavBar.js" type="module"></script>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<div>
				<span class="bold">Important !!! : </span>
				<span>${sessionWeb.webNote1}</span>
			</div>
		</div>
		<div id="preventActionScreen"></div>
		<div id="leftContent">
			<div id="toggleMenu">Menu</div>
			<div id="contentLeftMenu">
				<nav>
					<ul>
						<c:if test="${userAuth.authenticated == true}">
							<li><span id="welcomeMessage">Welcome, ${userAuth.usernameEscaped}</span></li>
						</c:if>
						<c:if test="${userAuth.authenticated == false}">
							<li><a id="welcomeMessage" href="/login">Login</a></li>
						</c:if>
						<li><a href="/Home">Home</a></li>
						<li><a href="/accountInfo">My account</a></li>
						<li><a href="/feedback">Give feedback / bug report</a></li>
						<c:if test="${userAuth.authenticated == true}">
							<li><a href="/sLogout">Log out</a></li>
						</c:if>
					</ul>
					<%-- <c:if test="${userAuth.isAdmin == true}">
						<ul>
							<li><span class="fontSize bold">Admin-only:</span></li>
							<li><a href="/adminPanel">Admin Panel</a></li>
						</ul>
					</c:if> --%>
				</nav>
			</div>
		</div>
		<div id="mainContent">
			<div id="contentPane">
				<div id="contentPageDescription" class="content">
					<h1 class="contentLabel">Account Management</h1>
					<div class="text text-color-dim">This page allows you to edit your account information.</div>
				</div>
				<div id="contentChangePassword" class="content">
					<h1 class="contentLabel">Change Password</h1>
					<form method="post" action="/accountInfo/sChangePassword">
						<div class="inputWrapper">
							<label for="password">Current password</label>
							<input id="password" type="password" name="currentPassword"
								placeholder="Current password"/>
							<span class="errMessage">${dataAccountInfo.currentPasswordErr}</span>
						</div>
						<div class="inputWrapper">
							<label for="password1">New password</label>
							<input id="password1" type="password" name="password1"
								placeholder="New password"/>
							<span class="errMessage">${dataAccountInfo.password1Err}</span>
						</div>
						<div class="inputWrapper">
							<label for="password2">Confirm new password</label>
							<input id="password2" type="password" name="password2"
								placeholder="Repeat your new password"/>
							<span class="errMessage">${dataAccountInfo.password2Err}</span>
						</div>
						<button type="submit" class="formButton">Confirm</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					</form>
					<div class="errMessageWrapper">
						<div>
							<span class="errMessage mainErr">test main err</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
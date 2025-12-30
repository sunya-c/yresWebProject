<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
						<li><a href="/home">Home</a></li>
						<li><a href="/accountInfo">My account</a></li>
						<li><a href="/feedback">Give feedback / bug report</a></li>
						<c:if test="${userAuth.authenticated == true}">
							<li>
								<form id="logoutForm" action="/sLogout" method="post">
									<button type="submit">Log out</button>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								</form>
							</li>
						</c:if>
					</ul>
					<c:if test="${userAuth.admin == true}">
						<ul>
							<li><span class="fontSize bold">Admin-only:</span></li>
							<li><a href="/adminPanel">Admin Panel</a></li>
						</ul>
					</c:if>
				</nav>
			</div>
		</div>
		<div id="mainContent">
			<div id="contentPane">
				<div id="contentCreateAccount" class="content">
					<h1 class="contentLabel">Creating an account</h1>
					<form method="post" action="/sCreateAccount">
						<div class="inputWrapper">
							<label for="username">Username</label>
							<input id="username" type="text"
								placeholder="Create your username" name="username"
								value="${dataCreateAccount.usernamePreTyped}">
							<span class="errMessage">${dataCreateAccount.usernameErr}</span>
						</div>
						<div class="inputWrapper">
							<label for="password1">Password</label>
							<input id="password1" type="password" placeholder="Create your password" name="password1">
							<span class="errMessage">${dataCreateAccount.password1Err}</span>
						</div>
						<div class="inputWrapper">
							<label for="password2">Confirm Password</label>
							<input id="password2" type="password" placeholder="Repeat your password" name="password2">
							<span class="errMessage">${dataCreateAccount.password2Err}</span>
						</div>
						<button class="formButton" type="submit">Create account</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					</form>
					<div class="errMessageWrapper">
						<div>
							<span class="errMessage mainErr"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
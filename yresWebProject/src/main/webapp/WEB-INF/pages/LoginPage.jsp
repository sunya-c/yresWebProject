<!-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres home</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<!-- <%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%> -->
<!-- <link rel="stylesheet" href="/resources/css/LoginPageCss.css<%= cssVersion %>" /> -->
<link rel="stylesheet" href="/resources/css/LoginPageCss.css" />
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
					<%--<c:if test="${userAuth.isAdmin == true}">
						<ul>
							<li><span class="fontSize bold">Admin-only:</span></li>
							<li><a href="/adminPanel">Admin Panel</a></li>
						</ul>
					</c:if>--%>
				</nav>
			</div>
		</div>
		<div id="mainContent">
			<div id="contentPane">
				<div id="contentLogin" class="content">
					<h1 class="contentLabel">Login</h1>
					<form method="post" action="/sLogin">
						<div class="inputWrapper">
							<label for="username">Username</label>
							<input id="username" type="text" placeholder="Enter your username" name="username" value="${sessionLogin.usernamePreTyped}"/>
							<span class="errMessage">${sessionLogin.usernameErr}</span>
						</div>
						<div class="inputWrapper">
							<label for="password">Password</label>
							<input id="password" type="password" placeholder="Enter your password" name="password">
							<span class="errMessage">${sessionLogin.passwordErr}</span>
						</div>
						<button class="formButton" type="submit">Log in</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<div class="errMessageWrapper">
						<div>
							<span class="errMessage mainErr">Main error</span>
						</div>
					</div>
					<a href="/createAccount">Create an account</a>
				</div>





				<!-- <div id="ileipf">
					<span id="illyu">What are you looking for?</span>
				</div>
				<form method="get" action="/personalInformation" id="i90xc5">
					<button type="submit" id="i4zxdj">Personal Information</button>
				</form>
				<form method="get" id="iuoax" action="/sDownloadResume">
					<button type="submit" id="i9ami">Download Resume</button>
					<div id="if14we">
						Last update: <span id="i9n2n3k">${sessionWeb.resumeDate}</span>
					</div>
				</form>
				<form method="get" action="/webHistory" id="i8arqt">
					<button type="submit" id="ia7xhn">About this website</button>
				</form>
				<form method="get" action="/restApi" id="i8t8wg">
					<button type="submit" id="ijuhm8">Try REST api</button>
				</form>
				<div id="ip7596">
					Website's source code:  <a id="ir0137"
						href="https://github.com/sunya-c/yresWebProject">https://github.com/sunya-c/yresWebProject</a>
				</div> -->
			</div>
		</div>
	</div>
	<!-- test Environment variable(getenv) : ${trial1}
	<br> test Environment variable(getProp) : ${trial2}
	<br> version : ${sessionWeb.webVersion} -->
</body>
</html>





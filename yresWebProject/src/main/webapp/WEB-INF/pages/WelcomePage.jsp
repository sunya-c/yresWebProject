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
<!-- <link rel="stylesheet"
	href="/resources/css/WelcomePageCss.css<%=cssVersion%>" /> -->
<link rel="stylesheet" href="/resources/css/WelcomePageCss.css" />
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
					</c:if>--%>
				</nav>
			</div>
		</div>
		<div id="mainContent">
			<div id="contentPane">
				<div id="contentMenu" class="content">
					<h1 class="contentLabel">What are you looking for?</h1>
					<div class="gridContent">
						<div class="linkWrapper">
							<a href="/personalInformation"><span>Online Profile</span></a>
						</div>
						<div class="linkWrapper">
							<a href="/sDownloadResume"><span>Download Resume (pdf)</span></a>
							<div id="resumeLastUpdateWrapper">
								<span class="text-color-dim">Last update:&nbsp;</span><span id="resumeLastUpdate">${sessionWeb.resumeDate}</span>
							</div>
						</div>
						<div class="linkWrapper">
							<a href="/webHistory"><span>About this website</span></a>
						</div>
						<div class="linkWrapper">
							<a href="/restApi"><span>Try REST api</span></a>
						</div>
						<div class="linkWrapper">
							<a href="https://github.com/sunya-c/yresWebProject">
								<span>This Website's source code</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
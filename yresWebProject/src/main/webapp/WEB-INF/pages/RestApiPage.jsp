<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres - REST api</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/RestApiPageCss.css<%=cssVersion%>" />
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
			<label id="toggleMenu" for="toggleMenuCheckbox">
				Menu
				<input id="toggleMenuCheckbox" type="checkbox" style="display: none;">
			</label>
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

				<div id="contentPageDescription" class="content">
					<h1 class="contentLabel">REST API built on Spring</h1>
					<div class="text text-color-dim">
						You can try REST by adding '<span class="bold">/api/rest/available_path</span>' to the end of this website's domain name where you replace '<span class="bold">available_path</span>' with the available options provided below. For example, '<span class="bold">${dataRestApi.domainName}/api/rest/persinfo</span>' will return my personal information. You would expect the results to be in either .xml or .json format. Feel free to try any URL below with '<span class="bold">Try me!</span>' form.
					</div>
				</div>
				<div id="contentTryMe" class="content">
					<form method="get" action="/restApi/sSendRequest">
						<h1 class="contentLabel">Try me!</h1>
						<div class="inputWrapper">
							<label for="restMethod">HTTP method</label>
							<select id="restMethod" name="restMethod">
								<option value="get">GET</option>
								<option value="post">POST</option>
								<option value="put">PUT</option>
								<option value="delete">DELETE</option>
							</select>
						</div>
						<div class="inputWrapper">
							<label for="restUrl">URL</label>
							<textarea id="restUrl" placeholder="Paste the URL here!" name="restUrl"></textarea>
						</div>
						<button class="formButton" type="submit">Send request</button>
					</form>
					<div class="errMessageWrapper">
						<div>
							<span class="errMessage mainErr"></span>
						</div>
					</div>
				</div>

				<div class="content firstContent">
					<div class="text bold">
						GET<span class="text space"> </span>${dataRestApi.domainName}/api/rest/user
					</div>
					<br>
					<div class="text-14 text-color-dim">
						This URL returns all accounts' detail, excluding password.
					</div>
				</div>

				<div class="content">
					<div class="text bold">
						GET<span class="text space"> </span>${dataRestApi.domainName}/api/rest/user/{username}
					</div>
					<br>
					<div class="text-14 text-color-dim">
						This URL returns the specified account's
						detail, excluding password. Replace '{username}' with the desired
						username.
					</div>
				</div>
				<div class="content">
					<div class="text bold">
						GET<span class="text space"> </span>${dataRestApi.domainName}/api/rest/feedback/{refNumber}
					</div>
					<br>
					<div class="text-14 text-color-dim">
						This URL returns the feedback detail of the
						specified reference number. Replace '{refNumber}' with the feedback
						reference number.
					</div>
				</div>
				<div class="content">
					<div class="text bold">
						GET<span class="text space"> </span>${dataRestApi.domainName}/api/rest/persinfo
					</div>
					<br>
					<div class="text-14 text-color-dim">
						This URL returns my personal information,
						including everything in personal information page.
					</div>
				</div>
				<div class="content">
					<div class="text bold">
						GET<span class="text space"> </span>${dataRestApi.domainName}/api/rest/persinfo/fullversion
					</div>
					<br>
					<div class="text-14 text-color-dim">
						This URL returns my personal information,
						including everything in personal information page and other
						additional detail.
					</div>
				</div>
				<div class="content">
					<div class="text bold">
						GET<span class="text space"> </span>${dataRestApi.domainName}/api/rest/ipblacklist
					</div>
					<br>
					<div class="text-14 text-color-dim">
						This URL returns all spam BOTs' IP addresses
						that I've been collecting since this web application version v0.71.</div>
				</div>
				<div class="content">
					<div class="text bold">
						GET<span class="text space"> </span>${dataRestApi.domainName}/api/rest/ipblacklist/{ipAddress}
					</div>
					<br>
					<div class="text-14 text-color-dim">
						This URL returns 'true' if the specified IP
						address exists in the blacklist database, otherwise returns 'false'.
						Replace '{ipAddress}' with the IP address you'd like to check.
					</div>
				</div>

				<div class="content">
					<div class="text-14 margin-auto-block">
						More to be updated soon.
					</div>
				</div>

			</div>
		</div>
	</div>
</body>
</html>
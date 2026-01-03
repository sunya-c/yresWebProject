<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres - feedback</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/FeedbackPageCss.css<%=cssVersion%>" />
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
				<div id="contentFeedback" class="content">
					<h1 class="contentLabel">Give feedback or report bugs</h1>
					<c:if test="${dataFeedback.submittedFeedback == false}">
						<form method="post" action="/sFeedback">
							<div class="inputWrapper">
								<label for="feedbackTitle">Title</label>
								<input id="feedbackTitle" type="text"
									placeholder="Give a meaningful name for this issue/feedback"
									name="feedbackTitle" value="${dataFeedback.titlePreTyped}">
								<span class="errMessage">${dataFeedback.titleErr}</span>
							</div>
							<div class="inputWrapper">
								<label for="feedbackDetail">Detail</label>
								<textarea id="feedbackDetail"
									placeholder="Give an explanation about the issue or leave your feedback here"
									name="feedbackDetail">${dataFeedback.detailPreTyped}</textarea>
								<span class="errMessage">${dataFeedback.detailErr}</span>
							</div>
							<div class="inputWrapper">
								<label for="feedbackErrorMessage">Error message (optional)</label>
								<textarea id="feedbackErrorMessage"
									placeholder="In case of encountering an error, put the error message here"
									name="feedbackErrorMessage">${param.preTypedFeedbackErrorMessage}</textarea>
								<span class="errMessage">${dataFeedback.errorMessageErr}</span>
							</div>
							<button class="formButton" type="submit">Submit</button>
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						</form>
						<div class="errMessageWrapper">
							<div>
								<span class="errMessage mainErr"></span>
							</div>
						</div>
					</c:if>
					<c:if test="${dataFeedback.submittedFeedback == true}">
						<div class="outputWrapper">
							<span>Your issue has been submitted.</span>
						</div>
						<div class="outputWrapper">
							<span class="text-color-dim">Reference number : </span><span id="refNumber">${dataFeedback.refNumber}</span>
						</div>
						<a class="linkButton" href="/home">Go to Home page</a>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres - admin panel</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/AdminPanelPageCss.css<%=cssVersion%>" />
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
					<h1 class="contentLabel">&gt;&gt;&gt; Admin panel &lt;&lt;&lt;</h1>
					<div class="text text-color-dim">This page provides admin tools to configure this web application.</div>
				</div>
				<div id="contentWebAction" class="content">
					<h1 class="contentLabel">Web actions</h1>
					<form method="post" action="/adminPanel/sAction">
						<select name="action">
							<option value="none">Select an action</option>
							<option value="saveBotstodatabase">Save bots to DB</option>
							<option value="deleteBotsinusageinfo">Clear usageinfo</option>
						</select>
						<button class="formButton" type="submit">Confirm action</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<div id="contentWebAction-result" class="text">${dataAdminPanel.actionResults}</div>
				</div>

				<div id="contentUploadResume" class="content">
					<h1 class="contentLabel">Upload Resume</h1>
					<form method="post" action="/adminPanel/sUploadResume" enctype="multipart/form-data">
						<div class="inputWrapper">
							<label for="contentUploadResume-resumeDate">Resume version (date)</label>
							<input id="contentUploadResume-resumeDate" type="text" name="resumeDate" placeholder="Resume version, eg. 20250515" />
						</div>
						<label class="inputFile">
							<input type="file" name="resumeFile"/>
						</label>
						<button class="formButton" type="submit" value="Upload File">Upload</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<div class="errMessageWrapper">
						<div>
							<span class="errMessage mainErr">${dataAdminPanel.uploadResumeErr}</span>
						</div>
					</div>
				</div>
				<div id="contentResumeVersion" class="content">
					<h1 class="contentLabel">Resume Version (for download)</h1>
					<form method="post" action="/adminPanel/sSetResumeVersion">
						<div class="inputWrapper">
							<label for="contentResumeVersion-resumeVersion">Resume version</label>
							<select id="contentResumeVersion-resumeVersion" name="resumeName">
								<option value="none">Select a resume</option>
								<c:forEach items="${dataAdminPanel.resumeModels}" var="model">
									<option value="${model.filename}">${model.filename}</option>
								</c:forEach>
							</select>
						</div>
						<button class="formButton" type="submit">Set version</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<div class="errMessageWrapper">
						<div>
							<span class="errMessage mainErr">${dataAdminPanel.resumeVersionErr}</span>
						</div>
					</div>
				</div>
				<div id="contentAnnouncement" class="content">
					<h1 class="contentLabel">Web Announcement Message</h1>
					<form method="post" action="/adminPanel/sSetAnnouncement">
						<textarea placeholder="Enter the announcement message" name="announcementMessage"></textarea>
						<button type="submit" class="formButton">Set announcement</button>
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
					<div class="errMessageWrapper">
						<div>
							<span class="errMessage mainErr">${dataAdminPanel.announcementErr}</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
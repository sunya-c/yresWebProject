<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres - personal information</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="/resources/css/PersonalInformationPageCss.css<%=cssVersion%>" />
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
				<div id="contentPersonalInformation" class="content">
					<h1 class="contentLabel">Personal Information</h1>
					<div class="outputWrapper">
							<span class="label">Name :</span>
							<span class="output">${dataPersInfo.firstname} ${dataPersInfo.lastname}</span>
							
							<span class="label">Date of Birth :</span>
							<span class="output">${dataPersInfo.dateOfBirth}</span>

							<span class="label">Age :</span>
							<span class="output">${dataPersInfo.age}</span>

							<span class="label">Gender :</span>
							<span class="output">${dataPersInfo.gender}</span>

							<span class="label">Nationality :</span>
							<span class="output">${dataPersInfo.nationality}</span>

							<span class="label">Driving license :</span>
							<span class="output">${dataPersInfo.drivingLicense}</span>

							<span class="label">Engineering license :</span>
							<span class="output">${dataPersInfo.engineeringLicense}</span>
					</div>
					<div class="outputWrapper">
						<span class="label">Languages :</span>
						<div class="output">
							<!-- Language -->
							<!-- <%
							request.setAttribute("counter", 0);
							%> -->
							<c:forEach items="${dataPersInfo.listLanguage}" var="language">
								<span>${language.language}<br>
								(${language.proficiency})
								</span>
								<c:if test="${counter != 1}">
									<!-- , -->
									<br>
									<br>
								</c:if>
								<!-- <%
								int count = (int)request.getAttribute("counter");
								count += 1;
								request.setAttribute("counter", count);
								%> -->
							</c:forEach>
							<!-- <%
							request.setAttribute("counter", 0);
							%> -->
						</div>
					</div>
					<div class="outputWrapper">
						<span class="label">Programming languages :</span>
						<div class="output">
							<!-- Programming Language -->
							<c:forEach items="${dataPersInfo.listProgrammingLanguage}"
								var="programmingLanguage">
								<span>${programmingLanguage.language}<br>
								(${programmingLanguage.proficiency})</span>
								<c:if test="${counter != 4}">
									<!-- , -->
									<br>
									<br>
								</c:if>
								<!-- <%
								int count = (int)request.getAttribute("counter");
								count += 1;
								request.setAttribute("counter", count);
								%> -->
							</c:forEach>
							<!-- <%
							request.setAttribute("counter", 0);
							%> -->
						</div>
					</div>
					<div class="outputWrapper">
							<span class="label">Phone number :</span>
							<span class="output">${dataPersInfo.phoneNumber}</span>

							<span class="label">Email :</span>
							<span class="output">${dataPersInfo.email}</span>

							<span class="label">Line ID :</span>
							<span class="output">${dataPersInfo.lineId}</span>
					</div>
				</div>
				<div class="content">
					<h1 class="contentLabel">Coding Experience</h1>
					<div class="outputWrapper">
						
					</div>
				</div>
				<div id="contentCertificate" class="content">
					<h1 class="contentLabel">Certificates</h1>
					<img class="certificateImage" src="/resources/pics/Certificate1.png">
					<img class="certificateImage" src="/resources/pics/Certificate2.png">
				</div>
			</div>
		</div>
	</div>
</body>
</html>
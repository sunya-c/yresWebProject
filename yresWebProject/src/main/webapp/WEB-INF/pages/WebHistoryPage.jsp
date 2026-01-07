<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Yres - web history</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/WebHistoryPageCss.css<%=cssVersion%>" />
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
<body id="ibrvou">
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
					<h1 class="contentLabel">About '<span class="bold">y-res.com</span>'</h1>
					<p class="text text-color-dim">
						<span class="bold">y-res.com</span> was created to satisfy my own
						curiosity about different topics in Java. I use this website as a
						playground for trying new approaches as I continue to learn new
						things which indirectly reflects my learning path when looking at its
						history. The best way to look at a project's history is to explore
						that project's Git repository. Here in this page, I simplified the
						timeline of <span class="bold">y-res.com</span> into a digestible
						format. You might find this informative if you want to know how much
						I know about Java.
					</p>
					<p class="text text-color-dim">
						*The timeline below might not
						contain all versions, it shows only the important versions for
						simplicity. If you'd like to see every version available, please
						visit my Github.
					</p>
				</div>
				
				<div id="contentWebHistory" class="content">
					<h1 class="contentLabel">Timeline</h1>
					<div id="webHistoryWrapper">
						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 0.1</span>
								<span class="text-13 text-color-dim">13 Jan 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: dc763a8 (tag: v0.1)
									<span class="bold">Getting started with Java servlets</span>
								</span>
								<span class="body">
									The first version in Git. This is servlets-based, and it was deployed on AWS Elastic Beanstalk. Nothing fancy, just me starting to learn about Git, Java servlets, and deployment on AWS.
								</span>
							</span>
						</div>
						
						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 0.3</span>
								<span class="text-13 text-color-dim">16 Jan 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 8b3e352 (tag: v0.3)
									<span class="bold">Getting familiar</span>
								</span>
								<span class="body">
									Fixing AWS issue where environment variables were inaccessible only on AWS server.<br>
									Adding a class 'SessionManager' to manage data in the session so that we don't have to modify the session directly, making it less error-prone.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 0.4</span>
								<span class="text-13 text-color-dim">19 Jan 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 68ebd19 (tag: v0.4)
									<span class="bold">Dealing with AWS issue</span>
								</span>
								<span class="body">
									Large environment variable values have been moved to the database as AWS limits the size of environment variables.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 0.5</span>
								<span class="text-13 text-color-dim">19 Jan 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 22e0375 (tag: v0.5)
									<span class="bold">UTC+7 is the standard</span>
								</span>
								<span class="body">
									Many features relied on time which caused inconsistency when testing locally and on AWS server. To solve this issue, server time zone detection was added. Any time zone is converted to UTC+7 before further operations for consistency purpose.<br>
									Adding website usage tracking. It's a simple feature counting number of times a page has been visited by clients.
								</span>
							</span>
						</div>
						
						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 0.71</span>
								<span class="text-13 text-color-dim">24 Jan 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: df3880e (tag: v0.71)
									<span class="bold">Noticing something weird</span>
								</span>
								<span class="body">
									I saw some abnormal behavior from the data obtained from usage tracking. I presumed those could be something inhuman. In this version, I added an operation to collect IP addresses of the incoming requests for further investigation.
								</span>
							</span>
						</div>
						
						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 0.8</span>
								<span class="text-13 text-color-dim">25 Jan 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 179bb6b (tag: v0.8)
									<span class="bold">Filtering BOTs</span>
								</span>
								<span class="body">
									At this point, I was quite certain that those requests are spam bot requests. So, I added a preliminary process to filter out those inhuman requests before it reaches the logical part of the application. According to the information collected, most requests are from outside of Thailand, and since the visitors of this website are expected to be from Thailand, I redirected non-Thailand requests to the error page. For 0.001% possibility where a real human might access this website from other countries, I still allowed a level of compromise by letting those minority of real human to send a feedback ticket, so that I can manually white-list them case by case.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 1.0</span>
								<span class="text-13 text-color-dim">19 Feb 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 03486d9 (tag: v1.0)
									<span class="bold">Converting to Spring</span>
								</span>
								<span class="body">
									Turning this project from traditional servlets into Spring. At this point, I was learning Spring and Spring MVC. Starting from the most fundamental part like servlets helped me understand Spring from its core which was very beneficial in the long term. Spring is like a two-sided coin, it has a level of abstraction which on one side brings a huge quality-of-life upgrade, while on the other side we will just forget it someday if we don't really understand how it works under the hood.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 1.3</span>
								<span class="text-13 text-color-dim">17 Mar 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 7624954 (tag: v1.3)
									<span class="bold">Converting to Spring Boot</span>
								</span>
								<span class="body">
									Turning this project from Spring to Spring Boot. I was trying to get used to Spring Boot, and the best way to do it is to actually do it. Spring Boot is like a new era of Spring. It upgrades quality of life even further from what normal Spring does.<br>
									As of this version, this project has had its own embedded Tomcat server by the power of Spring Boot. Unfortunately, JSP somehow just didn't work with JAR packaging, which is the default packaging for Spring Boot project with embedded Tomcat. The workaround is to package it into WAR instead of JAR. In other words, this project has an embedded Tomcat within a WAR file, so no need for external Tomcat.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 1.4</span>
								<span class="text-13 text-color-dim">23 Mar 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 65693a2 (tag: v1.4)
									<span class="bold">Multi-threading</span>
								</span>
								<span class="body">
									I was interested in multi-threading, so I dove deep into this topic and found out how severe race condition was. As Spring is based on multi-threading and I had been building this project without race conditions in mind since the beginning, I went back and overhauled the entire code in this project so that it had a strong structure against race conditions.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 1.6</span>
								<span class="text-13 text-color-dim">21 Apr 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 12089d8 (tag: v1.6)
									<span class="bold">Spring REST</span>
								</span>
								<span class="body">
									Adding REST API to this website. I'd been learning REST for a while and wanted to make use of it. So, I added a few REST API paths that might come in handy when I want to quickly see some information. So now, instead of going to MySQL workbench, I can just open a browser and take a sneak peek of the information in the database. And guess what!!! Even you can try it out!!!! Just go to Home page and click 'Try REST api' button!!!
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 1.61</span>
								<span class="text-13 text-color-dim">23 Apr 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 12e9562 (tag: v1.61)
									<span class="bold">My first Domain Name, 'y-res.com'</span>
								</span>
								<span class="body">
									I've bought a new Domain Name!!! I'd been using the Elastic Beanstalk's default endpoint since the beginning. I just got to the point that I wanted to expand my own knowledge to cover security connection (https / SSL certificate) which AWS didn't allow me to do so with the default Elastic Beanstalk URL. As of this version, this web application will support 'https'. This is a good start to Spring Security which I'm planning to learn soon.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 1.62</span>
								<span class="text-13 text-color-dim">29 Apr 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: 9e304a9 (tag: v1.62)
									<span class="bold">BOT filtering just got redesigned</span>
								</span>
								<span class="body">
									Optimizing BOT filter. Our web application has been overwhelmed by random spam requests. This chunk of spam requests has significantly grown to the point that the free-tier limit of a library FilterBot implementing was reached. I added various layers of BOT detection before utilizing the library. For example, implementing our own caching system and finding the IP in the cache first, then leveraging our own IP blacklist, and saving the library for last if nothing earlier matches.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 1.71</span>
								<span class="text-13 text-color-dim">07 May 2025</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: d027dc6 (tag: v1.71)
									<span class="bold">AWS to THAI DATA CLOUD</span>
								</span>
								<span class="body">
									Moving from AWS to THAI DATA CLOUD. As this web application continues to grow, some of the new features made me go beyond AWS's free-tier limit. I had to find a new server which is more affordable than AWS. THAI DATA CLOUD is the one I go for as it is significantly cheaper and offering a fixed monthly cost. Even though AWS provides many services out of the box which is very convenient for me to learn the flow of data within the server side, the cost was just too high for a learning project like this web application.
								</span>
							</span>
						</div>

						<div class="webHistory">
							<span class="version">
								<span class="text-14 bold">Version 2.0</span>
								<span class="text-13 text-color-dim">07 Jan 2026</span>
							</span>
							<span class="arrow">---&gt;</span>
							<span class="description">
								<span class="title">
									commit hash: a55c017 (tag: v2.0)
									<span class="bold">New UI</span>
								</span>
								<span class="body">
									For the last 6 months, I've been working on a freelance webapp project. Fortunately, I had an opportunity to learn some new cool skills, which are frontend techs like HTML, CSS, and JavaScript. As the existing frontend code was quite messy, since it was generated by a HTML/CSS generator, I find this a perfect time to redesign the frontend code to build a strong and maintainable code for the future me. This is just the first step though. I also learned a lot of backend things from the mentioned freelance webapp project, so the backend overhaul is on the way.
								</span>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<%@page import="com.sunya.SessionManager"%>
<%@page import="com.sunya.FromServlet"%>
<%@page import="com.sunya.PrintError"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Personal Information</title>
<%
	String cssVersion;
	if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
		cssVersion = System.getProperty("SERY_CSS_VERSION");
	else
		cssVersion = System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="css/PersonalInformationPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="io13me">
	<%
	SessionManager sm = new SessionManager(session);
	session.setAttribute(sm.LOGIN_FROMPAGE, "PersonalInformationPage.jsp");
	%>

	<!-- Prevent the back button -->
	<%
	response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // http 1.1
	response.setHeader("Pragma", "no-cache"); // http 1.0
	response.setHeader("Expires", "0"); // Proxies
	%>

	<!-- Forcing to access this page by Servlet -->
	<!-- Check if this page is called by its servlet -->
	<%
	FromServlet fs = new FromServlet(application, "ServletPersonalInformation", session);

	if (fs.isFromServlet())
	{
		System.out.println("fromServlet and context matched.");
		session.setAttribute("fromServlet", null);
	}
	else
	{
		String errText = "fromServlet mismatch";
		PrintError.println(errText);
		System.err.println("fromServlet attribute : " + fs.getFromServletAttribute());
		System.err.println("ServletContext        : " + fs.getFullServletName());
		session.setAttribute("fromServlet", null);

		RequestDispatcher rd = request.getRequestDispatcher("ServletPersonalInformation");
		rd.forward(request, response);
	}
	%>

	<!-- Page content -->
	<div id="i1lfxj">
		<form method="get" id="iop9qh" action="WelcomePage.jsp">
			<button type="submit" id="iqm8le">Home</button>
		</form>
		<c:if test="${loggedIn == null || loggedIn != true}">
			<div id="inxnwh">
				<form method="post" action="ServletLogin" id="ivnm6i">
					<div id="isl8bj">
						<label id="i94q24">Username<br /></label><input type="text"
							placeholder="Enter your username" name="username"
							value="${preTypeUsername}" id="i7vzyb" /><label id="i1bciw"><br>${wrongUsername}<br /></label>
					</div>
					<div id="i7ri3j">
						<label id="i10bjd">Password<br /></label><input type="password"
							placeholder="Enter your password" name="password" id="i1tpzs" /><label
							id="ibl3w1"><br>${wrongPassword}<br /></label>
					</div>
					<button type="submit" id="i2ckyv">Log in</button>
				</form>
				<form method="get" action="CreateAccountPage.jsp" id="iwbnu1">
					<button type="submit" id="im72si">Create an account</button>
				</form>
			</div>
		</c:if>
		<c:if test="${loggedIn == true}">
			<div id="ic3x5g">
				<form method="post" action="ServletLogout" id="iuj89x">
					<label id="iv62jr">Welcome ${username},<br /></label><label
						id="irlj8d">You're logged in<br /></label>
					<button type="submit" id="i5ywx4">Log out</button>
				</form>
			</div>
		</c:if>
		<form method="get" action="FeedbackPage.jsp" id="ir5ivx">
			<button type="submit" id="itoc01">Give feedback / bug report</button>
		</form>
	</div>
	<div id="ihfb5e">
		<div id="iq7gql">Personal Information</div>
		<div id="imcst7">
			<div id="ijzbef">
				<div id="iyobxw">Name  :</div>
				<div id="ixssr5">Date of Birth  :</div>
				<div id="ihqgah">Age  :</div>
				<div id="i9a8eg">Gender  :</div>
				<div id="ipeusp">Nationality  :</div>
				<div id="ij5rfj">Driving license  :</div>
				<div id="itln1x">Engineering license  :</div>
			</div>
			<div id="izyktm">
				<div id="is526e">${firstName}&nbsp;${lastName}</div>
				<div id="i7gimj">${dateOfBirth}</div>
				<div id="imbwyx">${age}</div>
				<div id="itmcmf">${gender}</div>
				<div id="inlbga">${nationality}</div>
				<div id="ifh8ea">${drivingLicense}</div>
				<div id="i213kh">${engineeringLicense}</div>
			</div>
		</div>
		<div id="iyz0zi">
			<div id="ig7ge7">
				<div id="ihl9vf">Languages  :</div>
			</div>
			<div id="irb5ln">
				<div id="ihs3wy">
					<!-- Language -->
					<%
					request.setAttribute("counter", 0);
					%>
					<c:forEach items="${listLanguage}" var="language">
						<c:forEach items="${language}" var="langAndProf">
							<c:if test="${counter % 2 == 0}">
								${langAndProf}
								<br>
							</c:if>
							<c:if test="${counter % 2 == 1}">
								(${langAndProf})
								<c:if test="${counter != 3}">
									<!-- , -->
									<br>
									<br>
								</c:if>
							</c:if>
							<%
							int count = (int) request.getAttribute("counter");
							count += 1;
							request.setAttribute("counter", count);
							%>
						</c:forEach>
					</c:forEach>
					<%
					request.setAttribute("counter", 0);
					%>
				</div>
			</div>
		</div>
		<div id="i0q6is">
			<div id="ieml5q">
				<div id="islcj5">
					Programming  :<br />languages
				</div>
			</div>
			<div id="isa74l">
				<div id="ix2psb">
					<!-- Programming Language -->
					<c:forEach items="${listProgrammingLanguage}"
						var="programmingLanguage">
						<c:forEach items="${programmingLanguage}" var="langAndProf">
							<c:if test="${counter % 2 == 0}">
								${langAndProf}
								<br>
							</c:if>
							<c:if test="${counter % 2 == 1}">
								(${langAndProf})
								<c:if test="${counter != 7}">
									<!-- , -->
									<br>
									<br>
								</c:if>
							</c:if>
							<%
							int count = (int) request.getAttribute("counter");
							count += 1;
							request.setAttribute("counter", count);
							%>
						</c:forEach>
					</c:forEach>
					<%
					request.setAttribute("counter", 0);
					%>
				</div>
			</div>
		</div>
		<div id="il0rsq">
			<div id="ievfsr">
				<div id="ia5yky">Phone number  :</div>
				<div id="ikobp9">Email  :</div>
				<div id="iwgisy">Line ID  :</div>
			</div>
			<div id="inroda">
				<div id="i40kx2">${phoneNumber}</div>
				<div id="i8nard">${email}</div>
				<div id="iatzbi">${lineId}</div>
			</div>
		</div>
		<img id="i1t2dk" src="pics/Certificate1.png" />
		<img id="i8tfk1" src="pics/Certificate2.png" />
	</div>

</body>
</html>
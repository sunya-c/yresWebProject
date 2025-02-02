<%@page import="com.sunya.managers.SessionManager"%>
<%@page import="com.sunya.PrintError"%>
<%@page import="com.sunya.FromServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create new account</title>
<%
	String cssVersion;
	if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
		cssVersion = System.getProperty("SERY_CSS_VERSION");
	else
		cssVersion = System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet" href="css/CreateAccountPageCss.css<%= cssVersion %>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>

<body id="ihjsk6">

	<%
	FromServlet fs = new FromServlet(application, "ServletCreateAccount", session);
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

		SessionManager sm = new SessionManager(session);
		sm.removeCreateAccountErr();
	}
	%>

	<div id="i1h2wx">
		<form method="get" action="Home" id="idtzab">
			<button type="submit" id="i1ckeh">Home</button>
		</form>
		<form method="get" action="feedback" id="in9x4b">
			<button type="submit" id="ikzg0o">Give feedback / bug report</button>
		</form>
	</div>
	<div id="ia2ryx">
		<div id="i82jpw">Creating an account</div>
		<div id="itqzxp">
			<form method="post" action="sCreateAccount" id="ijtey8">
				<div id="ip4cc6">
					<label id="iyd4c4">Username<br /></label><input type="text"
						placeholder="Create your username" name="username"
						value="${preTypedCreateUsername}" id="ir4udv" /><label
						id="iy009g"><br>${usernameErr}<br /></label>
				</div>
				<div id="i69h82">
					<label id="inwehq">Password<br /></label><input type="password"
						placeholder="Create your password" name="password1" id="icug3i" /><label
						id="iq5yjh"><br>${passwordErr1}<br /></label>
				</div>
				<div id="ir3u0x">
					<label id="ibtkzi">Confirm Password<br /></label><input
						type="password" placeholder="Repeat your password"
						name="password2" id="i5yupc" /><label id="ib1ixt"><br>${passwordErr2}<br /></label>
				</div>
				<button type="submit" id="ih73op">Create account</button>
			</form>
		</div>
		<label id="i24vwz">*Features/webpages requiring login are
			still<br />in the developing phase. For now, you can<br />create an
			account and login to your account,<br />but there's no reason to do
			so other than just<br />for fun. XD<br />
		</label>
	</div>
</body>
</html>
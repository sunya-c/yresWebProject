<%@page import="com.sunya.PrintError"%>
<%@page import="com.sunya.FromServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create new account</title>
<link rel="stylesheet" href="css/CreateAccountPageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap" rel="stylesheet" >
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

		session.invalidate();
	}
	%>

	<div id="i1h2wx">
		<form method="get" action="WelcomePage.jsp" id="idtzab">
			<button type="submit" id="i1ckeh">Home</button>
		</form>
		<form method="get" action="FeedbackPage.jsp" id="in9x4b">
			<button type="submit" id="ikzg0o">Give feedback / bug report</button>
		</form>
	</div>
	<div id="ia2ryx">
		<div id="i82jpw">Creating an account</div>
		<div id="itqzxp">
			<form method="post" action="ServletCreateAccount" id="ijtey8">
				<div id="ip4cc6">
					<label id="iyd4c4">Username<br /></label><input type="text"
						placeholder="Create your username" name="username" value="${preTypeCreateUsername}" id="ir4udv" /><br>
					<label id="iy009g">${usernameErr}<br /></label>
				</div>
				<div id="i69h82">
					<label id="inwehq">Password<br /></label><input type="password"
						placeholder="Create your password" name="password1" id="icug3i" /><br>
					<label id="iq5yjh">${passwordErr1}<br /></label>
				</div>
				<div id="ir3u0x">
					<label id="ibtkzi">Confirm Password<br /></label><input
						type="password" placeholder="Repeat your password"
						name="password2" id="i5yupc" /><br> <label id="ib1ixt">${passwordErr2}<br /></label>
				</div>
				<button type="submit" id="ih73op">Create account</button>
			</form>
		</div>
		<label id="i24vwz">*All accounts created will be automatically
			deleted<br />from the database 1 hour after creation.<br />As
			features/webpages requiring login are still<br />in the developing
			phase. For now, you can just<br />try to create the account out for
			fun.<br />
		</label>
	</div>
</body>
</html>
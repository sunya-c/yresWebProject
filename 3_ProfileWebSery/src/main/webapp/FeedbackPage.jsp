<%@page import="com.sunya.SessionManager"%>
<%@page import="com.sunya.PrintError"%>
<%@page import="com.sunya.FromServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Feedback/Bug report</title>
<link rel="stylesheet" href="css/FeedbackPageCss.css" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>
<body id="ih5e81">

	<%
	FromServlet fs = new FromServlet(application, "ServletFeedback", session);
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
		sm.removeFeedbackErr();
	}
	%>

	<div id="ixq7f1">
		<form method="get" action="WelcomePage.jsp" id="i2n2qc">
			<button type="submit" id="iyqs2g">Home</button>
		</form>
	</div>
	<div id="i9sdt7">
		<div id="ipethv">Give feedback or report bugs</div>
		<div id="ilnnh1">
			<form method="post" action="ServletFeedback" id="i9g2n7">
				<div id="i72yhh">
					<label id="i1qbol">Title<br /></label><input type="text"
						placeholder="Give a meaningful name for this issue/feedback"
						name="reportTitle" value="${preTypedReportTitle}" id="iay1m4" /><label
						id="i5n6pj"><br>${titleErr}<br /></label>
				</div>
				<div id="i37wis">
					<label id="igsftu">Detail<br /></label>
					<textarea id="iw3s5g"
						placeholder="Give an explanation about the issue or leave your feedback here"
						name="reportDetail">${preTypedReportDetail}</textarea>
					<label id="iuost3"><br>${detailErr}<br /></label>
				</div>
				<div id="islbdj">
					<label id="irmd7o">Error message (optional)<br /></label>
					<textarea
						placeholder="In case of encountering an error, put the error message here"
						name="errorMessage" id="iq8lxa">${param.preTypedErrMessage}</textarea>
				</div>
				<button type="submit" id="i4b9eg">Submit</button>
			</form>
		</div>
	</div>
</body>
</html>
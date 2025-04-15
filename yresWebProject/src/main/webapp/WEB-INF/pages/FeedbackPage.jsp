<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>
<body id="ih5e81">
	<div id="ixq7f1">
		<form method="get" action="/Home" id="i2n2qc">
			<button type="submit" id="iyqs2g">Home</button>
		</form>
	</div>
	<div id="i9sdt7">
		<div id="ipethv">Give feedback or report bugs</div>
		<c:if test="${dataFeedback.submittedFeedback == false}">
			<div id="ilnnh1">
				<form method="post" action="/sFeedback" id="i9g2n7">
					<div id="i72yhh">
						<label id="i1qbol">Title<br /></label><input type="text"
							placeholder="Give a meaningful name for this issue/feedback"
							name="feedbackTitle" value="${dataFeedback.titlePreTyped}"
							id="iay1m4" /><label id="i5n6pj"><br>${dataFeedback.titleErr}<br /></label>
					</div>
					<div id="i37wis">
						<label id="igsftu">Detail<br /></label>
						<textarea id="iw3s5g"
							placeholder="Give an explanation about the issue or leave your feedback here"
							name="feedbackDetail">${dataFeedback.detailPreTyped}</textarea>
						<label id="iuost3"><br>${dataFeedback.detailErr}<br /></label>
					</div>
					<div id="islbdj">
						<label id="irmd7o">Error message (optional)<br /></label>
						<textarea
							placeholder="In case of encountering an error, put the error message here"
							name="feedbackErrorMessage" id="iq8lxa">${param.preTypedFeedbackErrorMessage}</textarea>
						<label id="iyh6a9"><br>${dataFeedback.errorMessageErr}<br /></label>
					</div>
					<button type="submit" id="i4b9eg">Submit</button>
				</form>
			</div>
		</c:if>
		<c:if test="${dataFeedback.submittedFeedback == true}">
			<div id="ibgd9d">
				<form method="get" action="/Home" id="ibfdco">
					<div id="iclv6l">
						Your issue has been submitted.<br /> <br />Reference number :
						${dataFeedback.refNumber}
					</div>
					<button type="submit" id="iw8t3k">Go to Home page</button>
				</form>
			</div>
		</c:if>
	</div>
</body>
</html>
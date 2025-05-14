<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
</head>
<body id="ibrvou-2">
	<div id="ijp2zh-2">
		<form method="get" action="/Home" id="iglplu-2">
			<button type="submit" id="ib78ph-2">Home</button>
		</form>
		<div id="ilcg8t-2">
			<form method="post" action="/sLogout" id="icsvqi-2">
				<label id="idihl7-2">Welcome <span id="iziw2p-2">${sessionLogin.username}</span>,<br /></label><label
					id="i1esqz-2">You're logged in<br /></label>
				<button type="submit" id="ika14b-2">Log out</button>
			</form>
		</div>
		<form method="get" action="/feedback" id="iltvjz-2">
			<button type="submit" id="idlvn2-2">Give feedback / bug
				report</button>
		</form>
	</div>
	<div id="i2uo19-2">
		<div id="if1d0a-2">&gt;&gt;&gt; Admin panel &lt;&lt;&lt;</div>
		<div id="ibyi03-2">
			<form method="post" action="/adminPanel/sAction" id="ibfdco-2">
				<label id="ixh8osa">Web actions :</label>
				<select id="iy5chbm" name="action">
					<option value="none">Select an action</option>
					<option value="saveBotstodatabase">Save bots to DB</option>
					<option value="deleteBotsinusageinfo">Clear usageinfo</option>
				</select>
				<button type="submit" id="iw8t3k-2">Confirm action</button>
			</form>
			<label id="iesbes8"><span id="ibnapgl">${dataAdminPanel.actionResults}</span></label>
		</div>
		<div id="ibyi03-2-2-2">
			<form method="post" action="/adminPanel/sUploadResume"
				enctype="multipart/form-data" id="iov8pa6">
				<label id="itv4exl">Upload Resume :</label><br />
				<input type="text" name="resumeDate" id="ihwwbda"
					placeholder="Resume version, eg. 20250515" />
				<button type="submit" value="Upload File" id="io1iu2j">Upload</button>
				<br />
				<label id="i5n6pj-2-3">${dataAdminPanel.uploadResumeErr}<br /></label><input
					type="file" name="resumeFile" id="injdihj" /><br />
			</form>
		</div>
		<div id="ibyi03-2-2-2-2">
			<form method="post" action="/adminPanel/sSetResumeVersion"
				id="i9g2n7-2">
				<label id="i1qbol-2">Set Resume version for download:<br /></label>
				<select id="iqkzyuk" name="resumeName">
					<option value="none">Select a resume</option>
					<c:forEach items="${dataAdminPanel.resumeModels}" var="model">
						<option value="${model.filename}">${model.filename}</option>
					</c:forEach>
				</select>
				<button type="submit" id="i4b9eg-2">Set version</button>
				<label id="i5n6pj-2"><br>${dataAdminPanel.resumeVersionErr}<br /></label>
			</form>
		</div>
		<div id="ibyi03-2-2-2-2-2">
			<form method="post" action="/adminPanel/sSetAnnouncement"
				id="i9g2n7-2-2">
				<label id="i1qbol-2-2">Set web announcement message:<br /></label>
				<textarea id="iw3s5g-2" placeholder="Enter the announcement message"
					name="announcementMessage"></textarea>
				<label id="i5n6pj-2-2">${dataAdminPanel.announcementErr}<br /></label>
				<button type="submit" id="i4b9eg-2-2">Set announcement</button>
			</form>
		</div>
	</div>
</body>
</html>
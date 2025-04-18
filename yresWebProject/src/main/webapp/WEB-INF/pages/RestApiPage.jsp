<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Yres - REST api</title>
<link rel="icon" href="/resources/pics/Icon.png" type="image/png">
<%
String cssVersion = "?";
if (System.getenv("SERY_CSS_VERSION")==null || System.getenv("SERY_CSS_VERSION").isBlank())
	cssVersion += System.getProperty("SERY_CSS_VERSION");
else
	cssVersion += System.getenv("SERY_CSS_VERSION");
%>
<link rel="stylesheet"
	href="/resources/css/RestApiPageCss.css<%=cssVersion%>" />
<link href="https://fonts.googleapis.com" rel="preconnect">
<link href="https://fonts.gstatic.com" rel="preconnect" crossorigin>
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:regular,italic&display=swap"
	rel="stylesheet">
</head>
<body id="i96scr">
	<div id="ie2uro">
		<form method="get" action="/Home" id="i9m7kf">
			<button type="submit" id="irgx3m">Home</button>
		</form>
		<c:if test="${sessionLogin.loggedIn == false}">
			<div id="ivy1r3">
				<form method="post" action="/sLogin" id="i9cj3v">
					<div id="im4j5a">
						<label id="itzy77">Username<br /></label><input type="text"
							placeholder="Enter your username" name="username"
							value="${sessionLogin.usernamePreTyped}" id="iz0gul" /><label
							id="izw88e"><br>${sessionLogin.usernameErr}<br /></label>
					</div>
					<div id="iez4lk">
						<label id="iilz28">Password<br /></label><input type="password"
							placeholder="Enter your password" name="password" id="ioq0nk" /><label
							id="iv744l"><br>${sessionLogin.passwordErr}<br /></label>
					</div>
					<button type="submit" id="i2w5cn">Log in</button>
				</form>
				<form method="get" action="/createAccount" id="iueefk">
					<button type="submit" id="ijgpam">Create an account</button>
				</form>
			</div>
		</c:if>
		<c:if test="${sessionLogin.loggedIn == true}">
			<div id="iyfwz7">
				<form method="post" action="/sLogout" id="ii3nc3">
					<label id="i1l84x">Welcome ${sessionLogin.username},<br /></label><label
						id="ilinpi">You're logged in<br /></label>
					<button type="submit" id="iwuy1p">Log out</button>
				</form>
			</div>
		</c:if>
		<form method="get" action="/feedback" id="igi9b8">
			<button type="submit" id="i0hicj">Give feedback / bug report</button>
		</form>
	</div>
	<div id="i1xzpl">
		<div id="infkrv">REST API built on Spring</div>
		<div id="ix9svt">
			You can try REST by adding '<span id="ir0id7">/api/rest/available_path</span>'
			to the end of this website's domain name where you replace '<span
				id="idc5lk">available_path</span>' with the available options
			provided below. For example, '<span id="i6efs5">${dataRestApi.domainName}/api/rest/persinfo</span>'
			will return my personal information. You would expect the results to
			be in either .xml or .json format. Feel free to try any URL below
			with 'Try me!' form.
		</div>
		<div id="i00rjm">
			<form method="get" action="/restApi/sSendRequest" id="ia1sa8">
				<div id="i8qkte">Try me!</div>
				<select id="infg1j" name="restMethod">
					<option value="get">GET</option>
					<option value="post">POST</option>
					<option value="put">PUT</option>
					<option value="delete">DELETE</option>
				</select>
				<textarea placeholder="Paste the URL here!" name="restUrl"
					id="ibgifx"></textarea>
				<button type="submit" id="ijn4wn">Send request</button>
			</form>
		</div>
		<div id="ib0uwp">
			<div id="ilph7x">GET  ${dataRestApi.domainName}/api/rest/user</div>
			<div id="i9rta6">This URL returns all accounts' detail, excluding password.</div>
		</div>
		<div id="i9kesp">
			<div id="idpyeq">GET 
				${dataRestApi.domainName}/api/rest/user/{username}</div>
			<div id="idp5ag">This URL returns the specified account's 
				detail, excluding password. Replace '{username}' with
				the desired username.</div>
		</div>
		<div id="ix28e9">
			<div id="i1p86y">GET 
				${dataRestApi.domainName}/api/rest/feedback/{refNumber}</div>
			<div id="igenqc">This URL returns the feedback detail of the
				specified reference number. Replace '{refNumber}' with the feedback
				reference number.</div>
		</div>
		<div id="i3j6qu">
			<div id="imqaoa">GET 
				${dataRestApi.domainName}/api/rest/persinfo</div>
			<div id="icjf2h">This URL returns my personal information,
				including everything in personal information page.</div>
		</div>
		<div id="i6h12r">
			<div id="im2a74">GET 
				${dataRestApi.domainName}/api/rest/persinfo/fullversion</div>
			<div id="ifumzu">This URL returns my personal information,
				including everything in personal information page and other
				additional detail.</div>
		</div>
		<div id="imqiwi">
			<div id="iklldp">GET 
				${dataRestApi.domainName}/api/rest/ipblacklist</div>
			<div id="iq79wi">This URL returns all spam BOTs' IP addresses
				that I've been collecting since this web application version v0.71.</div>
		</div>
		<div id="itkacr">
			<div id="ifkqzj">GET 
				${dataRestApi.domainName}/api/rest/ipblacklist/{ipAddress}</div>
			<div id="ih95tg">This URL returns 'true' if the specified IP
				address exists in the blacklist database, otherwise returns 'false'.
				Replace '{ipAddress}' with the IP address you'd like to check.</div>
		</div>
	</div>
</body>
</html>
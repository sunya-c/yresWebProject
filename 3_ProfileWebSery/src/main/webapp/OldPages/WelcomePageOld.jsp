<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<body>
	Welcome ${username} <br>
	You're logged in <br>
	<br>
	<form action = "ServletLogout", method = "Post">
		<input type = submit value = "Logout">
	</form>
	
	<br>
	<br>
	
	What do you want to do today? <br>
	<br>
	<form action = "ServletPersonalInformation", method = "get">
		<input type = submit value = "Personal information">
	</form>
	<br>
	<form action = "ServletDownloadResume", method = "get">
		<input type = submit value = "Download Resume">
	</form>
	
</body>

</html>
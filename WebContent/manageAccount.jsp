<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Account</title>
</head>
<body>
	What would you like to change?

	<form action="Manage" method="get">
		<input type="submit" value="Change Password" name="change_password"> <br></br>
		<input type="submit" value="Change Email" name="change_email"> <br></br>
		<input type="submit" value="Change Address" name="change_address"> <br></br>
		<input type="submit" value="Change Credit Card" name="change_card">
	</form>
</body>
</html>
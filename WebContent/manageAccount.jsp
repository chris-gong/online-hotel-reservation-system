<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Manage Account</title>
<style > <jsp:include page = "navBarStyle.jsp"/> </style>
</head>
<body>
<jsp:include page="navbar.jsp"/>

	What would you like to change?

	<form action="Manage" method="get">
		<input type="submit" value="Change Password" name="change_password"><br></br> 
		<input type="submit" value="Change Email" name="change_email"> <br></br> 
		<input type="submit" value="Change Address" name="change_address"> <br></br> 
		<input type="submit" value="Change Credit Card" name="change_card"><br></br>
		<input type = "submit" value ="Change Phone Number" name = "change_number">
			
	</form>
	<% 
		String updated = (String) request.getAttribute("update");
		if (updated != null) {
			out.print(updated);
		}
	%>
</body>
<script>
	document.getElementById("logout_btn").onclick = function() {
	    document.getElementById("logout_form").submit();
	}
	document.getElementById("home_btn").onclick = function() {
	    document.getElementById("home_form").submit();
	}
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
</head>
<%-- User registers an account --%>

<body>

Register here!
	<form id= "registerinfo" class = "cmxform" method="post" action="RegisterServlet">
		First Name: (at least 2 characters) <br>
		<input type = "text" name = "rfname" minlength = "2" required>
		<br>
		Last Name: (at least 2 characters) <br>
		<input type = "text" name = "rlname" minlength = "2" required>
		<br>
		Email:<br>
		<input type = "email" name = "remail" required>
		<br>
		
		Password:(at least 6) <br>
		<input type = "password" name = "rpassword" minlength = "6" required>
		<br>
		Address: <br>
		<input type = "text" name = "raddress" minlength = "10" required>
		<br>
		
		Phone number: (10 digits) <br>
		<input type = "text" name = "rphone" minlength = "10" maxlength = "10" required>
		
		<br>
		<input type = "submit">
	</form>
	
	<script>
		$("#registerinfo").validate();
	</script>
	
	<%
	String test =(String)request.getAttribute("message");
	if(test!=null){
		out.print(test);
	}
	%>
</body>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src = "https://cdn.jsdelivr.net/npm/jquery-validation@1.17.0/dist/jquery.validate.js"></script>
</html>
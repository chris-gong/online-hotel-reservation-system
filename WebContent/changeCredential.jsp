<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change <%
	String test =(String)request.getAttribute("change");
	if(test!=null){
		out.print(test);
	}
	%></title>
</head>
<body>

<% 
if(((String)request.getAttribute("change")).equals("Password")){ %>
<form id= "manageinfo" class = "cmxform" method="post" action="Manage">
	Old Password:
	<input type="password" name="old_password" minlength="6" required> <br></br>
	New Password:
	<input type="password" name="new_password" minlength="6" required><br></br>
	<input type = "submit">
	</form>
<% } %>

<% 
if(((String)request.getAttribute("change")).equals("Email")){ %>
<form id= "manageinfo" class = "cmxform" method="post" action="Manage">
	Old Email:
	<input type="email" name="old_email" required> <br></br>
	New Email:
	<input type="email" name="new_password" required><br></br>
	<input type = "submit">
	</form>
<% } %>

<% 
if(((String)request.getAttribute("change")).equals("Address")){ %>
<form id= "manageinfo" class = "cmxform" method="post" action="Manage">
	Old Address:
	<input type="text" name="old_address" required> <br></br>
	New Address:
	<input type="text" name="new_address" required><br></br>
	<input type = "submit">
	</form>
<% } %>

<% 
if(((String)request.getAttribute("change")).equals("Credit Card")){ %>
<form id= "manageinfo" class = "cmxform" method="post" action="Manage">
	Old Credit Card:
	<input type="text" name="old_card" minlength="16" required> <br></br>
	New Credit Card:
	<input type="text" name="new_card" minlength="16" required><br></br>
	<input type = "submit">
	</form>
<% } %>
</body>
</html>
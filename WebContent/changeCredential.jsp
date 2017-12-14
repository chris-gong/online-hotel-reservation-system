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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<style > <jsp:include page = "navBarStyle.jsp"/> </style>

</head>
<body>
<jsp:include page="navbar.jsp"/>

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
	<input type="email" name="new_email" required><br></br>
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
	Old Credit Card Number:
	<input type="text" name="old_card" required> <br></br>
	Old Security Code
	<input type="text" name="old_sec" minlength="3" maxlength="3" required> <br></br> <br></br>
	New Credit Card Number:
	<input type="text" name="new_card" required><br></br>
	New Security Code:
	<input type="text" name="new_sec" minlength="3" maxlength="3" required><br></br>
	New Expiration Date:
	<input type="Date" name="new_date" required><br></br>
	New Billing Address:
	<input type="text" name="new_address" required><br></br>
	New Name on Card:
	<input type="text" name="new_name" minlength="2" required><br></br>
	New Type:
	<input type="text" name="new_type" required><br></br>
	<input type = "submit">
	</form>
<% } %>

<% 
if(((String)request.getAttribute("change")).equals("Number")){ %>
<form id= "manageinfo" class = "cmxform" method="post" action="Manage">
	Old Number:
	<input type="text" name="old_num" required> <br></br>
	New Number:
	<input type="text" name="new_num" required><br></br>
	<input type = "submit">
	</form>
<% } %>
</body>
<script>

	function setMin(){
		var today = new Date();
		
		var tomorrow = new Date();
		tomorrow.setDate(tomorrow.getDate() + 1);
		
		var ddd = tomorrow.getDate();
		var mmm = tomorrow.getMonth()+1; //January is 0!
		var yyyyy = tomorrow.getFullYear();
		
		var dd = today.getDate();
		var mm = today.getMonth()+1; //January is 0!
		var yyyy = today.getFullYear();
		 if(dd<10){
		        dd='0'+dd
		    } 
		    if(mm<10){
		        mm='0'+mm
		    } 
		    
		    if(ddd<10){
		        ddd='0'+ddd
		    } 
		    if(mmm<10){
		        mmm='0'+mmm
		    } 

		today = yyyy+'-'+mm+'-'+dd;
		tomorrow = yyyyy+'-'+mmm+'-'+ddd;
		document.getElementById("new_date").setAttribute("min", today);
	}
	window.onload = setMin;

	
</script>
<script>
	document.getElementById("logout_btn").onclick = function() {
	    document.getElementById("logout_form").submit();
	}
	document.getElementById("home_btn").onclick = function() {
	    document.getElementById("home_form").submit();
	}
</script>
</html>
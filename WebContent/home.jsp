<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Member Home</title>
<style > <jsp:include page = "navBarStyle.jsp"/> </style>
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	Welcome: <%
	String test =(String)request.getAttribute("name");
	String confirmationMsg = (String)request.getParameter("confirmation");
	if(test!=null){
		out.println(test + "<br>");
	}
	if(confirmationMsg!=null){
		out.println(confirmationMsg);
	}
	%>

	<br></br>

	<form method="get" action="HotelLookup">
		<input type="submit" value="Make Reservation">
	</form>

	<br></br>

	<form method="get" action="Review"> 
		<input type="submit" value="Make Review">

	</form>

	<br></br>

	<form method="get" action="manageAccount.jsp">
		<input type="submit" value="Manage Account">

	</form>
	
	

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
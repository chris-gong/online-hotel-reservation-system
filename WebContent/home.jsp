<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
}
li {
    float: right;
}
li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}
li a:hover {
    background-color: #111;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Member Home</title>
</head>
<body>
	<jsp:include page="navbar.jsp"/>
	Welcome: <%
	String test =(String)request.getAttribute("name");
	if(test!=null){
		out.print(test);
	}
	%>

	<br></br>

	<form method="get" action="HotelLookup">
		<input type="submit" value="Make Reservation">
	</form>

	<br></br>

	<form method="get" action="index.jsp"> 
		<input type="submit" value="Make Review">

	</form>

	<br></br>

	<form method="get" action="index.jsp">
		<input type="submit" value="Manage Account">

	</form>

</body>
<script>
	document.getElementById("logout_btn").onclick = function() {
	    document.getElementById("logout_form").submit();
	}
</script>
</html>
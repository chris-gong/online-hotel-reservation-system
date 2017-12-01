<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Member Home</title>
</head>
<body>
	Welcome: <%
	String test =(String)request.getAttribute("name");
	if(test!=null){
		out.print(test);
	}
	%>

	<br></br>

	<form method="get" action="searchHotel.jsp">
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
</html>
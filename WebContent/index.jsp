<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Hotel Reservation System</title>
</head>

<%-- Allow user to enter valid email and password to login --%>

<body>
	<form method="post" action="LoginServlet">
		Email: <br> <input type="text" name="email"> <br>

		Password: <br> <input type="password" name="password"> <br>

		<input type="submit"> <br> Don't have an account? Click <a
			href="register.jsp">here</a> to register!

	</form>
	<%
	String test =(String)request.getAttribute("message");
	if(test!=null){
		out.print(test);
	}
	%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Hotel</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
            $(function () {
                $("#datepicker").datepicker();
            });
        </script>
</head>
<body>
<form method="post" action="InfoCheck">
	Country:
	<select name="country">
		<option></option>
		<option>Brazil</option>
	</select> State/City:
	<select name="state">
		<option></option>
		<option>Brazil</option>
	</select>

	<br></br> Room Type:
	<select name="rtype">
		<option></option>
		<option>Brazil</option>
	</select>

	<br></br>

	
		<label>Check in Date:</label> <input type="Date" name="inDate" /><br>
		<label>Check out Date:</label> <input type="Date" name="outDate" /><br> <input
			type="submit" />
	</form>

	


</body>
</html>
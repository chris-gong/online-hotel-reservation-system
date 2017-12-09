<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stats Page</title>
</head>
<body>


<div id = "first">
	<form id = "firstquery" method = "post" action = "StatsServlet">
		<input type = "submit">
	</form>

</div>
<br>

<div id  = "firstquery">

	<table id = "t1" border = "1" style="float: left;">
		<tr>
			<th>Type</th>
			<c:forEach items = "${types}" var = "type"> 
				<tr>
					<td>${type}</td>
				
				</tr>
			</c:forEach>
	</table>
	<table border = "1" style="float: left;">
		<th>HotelID</th>
			<c:forEach items = "${hotelids}" var = "id"> 
				<tr>
					<td>${id}</td>
				
				</tr>
			</c:forEach> 
	</table>
	<table border = "1" >
		<th>Max Rating</th>			
			<c:forEach items = "${maxs}" var = "mx"> 
				<tr>
					<td>${mx}</td>
				
				</tr>
			</c:forEach> 
			
	</table>


</div>
<br>




</body>
</html>
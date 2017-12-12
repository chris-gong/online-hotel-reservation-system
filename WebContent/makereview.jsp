<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Make your review!</title>
</head>
<body>


<div id = "test">
Breakfasts you ordered:
<form method = "post" action = "MakeReviewServlet">

	<c:forEach var="b" items="${btype}">
	   <c:out value="${b}" />
	   <input type = "text" id = "breakfasts" value = "${b}" name = "${b}">
	 	<br>
	</c:forEach>
	

Services:
	
	
	
	<br>
	<input type = "submit">
</form>
</div>


<br>

<h1>Select which breakfast you want to order for invoice # ${invoiceNum} at hotel ${firsthotel} </h1>
<div id = "test2">
	<c:forEach items = "${btype}" var = "b">
		<form method = "get" action = "WriteReviewServlet">
			<input name = "in2" id = "in2" type = "hidden" value = "${invoiceNum}">
			<input name = "hotel" id = "hotel" type = "hidden" value = "${firsthotel}">
			<input name = "ind" id = "ind" type = "hidden" value = "${indate}">
			<input name = "outd" id = "outd" type = "hidden" value = "${outdate}">
			<input type = "submit" value = "${b}" name = "breakfast">
			
		</form>
	</c:forEach>

</div>


</body>
</html>
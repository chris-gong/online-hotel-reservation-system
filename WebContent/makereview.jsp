<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Make your review!</title>
<style > <jsp:include page = "navBarStyle.jsp"/> </style>

</head>
<body>
<jsp:include page="navbar.jsp"/>
If there is nothing listed underneath, that's means a review already exists. 

<h1>Select which breakfast you want to review for invoice # ${invoiceNum} </h1>
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
<h1>Select which service you want to review for invoice # ${invoiceNum} at hotel ${firsthotel} </h1>
<div id="test3">
	<c:forEach items = "${stype}" var = "s">
		<form method = "get" action = "WriteReviewServlet">
			<input name = "in2" id = "in2" type = "hidden" value = "${invoiceNum}">
			<input name = "hotel" id = "hotel" type = "hidden" value = "${firsthotel}">
			<input name = "ind" id = "ind" type = "hidden" value = "${indate}">
			<input name = "outd" id = "outd" type = "hidden" value = "${outdate}">
			<input type = "submit" value = "${s}" name = "service">
			
		</form>
	</c:forEach>
</div>

<h1>Select which room you want to review for invoice # ${invoiceNum} at hotel ${firsthotel} </h1>
<div id="test4">
	<c:forEach items = "${room1}" var = "r">
		<form method = "get" action = "WriteReviewServlet">
			<input name = "in2" id = "in2" type = "hidden" value = "${invoiceNum}">
			<input name = "hotel" id = "hotel" type = "hidden" value = "${firsthotel}">
			<input name = "ind" id = "ind" type = "hidden" value = "${indate}">
			<input name = "outd" id = "outd" type = "hidden" value = "${outdate}">
			<input type = "submit" value = "${r}" name = "roomNumber">
			
		</form>
	</c:forEach>
</div>

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
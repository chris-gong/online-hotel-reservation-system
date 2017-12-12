<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Here are your reservations:</title>
</head>
<body>
Select which reservation you want to review:
<br>

<div id = "results">
	<table border="1" style="float: left;" >
		
		<th>Invoice Num</th>
			<c:forEach items="${invoice}" var="in">
				<tr>
					<td>${in}</td>

				</tr>
		</c:forEach>
		
	</table>
		
	<table border="1" style="float: left;" >
		
		<th>Reservation Date</th>
			<c:forEach items="${resDate}" var="re">
				<tr>
					<td>${re}</td>

				</tr>
		</c:forEach>
		
	</table>
	
	
	<table border="1"  >
		
		<th>Credcard  Num</th>
			<c:forEach items="${cnum}" var="c">
				<tr>
					<td>${c}</td>

				</tr>
		</c:forEach>
		
	</table>
		

</div>

<br>
<h1>Select which invoice number you want to review:</h1>
<select name="invoice_num">
    <c:forEach items="${invoice}" var="in">
        <option value="${in}"><c:out value="${in}" /></option>
    </c:forEach>
</select>


</body>
</html>
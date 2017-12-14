<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Confirm order</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
<style>
.service_summary{
	background-color:#F5F5F5;
	border: 2px solid #59BDF7;
    border-radius: 5px;
}
.breakfast_summary{
	background-color:#F5F5F5;
	border: 2px solid #59BDF7;
    border-radius: 5px;
}
</style>
</head>
<body>
	<form method="post" action="BreakfastServiceSummary">
		<input type="text" name="invoice_no" value="${invoice_no}" hidden>
		<input type="text" name="breakfast_req_string" value='${breakfast_req_string}' hidden>
		<input type="text" name="service_req_string" value='${service_req_string}' hidden>
		<input type="text" name="hotel_id" value="${hotel_id}" hidden>
		<div class="container-fluid">
			<div class="row"> 
				<div class="col-6 center-block text-center">
					Breakfast orders
				</div>
				<div class="col-6 center-block text-center">
					Service orders
				</div>
			</div>
			<div class="row">
				<div class="breakfast_summary col-6">
					<c:forEach items="${breakfast_reqs}" var="breakfast">
						${breakfast.getTimesOrdered()} orders of ${breakfast.getType()} at $${breakfast.getPrice()} each <br>
					</c:forEach>
				</div>
				<div class="service_summary col-6">
					<c:forEach items="${service_reqs}" var="service">
						${service.getTimesOrdered()} orders of ${service.getType()} at $${service.getPrice()} each <br>
					</c:forEach>
				</div>
			</div>
			Your total cost comes out to $${total_cost}
			<br>
			<button type="submit">Confirm purchase</button>
		</div>
	</form>
</body>
</html>
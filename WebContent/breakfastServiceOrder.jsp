<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order breakfast and service</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
</head>
<body>
<form>
	<div class="container-fluid">
		<div class="row">
			<div id="breakfast_list" class="col-6">
				Available breakfasts <br>
				<c:forEach items="${breakfasts}" var = "breakfast">
					<input type="radio" name="breakfast_type" value="${breakfast.getType()}"> ${breakfast.getDescription()} for $${breakfast.getPrice()}<br>
				</c:forEach>
					<button type = "button">Add breakfast</button>
					<button type = "button">Remove breakfast</button>
			</div>
			<div id="service_list" class="col-6">
				Available services <br>
				<c:forEach items="${services}" var = "service">
					<input type="radio" name="service_type" value="${service.getType()}"> ${service.getType()} for $${service.getPrice()}<br>
				</c:forEach>
				<button type = "button">Add service</button>
				<button type = "button">Remove service</button>
			</div>
		</div>
	</div>
	
	
	<br>

	<button type = "button" onclick="addMe()">
	Order
	</button>

<br>

</form>
<br>

<div id = "list">
	Your order (order #, Breakfast, Service):
	<p id = "inside">
	</p>

</div>


<script>

</script>


</body>
</html>
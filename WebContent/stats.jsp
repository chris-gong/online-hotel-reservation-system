<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Stats Page</title>
<style > <jsp:include page = "navBarStyle.jsp"/> </style>

</head>
<body>
<ul>
	<li>
		<form id="logout_form" method="post" action="Logout">
			<a id="logout_btn" href="javascript:void(0)">Logout</a>
		</form>
	</li>
	</ul>

	<br>
	Top 5 customers by userId
	<br>
	
	
	<c:forEach items="${customers}" var="c">
		${c.getUserId()} <br>
	</c:forEach>
	<div id="firstquery">
		<table border="1" style="float: left;">
			<th>HotelID</th>
			<c:forEach items="${hotelids}" var="id">
				<tr>
					<td>${id}</td>

				</tr>
			</c:forEach>
		</table>
		<table id="t1" border="1" style="float: left;">
			<th>Type</th>
			<c:forEach items="${types}" var="type">
				<tr>
					<td>${type}</td>

				</tr>
			</c:forEach>
		</table>

		<table border="1">
			<th>Max Rating</th>
			<c:forEach items="${maxs}" var="mx">
				<tr>
					<td>${mx}</td>

				</tr>
			</c:forEach>

		</table>


	</div>
	<br>

	<div id="thirdquery">
		<table border="1" style="float: left;">
			<th>HotelID</th>
			<c:forEach items="${hotel_id}" var="id">
				<tr>
					<td>${id}</td>

				</tr>
			</c:forEach>
		</table>

		<table id="t3" border="1" style="float: left;">
			<tr>
				<th>BType</th>
				<c:forEach items="${b_type}" var="type">
					<tr>
						<td>${type}</td>

					</tr>
				</c:forEach>
		</table>



		<table border="1">
			<th>Max Rating</th>
			<c:forEach items="${mx}" var="mxs">
				<tr>
					<td>${mxs}</td>

				</tr>
			</c:forEach>

		</table>


	</div>

	<br>
	<div id="fourthquery">
		<table border="1" style="float: left;">
			<th>HotelID</th>
			<c:forEach items="${hotel_id4}" var="id">
				<tr>
					<td>${id}</td>

				</tr>
			</c:forEach>
		</table>

		<table id="t3" border="1" style="float: left;">
			<tr>
				<th>SType</th>
				<c:forEach items="${b_type4}" var="type">
					<tr>
						<td>${type}</td>

					</tr>
				</c:forEach>
		</table>



		<table border="1">
			<th>Max Rating</th>
			<c:forEach items="${mx4}" var="mxs">
				<tr>
					<td>${mxs}</td>

				</tr>
			</c:forEach>

		</table>


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
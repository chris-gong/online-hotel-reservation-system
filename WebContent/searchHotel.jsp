<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Hotel</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<%-- User selects options for kind of hotel they want to reserve --%>


<body>

	<form method="post" action="RoomLookup">
		<div id="countrylist">
			<label>Country:</label>
			<select size="1" id="countries"
				class=" validate['required']" title="" type="select" name="country">
				<%-- changed name from style to country --%>
				<option value="">Choose a country</option>
				<c:forEach items="${countries}" var="country"> 
					<option value="${country}" >${country}</option>
				</c:forEach>
			</select>
			<div class="clear"></div>
			<div id="error-message-style"></div>
		</div>
		<div id="statelist">
			<label>State:</label>
			<select size="1" id="Countries"class="validate["required"]" title="" type="select" name="state">
				<option value="">Choose a state</option>
			<c:out value ="${RoomLookupServlet.getStates('United States')}"/>
			<c:forEach items="${RoomLookupServlet.getStates('United States')}" var="state">
				<option value="${state}" >${state}</option>
			</c:forEach></select><div class="clear"></div>
			<div id="error-message-style"></div>
		</div>
		<div class="clear"></div>
		<div id="error-message-style-sub-1"></div>

		<label>Check in Date:</label> <input type="Date" name="inDate" /><br>
		<label>Check out Date:</label> <input type="Date" name="outDate" /><br>

		Room type: <select name="roomType">
			<option>Single</option>
			<option>Double</option>
			<option>Triple</option>
			<option>Quad</option>
			<option>Queen</option>
			<option>King</option>
			<option>Twin</option>
			<option>Double-double</option>
			<option>Studio</option>
		</select> <br> <input type="submit">
	</form>
</body>
<script>
	function generateStatesHtml(country){
		document.getElementById("statelist").innerHTML = '<label>State:</label><select size="1" id="Countries"class="validate["required"]" title="" type="select" name="state"><option value="">Choose a state</option><c:forEach items="${RoomLookupServlet.getStates('+country+')}" var="state"><option value="${state}" >${state}</option></c:forEach></select><div class="clear"></div><div id="error-message-style"></div>';
	}
	$(document).ready(function() {
		$("#countries").change(function() {
			var targID = $(this).val();
			//generateStatesHtml("United States");
			/*$('#statelist').html('hello');*/
			$('#statelist').show();
			console.log('country changed');
		});
		$("#USA").change(function() {
			
			var e = document.getElementById("USA");
			var strUser = e.options[e.selectedIndex].value;
			//alert(strUser);
			//window.alert(strUser);
			$("div.style-sub-2").hide();
			$('#' + strUser).show();
			
		    // var targID = $(this).val();
			//$('#' + targID).show(); //was show
			//$('#NYCities').show();
		});
	});
</script>
</html>
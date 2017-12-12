<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:useBean id="HotelLookup" class="servlets.HotelLookupServlet"
	scope="session" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Hotel</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
</head>
<script>

	function setMin(){
		var today = new Date();
		
		var tomorrow = new Date();
		tomorrow.setDate(tomorrow.getDate() + 1);
		
		var ddd = tomorrow.getDate();
		var mmm = tomorrow.getMonth()+1; //January is 0!
		var yyyyy = tomorrow.getFullYear();
		
		var dd = today.getDate();
		var mm = today.getMonth()+1; //January is 0!
		var yyyy = today.getFullYear();
		 if(dd<10){
		        dd='0'+dd
		    } 
		    if(mm<10){
		        mm='0'+mm
		    } 
		    
		    if(ddd<10){
		        ddd='0'+ddd
		    } 
		    if(mmm<10){
		        mmm='0'+mmm
		    } 

		today = yyyy+'-'+mm+'-'+dd;
		tomorrow = yyyyy+'-'+mmm+'-'+ddd;
		document.getElementById("indate").setAttribute("min", today);
		document.getElementById("outdate").setAttribute("min", tomorrow);
	}
	window.onload = setMin;
	
	
	
	/*using the scripts:
		<form id = "dateinput" method="post" action="AdminServlet">
	Check in date:<br>
	<input  onchange = "checkDate()" type = "Date" name = "inDate" id = "indate" required>
	<br>Check out date: <br>
	<input onchange = "checkDate()" type = "Date" name = "outDate" id = "outdate" required>
	<br>
	<input type ="submit">
	</form>*/
	
	
	
</script>


<%-- User selects options for kind of hotel they want to reserve --%>



<body>

	<form method="post" action="HotelLookup" autocomplete="off">
		<div id="countrylist">
			<label>Country:</label> <select size="1" id="countries" title="" name="country" required>
				<option value="">Choose a country</option>
				<c:forEach items="${countries}" var="country">
					<option value="${country}">${country}</option>
				</c:forEach>
			</select>
		</div>
		<div id="statelist">
			<label>State:</label> <select size="1" id="states" title="" name="state" required>
			</select>
			<div class="clear"></div>
			<div id="error-message-style"></div>
		</div>
		<div id="citylist">
			<label>City:</label> <select size="1" id="cities" title="" name="city" required>
			</select>
		</div>
		<label>Check in Date:</label> <input type="Date" name="inDate" onchange = "checkDate()"  id = "indate" required/><br>
		<label>Check out Date:</label> <input type="Date" name="outDate" onchange = "checkDate()" id = "outdate" required/><br>

		Number of rooms: <select id="num_rooms" name="num_rooms" >
			<c:forEach begin="1" end="3" varStatus="loop">
				<option value="${loop.index}">${loop.index}</option>
			</c:forEach>
		</select> 
		<div id="rooms">
			<label style="padding-right:5em;">Rooms</label> 
			<label>Capacity</label>
			<div class="room_info">
				<label style="padding-right:4.5em;">Room 1:</label>
				<select class="num_people" name="num_people" >
					<c:forEach begin="1" end="4" varStatus="loop">
						<option value="${loop.index}">${loop.index}</option>
					</c:forEach>
				</select> 
			</div>
		</div>
		<input type="submit">
	</form>
</body>
<script>
	function generateStatesHtml(country) {
		$.ajax({
			url: 'HotelLookup',
			data: {country: country},
			type: 'get',
			success: function(result){
				console.log(result);
				$("#states").html(result);
			},
			error: function(){
				console.log("error yay");
			}
		});
	}
	function generateCitiesHtml(country,state) {
		$.ajax({
			url: 'HotelLookup',
			data: {country: country,state:state},
			type: 'get',
			success: function(result){
				console.log(result);
				$("#cities").html(result);
			},
			error: function(){
				console.log("error yay");
			}
		});
	}
	
	
	$(document).ready(function() {
		var room_count = 1;
		$('#statelist').hide();
		$('#citylist').hide();
		$("#countries").change(function() {
			var country = $(this).val();
			//console.log(country);
			if(country===""){
				$('#statelist').hide();
			}else{
				generateStatesHtml(country);
				$('#statelist').show();
			}
			$('#citylist').hide();
			console.log('country changed');
		});
		$("#states").change(function() {
			var state = $(this).val();
			var country = $("#countries").val();
			//console.log(country);
			if(state===""){
				$('#citylist').hide();
			}else{
				generateCitiesHtml(country,state);
				$('#citylist').show();
			}
			console.log('city changed');
		});
		$("#num_rooms").change(function(){
			if($(this).val() > room_count){
				//add rooms
				var rooms_to_add = $(this).val() - room_count;
				for(i = 0; i < rooms_to_add; i++){
					var room_num = (room_count+i+1);
					$('#rooms .room_info').last().append('<div class="room_info"><label style="padding-right:4.5em;">Room'+room_num+': </label><select class="num_people" name="num_people" ><c:forEach begin="1" end="4" varStatus="loop"><option value="${loop.index}">${loop.index}</option></c:forEach></select></div>');
				}
			}
			else{
				//delete rooms
				var rooms_to_delete = room_count - $(this).val();
				for(i = 0; i < rooms_to_delete; i++){
					$('#rooms .room_info').last().remove();
				}
			}
			room_count = $(this).val();
		});
	});
	
	
	document.getElementById("inDate").onchange = function(){checkDate()};
	document.getElementById("outDate").onchange = function(){checkDate()};
	function checkDate(){
		
		var indate = document.getElementById("indate").value;
		var inVarDate = new Date (indate);
		
		var todayDate = new Date(); //today's date
		
		var outdate = document.getElementById("outdate").value;
		var outVarDate = new Date (outdate);
	
		
		if (inVarDate >= outVarDate ){	
			alert ("Invalid date range selected. The check-out date has to be after the check-in date");
			$("#indate").val('');
			$("#outdate").val('');
		}
	}
</script>
</html>
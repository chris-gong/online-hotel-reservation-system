<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:useBean id="RoomLookup" class="servlets.RoomLookupServlet"
	scope="session" />
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search Hotel</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
</head>
<%-- User selects options for kind of hotel they want to reserve --%>


<body>

	<form method="post" action="RoomLookup">
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

		<label>Check in Date:</label> <input type="Date" name="inDate" required/><br>
		<label>Check out Date:</label> <input type="Date" name="outDate" required/><br>

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
				<select class="num_people" name="num_people_1" >
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
			url: 'RoomLookup',
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
			url: 'RoomLookup',
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
					$('#rooms .room_info').last().append('<div class="room_info"><label style="padding-right:4.5em;">Room'+room_num+': </label><select class="num_people" name="num_people_'+ room_num +'" ><c:forEach begin="1" end="4" varStatus="loop"><option value="${loop.index}">${loop.index}</option></c:forEach></select></div>');
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
</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Room Selection</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">

<style>
.room{
	border-style:solid;
	border-color:green;
	border-radius:25px;
}
</style>
</head>
<body>
	<div id="caps" hidden>
		<c:forEach items="${caps}" var="cap">
			<div class="cap">${cap}</div>
		</c:forEach>
		<c:forEach items="${req_rooms}" var="req_room">
			<div class="req_room">${req_room}</div>
		</c:forEach>
		<div class="in_date">${in_date}</div>
		<div class="out_date">${out_date}</div>
		<div class="room_num">${room_num}</div>
	</div>
	<div id="avail_rooms" class="container-fluid">
		Displaying all available rooms for ${hotel_name} <br>
		Please select a room for at least ${caps[room_num-1]} people
		<c:if test="${empty rooms}">
			<div id="msg">
				No more rooms available!
			</div>
		</c:if>
		<c:forEach items="${rooms}" var="room">
			<div class="row">
				<div id="${room.getRoomNum()}" data-hotel_id="${hotel_id}"data-hotel_name="${hotel_name}"class="col-4 room" style="cursor:pointer;">
					Room ${room.getRoomNum()}: <br>
					Floor: ${room.getFloorNum()} <br>
					Description: ${room.getDescription()} <br>
					Type: ${room.getType()} <br>
					Capacity: ${room.getCapacity()} <br>
					Price: $${room.getPrice()} <br>
				</div> 
			</div>
			<br>
		</c:forEach>
	</div>
</body>
<script>
function sendRoomInfo(id, name, in_date, out_date, caps, room_num, req_rooms, message) {
	$.ajax({
		url : 'HotelSelect',
		data : {
			hotel_id : id,
			name : name,
			in_date : in_date,
			out_date : out_date,
			caps : caps,
			room_num : room_num,
			req_rooms : req_rooms,
			message: message
		},
		type : 'get',
		success : function(result) {
			console.log(result);
			window.location.href = result;
		},
		error : function() {
			console.log("error yay");
		}
	});
}
$(document).ready(function() {
	$(".room").click(function(){
		var id = $(this).data("hotel_id");
		console.log(id);
		var name = $(this).data("hotel_name");
		var in_date = $("#caps .in_date").html();
		var out_date = $("#caps .out_date").html();
		var room_num = parseInt($("#caps .room_num").html()) + 1; //current room we're iterating on
		console.log(room_num);
		var room_no = $(this).attr('id'); //room selected by the user
		var num_rooms = $(this).data("num_rooms");
		var caps = Array();
		var req_rooms = Array();
		var message = 'room selection phase';
		$("#caps .cap").each(function(){
			caps.push($(this).html());
		});
		$("#caps .req_room").each(function(){
			req_rooms.push($(this).html());
		});
		req_rooms.push(room_no);
		console.log(req_rooms);
		caps = JSON.stringify(caps);
		req_rooms = JSON.stringify(req_rooms);
		sendRoomInfo(id, name, in_date, out_date, caps, room_num, req_rooms, message);
	});
});

</script>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Hotel</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<style > <jsp:include page = "navBarStyle.jsp"/> </style>

</head>
<body>
<jsp:include page="navbar.jsp"/>
	<div id="caps" hidden>
		<c:forEach items="${caps}" var="cap">
			<div class="cap">${cap}</div>
		</c:forEach>
		<div class="in_date">${in_date}</div>
		<div class="out_date">${out_date}</div>
	</div>
	Listing all hotels near ${address}
	<div id="hotel_list">
		<c:forEach items="${hotels}" var="hotel">
			<div id="${hotel.getId()}">
				${hotel.getName()}
				<button type="button" class="book_btn"
					data-hotel_id="${hotel.getId()}"
					data-hotel_name="${hotel.getName()}"
					data-num_rooms=${room_count}>Book Room(s)</button>
			</div>
		</c:forEach>
	</div>
</body>
<script type="text/javascript">
	function sendHotelInfo(id, name, caps, num_rooms, in_date, out_date) {
		$.ajax({
			url : 'HotelSelect',
			data : {
				id : id,
				name : name,
				caps : caps,
				num_rooms : num_rooms,
				in_date : in_date,
				out_date : out_date
			},
			type : 'post',
			success : function(result) {
				result = $.parseJSON(result);
				//result.map is because of how jackson parses json into a whole encompassing object called json
				var message = result.map.message;
				var url = result.map.url;
				console.log(message); 
				//if no message was sent back then there are enough rooms and we can redirect to the next step
				if(message === ''){
					console.log(url);
					window.location.href = url;
				}
				else{
					//if it reached this step, that means the button did not redirect to the next step
					//and we have to tell the user that the hotel he/she selected doesn't have enough rooms as requested
					$("#"+id).append('<span id="err_msg">' +message+ '</span>')
				}
			},
			error : function() {
				console.log("error yay");
			}
		});
	}

	$(document).ready(function() {
		$('.book_btn').click(function() {
			$("#err_msg").remove();
			var id = $(this).data("hotel_id");
			var name = $(this).data("hotel_name");
			var in_date = $("#caps .in_date").html();
			var out_date = $("#caps .out_date").html();
			var num_rooms = $(this).data("num_rooms");
			var caps = Array();
			$("#caps .cap").each(function(){
				caps.push($(this).html());
			});
			caps = JSON.stringify(caps);
			sendHotelInfo(id, name,caps, num_rooms, in_date, out_date);
			
			
			//window.location.href = '/HotelReservations/index.jsp';
		});
	});
</script>
<script>
	document.getElementById("logout_btn").onclick = function() {
	    document.getElementById("logout_form").submit();
	}
	document.getElementById("home_btn").onclick = function() {
	    document.getElementById("home_form").submit();
	}
</script>
</html>
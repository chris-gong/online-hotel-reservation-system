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
	<div id="avail_rooms" class="container-fluid">
		Displaying all available rooms for ${hotel_name} <br>
		<c:if test="${empty rooms}">
			<div id="msg">
				No more rooms available!
			</div>
		</c:if>
		<c:forEach items="${rooms}" var="room">
			<div class="row">
				<div id="${room.getRoomNum()}" class="col-4 room" style="cursor:pointer;">
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
$(document).ready(function() {
	
});
}
</script>
</html>
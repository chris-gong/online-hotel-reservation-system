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
</head>
<body>
	Listing all hotels near ${address}
	<div id="hotel_list">
		<c:forEach items="${hotels}" var="hotel">
			<div>
				${hotel.getName()}
				<button type="button" class="book_btn"
					data-hotel_id="${hotel.getId()}">Book Room(s)</button>
			</div>
		</c:forEach>
	</div>
</body>
<script type="text/javascript">
$(document).ready(function() {
	$('.book_btn').click(function(){
		var id = $(this).data("hotel_id");
		console.log(id);
	});
});


</script>
</html>
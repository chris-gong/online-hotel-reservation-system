<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin page</title>
</head>
<body>
<script>

	/*function setMin(){
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
	}*/
	//window.onload = setMin;
	
	
	
	/*using the scripts:
		<form id = "dateinput" method="post" action="AdminServlet">
	Check in date:<br>
	<input  onchange = "checkDate()" type = "Date" name = "inDate" id = "indate" required>
	<br>Check out date: <br>
	<input onchange = "checkDate()" type = "Date" name = "outDate" id = "outdate" required>
	<br>
	<input type ="submit">
	</form>
	*/
	
	
</script>

<form id = "dateinput" method="post" action="AdminServlet">
	Check in date:<br>
	<input   type = "Date" name = "inDate" id = "indate" required>
	<br>Check out date: <br>
	<input  type = "Date" name = "outDate" id = "outdate" required>
	<br>
	<input type ="submit">



</form>
<br>
<a href="http://localhost:8080/HotelReservations/index.jsp" class="button">Logout</a>


<script>

	document.getElementById("indate").onchange = function(){checkDate()};
	document.getElementById("outdate").onchange = function(){checkDate()};
	function checkDate(){
		
		var indate = document.getElementById("indate").value;
		var inVarDate = new Date (indate);
		
		var todayDate = new Date(); //today's date
		
		var outdate = document.getElementById("outdate").value;
		var outVarDate = new Date (outdate);
	
		
		if (inVarDate >= outVarDate ){	
			alert ("Invalid date range. Check out date has to be after check in date");
			document.forms["dateinput"].reset();
		}
	}
</script>
</body>
</html>
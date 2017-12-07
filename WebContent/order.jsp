<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order breakfast and service</title>
</head>
<body>
<form>

	Select what you want for each day and add. <br>
	<select id = "breakfast">
		<option value = "french">French</option>
		<option value = "english">English</option>
		<option value = "spanish">Spanish</option>
	
	</select>


<br>
	<select id = "service">
		<option value = "cleaning">Cleaning</option>
		<option value = "ironing">Ironing</option>
		<option value = "driving">Driving</option>
	
	</select>

<br>
	<button type = "button" onclick="addMe()">
	Add
	</button>

<br>

</form>
<br>

<div id = "list">
	Your order (order #, Breakfast, Service):
	<p id = "inside">
	</p>

</div>

<!--  
<div id = "table">
	<table id = "tb">
		<tr>
			<th> Order #</th>
			<th> Breakfast</th>
			<th> Service</th>
		
		</tr>
	
	</table>

</div>
-->

<script>
	var count = 1;
	
	function addMe(){
		var e = document.getElementById("breakfast");
		var breakfast = e.options[e.selectedIndex].text;
		
		var g = document.getElementById("service");
		var service = g.options[g.selectedIndex].text;
		
		var p = document.getElementById("inside");
		
		p.innerHTML += count + ")  " +  breakfast + " " + service + '<br>';
		count++;
		
		/*var table = document.getElementById("tb");
		//var rowC = 0;
		var row = table.insertRow(-1);
		rowC++;
		
		var cell1 = row.insertCell(0);
		var cell2 = row.insertCell(1);
		var cell3 = row.insertCell(2);
		
		cell1.inerHTML(count);
		cell2.innerHTML(breakfast);
		cell3.innerHTML(service);*/
		
		
	}




</script>


</body>
</html>
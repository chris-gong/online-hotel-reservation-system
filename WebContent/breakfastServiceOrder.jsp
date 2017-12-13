<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order breakfast and service</title>
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css">
</head>
<body>
<form method="post" action="BreakfastServiceSelect" onSubmit="return collectOrders();">
	<input type="text" name="order_limit" value="${order_limit}" hidden>
	<div class="container-fluid">
		<div class="row">
			<div id="breakfast_list" class="col-6">
				Available breakfasts <br>
				<c:forEach items="${breakfasts}" var = "breakfast">
					<input type="radio" name="breakfast_type" value="${breakfast.getType()}"> ${breakfast.getDescription()} for $${breakfast.getPrice()}<br>
				</c:forEach>
					<button type = "button" id="add_breakfast">Add breakfast</button>
					<button type = "button" id="remove_breakfast">Remove breakfast</button>
			</div>
			<div id="service_list" class="col-6">
				Available services <br>
				<c:forEach items="${services}" var = "service">
					<input type="radio" name="service_type" value="${service.getType()}"> ${service.getType()} for $${service.getPrice()}<br>
				</c:forEach>
				<button type = "button" id="add_service">Add service</button>
				<button type = "button" id="remove_service">Remove service</button>
			</div>
		</div>
		<div class="row">
		<!-- ASSUME BREAKFAST TYPES AND SERVICE TYPES CAN'T HAVE THE SAME NAMES OR ELSE THIS WON'T WORK -->
			<div id="breakfast_requests" class="col-6">
				Requested Breakfasts 
			</div>
			<div id="service_requests" class="col-6">
				Requested Services <br>
			</div>
		</div>
		<br>
		<button type = "submit">Proceed to order</button>
	</div>

</form>

<script>
	//this method will be called right before submitting the form to gather all the breakfast
	//and service divs to retrieve the info in them and put it into two hidden input tags
	function collectOrders(){
		console.log("Orders being collected...");
		var breakfast_array = new Array();
		$("#breakfast_requests > div").each(function(){
			var b_type = $(this).data('btype');
			console.log(b_type);
			var quant = $(this).children("span").html();
			var breakfast_obj = new Array();
			breakfast_obj.push(b_type);
			breakfast_obj.push(quant);
			breakfast_array.push(JSON.stringify(breakfast_obj));
		});
		var service_array = new Array();
		$("#service_requests > div").each(function(){
			var s_type = $(this).data('stype');
			console.log(s_type);
			var quant = $(this).children("span").html();
			var service_obj = new Array();
			service_obj.push(s_type);
			service_obj.push(quant);
			service_array.push(JSON.stringify(service_obj));
		});
		var breakfast_array_string = JSON.stringify(breakfast_array);
		var service_array_string = JSON.stringify(service_array);
		console.log(breakfast_array_string);
		console.log(service_array_string);
		$(".container-fluid").append("<input type='text' name='breakfast_array_string' value="+breakfast_array_string+" hidden>");
		$(".container-fluid").append("<input type='text' name='service_array_string' value="+service_array_string+" hidden>");
		return true;
	}
	$(document).ready(function(){
		$("#add_breakfast").click(function(){
			var b_type = $("input[name='breakfast_type']:checked").val();
			console.log(b_type);
			if(b_type != null){
				var b_type_with_spaces = b_type;
				console.log(b_type_with_spaces);
				var b_type_with_scores = b_type.replace(/ /g,'_'); //replace spaces with underscores because spaces aren't being passed in json string properly
				b_type = b_type.replace(/ /g,''); //remove spaces so that it doesn't mess up the div id
				
				if($("#"+b_type+"div").length){
					//if the user already requested a breakfast type
					var count = parseInt($("#"+b_type+"div").children("span").html());
					$("#"+b_type+"div").children("span").html(count+1);
				}
				else{
					//if the user did not request the breakfast type yet
					$("#breakfast_requests").append('<div id='+b_type+'div data-btype="'+b_type_with_scores+'">'+b_type_with_spaces+', Quantity: <span>1</span></div>');
				}
			}
		});
		$("#remove_breakfast").click(function(){
			var b_type = $("input[name='breakfast_type']:checked").val();
			b_type = b_type.replace(/ /g,'_');
			if(b_type != null){
				if($("#"+b_type+"div").length){
					//if the user already requested that breakfast type once
					var count = parseInt($("#"+b_type+"div").children("span").html());
					if(count == 1){
						$("#"+b_type+"div").remove();
					}
					else{
						$("#"+b_type+"div").children("span").html(count-1);
					}

				}
				else{
					//if the user never requested the breakfast type yet
				}
			}
		});
		$("#add_service").click(function(){
			var s_type = $("input[name='service_type']:checked").val();
			console.log(s_type);
			if(s_type != null){
				var s_type_with_spaces = s_type;
				console.log(s_type_with_spaces);
				var s_type_with_scores = s_type.replace(/ /g,'_');
				s_type = s_type.replace(/ /g,'_'); //remove spaces so that it doesn't mess up the div id
				if($("#"+s_type+"div").length){
					//if the user already requested a service type
					var count = parseInt($("#"+s_type+"div").children("span").html());
					$("#"+s_type+"div").children("span").html(count+1);
				}
				else{
					//if the user did not request the service type yet
					$("#service_requests").append('<div id='+s_type+'div data-stype="'+s_type_with_scores+'">'+s_type_with_spaces+', Quantity: <span>1</span></div>');
				}
			}
		});
		$("#remove_service").click(function(){
			var s_type = $("input[name='service_type']:checked").val();
			s_type = s_type.replace(/ /g,'');
			if(s_type != null){
				if($("#"+s_type+"div").length){
					//if the user already requested that breakfast type once
					var count = parseInt($("#"+s_type+"div").children("span").html());
					if(count == 1){
						$("#"+s_type+"div").remove();
					}
					else{
						$("#"+s_type+"div").children("span").html(count-1);
					}

				}
				else{
					//if the user never requested the breakfast type yet
				}
			}
		});
	});
</script>


</body>
</html>
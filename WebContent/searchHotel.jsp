<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
		<div class="ccms_form_element cfdiv_custom" id="style_container_div">


			<label>Country:</label><select size="1" id="beerStyle"
				class=" validate['required']" title="" type="select" name="country">
				<%-- changed name from style to country --%>
				<option value="">Choose a country</option>
				<option value="America">America</option>
				<option value="France">France</option>
				<option value="India">India</option>
			</select>
			<div class="clear"></div>
			<div id="error-message-style"></div>
		</div>


		<div id="America" class="style-sub-1" style="display: none;"
			name="stylesub1" onchange="ChangeDropdowns(this.value)">
			<label>Which state in United States?</label> 
			<select name="state1" onchange="f()">
				<option value="">-Choose state-</option>
				<option value="NY">NY</option>
				<option value="Washington DC">Washington DC</option>
			</select>
		</div>
		<div id="India" class="style-sub-1" style="display: none;"
			name="stylesub1" onchange="ChangeDropdowns(this.value)">
			<label>Which state in India?</label> <select name="state2">
				<option value="">-Choose state</option>
				<option value="Gujurat">Gujurat</option>
				<option value="Tamil Nadu">Tamil Nadu</option>
			</select>
		</div>
		<div id="France" class="style-sub-1" style="display: none;"
			name="stylesub1" onchange="ChangeDropdowns(this.value)">
			<label>Which state in France?</label> <select name="state3">
				<option value="">Choose state</option>
				<option value="Test">Test</option>
				<option value="Herbed / Spiced Beer">Herbed / Spiced Beer</option>
				<option value="Smoked Beer">Smoked Beer</option>
			</select>
		</div>
		<div class="clear"></div>
		<div id="error-message-style-sub-1"></div>


		<div class="row">
			<div id="NY" style=visibility:hidden>
				<Label> Pick a City </label><select>
				<option value=""></option>
				<option value="New York">New York</option>
				</select>
			</div>
		</div>
		
		<script>
			function f(){
				document.getElementById('NY').style.visibility='visible';
			}
		
		</script>








		<script>
			$("#beerStyle").change(function() {
				var targID = $(this).val();
				$("div.style-sub-1").hide();
				$('#' + targID).show();
			})
		</script>

		<script>
			$("#NY").change(function() {
				var targID = $(this).val();
				$("div.style-sub-1").hide();
				$('#' + targID).show();
			})
		</script>




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
</html>
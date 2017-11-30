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


<body>

<form>
<div class="ccms_form_element cfdiv_custom" id="style_container_div">


<label>Country:</label><select size="1" id="beerStyle" class=" validate['required']" title="" type="select" name="style">
<option value="">Choose a country</option>
<option value="America">America</option>
<option value="France">France</option>
<option value="India">India</option>
</select><div class="clear"></div><div id="error-message-style"></div></div>

<div id="America"  class="style-sub-1"  style="display: none;" name="stylesub1" onchange="ChangeDropdowns(this.value)">
  <label>What state in America?</label>
    <select>
      <option value="">-Choose state-</option> 
      <option value="NJ">NJ</option>
      <option value="NY">NY</option>
      <option value="California">California/option>
      <option value="Texas">Texas</option>
    </select>
</div>
<div id="India"  class="style-sub-1"  style="display: none;" name="stylesub1" onchange="ChangeDropdowns(this.value)">
  <label>Which state in India?</label>
    <select>
    <option value="">-Choose stater-</option>
      <option value="Gujurat">American Lager</option>
      <option value="Something">Somethingr</option>
      <option value="European Lager">European Lager</option>
      <option value="German Lager">German Lager</option>
      <option value="Japanese Lager">Japanese Lager</option>
    </select>
</div>
<div id="France"  class="style-sub-1"  style="display: none;" name="stylesub1" onchange="ChangeDropdowns(this.value)">
  <label>Which state in France?</label> 
    <select>
    <option value="">-Choose state</option>
      <option value="Idk states">Test</option>
      <option value="Herbed / Spiced Beer">Herbed / Spiced Beer</option>
      <option value="Smoked Beer">Smoked Beer</option>
    </select>
</div><div class="clear"></div><div id="error-message-style-sub-1"></div></div>


<script>
$("#beerStyle").change ( function () {
    var targID  = $(this).val ();
    $("div.style-sub-1").hide ();
    $('#' + targID).show ();
} )
</script>


<script>
  $( function() {
    $( "#checkindate" ).datepicker();
  } );
  </script>
  
  CheckinDate: <input type="text" id="checkindate">
  <br>

<script>
  $( function() {
    $( "#checkoutdate" ).datepicker();
  } );
  </script>
  
  CheckoutDate: <input type="text" id="checkoutdate">
  
  <br>
  Room type: 
  <select name = "roomType">
  	<option>Single</option>
  	<option>Double</option>
  	<option>Triple</option>
  	<option>Quad</option>
  	<option>Queen</option>
  	<option>King</option>
  	<option>Twin</option>
  	<option>Double-double</option>
  	<option>Studio</option>
  </select>
  
  <br>
  <input type = "submit">
</form>
</body>
</html>
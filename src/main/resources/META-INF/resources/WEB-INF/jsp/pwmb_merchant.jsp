<!DOCTYPE html>
<html>
<head>
	<title>PWMB Test Merchant</title>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
	<link href='https://sandbox.paywithmybank.com/start/styles/pwmb.css' rel='stylesheet' type='text/css'>	
	
	<script type='text/javascript'>
	
		var merchantUrl = "<%= request.getAttribute("name") %>";
	
	</script>
	
	
	<script type="text/javascript" src="https://sandbox.paywithmybank.com/start/scripts/pwmb/pwmb.js?accessId=RqBNyqzgTVGhmvyV74NM"></script>
	<script type="text/javascript" src="<%= request.getAttribute("name") %>/TCKValidationInterface.js"></script>	

<script type='text/javascript'>
	//Replace this value with the url you want to redirect to after login (if not the current page)
	// var merchantUrl ="http://localhost:8080";
	

	
	function IsEmpty(){ 

		if(document.form.question.value == "")
		{
		alert("Enrollment id is empty");
		}
		    return;
		}
	
	
	function Enroll(){
		
		var obj = new Object();
		obj.transactionId = document.getElementById("transactionID").value;
		obj.name = document.getElementById("username").value;
		obj.bank_rt = document.getElementById("bank_routing").value;
		obj.bank_acct = document.getElementById("bank_acct").value;
		obj.city = document.getElementById("city").value;
		obj.state = document.getElementById("state").value;
		obj.zip = document.getElementById("zip").value;
		obj.phone = document.getElementById("phone").value;
		obj.email = document.getElementById("email").value;
		obj.address1 = document.getElementById("address").value;
		obj.address2 = document.getElementById("address2").value;
		
		var data = JSON.stringify(obj);
		
		// alert(data);
		
		xmlhttp_enroll.send(data);		
		
	}
	
	
	
	
</script>
</head>
<body>
<H3>PWMB Test Merchant</H3><br>
<button name="pwmb" onclick="javascript:TCKValidationService('pwmb.getAll')">Enroll using PWMB</button><br><br>
<!-- options: pwmb.getAll, pwmb.getConsumerInfo, pwmb.getBankInfo -->
<H2>Pay with my bank validation call response</H2>
<table>
	<tr><td>Transaction ID:</td><td><input type='text' id='transactionID' /></td></tr>
	<tr><td>Customer Name:</td><td><input type='text' id='username' /></td></tr>
	<tr><td>Bank RTN:</td><td><input type='text' id='bank_routing' /></td></tr>
	<tr><td>Bank Account:</td><td><input type='text' id='bank_acct' /></td></tr>
	<tr><td>Address:</td><td><input type='text' id='address' /></td></tr>
	<tr><td>Address2:</td><td><input type='text' id='address2' /></td></tr>	
	<tr><td>City:</td><td><input type='text' id='city' /></td></tr>
	<tr><td>State:</td><td><input type='text' id='state' /></td></tr>
	<tr><td>Zip:</td><td><input type='text' id='zip' /></td></tr>
	<tr><td>Phone:</td><td><input type='text' id='phone' /></td></tr>
	<tr><td>Email:</td><td><input type='text' id='email' /></td></tr>
</table>
<H2>Perform transaction (need enrollment id first)</H2>
<form action="/transactions">
<table>
	<tr><td><button type="button" name="enroll" onClick="Enroll();">Get enrollment id</button></td></tr>
	<tr><td>Enrollment ID:</td><td><input type='text' id='enrollmentID' name='question' /></td></tr>
	<tr><td>Enter amount:</td><td><input type='text' id='amount' name='amount'/></td></tr>
</table>
<button name="transact" onclick="IsEmpty();">Perform transactions</button>
</form>
</body></html>

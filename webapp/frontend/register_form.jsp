<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
     <meta charset="ISO-8859-1">
     <title>Register as a Customer</title>
     <script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
     <script type="text/javascript" src="js/jquery.validate.min.js"></script>
     <link rel="stylesheet" href="css/style.css" >
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">Register as a Customer</h2>
	</div>

	<div align="center">
        <form action="customer_register" method="post" id="customerForm">
		<table class="form">
			<tr>
				<td align="right">Email:</td>
				<td align="left"><input type="text" id="email" name="email" size="45" /></td>
			</tr>

			<tr>
				<td align="right">Full Name:</td>
				<td align="left"><input type="text" id="fullName" name="fullName" size="45" /></td>
			</tr>

			<tr>
				<td align="right">Password:</td>
				<td align="left"><input type="password" id="password" name="password" size="45" /></td>
			</tr>

			<tr>
				<td align="right">Confirm Password:</td>
				<td align="left"><input type="password" id="confirmPassword" name="confirmPassword" size="45" /></td>
			</tr>

			<tr>
				<td align="right">Phone Number:</td>
				<td align="left"><input type="text" id="phone" name="phone" size="45" /></td>
			</tr>

			<tr>
				<td align="right">Address:</td>
				<td align="left"><input type="text" id="address" name="address" size="45" /></td>
			</tr>
			
			<tr>
				<td align="right">City:</td>
				<td align="left"><input type="text" id="city" name="city" size="45" /></td>
			</tr>
			
			<tr>
				<td align="right">Zip Code:</td>
				<td align="left"><input type="text" id="zipCode" name="zipCode" size="45" /></td>
			</tr>
			
			<tr>
				<td align="right">Country:</td>
				<td align="left"><input type="text" id="country" name="country" size="45" /></td>
			</tr>

			<tr>
				<td>&nbsp;</td>
			</tr>

			<tr>
				<td colspan="2" align="center">
					<button type="submit">Save</button>&nbsp;&nbsp;&nbsp;
					<button id="buttonCancel">Cancel</button>
				</td>
			</tr>
		</table>
		</form>
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>

<script type="text/javascript">
   $(document).ready(function(){
	   $("#customerForm").validate({
		   rules:{
			   email:{
				   required: true,
				   email: true
			   },
			   fullName: "required",	         
			   password: "required",
			   confirmPassword:{
				   required: true,
				   equalTo: "#password"
			   },
			   phone: "required",
			   address: "required",
			   city: "required",
			   zipCode: "required",
			   country: "required",		 
		   },
		   
		   messages:{	
			   email:{
				   required: "Please ente email",
				   email: "Please enter a valid email format"
			   },
			   fullName: "Please enter full name",
			   password: "Please enter password",
			   confirmPassword:{
				   required: "Please confirm password",
				   equalTo: "Please enter the same password"
			   },
			   phone: "Please enter  phone",
			   address: "Please enter address",
			   city: "Please enter city",
			   zipCode: "Please enter zip code",
			   country: "Please enter country",		  		   
		   }
	   });
	   
	   $("#buttonCancel").click(function(){
		   window.history.go(-1);return false;
	   });	
   } );
  
</script>
</html>
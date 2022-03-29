<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Edit Customer Profile</title>
    <link rel="stylesheet" href="css/style.css">
    <script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.min.js"></script>
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
		<h2 class="pageheading">Edit My Profile</h2>
	</div>

	<div align="center">	
		<form action="update_profile" method="post" id="customerForm">

		<table class="form">
			<tr>
				<td align="left">Email:</td>
				<td align="left"><b>${loginCustomer.email}</b> (Cannot be changed)</td>
			</tr>

			<tr>
				<td align="left">Full Name:</td>
				<td align="left"><input type="text" id="fullName" name="fullName" size="45" value="${loginCustomer.fullName}" /></td>
			</tr>

			<tr>
				<td align="left">Phone Number:</td>
				<td align="left"><input type="text" id="phone" name="phone" size="45" value="${loginCustomer.phone}" /></td>
			</tr>

			<tr>
				<td align="left">Address:</td>
				<td align="left"><input type="text" id="address" name="address" size="45" value="${loginCustomer.address}" /></td>
			</tr>
			
			<tr>
				<td align="left">City:</td>
				<td align="left"><input type="text" id="city" name="city" size="45" value="${loginCustomer.city}" /></td>
			</tr>
			
			<tr>
				<td align="left">Zip Code:</td>
				<td align="left"><input type="text" id="zipCode" name="zipCode" size="45" value="${loginCustomer.zipCode}" /></td>
			</tr>
			
			<tr>
				<td align="left">Country:</td>
				<td align="left"><input type="text" id="country" name="country" size="45" value="${loginCustomer.country}" /></td>
			</tr>
			
			<tr>
			    <td colspan="2"><i>Leave blank if password keeps unchanged!</i></td>
			</tr>
				
			<tr>
				<td align="left">Password:</td>
				<td align="left"><input type="password" id="password" name="password" size="45" /></td>
			</tr>

			<tr>
				<td align="left">Confirm Password:</td>
				<td align="left"><input type="password" id="confirmPassword" name="confirmPassword" size="45"/></td>
			</tr>			
			
			<tr><td>&nbsp;</td></tr>

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
			   fullName: "required",	         
			   confirmPassword:{				   
				   equalTo: "#password"
			   },
			   phone: "required",
			   address: "required",
			   city: "required",
			   zipCode: "required",
			   country: "required",		 
		   },
		   
		   messages:{				  
			   fullName: "Please enter full name",			   
			   confirmPassword:{				  
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
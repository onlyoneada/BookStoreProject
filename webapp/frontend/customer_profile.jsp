<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
   <meta charset="ISO-8859-1">
   <title>Customer Profile - Online Books Store</title>
   <link rel="stylesheet" href="css/style.css" >
</head>
<body>
	<jsp:directive.include file="header.jsp" />
	
	<c:if test="${message != null}">
	   <div align="center">
	        <h4 class="message">${message}</h4>
	    </div>
	</c:if>
	
	<div class="center">
	   <h3>Welcome, ${loginCustomer.fullName}</h3>
	    
	    <table class="profile" align="center">
	        <tr>
	            <td align="left"><b>E-mail Address:</b></td>
	            <td align="left">${loginCustomer.email}</td>
	        </tr>
	        
	        <tr>
	            <td align="left"><b>Full Name:</b></td>
	            <td align="left">${loginCustomer.fullName}</td>
	        </tr>
	        
	        <tr>
	            <td align="left"><b>Phone Number:</b></td>
	            <td align="left">${loginCustomer.phone}</td>
	        </tr>
	        
	        <tr>
	            <td align="left"><b>Address:</b></td>
	            <td align="left">${loginCustomer.address}</td>
	        </tr>
	        
	        <tr>
	            <td align="left"><b>City:</b></td>
	            <td align="left">${loginCustomer.city}</td>
	        </tr>
	        
	        <tr>
	            <td align="left"><b>Zip Code:</b></td>
	            <td align="left">${loginCustomer.zipCode}</td>
	        </tr>
	        
	        <tr>
	            <td align="left"><b>Country:</b></td>
	            <td align="left">${loginCustomer.country}</td>
	        </tr>
	    </table>	    	    
	    
	    <br>
	    <a href="edit_profile"><h3>Edit My Profile</h3></a>
	</div>
	
	<jsp:directive.include file="footer.jsp" />
</body>
</html>
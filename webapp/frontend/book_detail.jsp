<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>${book.title} - Online Books Store</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div class="center">
	    <table class="book">
	        <tr>
	            <td colspan="3" align="left"><p id="book-title">${book.title}</p>
	            by <span id="author"> ${book.author}</span></td>
	        </tr>
	        	      
	        <tr>	       
	            <td valign="top" rowspan="2"><img class="book-large" src="data:image/jpg;base64,${book.base64Image}" /></td>
	            <td valign="top" align="left">Rating*********</td>
	            <td valign="top" rowspan="2" width="20%"><h2><b>$${book.price}</b></h2>
	            <br/><br/>
	            <button type="submit">Add to Cart</button></td>
	        </tr>
	        
	        <tr>
	            <td id="description">${book.description}</td>
	        </tr>
	        
	        <tr><td>&nbsp;</td></tr>
	        
	        <tr>
	            <td><h2><b>Customer Reviews</b></h2></td>
	            <td align="center" colspan="2"><button type="submit">Write a customer Review</button></td>
	        </tr>  
	        
	       
	    </table>
  
	</div>

	<jsp:directive.include file="footer.jsp" />
</body>
</html>
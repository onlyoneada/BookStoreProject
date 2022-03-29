<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Edit Review</title>
    <link rel="stylesheet" href="../css/style.css" >
    <link rel="stylesheet" href="../css/jquery-ui.min.css" >
    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.1/themes/base/jquery-ui.css">
    <script type="text/javascript" src="../js/jquery-3.6.0.min.js" ></script>
    <script type="text/javascript" src="../js/jquery.validate.min.js" ></script>
    <script type="text/javascript" src="../js/jquery-ui.min.js" ></script>
   
    <link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/richtext.min.css">
    <script type="text/javascript" src="../js/jquery.richtext.min.js" ></script>
    
      
</head>
<body>
	<jsp:directive.include file="header.jsp" />

	<div align="center">
	    <h2 class="pageheading">Edit Review</h2>
	</div>

	<div align="center">
	    <form action="update_review" method="post" id="reviewForm">
		<input type="hidden" name="reviewId" value="${review.reviewId}">
	   
		<table class="form">    		    
		    <tr>
		       <td align="right">Book:</td>
		       <td align="left"><b>${review.book.title}</b></td>		       		       
	        </tr>	
	        
		    <tr>
		       <td align="right">Rating:</td>
		       <td align="left"><b>${review.rating}.0</b></td>		       		       
	        </tr> 
	        
		    <tr>
		       <td align="right">Customer:</td>
		       <td align="left"><b>${review.customer.fullName}</b></td>		       		       
	        </tr>
	        
		    <tr>
		       <td align="right">Headline:</td>
		       <td align="left"><input type="text" id="headline" name="headline" size="20" value="${review.headline}" /></td>		       		       
	        </tr> 	 	        	              	        	       
	        
		    <tr>
		       <td align="right">Comment:</td>
		       <td align="left"><textarea rows="5" cols="70" name="comment">${review.comment}</textarea></td>		       		       
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
	   $("#reviewForm").validate({
		   rules:{		  
			   headline: "required",
			   comment: "required",		   		  	
		   },
		   
		   messages:{				  
			   headline: "Please enter headline of this review",
			   comment: "Please enter comment for this book"			   
		   }
	   });
	   
	   $("#buttonCancel").click(function(){
		   window.history.go(-1);return false;
	   });	
	   $('#comment').richText();
	   
   });
</script>
</html>
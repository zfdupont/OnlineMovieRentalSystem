<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Customer"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!--
	This is the Open Auction Details page
	This page displays the data for each Auction which is currently open
	The details are fetched using the "auctions" field from the request object
-->

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width:device-width, initial-scale=1">
	<link href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" />
	<title>Open Order Details</title>
</head>
<body>

	<h1>The Open Orders are:</h1>
	<div class="container">
	<c:if test="${empty orders}">
		<h3> Order Data not found! </h3> 
	</c:if>
	<c:if test="${not empty orders}">
		<table class="table table-striped">
		  <thead>
		    <tr>
		      <th>Order ID</th>
		      <th>Date Time</th>
		      <th>Return Date</th>
<!-- 		      <th>Closing Bid</th>
		      <th>Current High Bid</th> -->
		      <th></th>
		    </tr>
		  </thead>
		  <tbody>
		     <c:forEach items="${orders}" var="cd">
		       <tr>
		         <td>${cd.orderID}</td>
		         <td>${cd.dateTime}</td>		         
		         <td>${cd.returnDate}</td>
<%-- 		         <td>${cd.closingBid}</td>
		         <td>${cd.currentHighBid}</td> --%>
		         <td>
		         	<form method="POST" action="recordSale">
						<div class="form-group">
			            	<input type="hidden" class="form-control" name="orderID" value=${cd.orderID}>
			        	</div>
						<input type="submit" value="Record the Order" class="btn btn-success"/>
					</form>
		         </td>		         
		         
		       </tr>
		     </c:forEach>
		  </tbody>
		</table>
	</c:if>
	</div>

	<div class="container pt-1">
		<form action="home.jsp">
			<input type="submit" value="Home" class="btn btn-success"/>
		</form>
	</div>

	
	<script src="webjars/jquery/3.3.1-1/jquery.min.js"></script>
	<script src="webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
	
</body>
</html>
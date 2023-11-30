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
	This is the Show Auction History page
	This page displays the history of a particular auction in terms of bidding history
	The details are fetched using the "bids" field from the request object
-->

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width:device-width, initial-scale=1">
	<link href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" />
	<title>Order History</title>
</head>
<body>

	<h1>The Order History is:</h1>
	<div class="container">
	<c:if test="${empty rentals}">
		<h3> Order History not found! <h3/> 
	</c:if>
	<c:if test="${not empty rentals}">
		<table class="table table-striped">
		  <thead>
		    <tr>
		      <th>Order ID</th>
		      <th>Movie ID</th>
		      <th>CustomerRepresentitive ID</th>
		    </tr>
		  </thead>
		  <tbody>
		     <c:forEach items="${rentals}" var="cd">
		       <tr>
		         <td>${cd.orderID}</td>
		         <td>${cd.movieID}</td>		         
		         <td>${cd.customerRepID}</td>
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
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
	This is the Search Summary Listing page
	This Sales Summary can be searched by item name, item type or customer name
	This page redirects to the Show Summary Listing page
-->


<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width:device-width, initial-scale=1">
		<link href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" />
	</head>
	<body>
	<h2>Summary Listing</h2>
		<div class="container">
			<h2>Search Movie Name, Movie Type or Customer Name:</h2>
			<form action="getSummaryListing">
				<input type="text" name="searchKeyword" placeholder="Movie Name, Movie Type or Customer Name" class="form-control" />
				<input type="submit" value="Search" class="btn btn-success"/>
			</form>
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

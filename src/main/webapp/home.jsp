<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--
	This is the Home page for Customer Representative
	This page contains various buttons to navigate the online auction house
	This page contains customer representative specific accessible buttons
-->

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<meta name="viewport" content="width:device-width, initial-scale=1">
		<link href="webjars/bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet" />
		<title>Customer Home</title>
	</head>
	<body>
	
		<h1>Welcome to the Online Movie Rental System!</h1>
		<div class="container">
			<h2>Customer Options:</h2>
			<%
			String email = (String)session.getAttribute("email");
			String role = (String)session.getAttribute("role");
			
			//redirect to appropriate home page if already logged in
			if(email != null) {
				if(role.equals("manager")) {
					response.sendRedirect("managerHome.jsp");
				}
				else if(role.equals("customerRepresentative")) {
					response.sendRedirect("customerRepresentativeHome.jsp");
				}
			}
			else {
				// redirect to log in if not alreaddy logged in
				response.sendRedirect("index.jsp");
			}
			%>
			
			<div class="row">
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">View Currently Hold Movies</h5>
    					<div class="container">
							<form action="getCurrentMovies">
								<input type="submit" value="View Movies" class="btn btn-success"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">View Queue of Movies</h5>
    					<div class="container">
							<form action="getQueueOfMovies">
								<input type="submit" value="View Queue" class="btn btn-success"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">Account Setting</h5>
    					<div class="container">
							<form action="setAccount.jsp">
								<input type="submit" value="Set Account" class="btn btn-success"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">History of all orders</h5>
    					<div class="container">
							<form action="getOrderHistory">
								<input type="submit" value="All History" class="btn btn-primary"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				<!-- <div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">Items sold by Seller</h5>
    					<div class="container">
							<form action="getSellers">
								<input type="submit" value="Seller Info" class="btn btn-primary"/>
							</form>
						</div>
					  </div>
					</div>
				</div> -->
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">Search Movies</h5>
    					<div class="container">
							<form action="searchMovies">
								<input type="submit" value="Search Movies" class="btn btn-success"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">View Best Seller Movies</h5>
    					<div class="container">
							<form action="getBestsellersForCustomer">
								<input type="submit" value="View Best Sellers" class="btn btn-success"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				<div class="col">
					<div class="card">
					  <div class="card-body">
					    <h5 class="card-title">Personalized Item Suggestion List</h5>
    					<div class="container">
							<form action="personalizedSuggestions">
								<input type="submit" value="View Personalized Suggestions" class="btn btn-success"/>
							</form>
						</div>
					  </div>
					</div>
				</div>
				
		</div>
		<div class="container">
			<form action="logout">
				<input type="submit" value="Logout" class="btn btn-danger"/>
			</form>
		</div>
		
		<script src="webjars/jquery/3.3.1/jquery.min.js"></script>
		<script src="webjars/bootstrap/4.1.3/bootstrap.min.js"></script>
	</body>
</html>
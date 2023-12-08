package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Account;
import model.Customer;
import model.Customer;
import model.Employee;
import resources.ConnectionSingleton;

import java.util.stream.IntStream;

public class CustomerDao {
	/*
	 * This class handles all the database operations related to the customer table
	 */
	
	/**
	 * @param String searchKeyword
	 * @return ArrayList<Customer> object
	 */

	public List<Customer> getCustomers() {
		/*
		 * This method fetches one or more customers and returns it as an ArrayList
		 */
		
		List<Customer> customers = new ArrayList<Customer>();

		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		Connection conn = null;
		Statement st;
		ResultSet rs;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			st = conn.createStatement();
			String query = new StringBuilder()
					.append("SELECT \n")
					.append("C.Email, \n")
					.append("P.FirstName, \n")
					.append("P.LastName, \n")
					.append("P.Address, \n")
					.append("P.ZipCode, \n")
					.append("Lc.State, \n")
					.append("Lc.City, \n")
					.append("P.Telephone, \n")
					.append("C.ID AS CustomerID, \n")
					.append("C.Rating, \n")
					.append("C.CreditCardNumber \n")
					.append("FROM Customer C\n")
					.append("JOIN Person P ON C.ID = P.SSN\n")
					.append("JOIN Location Lc ON P.ZipCode = Lc.ZipCode\n")
					.append(";")
					.toString();
			rs = st.executeQuery(query);
			while(rs.next()) {
				Customer customer = new Customer();
				
				customer.setCustomerID(Integer.toString(rs.getInt("CustomerID")));
				customer.setEmail(rs.getString("Email"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setLastName(rs.getString("LastName"));
				customer.setAddress(rs.getString("Address"));
				customer.setState(rs.getString("State"));
				customer.setCity(rs.getString("City"));
				customer.setZipCode(rs.getInt("ZipCode"));
				customer.setTelephone(rs.getString("Telephone"));
				customer.setRating(rs.getInt("Rating"));
				customer.setCreditCard(rs.getString("CreditCardNumber"));

				customers.add(customer);
			}
		} catch(Exception e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return customers;
	}


	public Customer getHighestRevenueCustomer() {
		/*
		 * This method fetches the customer who generated the highest total revenue and returns it
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */

		Customer customer = new Customer();
		Connection conn = null;
		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = new StringBuilder()
					.append("SELECT C.ID AS CustomerID, P.FirstName, C.Email, P.LastName, SUM(M.DistrFee) AS TotalSpent \n")
					.append("FROM Customer C \n")
					.append("JOIN Person P ON C.ID = P.SSN \n")
					.append("JOIN Account A ON C.ID = A.CustomerId \n")
					.append("JOIN Rental R ON A.ID = R.AccountId \n")
					.append("JOIN `Order` O ON R.OrderId = O.ID \n")
					.append("JOIN Movie M ON R.MovieId = M.ID \n")
					.append("GROUP BY C.ID, P.FirstName, P.LastName \n")
					.append("ORDER BY TotalSpent DESC \n")
					.append("LIMIT 1;")
					.toString();

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				customer.setCustomerID(String.valueOf(rs.getInt("CustomerID")));
				customer.setFirstName(rs.getString("P.FirstName"));
				customer.setLastName(rs.getString("P.LastName"));
				customer.setEmail(rs.getString("C.Email"));
			}
		} catch (Exception e) {
			e.printStackTrace();  // Handle exceptions appropriately
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();  // Handle exceptions during close
				}
			}
		}
		return customer;
		
	}

	public List<Customer> getCustomerMailingList() {

		/*
		 * This method fetches the all customer mailing details and returns it
		 * The students code to fetch data from the database will be written here
		 * Each customer record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		
		List<Customer> customers = new ArrayList<Customer>();

		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		Connection conn = null;
		Statement st;
		ResultSet rs;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			st = conn.createStatement();
			String query = new StringBuilder()
					.append("SELECT \n")
					.append("C.Email, \n")
					.append("P.FirstName, \n")
					.append("P.LastName, \n")
					.append("P.Address, \n")
					.append("P.ZipCode, \n")
					.append("Lc.State, \n")
					.append("Lc.City, \n")
					.append("C.ID AS CustomerID \n")
					.append("FROM Customer C\n")
					.append("JOIN Person P ON C.ID = P.SSN\n")
					.append("JOIN Location Lc ON P.ZipCode = Lc.ZipCode\n")
					.append(";")
					.toString();
			rs = st.executeQuery(query);
			while(rs.next()) {
				Customer customer = new Customer();
				
				customer.setCustomerID(Integer.toString(rs.getInt("CustomerID")));
				customer.setEmail(rs.getString("Email"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setLastName(rs.getString("LastName"));
				customer.setAddress(rs.getString("Address"));
				customer.setState(rs.getString("State"));
				customer.setCity(rs.getString("City"));
				customer.setZipCode(rs.getInt("ZipCode"));

				customers.add(customer);
			}
		} catch(Exception e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
//		/*Sample data begins*/
//		for (int i = 0; i < 10; i++) {
//			Customer customer = new Customer();
//			customer.setCustomerID("111-11-1111");
//			customer.setAddress("123 Success Street");
//			customer.setLastName("Lu");
//			customer.setFirstName("Shiyong");
//			customer.setCity("Stony Brook");
//			customer.setState("NY");
//			customer.setEmail("shiyong@cs.sunysb.edu");
//			customer.setZipCode(11790);
//			customers.add(customer);			
//		}
//		/*Sample data ends*/
		
		return customers;
	}

	public Customer getCustomer(String customerID) {

		/*
		 * This method fetches the customer details and returns it
		 * customerID, which is the Customer's ID who's details have to be fetched, is given as method parameter
		 * The students code to fetch data from the database will be written here
		 * The customer record is required to be encapsulated as a "Customer" class object
		 */
		
		/*Sample data begins*/
		Customer customer = new Customer();
		Connection conn = null;
		Statement st;
		ResultSet rs;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			st = conn.createStatement();
			String query = new StringBuilder()
					.append("SELECT \n")
					.append("C.Email, \n")
					.append("P.FirstName, \n")
					.append("P.LastName, \n")
					.append("P.Address, \n")
					.append("P.ZipCode, \n")
					.append("Lc.State, \n")
					.append("Lc.City, \n")
					.append("C.ID AS CustomerID \n")
					.append("FROM Customer C\n")
					.append("JOIN Person P ON C.ID = P.SSN\n")
					.append("JOIN Location Lc ON P.ZipCode = Lc.ZipCode\n")
					.append(";")
					.toString();
			rs = st.executeQuery(query);
			if(rs.next()) {
				
				customer.setCustomerID(Integer.toString(rs.getInt("CustomerID")));
				customer.setEmail(rs.getString("Email"));
				customer.setFirstName(rs.getString("FirstName"));
				customer.setLastName(rs.getString("LastName"));
				customer.setAddress(rs.getString("Address"));
				customer.setState(rs.getString("State"));
				customer.setCity(rs.getString("City"));
				customer.setZipCode(rs.getInt("ZipCode"));
			}
		} catch(Exception e) {
			System.err.println(e);
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return customer;
	}
	
	public String deleteCustomer(String customerID) {

		/*
		 * This method deletes a customer returns "success" string on success, else returns "failure"
		 * The students code to delete the data from the database will be written here
		 * customerID, which is the Customer's ID who's details have to be deleted, is given as method parameter
		 */

		Connection conn = null;
		String result = "failure";

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
		    conn.setAutoCommit(false); // Start transaction
		    
		    int customerId = Integer.valueOf(customerID);
		    
		    AccountDao dao = new AccountDao();
		    
		    Account acc = dao.getAccount(customerID);
		    
		    if(acc.getAccountID() > 0) {
		        // Delete from Rental
		        String sqlDeleteRental = "DELETE FROM Rental WHERE AccountId IN (SELECT ID FROM Account WHERE CustomerID = ?)";
		        PreparedStatement statementRental = conn.prepareStatement(sqlDeleteRental);
		        statementRental.setInt(1, customerId);
		        statementRental.executeUpdate();

		        // Delete from MovieQ
		        String sqlDeleteMovieQ = "DELETE FROM MovieQ WHERE AccountId IN (SELECT ID FROM Account WHERE CustomerID = ?)";
		        PreparedStatement statementMovieQ = conn.prepareStatement(sqlDeleteMovieQ);
		        statementMovieQ.setInt(1, customerId);
		        statementMovieQ.executeUpdate();

		        // Delete from Account
		        String sqlDeleteAccount = "DELETE FROM Account WHERE CustomerID = ?";
		        PreparedStatement statementAccount = conn.prepareStatement(sqlDeleteAccount);
		        statementAccount.setInt(1, customerId);
		        statementAccount.executeUpdate();
		    }

		    // Delete from Login
		    String sqlDeleteLogin = "DELETE FROM Login WHERE PersonID = (SELECT SSN FROM Person WHERE SSN = (SELECT ID FROM Customer WHERE ID = ?))";
		    PreparedStatement statementLogin = conn.prepareStatement(sqlDeleteLogin);
		    statementLogin.setInt(1, customerId);
		    statementLogin.executeUpdate();

		    // Delete the customer
		    String sqlDeleteCustomer = "DELETE FROM Customer WHERE ID = ?";
		    PreparedStatement statementCustomer = conn.prepareStatement(sqlDeleteCustomer);
		    statementCustomer.setInt(1, customerId);
		    int rowsAffectedCustomer = statementCustomer.executeUpdate();
		
		    if (rowsAffectedCustomer > 0) {
		        conn.commit(); // Commit the transaction
		        result = "success";
		    } else {
		        conn.rollback(); // Rollback if no rows are affected
		    }
		} catch (Exception e) {
		    if (conn != null) {
		        try {
		            conn.rollback(); // Rollback transaction on exception
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		    e.printStackTrace();
		} finally {
		    if (conn != null) {
		        try {
		            conn.setAutoCommit(true); // Reset auto-commit to default
		            conn.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }
		}
		
		return result;
		
	}


	public String getCustomerID(String username) {
		/*
		 * This method returns the Customer's ID based on the provided email address
		 * The students code to fetch data from the database will be written here
		 * username, which is the email address of the customer, who's ID has to be returned, is given as method parameter
		 * The Customer's ID is required to be returned as a String
		 */
		Connection conn = null;
		Customer customer = new Customer();
		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = "SELECT * FROM Customer C WHERE C.Email = ?;";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, username);

			ResultSet rs = statement.executeQuery();

			if(rs.next()){
				customer.setCustomerID(rs.getString("ID"));
				customer.setEmail(rs.getString("Email"));
				System.out.printf("Got customer in (%s, %s)", customer.getEmail(), customer.getCustomerID());
				return customer.getCustomerID();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return customer.getCustomerID();
	}


	public List<Customer> getSellers() {
		
		/*
		 * This method fetches the all seller details and returns it
		 * The students code to fetch data from the database will be written here
		 * The seller (which is a customer) record is required to be encapsulated as a "Customer" class object and added to the "customers" List
		 */

		List<Customer> customers = new ArrayList<Customer>();
		
		/*Sample data begins*/
		for (int i = 0; i < 10; i++) {
			Customer customer = new Customer();
			customer.setCustomerID("111-11-1111");
			customer.setAddress("123 Success Street");
			customer.setLastName("Lu");
			customer.setFirstName("Shiyong");
			customer.setCity("Stony Brook");
			customer.setState("NY");
			customer.setEmail("shiyong@cs.sunysb.edu");
			customer.setZipCode(11790);
			customers.add(customer);			
		}
		/*Sample data ends*/
		
		return customers;

	}


	public String addCustomer(Customer customer) {

		/*
		 * All the values of the add customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the customer details and return "success" or "failure" based on result of the database insertion.
		 */
		
		Connection conn = null;
        String result = "failure";

        try {
        	conn = ConnectionSingleton.getInstance().getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            
            int ssn = Integer.valueOf(customer.getCustomerID().replaceAll("-", ""));
            // Insert into Location table if needed
            String sqlLoc = "INSERT IGNORE INTO Location (City, ZipCode, State) VALUES (?, ?, ?)";
            PreparedStatement statementLoc = conn.prepareStatement(sqlLoc);
            statementLoc.setString(1, customer.getCity());
            statementLoc.setInt(2, customer.getZipCode());
            statementLoc.setString(3, customer.getState());
            statementLoc.executeUpdate();

            // Insert into Person table
            String sqlPerson = "INSERT IGNORE INTO Person (SSN, LastName, FirstName, Address, ZipCode, Telephone) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statementPerson = conn.prepareStatement(sqlPerson);
            statementPerson.setInt(1, ssn);
            statementPerson.setString(2, customer.getLastName());
            statementPerson.setString(3, customer.getFirstName());
            statementPerson.setString(4, customer.getAddress());
            statementPerson.setInt(5, customer.getZipCode());
            statementPerson.setString(6, customer.getTelephone());
            statementPerson.executeUpdate();

            // Insert into Customer table
            String sqlCustomer = "INSERT INTO Customer (ID, Email, Rating, CreditCardNumber) VALUES (?, ?, ?, ?)";
            PreparedStatement statementCustomer = conn.prepareStatement(sqlCustomer);
            statementCustomer.setInt(1, ssn);
            statementCustomer.setString(2, customer.getEmail());
            statementCustomer.setInt(3, customer.getRating());
            statementCustomer.setString(4, customer.getCreditCard());
            statementCustomer.executeUpdate();

            // Insert into Account table
            String sqlAccount = "INSERT INTO Account (DateOpened, Type, CustomerID) VALUES (CURRENT_DATE, 'Limited', ?)";
            PreparedStatement statementAccount = conn.prepareStatement(sqlAccount);
            statementAccount.setInt(1, ssn);
            statementAccount.executeUpdate();

            conn.commit(); // Commit the transaction
            result = "success";
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit to default
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
	}

	public String editCustomer(Customer customer) {
		/*
		 * All the values of the edit customer form are encapsulated in the customer object.
		 * These can be accessed by getter methods (see Customer class in model package).
		 * e.g. firstName can be accessed by customer.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		Connection conn = null;
        String result = "failure";

        try {
        	conn = ConnectionSingleton.getInstance().getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            
            int ssn = Integer.valueOf(customer.getCustomerID().replaceAll("-", ""));
            
            // Upsert into Location table
            String sqlLocationUpsert = "INSERT INTO Location (ZipCode, City, State) VALUES (?, ?, ?) " +
                                       "ON DUPLICATE KEY UPDATE City = VALUES(City), State = VALUES(State)";
            PreparedStatement statementLocation = conn.prepareStatement(sqlLocationUpsert);
            statementLocation.setInt(1, customer.getZipCode());
            statementLocation.setString(2, customer.getCity());
            statementLocation.setString(3, customer.getState());
            statementLocation.executeUpdate();
            
            
            // Update Person table
            String sqlPerson = "UPDATE Person SET LastName = ?, FirstName = ?, Address = ?, ZipCode = ?, Telephone = ? WHERE SSN = ?";
            PreparedStatement statementPerson = conn.prepareStatement(sqlPerson);
            statementPerson.setString(1, customer.getLastName());
            statementPerson.setString(2, customer.getFirstName());
            statementPerson.setString(3, customer.getAddress());
            statementPerson.setInt(4, customer.getZipCode());
            statementPerson.setString(5, customer.getTelephone());
            statementPerson.setInt(6, ssn);
            statementPerson.executeUpdate();

            // Update Customer table
            String sqlCustomer = "UPDATE Customer SET Email = ?, Rating = ?, CreditCardNumber = ? WHERE ID = ?";
            PreparedStatement statementCustomer = conn.prepareStatement(sqlCustomer);
            statementCustomer.setString(1, customer.getEmail());
            statementCustomer.setInt(2, customer.getRating());
            statementCustomer.setString(3, customer.getCreditCard());
            statementCustomer.setInt(4, ssn);
            statementCustomer.executeUpdate();


            conn.commit(); // Commit the transaction
            result = "success";
        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit to default
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
	}

}

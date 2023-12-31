package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.Employee;
import resources.ConnectionSingleton;

public class EmployeeDao {
	/*
	 * This class handles all the database operations related to the employee table
	 */

	public String addEmployee(Employee employee) {

		/*
		 * All the values of the add employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the employee details and return "success" or "failure" based on result of the database insertion.
		 */

		Connection conn = null;
		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			conn.setAutoCommit(false);

			PreparedStatement psLocation = conn.prepareStatement(
				"INSERT IGNORE INTO `Location` (ZipCode, City, State) VALUES (?, ?, ?)"
			);
			psLocation.setInt(1, employee.getZipCode());
			psLocation.setString(2, employee.getCity());
			psLocation.setString(3, employee.getState());
			psLocation.executeUpdate();

			PreparedStatement psPerson = conn.prepareStatement(
					"INSERT IGNORE INTO Person (SSN, LastName, FirstName, Address, ZipCode, Telephone) VALUES (?, ?, ?, ?, ?, ?)"
			);
			psPerson.setInt(1, Integer.parseInt(employee.getEmployeeID())); // holds SSN value fsr?
			psPerson.setString(2, employee.getLastName());
			psPerson.setString(3, employee.getFirstName());
			psPerson.setString(4, employee.getAddress());
			psPerson.setInt(5, employee.getZipCode());
			psPerson.setString(6, employee.getTelephone());
			psPerson.executeUpdate();


			PreparedStatement psEmployee = conn.prepareStatement(
					"INSERT INTO Employee (SSN, StartDate, HourlyRate) VALUES (?, ?, ?, ?)"
			);
			psEmployee.setInt(1, Integer.parseInt(employee.getEmployeeID()));
			psEmployee.setString(2, employee.getStartDate());
			psEmployee.setFloat(3, employee.getHourlyRate());
			psEmployee.executeUpdate();

			conn.commit();
			return "success";
		} catch (Exception e) {
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
			return "failure";
		}

	}

	public String editEmployee(Employee employee) {
		/*
		 * All the values of the edit employee form are encapsulated in the employee object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. firstName can be accessed by employee.getFirstName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		Connection conn = null;
        String result = "failure";

        try {
        	conn = ConnectionSingleton.getInstance().getConnection();
			
            conn.setAutoCommit(false); // Start transaction
            
            int id = Integer.valueOf(employee.getEmployeeID());
            
            // Upsert into Location table
            String sqlLocationUpsert = "INSERT INTO Location (ZipCode, City, State) VALUES (?, ?, ?) " +
                                       "ON DUPLICATE KEY UPDATE City = VALUES(City), State = VALUES(State)";
            PreparedStatement statementLocation = conn.prepareStatement(sqlLocationUpsert);
            statementLocation.setInt(1, employee.getZipCode());
            statementLocation.setString(2, employee.getCity());
            statementLocation.setString(3, employee.getState());
            statementLocation.executeUpdate();
            
            // Update Person table
            String sqlPerson = "UPDATE Person SET LastName = ?, FirstName = ?, Address = ?, ZipCode = ?, Telephone = ? WHERE SSN = ?";
            PreparedStatement statementPerson = conn.prepareStatement(sqlPerson);
            statementPerson.setString(1, employee.getLastName());
            statementPerson.setString(2, employee.getFirstName());
            statementPerson.setString(3, employee.getAddress());
            statementPerson.setInt(4, employee.getZipCode());
            statementPerson.setString(5, employee.getTelephone());
            statementPerson.setInt(6, id);
            statementPerson.executeUpdate();

            // Update Employee table
            String sqlEmployee = "UPDATE Employee SET Email = ?, StartDate = ?, HourlyRate = ? WHERE ID = ?";
            PreparedStatement statementEmployee = conn.prepareStatement(sqlEmployee);
            statementEmployee.setString(1, employee.getEmail());
            statementEmployee.setDate(2, Date.valueOf(employee.getStartDate()));
            statementEmployee.setInt(3, (int) employee.getHourlyRate());
            statementEmployee.setInt(4, id);
            statementEmployee.executeUpdate();

            conn.commit(); // Commit the transaction
            result = "success";
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
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

	}

	public String deleteEmployee(String employeeID) {
		/*
		 * employeeID, which is the Employee's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		
		/*Sample data begins*/
		return "success";
		/*Sample data ends*/

	}

	
	public List<Employee> getEmployees() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to return details about all the employees must be implemented
		 * Each record is required to be encapsulated as a "Employee" class object and added to the "employees" List
		 */

		List<Employee> employees = new ArrayList<>();

		Connection conn = null;
		Statement st;
		ResultSet rs;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			st = conn.createStatement();
			String query = new StringBuilder()
					.append("SELECT \n")
					.append("E.Email, \n")
					.append("P.FirstName, \n")
					.append("P.LastName, \n")
					.append("P.Address, \n")
					.append("E.StartDate, \n")
					.append("P.ZipCode, \n")
					.append("Lc.State, \n")
					.append("Lc.City, \n")
					.append("P.Telephone, \n")
					.append("E.ID AS EmployeeID, \n")
					.append("E.SSN, \n")
					.append("E.HourlyRate\n")
					.append("FROM Employee E\n")
					.append("JOIN Person P ON E.SSN = P.SSN\n")
					.append("JOIN Location Lc ON P.ZipCode = Lc.ZipCode\n")
					.append("JOIN Login L ON P.SSN = L.PersonID\n")
					.append("WHERE L.Role IN ('Manager', 'CustomerRepresentative');")
					.toString();
			rs = st.executeQuery(query);
			while(rs.next()) {
				Employee employee = new Employee();

				employee.setEmail(rs.getString("Email"));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setLastName(rs.getString("LastName"));
				employee.setAddress(rs.getString("Address"));
				employee.setStartDate(rs.getString("StartDate"));
				employee.setState(rs.getString("State"));
				employee.setCity(rs.getString("City"));
				employee.setZipCode(rs.getInt("ZipCode"));
				employee.setTelephone(rs.getString("Telephone"));
				employee.setEmployeeID(rs.getString("SSN"));
				employee.setHourlyRate(rs.getInt("HourlyRate"));

				employees.add(employee);
			}
		} catch(Exception e) {
			System.err.println(e);
		}
		
		return employees;
	}

	public Employee getEmployee(String employeeID) {

		/*
		 * The students code to fetch data from the database based on "employeeID" will be written here
		 * employeeID, which is the Employee's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Employee" class object
		 */

		Employee employee = new Employee();

		Connection conn = null;
		Statement st;
		ResultSet rs;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			st = conn.createStatement();
			String query = new StringBuilder()
					.append("SELECT *")
					.append("FROM Employee E\n")
					.append("JOIN Person P ON E.SSN = P.SSN\n")
					.append("JOIN Location Lc ON P.ZipCode = Lc.ZipCode\n")
					.append("WHERE ")
					.append(String.format("    E.SSN = %s;", employeeID))
					.toString();
			rs = st.executeQuery(query);
			if(rs.next()) {
				employee.setEmail(rs.getString("Email"));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setLastName(rs.getString("LastName"));
				employee.setAddress(rs.getString("Address"));
				employee.setStartDate(rs.getString("StartDate"));
				employee.setCity(rs.getString("City"));
				employee.setState(rs.getString("State"));
				employee.setZipCode(rs.getInt("ZipCode"));
				employee.setTelephone(rs.getString("Telephone"));
				employee.setEmployeeID(Integer.toString(rs.getInt("SSN")));
				employee.setHourlyRate(rs.getInt("HourlyRate"));
			}
		} catch(Exception e) {
			System.err.println(e);
		}
		
		return employee;
	}
	
	public Employee getHighestRevenueEmployee() {
		
		/*
		 * The students code to fetch employee data who generated the highest revenue will be written here
		 * The record is required to be encapsulated as a "Employee" class object
		 */
		
		Employee employee = new Employee();
		Connection conn = null;
		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = new StringBuilder()
					.append("SELECT E.SSN, P.FirstName, P.LastName, E.Email, SUM(M.DistrFee) AS TotalRevenue\n")
					.append("FROM Employee E \n")
					.append("JOIN Person P ON E.SSN = P.SSN \n")
					.append("JOIN Rental R ON E.ID = R.CustRepId \n")
					.append("JOIN `Order` O ON R.OrderId = O.ID \n")
					.append("JOIN Movie M ON R.MovieId = M.ID \n")
					.append("GROUP BY E.ID \n")
					.append("ORDER BY TotalRevenue DESC \n")
					.append("LIMIT 1;")
					.toString();

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				employee.setEmployeeID(String.valueOf(rs.getInt("SSN")));
				employee.setFirstName(rs.getString("FirstName"));
				employee.setLastName(rs.getString("LastName"));
				employee.setEmail(rs.getString("Email"));
				System.out.println(employee.getEmail());
			}
		} catch (Exception e) {
			e.printStackTrace();  // Handle exceptions appropriately
		}
		
		return employee;
	}

	public String getEmployeeID(String username) {
		/*
		 * The students code to fetch data from the database based on "username" will be written here
		 * username, which is the Employee's email address who's Employee ID has to be fetched, is given as method parameter
		 * The Employee ID is required to be returned as a String
		 */
		Connection conn;
		Statement st;
		ResultSet rs;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			st = conn.createStatement();
			String query = new StringBuilder()
					.append("SELECT \n")
					.append("    E.ID AS EmployeeID\n")
					.append("FROM \n")
					.append("    Employee E\n")
					.append("JOIN \n")
					.append("    Person P ON E.SSN = P.SSN\n")
					.append("JOIN \n")
					.append("    Login L ON P.SSN = L.PersonID\n")
					.append("WHERE \n")
					.append(String.format("    L.Username = '[Username]';", username)).toString();
			rs = st.executeQuery(query);
			if(rs.next()) {
				return rs.getString("EmployeeID");
			}
		} catch(Exception e) {
			System.err.println(e);
		}
		return null;
	}

}

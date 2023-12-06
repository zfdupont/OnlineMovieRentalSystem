package dao;

import model.Login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */

	private static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/CSE305";
	public Login login(String username, String password) {
		/*
		 * Return a Login object with role as "manager", "customerRepresentative" or "customer" if successful login
		 * Else, return null
		 * The role depends on the type of the user, which has to be handled in the database
		 * username, which is the email address of the user, is given as method parameter
		 * password, which is the password of the user, is given as method parameter
		 * Query to verify the username and password and fetch the role of the user, must be implemented
		 */
		
		/*Sample data begins*/
		Login login = new Login();
//		login.setRole("customerRepresentative");
		login.setRole("manager");
		return login;
		/*Sample data ends*/
		
	}
	
	public String addUser(Login login) {
		/*
		 * Query to insert a new record for user login must be implemented
		 * login, which is the "Login" Class object containing username and password for the new user, is given as method parameter
		 * The username and password from login can get accessed using getter methods in the "Login" model
		 * e.g. getUsername() method will return the username encapsulated in login object
		 * Return "success" on successful insertion of a new user
		 * Return "failure" for an unsuccessful database operation
		 */

		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "root");
			conn.setAutoCommit(false);

			PreparedStatement psLocation = conn.prepareStatement(
					"INSERT INTO `Login` (Username, Password, Role, PersonID) VALUES (?, ?, ?, ?)"
			);
			psLocation.setString(1, login.getUsername());
			psLocation.setString(2, login.getPassword());
			psLocation.setString(3, login.getRole());
			psLocation.setString(4, login.getPersonID());
			psLocation.executeUpdate();

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
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

}

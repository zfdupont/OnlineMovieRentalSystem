package dao;

import model.Login;
import resources.ConnectionSingleton;

import javax.xml.transform.Result;
import java.sql.*;

public class LoginDao {
	/*
	 * This class handles all the database operations related to login functionality
	 */

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
		Login login = null;
//		login.setRole("customer");
//		login.setRole("customerRepresentative");
//		login.setRole("manager");
//		return login;
		/*Sample data ends*/


		Connection conn = null;
		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			conn.setAutoCommit(false);

			String query = "SELECT * FROM Login L WHERE (L.Username = ? AND L.Password = ?)";
			PreparedStatement statement = conn.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();

			if(rs.next()){
				
				login = new Login();
				login.setUsername(rs.getString("Username"));
				login.setPassword(rs.getString("Password"));
				login.setRole(rs.getString("Role"));
				login.setPersonID(rs.getString("PersonID"));
				System.out.printf("Logging in (%s, %s, %s, %s)", login.getUsername(), login.getPassword(), login.getRole(), login.getPersonID());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return login;
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
			conn = ConnectionSingleton.getInstance().getConnection();
			conn.setAutoCommit(false);

			PreparedStatement psLocation = conn.prepareStatement(
					"INSERT INTO `Login` (Username, Password, Role, PersonID) VALUES (?, ?, ?, ?)"
			);
			psLocation.setString(1, login.getUsername());
			psLocation.setString(2, login.getPassword());
			psLocation.setString(3, login.getRole());
			psLocation.setInt(4, Integer.valueOf(login.getPersonID()));
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
		}

	}

}

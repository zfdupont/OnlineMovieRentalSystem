package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Employee;
import model.Movie;
import model.Account;

public class AccountDao {

	private static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/CSE305";
	public int getSalesReport(Account account) {
			
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to get sales report for a particular month must be implemented
		 * account, which has details about the month and year for which the sales report is to be generated, is given as method parameter
		 * The month and year are in the format "month-year", e.g. "10-2018" and stored in the dateOpened attribute of account object
		 * The month and year can be accessed by getter method, i.e., account.getAcctCreateDate()
		 */
	
		int income = 0;


		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(CONNECTION_STRING, "root", "root");

			String sql = new StringBuilder()
					.append("SELECT SUM(M.DistrFee) AS TotalIncome ")
					.append("FROM `Order` O ")
					.append("JOIN Rental R ON O.ID = R.OrderId ")
					.append("JOIN Movie M ON R.MovieId = M.ID ")
					.append("WHERE YEAR(O.DateTime) = ? AND MONTH(O.DateTime) = ?")
					.toString();

			String dateString[] = account.getAcctCreateDate().split("-");


			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, Integer.parseInt(dateString[0]));
			st.setInt(2, Integer.parseInt(dateString[1]));

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				income = rs.getInt("TotalIncome");
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace(); // Handle exceptions during close
				}
			}
		}

		return income;
	}
	
	public String setAccount(String customerID, String accountType) {

		
		/*Sample data begins*/
		return "success";
		/*Sample data ends*/

	}
	

}

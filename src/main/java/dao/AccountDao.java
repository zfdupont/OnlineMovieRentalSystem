package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Employee;
import model.Movie;
import model.Account;
import model.Customer;
import resources.ConnectionSingleton;

public class AccountDao {


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
            conn = ConnectionSingleton.getInstance().getConnection();

            // SQL query to calculate total income from subscription fees
            String sqlSubscriptionFee = new StringBuilder()
                    .append("SELECT A.Type, COUNT(DISTINCT R.MovieId) AS MoviesOut, COUNT(DISTINCT A.ID) AS NumAccounts ")
                    .append("FROM Account A ")
                    .append("JOIN Rental R ON A.ID = R.AccountId ")
                    .append("JOIN `Order` O ON R.OrderId = O.ID ")
                    .append("WHERE MONTH(O.DateTime) = ? AND YEAR(O.DateTime) = ? ")
                    .append("GROUP BY A.Type, A.ID")
                    .toString();

            String dateString[] = account.getAcctCreateDate().split("-");

            // Calculate income from subscription fees
            PreparedStatement stSubscriptionFee = conn.prepareStatement(sqlSubscriptionFee);
            stSubscriptionFee.setInt(1, Integer.parseInt(dateString[0]));
            stSubscriptionFee.setInt(2, Integer.parseInt(dateString[1]));
            ResultSet rs = stSubscriptionFee.executeQuery();

            while (rs.next()) {
                String type = rs.getString("Type");
                int moviesOut = rs.getInt("MoviesOut");
                int numAccounts = rs.getInt("NumAccounts");
                income += calculateMonthlyFee(type, moviesOut) * numAccounts;
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

	private int calculateMonthlyFee(String planType, int moviesOut) {
        // Assuming the fee depends on the number of movies out at a time
        if (planType.equals("Limited")) {
            return 10; // Flat rate for limited plan
        } else if (planType.startsWith("Unlimited")) {
            switch (moviesOut) {
                case 1: return 15;
                case 2: return 20;
                case 3: return 25;
                default: return 0; // Fallback for unexpected cases
            }
        } else {
            return 0; // Unknown plan type
        }
    }
	
	public String setAccount(String customerID, String accountType) {

		System.out.println(customerID);
		Connection conn = null;
		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			conn.setAutoCommit(false);

			String query = "UPDATE Account SET `Type` = ? WHERE CustomerId = ?";
            PreparedStatement statement = conn.prepareStatement(query);

            statement.setString(1, accountType);
            statement.setInt(2, Integer.valueOf(customerID.replaceAll("-", "")));
            
            statement.executeUpdate();
            
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
	
	public Account getAccount(String customerID) {

		Account account = new Account();
		Connection conn = null;
		PreparedStatement st;
		ResultSet rs;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			
			String query = new StringBuilder()
					.append("SELECT * FROM ")
					.append("Account ")
					.append("WHERE CustomerID = ?;")
					.toString();
			st = conn.prepareStatement(query);
			st.setInt(1, Integer.valueOf(customerID));
			rs = st.executeQuery();
			if(rs.next()) {
				account.setAccountID(rs.getInt("ID"));
				account.setAccountType(rs.getString("Type"));
				account.setAcctCreateDate(rs.getString("DateOpened"));
				account.setCustomerID(Integer.toString(rs.getInt("CustomerID")));
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
		
		return account;
	}
	

}

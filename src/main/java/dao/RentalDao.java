package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.Rental;
import resources.ConnectionSingleton;

public class RentalDao {
	public List<Rental> getOrderHisroty(String customerID) {
		
		List<Rental> rentals = new ArrayList<Rental>();
			
		Connection conn = null;

        try {
        	conn = ConnectionSingleton.getInstance().getConnection();
			
            String sql = "SELECT O.ID AS OrderId, R.MovieId, R.CustRepId " +
                    "FROM `Order` O " +
                    "JOIN Rental R ON O.ID = R.OrderId " +
                    "JOIN Account A ON R.AccountId = A.ID " +
                    "WHERE A.CustomerId = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Integer.valueOf(customerID.replaceAll("-", "")));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	Rental rental = new Rental();
            	int orderId = resultSet.getInt("OrderId");
                int movieId = resultSet.getInt("MovieId");
                int custRepId = resultSet.getInt("CustRepId");
                rental.setOrderID(orderId); rental.setMovieID(movieId); rental.setCustomerRepID(custRepId);
                rentals.add(rental);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace(); // Handle exceptions during close
                }
            }
        }
		
		return rentals;
		
	}

}

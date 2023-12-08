package dao;

import java.sql.*;
import java.util.*;

import model.Order;
import model.Employee;
import model.Movie;
import resources.ConnectionSingleton;

//import com.mysql.cj.jdbc.Driver;

public class MovieDao {


	public List<Movie> getMovies() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of all the movies has to be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = "SELECT ID, Name, Type, DistrFee, NumCopies, Rating FROM Movie";

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
		
		return movies;
	}
	
	public Movie getMovie(String movieID) {

		/*
		 * The students code to fetch data from the database based on "movieID" will be written here
		 * movieID, which is the Movie's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Movie" class object
		 */

		Movie movie = new Movie();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = "SELECT ID, Name, Type, DistrFee, NumCopies, Rating FROM Movie WHERE ID = ?";
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, Integer.valueOf(movieID.replaceAll("-","")));
			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
		
		return movie;
	}
	
	public String addMovie(Movie movie) {

		/*
		 * All the values of the add movie form are encapsulated in the movie object.
		 * These can be accessed by getter methods (see Employee class in model package).
		 * e.g. movieName can be accessed by movie.getMovieName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database insertion of the movie details and return "success" or "failure" based on result of the database insertion.
		 */
		
		Connection conn = null;
		try {
			conn = ConnectionSingleton.getInstance().getConnection();
			conn.setAutoCommit(false);

			PreparedStatement psMovie = conn.prepareStatement(
				"INSERT INTO Movie (`Name`, `Type`, Rating, DistrFee, NumCopies) VALUES (?,?,?,?,?)"
			);
			psMovie.setString(1, movie.getMovieName());
			psMovie.setString(2, movie.getMovieType());
			psMovie.setInt(3, movie.getRating());
			psMovie.setInt(4, movie.getDistFee());
			psMovie.setInt(5, movie.getNumCopies());
			psMovie.executeUpdate();

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
	
	public String editMovie(Movie movie) {
		/*
		 * All the values of the edit movie form are encapsulated in the movie object.
		 * These can be accessed by getter methods (see Movie class in model package).
		 * e.g. movieName can be accessed by movie.getMovieName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		
		Connection conn = null;
		boolean editSuccessful = false;
	    try {
	    	conn = ConnectionSingleton.getInstance().getConnection();
			conn.setAutoCommit(false); // Start transaction

	        String sql = "UPDATE Movie SET Name = ?, Type = ?, DistrFee = ?, NumCopies = ?, Rating = ? WHERE ID = ?";

	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setString(1, movie.getMovieName());
	        statement.setString(2, movie.getMovieType());
	        statement.setInt(3, movie.getDistFee());
	        statement.setInt(4, movie.getNumCopies());
	        statement.setInt(5, movie.getRating());
	        statement.setInt(6, movie.getMovieID());

	        int rowsAffected = statement.executeUpdate();
	        editSuccessful = rowsAffected > 0;
	        if (editSuccessful) {
	            conn.commit(); // Commit transaction
	            
	        } else {
	            conn.rollback(); // Rollback transaction if no rows affected
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
	                conn.setAutoCommit(true); // Reset auto-commit
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return editSuccessful ? "success" : "failure";

	}

	public String deleteMovie(String movieID) {
		/*
		 * movieID, which is the Movie's ID which has to be deleted, is given as method parameter
		 * The sample code returns "success" by default.
		 * You need to handle the database deletion and return "success" or "failure" based on result of the database deletion.
		 */
		
		Connection conn = null;
	    boolean deleteSuccessful = false;

	    try {
	    	conn = ConnectionSingleton.getInstance().getConnection();
	        conn.setAutoCommit(false); // Start transaction

	        String sql = "DELETE FROM Movie WHERE ID = ?";

	        PreparedStatement statement = conn.prepareStatement(sql);
	        statement.setInt(1, Integer.parseInt(movieID));

	        int rowsAffected = statement.executeUpdate();
	        if (rowsAffected > 0) {
	            conn.commit(); // Commit transaction
	            deleteSuccessful = true;
	        } else {
	            conn.rollback(); // Rollback transaction if no rows affected
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
	                conn.setAutoCommit(true); // Reset auto-commit
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    return deleteSuccessful ? "success" : "failure";
	}
	
	
	public List<Movie> getBestsellerMovies() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of the bestseller movies has to be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = new StringBuilder()
					.append("SELECT M.ID, M.Name, M.Type, M.DistrFee, M.NumCopies, M.Rating, SUM(M.DistrFee) AS TotalEarned ")
					.append("FROM Movie M JOIN Rental R ON M.ID = R.MovieId ")
					.append("GROUP BY M.ID, M.Name, M.Type, M.DistrFee, M.NumCopies, M.Rating ")
					.append("ORDER BY TotalEarned DESC")
					.toString();

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		} finally {

		}
//		/*Sample data begins*/
//		for (int i = 0; i < 5; i++) {
//			Movie movie = new Movie();
//			movie.setMovieID(1);
//			movie.setMovieName("The Godfather");
//			movie.setMovieType("Drama");
//			movies.add(movie);
//		}
//		/*Sample data ends*/
		
		return movies;

	}

	public List<Movie> getSummaryListing(String searchKeyword) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of summary listing of revenue generated by a particular movie or movie type must be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 * Store the revenue generated by an movie in the soldPrice attribute, using setSoldPrice method of each "movie" object
		 */

		List<Movie> movies = new ArrayList<Movie>();
				
		/*Sample data begins*/
		for (int i = 0; i < 6; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movie.setDistFee(10000);
			movie.setNumCopies(3);
			movie.setRating(5);
			movies.add(movie);
		}
		/*Sample data ends*/
		
		return movies;

	}
	
	
	

	public List<Movie> getMovieSuggestions(String customerID) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch movie suggestions for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom the movie suggestions are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			// Step 1: Find the most ordered genre for the given customer
			String mostOrderedGenreQuery = new StringBuilder()
					.append("SELECT M.Type, COUNT(*) as GenreCount ")
					.append("FROM Movie M ")
					.append("JOIN Rental R ON M.ID = R.MovieId ")
					.append("JOIN Account A ON R.AccountId = A.ID ")
					.append("WHERE A.CustomerId = ? ")
					.append("GROUP BY M.Type ")
					.append("ORDER BY GenreCount DESC ")
					.append("LIMIT 1;")
					.toString();

			PreparedStatement mostOrderedGenreStmt = conn.prepareStatement(mostOrderedGenreQuery);
			mostOrderedGenreStmt.setInt(1, Integer.valueOf(customerID.replaceAll("-","")));
			ResultSet genreResultSet = mostOrderedGenreStmt.executeQuery();

			if (!genreResultSet.next()) {
				return movies; // No genre found
			}
			String mostOrderedGenre = genreResultSet.getString("Type");

			// Get customer zip code
			String customerZipSql = "SELECT CAST(ZipCode AS CHAR) AS ZipString FROM Person WHERE SSN = ?";
			PreparedStatement customerZipStmt = conn.prepareStatement(customerZipSql);
			customerZipStmt.setInt(1, Integer.valueOf(customerID.replaceAll("-","")));
			ResultSet zipResultSet = customerZipStmt.executeQuery();

			if (!zipResultSet.next()) {
				return movies; // No zip code found
			}
			
			movies = (ArrayList<Movie>)getMoviesByType(mostOrderedGenre);
//			String customerZipPrefix = zipResultSet.getString("ZipString").substring(0, 2);
		
//			// Step 2: Recommend movies from the most ordered genre that the customer hasn't ordered yet but
//			// users in the customer's area have
//
//			String suggestedMoviesQuery = "SELECT DISTINCT M.ID, M.Name, M.Type " +
//                    "FROM Movie M " +
//                    "JOIN Rental R ON M.ID = R.MovieId " +
//                    "JOIN Account A ON R.AccountId = A.ID " +
//                    "JOIN Customer C ON A.CustomerId = C.ID " +
//                    "JOIN Person P ON C.ID = P.SSN " +
//                    "WHERE M.Type = ? AND M.ID NOT IN ( " +
//                    "    SELECT MovieId " +
//                    "    FROM Rental " +
//                    "    WHERE AccountId = A.ID " +
//                    ") AND CAST(P.ZipCode AS CHAR) LIKE ? " +
//                    "ORDER BY M.Name";
//
//			PreparedStatement suggestedMoviesStmt = conn.prepareStatement(suggestedMoviesQuery);
//			suggestedMoviesStmt.setString(1, mostOrderedGenre);
//			suggestedMoviesStmt.setString(2, customerZipPrefix + "%");
//
//			ResultSet rs = suggestedMoviesStmt.executeQuery();
//
//			while (rs.next()) {
//				Movie movie = new Movie();
//				movie.setMovieID(rs.getInt("ID"));
//				movie.setMovieName(rs.getString("Name"));
//				movie.setMovieType(rs.getString("Type"));
//				movies.add(movie);
//			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
		
		return movies;

	}
	
	public List<Movie> getCurrentMovies(String customerID){
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch currently hold movie for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom currently hold movie are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();
		Connection conn = null;

        try {
        	conn = ConnectionSingleton.getInstance().getConnection();

            String sql = "SELECT M.ID, M.Name, M.Type " +
                         "FROM Movie M " +
                         "JOIN Rental R ON M.ID = R.MovieId " +
                         "JOIN `Order` O ON R.OrderId = O.ID " +
                         "JOIN Account A ON R.AccountId = A.ID " +
                         "WHERE A.CustomerId = ? AND O.ReturnDate IS NULL";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Integer.valueOf(customerID.replaceAll("-", "")));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	Movie movie = new Movie();
                movie.setMovieID(resultSet.getInt("ID"));
                movie.setMovieName(resultSet.getString("Name"));
                movie.setMovieType(resultSet.getString("Type"));
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        } 
		
		return movies;
		
		
		
	}
	
public List<Movie> getQueueOfMovies(String customerID){
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch movie queue for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom movie queue are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();
		Connection conn = null;

        try {
        	conn = ConnectionSingleton.getInstance().getConnection();
            
            String sql = "SELECT MQ.MovieId, M.Name, M.Type " +
                         "FROM MovieQ MQ " +
                         "JOIN Movie M ON MQ.MovieId = M.ID " +
                         "WHERE MQ.AccountId IN (SELECT ID FROM Account WHERE CustomerId = ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, Integer.valueOf(customerID.replaceAll("-", "")));

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
            	Movie movie = new Movie();
                movie.setMovieID(resultSet.getInt("MovieID"));
                movie.setMovieName(resultSet.getString("Name"));
                movie.setMovieType(resultSet.getString("Type"));
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions appropriately
        } 		
		return movies;
	}
	
	

//	public List getMoviesBySeller(String sellerID) {
//		
//		/*
//		 * The students code to fetch data from the database will be written here
//		 * Query to fetch movies sold by a given seller, indicated by sellerID, must be implemented
//		 * sellerID, which is the Sellers's ID who's movies are fetched, is given as method parameter
//		 * The bid and order details of each of the movies should also be fetched
//		 * The bid details must have the highest current bid for the movie
//		 * The order details must have the details about the order in which the movie is sold
//		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
//		 * Each bid record is required to be encapsulated as a "Bid" class object and added to the "bids" List
//		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
//		 * The movies, bids and orders Lists have to be added to the "output" List and returned
//		 */
//
//		List output = new ArrayList();
//		List<Movie> movies = new ArrayList<Movie>();
//		List<Bid> bids = new ArrayList<Bid>();
//		List<Order> orders = new ArrayList<Order>();
//		
//		/*Sample data begins*/
//		for (int i = 0; i < 4; i++) {
//			Movie movie = new Movie();
//			movie.setMovieID(123);
//			movie.setDescription("sample description");
//			movie.setType("BOOK");
//			movie.setName("Sample Book");
//			movies.add(movie);
//			
//			Bid bid = new Bid();
//			bid.setCustomerID("123-12-1234");
//			bid.setBidPrice(120);
//			bids.add(bid);
//			
//			Order order = new Order();
//			order.setMinimumBid(100);
//			order.setBidIncrement(10);
//			order.setOrderID(123);
//			orders.add(order);
//		}
//		/*Sample data ends*/
//		
//		output.add(movies);
//		output.add(bids);
//		output.add(orders);
//		
//		return output;
//	}

	public List<Movie> getMovieTypes() {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 * A query to fetch the unique movie types has to be implemented
		 * Each movie type is to be added to the "movie" object using setType method
		 */
		
		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = "SELECT DISTINCT `Type` FROM Movie";

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}

		return movies;
	}

	public List getMoviesByName(String movieName) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The movieName, which is the movie's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieName in their name has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = String.format("SELECT * FROM Movie WHERE Name LIKE '%%%s%%'", movieName);

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}

		return movies;
	}
	
	public List getMoviesByActor(String actorName) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The movieName, which is the movie's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieName in their name has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = new StringBuilder()
					.append("SELECT M.ID, M.Name, M.Type ")
					.append("FROM Movie M ")
					.append("JOIN AppearedIn AI ON M.ID = AI.MovieId ")
					.append("JOIN Actor A ON AI.ActorId = A.ID ")
					.append("WHERE A.Name LIKE CONCAT('%',?,'%');")
					.toString();

			PreparedStatement st = conn.prepareStatement(query);
			st.setString(1, actorName);
			ResultSet rs = st.executeQuery();
			System.out.println(rs.getFetchSize());
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}
			
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
		
		return movies;
	}
	

	public List getMoviesByType(String movieType) {
		
		/*
		 * The students code to fetch data from the database will be written here
		 * The movieType, which is the movie's type on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieType as their type has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = String.format("SELECT * FROM Movie WHERE `Type` = '%s'", movieType);

			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			System.out.println(rs.getFetchSize());
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
		return movies;
	}
	
	public List getMovieRentalsByName(String movieName) {
		
		

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = new StringBuilder()
					.append("SELECT DISTINCT M.ID, M.Name, M.Type FROM Movie M ")
					.append("JOIN Rental R ON ID = R.MovieID ")
					.append("WHERE Name LIKE '%").append(movieName).append("%';")
					.toString();
			
			System.out.println(query);
			PreparedStatement st = conn.prepareStatement(query);

//			st.setString(1, movieName);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}

		return movies;
	}
	
	public List getMovieRentalsByCustomer(String customerName) {
		
		

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = new StringBuilder()
					.append("SELECT DISTINCT M.ID, M.Name, M.Type FROM Movie M ")
					.append("JOIN Rental R ON M.ID = R.MovieID ")
					.append("JOIN Account A ON R.AccountID = A.ID ")
					.append("JOIN Customer C ON A.CustomerID = C.ID ")
					.append("JOIN Person P ON C.ID = P.SSN ")
					.append("WHERE (P.FirstName LIKE CONCAT('%',?,'%') OR P.LastName LIKE CONCAT('%',?,'%'));")
					.toString();

			PreparedStatement st = conn.prepareStatement(query);

			st.setString(1, customerName);
			st.setString(2, customerName);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
		

		
		return movies;
	}
	

	public List getMovieRentalsByType(String movieType) {
		
	

		List<Movie> movies = new ArrayList<Movie>();

		Connection conn = null;

		try {
			conn = ConnectionSingleton.getInstance().getConnection();

			String query = new StringBuilder()
					.append("SELECT DISTINCT M.ID, M.Name, M.Type FROM Movie M ")
					.append("JOIN Rental R ON ID = R.MovieID ")
					.append("WHERE M.Type = ?;")
					.toString();

			PreparedStatement st = conn.prepareStatement(query);

			st.setString(1, movieType);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("ID"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}
		} catch (Exception e) {
			e.printStackTrace(); // Handle exceptions appropriately
		}
		
		return movies;
	}
	

	public List<Movie> getBestsellersForCustomer(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList.
		 * Query to get the Best-seller list of movies for a particular customer, indicated by the customerID, has to be implemented
		 * The customerID, which is the customer's ID for whom the Best-seller movies have to be fetched, is given as method parameter
		 */

		List<Movie> movies = new ArrayList<Movie>();
				
		/*Sample data begins*/
		for (int i = 0; i < 6; i++) {
			Movie movie = new Movie();
			movie.setMovieID(1);
			movie.setMovieName("The Godfather");
			movie.setMovieType("Drama");
			movie.setDistFee(10000);
			movie.setNumCopies(3);
			movie.setRating(5);
			movies.add(movie);
		}
		/*Sample data ends*/
		
		return movies;

	}

}

package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LocalDbConnect {
	//STEP 1: Identifying credentials
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/cs336";

	// Database credentials
	static final String USER = "newuser";
	static final String PASS = "test";
	
	public static ResultSet executeSelectQuery(String query){
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
			//STEP 4: Execute query
			ResultSet rs = stmt.executeQuery(query);
			return rs;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static int executeInsertQuery(String query){
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
			//STEP 4: Execute query
			int updated = stmt.executeUpdate(query); //returns 0 or 1 depending on success
			return updated;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}

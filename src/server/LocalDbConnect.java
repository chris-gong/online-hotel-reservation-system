package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import java.sql.CallableStatement;

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
	//Literally the same thing as insert. might want to change later
	public static int executeUpdateQuery(String query){
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
	//assumes the format of the query is "call name_of_proc(in?, in?, ....., out?, out?, .......)"
	public static ArrayList executeStoredProcedure(String query, ArrayList<String> inParams,ArrayList<Integer> outParams){
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
			//STEP 4: Register in and out parameters
			CallableStatement cStmt = conn.prepareCall("{"+query+"}");
			for(int i = 1; i <= inParams.size(); i++){
				cStmt.setString(i, inParams.get(i-1));
			}
			for(int i = inParams.size() + 1; i <= inParams.size() + outParams.size(); i++){
				cStmt.registerOutParameter(i, outParams.get(i - inParams.size() - 1));
			}
			//STEP 5: Call the procedure
			boolean hasResults = cStmt.execute();
			if(hasResults){
				//for now, assume all the output parameters are integers
				ArrayList results = new ArrayList();
				for(int i = inParams.size() + 1; i <= inParams.size() + outParams.size(); i++){
					results.add(cStmt.getInt(i)); 
				}
				return results;
			}
			else{
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static ResultSet executeInsertAndRetrieveKeys(String query){
		Connection conn = null;
		//PreparedStatement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			PreparedStatement stmt = (PreparedStatement) conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			//STEP 4: Execute query
			int updated = stmt.executeUpdate(); //returns 0 or 1 depending on success
			return stmt.getGeneratedKeys(); //returns a result set of keys of the entries just inserted
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/cs336";

	// Database credentials
	static final String USER = "newuser";
	static final String PASS = "test";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// STEP 3: Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			// STEP 4: Execute a query
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT password, firstname, lastname from users where email='" + email + "'";
			ResultSet rs = stmt.executeQuery(sql);
			try {
				if(!rs.next()) {
					request.setAttribute("message", "Invalid Email");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
				else {
					String s=rs.getString("password");
					if(s.equals(password)) {
						String name = rs.getString("firstname")+" "+rs.getString("lastname");
						System.out.println(name);
						request.setAttribute("name", name);
						request.getRequestDispatcher("/home.jsp").forward(request, response);
					}else{
						request.setAttribute("message", "Invalid Password");
						request.getRequestDispatcher("/index.jsp").forward(request, response);
					}
				}
			}catch (SQLException e ) {
				e.printStackTrace();
			}
			
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}
}

package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/cs336";

	// Database credentials
	static final String USER = "newuser";
	static final String PASS = "test";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
			String fname = request.getParameter("rfname");
			String lname = request.getParameter("rlname");
			String email = request.getParameter("remail");
			String address = request.getParameter("raddress");
			String password = request.getParameter("rpassword");
			String phone = request.getParameter("rphone");

			// verify input
			if (fname.length() < 2) {
				// invalid length
			}
			if (lname.length() < 2) {
				// invalid length
			}
			// in the future we can send a web request to email checker.net
			if (!email.contains("@")) {
				// invalid email
				// check if email exists
			}
			if (password.length() < 6) {
				// passowrd must be greater than 6
			}
			if (!(phone.matches("[0-9]+") && phone.length() > 9)) {
				// invalid phone number
			}

			// check if email is already in database.
			Statement s = conn.createStatement();
			String emailCheck = "select email from users where email ='" + email + "'";
			ResultSet eResult = s.executeQuery(emailCheck);
			try {
				if (!eResult.next()) {
					Statement s2 = conn.createStatement();
					String insertUser = "insert into users values('" + email + "','" + fname + "','" + lname + "','"
							+ password + "','0','" + phone + "','" +address +"')";
					s2.executeUpdate(insertUser);
					request.setAttribute("message", "Account successfully registered");
					//response.sendRedirect("index.jsp");
					request.getRequestDispatcher("/register.jsp").forward(request, response);
				}else {
					System.out.println("dup email");
					response.sendRedirect("register.jsp");
				}

			} catch (SQLException e) {
				//if email is in database
				//e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

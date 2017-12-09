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
import server.*;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet(urlPatterns="/Register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		System.out.println("entering register page");
		request.getRequestDispatcher("/register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fname = request.getParameter("rfname");
		String lname = request.getParameter("rlname");
		String email = request.getParameter("remail");
		String address = request.getParameter("raddress");
		String password = request.getParameter("rpassword");
		String phone = request.getParameter("rphone");

		if (!(phone.matches("[0-9]+") && phone.length() > 9)) {
			// invalid phone number
			request.setAttribute("message", "Phone number must only contain numbers");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}

		// check if email is already in database.
		String emailCheck = "select email from users where email ='" + email + "'";
		ResultSet eResult = LocalDbConnect.executeSelectQuery(emailCheck);
		System.out.println("Before checking results");
		try {
			if (!eResult.next()) {
				String insertUser = "insert into users(email,firstname,lastname,password,phone_num,address) values('"
						+ email + "','" + fname + "','" + lname + "','" + password + "','" + phone + "','" + address
						+ "')";
				LocalDbConnect.executeInsertQuery(insertUser);
				request.setAttribute("message", "Account successfully registered");
				request.getRequestDispatcher("/index.jsp").forward(request, response);

			} else {
				request.setAttribute("message", "This email already exists");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}

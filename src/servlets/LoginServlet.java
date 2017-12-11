package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.*;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(urlPatterns = "/Home")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		HttpSession session = request.getSession(true);
		//if the user is already logged in, then just send him/her to the homepage
		if(session.getAttribute("user_id") != null){
			System.out.println(session.getAttribute("user_id") + " " + session.getAttribute("name"));
			request.setAttribute("name", session.getAttribute("name"));
			request.getRequestDispatcher("/home.jsp").forward(request, response);
		}
		else{
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String sql;
		sql = "SELECT user_id, password, firstname, lastname,isAdmin from users where email='" + email + "'";
		
		ResultSet rs = LocalDbConnect.executeSelectQuery(sql);
		try {
			if(!rs.next()) {
				request.setAttribute("message", "Invalid Email");
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
			else {
				String s=rs.getString("password");
				String admincheck=rs.getString("isAdmin");
				if(s.equals(password) && Integer.parseInt(admincheck)==1) {
					String name = rs.getString("firstname")+" "+rs.getString("lastname");
					String userId = rs.getString("user_id");
					System.out.println(name);
					request.setAttribute("name", name);
					HttpSession session = request.getSession(true);
					session.setAttribute("user_id", userId);
					session.setAttribute("name", name);
					request.getRequestDispatcher("/admin.jsp").forward(request, response);
					
				}
				if(s.equals(password)) {
					String name = rs.getString("firstname")+" "+rs.getString("lastname");
					String userId = rs.getString("user_id");
					System.out.println(name);
					request.setAttribute("name", name);
					HttpSession session = request.getSession(true);
					session.setAttribute("user_id", userId);
					session.setAttribute("name", name);
					request.getRequestDispatcher("/home.jsp").forward(request, response);
				}else{
					request.setAttribute("message", "Invalid Password");
					request.getRequestDispatcher("/index.jsp").forward(request, response);
				}
			}
		}catch (SQLException e ) {
			e.printStackTrace();
		}
	}
}

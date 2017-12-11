package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.LocalDbConnect;

/**
 * Servlet implementation class ManageAccountServlet
 */
@WebServlet(urlPatterns = "/Manage")
public class ManageAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManageAccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isPassword = request.getParameter("change_password");
		String isEmail = request.getParameter("change_email");
		String isAddress = request.getParameter("change_address");
		String isCard = request.getParameter("change_card");

		if (isPassword != null) {
			request.setAttribute("change", "Password");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		} else if (isEmail != null) {
			request.setAttribute("change", "Email");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		} else if (isAddress != null) {
			request.setAttribute("change", "Address");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		} else if (isCard != null) {
			request.setAttribute("change", "Credit Card");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oldPassword = request.getParameter("old_password");
		String oldEmail = request.getParameter("old_email");
		String oldAddress = request.getParameter("old_address");
		String oldCard = request.getParameter("old_card");
		
		HttpSession sess = request.getSession(true);
		String userId = (String)sess.getAttribute("user_id");
		System.out.println(userId);
		
		
		if(oldPassword!=null) {
			String newPassword = request.getParameter("new_password");
			String passwordCheck = "select password from users where password ='"+oldPassword+"'and user_id ="+ userId ;
			ResultSet rs = LocalDbConnect.executeSelectQuery(passwordCheck);
			try {
				//if user entered in valid old password
				if(rs.next()) {
					String updatePassword = "update users set password ='"+newPassword+"' where user_id ="+ userId; 
					LocalDbConnect.executeUpdateQuery(updatePassword);
					request.setAttribute("update", "Password Updated");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}else {
					request.setAttribute("update", "Wrong old password");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}

		}else if(oldEmail!=null) {
			String newEmail = request.getParameter("new_email");
			String emailCheck = "select email from users where email ='"+oldEmail+"'and user_id ="+ userId ;
			ResultSet rs = LocalDbConnect.executeSelectQuery(emailCheck);
			try {
				//if user entered in valid old email
				if(rs.next()) {
					String updateEmail = "update users set email ='"+newEmail+"' where user_id ="+ userId; 
					LocalDbConnect.executeUpdateQuery(updateEmail);
					request.setAttribute("update", "Email Updated");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}else {
					request.setAttribute("update", "Wrong old email");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else if(oldAddress!=null) {
			String newAddress = request.getParameter("new_address");
			String addressCheck = "select address from users where address ='"+oldAddress+"'and user_id ="+ userId ;
			ResultSet rs = LocalDbConnect.executeSelectQuery(addressCheck);
			try {
				//if user entered in valid old email
				if(rs.next()) {
					String updateAddress = "update users set address ='"+newAddress+"' where user_id ="+ userId; 
					LocalDbConnect.executeUpdateQuery(updateAddress);
					request.setAttribute("update", "Address Updated");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}else {
					request.setAttribute("update", "Wrong old address");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			//have to change other fields as well. needs more testing
		}else if(oldCard!=null) {
			String newCard = request.getParameter("new_card");
			String cardCheck = "select C_number from users where C_number ='"+oldCard+"'and user_id ="+ userId ;
			ResultSet rs = LocalDbConnect.executeSelectQuery(cardCheck);
			try {
				//if user entered in valid old email
				if(rs.next()) {
					String updateCard = "update credit_cards set address ='"+newCard+"' where user_id ="+ userId; 
					LocalDbConnect.executeUpdateQuery(updateCard);
					request.setAttribute("update", "Credit Card Updated");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}else {
					request.setAttribute("update", "Wrong old Credit card");
					request.getRequestDispatcher("/manageAccount.jsp").forward(request, response);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}

}

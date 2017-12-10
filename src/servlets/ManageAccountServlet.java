package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ManageAccountServlet
 */
@WebServlet(urlPatterns="/Manage")
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String isPassword = request.getParameter("change_password");
		String isEmail = request.getParameter("change_email");
		String isAddress = request.getParameter("change_address");
		String isCard = request.getParameter("change_card");
		HttpSession session = request.getSession(true);
		System.out.println(session.getAttribute("user_id") + " " + session.getAttribute("name"));

		if(isPassword!=null) {
			request.setAttribute("change", "Password");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		}else if(isEmail!=null) {
			request.setAttribute("change", "Email");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		}else if(isAddress!=null) {
			request.setAttribute("change", "Address");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		}else if(isCard!=null) {
			request.setAttribute("change", "Credit Card");
			request.getRequestDispatcher("/changeCredential.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		response.sendRedirect("home.jsp");
		
	}

}

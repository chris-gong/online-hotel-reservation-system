package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import server.LocalDbConnect;

/**
 * Servlet implementation class Reservations
 */

@WebServlet(urlPatterns="/Review")
public class ReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//get userid first
		HttpSession session = request.getSession(true);
		String id = (String) session.getAttribute("user_id");
		int idNum = Integer.parseInt(id);
		System.out.println(idNum);

		String query = "select * from reservations where user_id = " + id;
		//all reservaitons for the userid
		ResultSet out = LocalDbConnect.executeSelectQuery(query);
		
		
		ArrayList <String> invoice = new ArrayList<String>();
		ArrayList <String> resDate = new ArrayList<String>();
		ArrayList <String> cnum = new ArrayList<String>();
		
		try {
			while (out.next()) {
				//System.out.println(first.getString("maxAV")); //type
				//request.setAttribute("type", first.getString("type"));
				
				invoice.add(out.getString("invoice_no"));
				resDate.add(out.getString("Res_Date"));
				cnum.add(out.getString("c_number"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch");
			e.printStackTrace();
		}
		
		request.setAttribute("invoice", invoice);
		request.setAttribute("resDate", resDate);
		request.setAttribute("cnum", cnum);
		
		
		request.getRequestDispatcher("/reservations.jsp").forward(request,response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
	}

}

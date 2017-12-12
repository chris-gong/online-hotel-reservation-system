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
 * Servlet implementation class MakeReviewServlet
 */
@WebServlet("/MakeReviewServlet")
public class MakeReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession session = request.getSession(true);
		String id = (String) session.getAttribute("user_id");
		int idNum = Integer.parseInt(id);
		System.out.println("id " + idNum);
		System.out.println("asdasd");
		String invoiceNum = (String) request.getAttribute("invoiceNum");
		System.out.println("Invoice num 1 " + invoiceNum);
		
		
		//getting breakfasts to make a review on
		String bquery = "select distinct * from breakfast_orders where invoice_no= " + invoiceNum + " AND \n" + 
				"b_type not in\n" + 
				"(select b_type from breakfast_reviews where invoice_no= " + invoiceNum + " )";
				
				//"select distinct * from breakfast_orders where invoice_no = " + invoiceNum;
		ResultSet breakfasts = LocalDbConnect.executeSelectQuery(bquery);
		
		ArrayList <String> btype = new ArrayList<String>();
		ArrayList <String> hotelid = new ArrayList<String>();
		ArrayList <String> timesordered = new ArrayList<String>();

		try {
			while (breakfasts.next()) {
				//System.out.println(first.getString("maxAV")); //type
				//request.setAttribute("type", first.getString("type"));
				
				btype.add(breakfasts.getString("b_type"));
				hotelid.add(breakfasts.getString("hotel_id"));
				timesordered.add(breakfasts.getString("times_ordered"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch");
			e.printStackTrace();
		}
		
		//System.out.println(btype.get(0));
		
		request.setAttribute("btype", btype);
		request.setAttribute("hotelid", hotelid);
		request.setAttribute("timesordered", timesordered);
		request.setAttribute("invoiceNum", invoiceNum);
		
		
		request.getRequestDispatcher("/makereview.jsp").forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

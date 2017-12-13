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
		String squery = "select distinct * from service_orders where invoice_no= " + invoiceNum + " AND \n" + 
				"s_type not in\n" + 
				"(select s_type from service_reviews where invoice_no= " + invoiceNum + " )";
		String rquery = "select distinct * from res_details where invoice_no= " + invoiceNum + " AND \n" + 
				"room_no not in\n" + 
				"(select room_no from room_review where invoice_no= " + invoiceNum + " )";
	
				
				
		ResultSet breakfasts = LocalDbConnect.executeSelectQuery(bquery);
		ResultSet services=LocalDbConnect.executeSelectQuery(squery);
		ResultSet rooms=LocalDbConnect.executeSelectQuery(rquery);
		String datesq = "select in_date ,out_date from res_details where invoice_no=" + invoiceNum;
		ResultSet dates = LocalDbConnect.executeSelectQuery(datesq);
		String indate = "", outdate = "";
		try {
			dates.next();
			 indate = dates.getString("in_date");
			 outdate = dates.getString("out_date");

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//System.out.println("indate: " + indate);
		//System.out.println("outdate " + outdate);
		

		
		ArrayList <String> btype = new ArrayList<String>();
		ArrayList <String> stype=new ArrayList<String>();
		ArrayList <String> room1=new ArrayList<String>();
		ArrayList <String> hotelid = new ArrayList<String>();
		//ArrayList <String> timesordered = new ArrayList<String>();
		

		try {
			while (breakfasts.next()) {
				//System.out.println(first.getString("maxAV")); //type
				//request.setAttribute("type", first.getString("type"));
				
				btype.add(breakfasts.getString("b_type"));
				hotelid.add(breakfasts.getString("hotel_id"));
				//timesordered.add(breakfasts.getString("times_ordered"));
			}
			while(services.next()) {
				stype.add(services.getString("s_type"));
			}
			while(rooms.next()) {
				room1.add(String.valueOf(rooms.getInt("room_no")));
				System.out.println(rooms.getString("room_no"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch");
			e.printStackTrace();
		}
		
		
		request.setAttribute("btype", btype);
		request.setAttribute("stype", stype);
		request.setAttribute("room1", room1);
		request.setAttribute("hotelid", hotelid);
		//request.setAttribute("timesordered", timesordered);
		request.setAttribute("invoiceNum", invoiceNum);
		request.setAttribute("indate", indate);
		request.setAttribute("outdate", outdate);
		String firsthotel = hotelid.get(0);
		request.setAttribute("firsthotel", firsthotel);

		
		
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

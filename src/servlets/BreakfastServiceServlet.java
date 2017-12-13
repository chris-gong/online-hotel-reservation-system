package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.LocalDbConnect;
import entities.Breakfast;
/**
 * Servlet implementation class BreakfastServiceServlet
 */
@WebServlet(urlPatterns = "/BreakfastServiceSelect")
public class BreakfastServiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BreakfastServiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String hotelId = request.getParameter("hotel_id");
		ArrayList<Breakfast> breakfasts = new ArrayList<Breakfast>();
		//retrieve breakfasts associated with the hotel id
		String breakfastQry = "Select * from breakfast_offers where hotel_id='"+hotelId+"';";
		System.out.println(breakfastQry);
		try{
			ResultSet breakfastRs = LocalDbConnect.executeSelectQuery(breakfastQry);
			while(breakfastRs.next()){
				String type = breakfastRs.getString("b_type");
				int price = breakfastRs.getInt("b_price");
				String desc = breakfastRs.getString("description");
				Breakfast breakfast = new Breakfast(type, price, desc);
				breakfasts.add(breakfast);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		for(Breakfast b : breakfasts){
			System.out.println(b.getDescription());
		}
		request.setAttribute("breakfasts", breakfasts);
		//retrieve services associated with the hotel id
		String serviceQry = "Select * from service_offers where hotel_id='"+hotelId+"';";
		request.getRequestDispatcher("/breakfastServiceOrder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

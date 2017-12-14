package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import entities.Breakfast;
import entities.Service;
import server.LocalDbConnect;

/**
 * Servlet implementation class OrderSummaryServlet
 */
@WebServlet(urlPatterns = "/BreakfastServiceSummary")
public class OrderSummaryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderSummaryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String hotelId = request.getParameter("hotel_id");
		String invoice = request.getParameter("invoice_no");
		String breakfastReqString = request.getParameter("breakfast_req_string");
		String serviceReqString = request.getParameter("service_req_string");
		System.out.println("breakfast req string: " + breakfastReqString);
		ArrayList<Breakfast> breakfasts = mapper.readValue(breakfastReqString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, Breakfast.class)); 
		ArrayList<Service> services = mapper.readValue(serviceReqString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, Service.class)); 
		//insert breakfast orders into breakfast_orders table
		try{
			for(Breakfast b : breakfasts){
				String insertBreakfastQry = "insert into breakfast_orders(hotel_id,invoice_no,b_type,time_breakfast_ordered) values"
						+ "('"+hotelId+"','"+invoice+"','"+b.getType()+"','"+b.getTimesOrdered()+"');";
				System.out.println(insertBreakfastQry);
				int inserted = LocalDbConnect.executeInsertQuery(insertBreakfastQry);
			}
			for(Service s : services){
				String insertServiceQry = "insert into service_orders(hotel_id,invoice_no,s_type,times_service_ordered) values"
						+ "('"+hotelId+"','"+invoice+"','"+s.getType()+"','"+s.getTimesOrdered()+"');";
				System.out.println(insertServiceQry);
				int inserted = LocalDbConnect.executeInsertQuery(insertServiceQry);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		String message = "Congrats! Your room, breakfast, and service orders have all been confirmed";
		response.sendRedirect("/HotelReservations/Home?confirmation="+message);
	}

}

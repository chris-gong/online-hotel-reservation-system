package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import server.LocalDbConnect;
import entities.Breakfast;
import entities.Service;
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
		ObjectMapper mapper = new ObjectMapper();
		String hotelId = request.getParameter("hotel_id");
		String inDate = request.getParameter("in_date");
		String outDate = request.getParameter("out_date");
		ArrayList<Breakfast> breakfasts = new ArrayList<Breakfast>();
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<String> requestedRoomNums = mapper.readValue(request.getParameter("req_rooms"),
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
		//retrieve breakfasts associated with the hotel id
		String breakfastQry = "Select * from breakfast_offers where hotel_id='"+hotelId+"';";
		int totalCap = 0;
		int orderLimit = 0;
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
		try{
			ResultSet serviceRs = LocalDbConnect.executeSelectQuery(serviceQry);
			while(serviceRs.next()){
				String type = serviceRs.getString("s_type");
				int price = serviceRs.getInt("s_cost");
				Service service = new Service(type, price);
				services.add(service);
			}
		}catch(Exception e){
			
		}
		request.setAttribute("services", services);
		for(Service s : services){
			System.out.println(s.getType());
		}
		//retrieve the capacities of each requested room
		//in order to find the total number of people
		for(String roomNum : requestedRoomNums){
			String capacityQry = "select capacity from rooms where hotel_id='"+hotelId+"' and room_no='"
					+roomNum+"';";
			try{
				ResultSet capacityRs = LocalDbConnect.executeSelectQuery(capacityQry);
				if(capacityRs.next()){
					totalCap += capacityRs.getInt("capacity");
				}
			}catch(Exception e){
				
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date in;
		Date out;
		try {
			in = sdf.parse(inDate);
			out = sdf.parse(outDate);
			LocalDate inD = in.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate outD = out.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			orderLimit = totalCap * daysBetween(inD, outD);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(orderLimit);
		request.setAttribute("order_limit", orderLimit);
		request.getRequestDispatcher("/breakfastServiceOrder.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String breakfastString = request.getParameter("breakfast_array_string");
		String serviceString = request.getParameter("service_array_string");
		String outerLimit = request.getParameter("outer_limit");
		System.out.println("breakfast service servlet doPost");
		System.out.println(breakfastString + " " + serviceString);
		//arraylist of json strings which in themselves represent
		//arrays containing two things, type and quantity
		ArrayList<String> breakfasts = mapper.readValue(breakfastString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
		ArrayList<String> services = mapper.readValue(serviceString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
		//TODO: make sure to filter out underscores in breakfast or service types
	}
	public int daysBetween(LocalDate d1, LocalDate d2){
        return (int) ChronoUnit.DAYS.between(d1,d2);
	}
}

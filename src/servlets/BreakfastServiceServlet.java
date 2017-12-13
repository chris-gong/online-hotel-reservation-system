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
		String invoiceNum = request.getParameter("invoice_no");
		String reqRoomString = request.getParameter("req_rooms");
		ArrayList<Breakfast> breakfasts = new ArrayList<Breakfast>();
		ArrayList<Service> services = new ArrayList<Service>();
		ArrayList<String> requestedRoomNums = mapper.readValue(reqRoomString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
		//retrieve breakfasts associated with the hotel id
		String breakfastQry = "Select * from breakfast_offers where hotel_id='"+hotelId+"';";
		int totalCap = 0;
		int orderLimit = 0;
		System.out.println(breakfastQry);
		request.setAttribute("in_date",inDate);
		request.setAttribute("out_date", outDate);
		request.setAttribute("invoice_no", invoiceNum);
		request.setAttribute("req_room_string", reqRoomString);
		request.setAttribute("hotel_id", hotelId);
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
		String orderLimit = request.getParameter("order_limit");
		String invoice = request.getParameter("invoice_no");
		String inDate = request.getParameter("in_date");
		String outDate = request.getParameter("out_date");
		String reqRoomString = request.getParameter("req_room_string");
		String hotelId = request.getParameter("hotel_id");
		int maxCap = Integer.parseInt(orderLimit);
		System.out.println("breakfast service servlet doPost");
		System.out.println(breakfastString + " " + serviceString);
		int totalBreakfastCount = 0;
		int totalServiceCount = 0;
		//arraylist of json strings which in themselves represent
		//arrays containing two things, type and quantity
		ArrayList<String> breakfasts = mapper.readValue(breakfastString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
		ArrayList<String> services = mapper.readValue(serviceString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
		ArrayList<Breakfast> breakfastReqs = new ArrayList<Breakfast>();
		ArrayList<Service> serviceReqs = new ArrayList<Service>();
		//retrieve the requested services and breakfasts from the user
		for(String b : breakfasts){
			ArrayList<String> breakfastObj = mapper.readValue(b,
					TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
			//filter out underscores in breakfast or service types
			String bType = breakfastObj.get(0).replaceAll("_", " ");
			Breakfast breakfast = new Breakfast(bType, Integer.parseInt(breakfastObj.get(2)), null);
			breakfast.setTimesOrdered(Integer.parseInt(breakfastObj.get(1)));
			breakfastReqs.add(breakfast);
		}
		for(String s : services){
			ArrayList<String> serviceObj = mapper.readValue(s,
					TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
			//filter out underscores in breakfast or service types
			String sType = serviceObj.get(0).replaceAll("_", " ");
			Service service = new Service(sType, Integer.parseInt(serviceObj.get(2)));
			service.setTimesOrdered(Integer.parseInt(serviceObj.get(1)));
			serviceReqs.add(service);
		}
		for(Breakfast b : breakfastReqs){
			totalBreakfastCount += b.getTimesOrdered();
		}
		for(Service s : serviceReqs){
			totalServiceCount += s.getTimesOrdered();
		}
		System.out.println("breakfastcount: " + totalBreakfastCount);
		System.out.println("servicecount: " + totalServiceCount);
		if(totalBreakfastCount > maxCap || totalServiceCount > maxCap){
			//user ordered too many breakfasts or services
			System.out.println("Ordered too many breakfasts or services");
			String message = "Number of orders is greater than the amount allowed for this reservation";
			response.sendRedirect("/HotelReservations/BreakfastServiceSelect?invoice_no="+invoice+"&in_date="+inDate+"&out_date="
					+outDate+"&hotel_id="+hotelId+"&req_rooms="+reqRoomString+"&err_message="+message);
		}
		else{
			//take the user to a review summary page for the breakfasts and services he/she ordered
		}
	}
	public int daysBetween(LocalDate d1, LocalDate d2){
        return (int) ChronoUnit.DAYS.between(d1,d2);
	}
}

package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import entities.Hotel;
import entities.Room;
import server.LocalDbConnect;
import org.json.JSONObject;
/**
 * Servlet implementation class HotelSelectServlet
 */
@WebServlet(urlPatterns = "/HotelSelect")
public class HotelSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HotelSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * Basic flow of the room reservations:
		 * Step ONE: 
		 * User selects rooms one by one from a list of rooms gathered from res_details
		 * where the entries' dates do not conflict with the requested dates
		 * Step TWO:
		 * Keep track of this room entry's room_no so that the user can not select again 
		 * in cases where 2 or more rooms were requested
		 * Step THREE:
		 * After bundling all of the requested rooms, check if all them are available one
		 * last time aka the requested dates do not conflict with any entries in res_details table
		 * Step FOUR: 
		 * Go to service/breakfast selection
		 */
		//Step ONE:
		System.out.println("Hotel Select doGet method");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String message = request.getParameter("message");
		System.out.println("message: " + message);
		String[] caps = mapper.readValue(request.getParameter("caps"),String[].class);
		ArrayList<String> requestedRooms = mapper.readValue(request.getParameter("req_rooms"),
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); //list of room_no already requested
		ArrayList<Room> availableRooms = new ArrayList<Room>(); //list of rooms to be send to the user to be viewed
		int id = Integer.parseInt(request.getParameter("hotel_id"));
		String name = request.getParameter("name");
		int roomNum = Integer.parseInt(request.getParameter("room_num"));
		String inDate = request.getParameter("in_date");
		String outDate = request.getParameter("out_date");
		for(String s : caps){
			System.out.print(s + " ");
		}
		System.out.println();
		for(String s : requestedRooms){
			System.out.print(s + " ");
		}
		System.out.println();
		System.out.println(requestedRooms.size() + " " + roomNum);
		System.out.println("in-date: " + inDate + " out-date: " + outDate);
		//message will only not be null if user has selected at least
		//one room in the room selection process
		if(message != null && message.equals("room selection phase")){
			//check if user is on the last room
			String reqRoomsString = mapper.writeValueAsString(requestedRooms);
			if(requestedRooms.size() == caps.length){
				System.out.println("Last room has been selected");
				String url = "/HotelReservations/ReservedRoomSummary?hotel_id="+id+"&name="+name+"&in_date="+inDate
						+"&out_date="+outDate+"&req_rooms="+reqRoomsString;
				out.println(url);
			}
			else{
				String capsString = mapper.writeValueAsString(caps);
				String url = "/HotelReservations/HotelSelect?hotel_id="+id+"&name="+name+"&in_date="+inDate
						+"&out_date="+outDate+"&caps="+capsString+"&room_num="+roomNum+"&req_rooms="+reqRoomsString;
				out.println(url);
			}
			
		}
		else{
			String availRoomsQry = "select * from rooms where capacity >= '"+caps[roomNum-1]+"' "
					+ "and hotel_id ='"+id+"'"
					+ " and (room_no, hotel_id) not in (select distinct room_no, hotel_id from res_details "
					+ "where ((in_date <= '"+outDate+"') and (out_date >= '"+inDate+"')) and hotel_id ='"+id+"')";
			for(String reqRoom : requestedRooms){
				availRoomsQry += " and (room_no, hotel_id)not in(select '"+reqRoom+"' as 'room_no', '"+id+"' as 'hotel_id') ";
			}
			availRoomsQry += ";";
			System.out.println(availRoomsQry);
			try{
				ResultSet rs = LocalDbConnect.executeSelectQuery(availRoomsQry);
				while(rs.next()){
					int room_no = rs.getInt("room_no");
					int floor_num = rs.getInt("floor_no");
					String description = rs.getString("description");
					String type = rs.getString("type");
					int price = rs.getInt("price"); 
					int cap = rs.getInt("capacity");
					Room room = new Room(id, room_no, floor_num, price, description, type, cap);
					availableRooms.add(room);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			request.setAttribute("caps", caps);
			request.setAttribute("in_date", inDate);
			request.setAttribute("out_date", outDate);
			request.setAttribute("hotel_name", name);
			request.setAttribute("hotel_id", id);
			request.setAttribute("room_num", roomNum);
			request.setAttribute("rooms", availableRooms);
			request.setAttribute("req_rooms", requestedRooms);
			request.getRequestDispatcher("/selectRoom.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Hotel Select doPost method");
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String inDate = request.getParameter("in_date");
		String outDate = request.getParameter("out_date");
		int numRooms = Integer.parseInt(request.getParameter("num_rooms"));
		System.out.println(inDate + " " + outDate);
		ObjectMapper mapper = new ObjectMapper();
		//line below needed for jackson library to convert jsonobject to string
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		String caps = request.getParameter("caps");
		ArrayList<String> requestedRooms = new ArrayList<String>();
		//check if hotel clicked on has enough rooms available at the specified capacities
		//using stored procedure
		String checkAvailQry = "call check_availability(?, ?, ?, ?)";
		ArrayList<String> inParams = new ArrayList<String>();
		ArrayList<Integer> outParams = new ArrayList<Integer>();
		inParams.add(id);
		inParams.add(inDate);
		inParams.add(outDate);
		outParams.add(Types.INTEGER);
		try{
			ArrayList results = LocalDbConnect.executeStoredProcedure(checkAvailQry, inParams, outParams);
			if(results != null){
				int roomsAvail = (int) results.get(0);
				System.out.println(roomsAvail + " " + numRooms);
				//not enough rooms available in the selected hotel
				JSONObject data = new JSONObject();
				if(numRooms > roomsAvail){
					data.put("message", "There are not enough rooms in " + name);
					System.out.println(mapper.writeValueAsString(data));
					out.println(mapper.writeValueAsString(data));
				}
				else{
					String reqRooms = mapper.writeValueAsString(requestedRooms);
					String url = "/HotelReservations/HotelSelect?hotel_id="+id+"&name="+name+"&in_date="+inDate
							+"&out_date="+outDate+"&caps="+caps+"&room_num=1"+"&req_rooms="+reqRooms;
					data.put("message", "");
					data.put("url", url);
					System.out.println(mapper.writeValueAsString(data));
					out.println(mapper.writeValueAsString(data));
				}
			}
			else{
				System.out.println("Query returned a empty set");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return;
		//System.out.println(name+":"+id);
		
	}

}

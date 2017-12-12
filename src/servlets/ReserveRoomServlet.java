package servlets;


import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

import entities.Card;
import entities.Room;
import server.LocalDbConnect;

/**
 * Servlet implementation class ReserveRoomServlet
 */
@WebServlet(urlPatterns="/ReservedRoomSummary")
public class ReserveRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReserveRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		String user_id = (String) session.getAttribute("user_id");
		int id = Integer.parseInt(request.getParameter("hotel_id"));
		String name = request.getParameter("name");
		String inDate = request.getParameter("in_date");
		String outDate = request.getParameter("out_date");
		String reqRooms = request.getParameter("req_rooms");
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<String> requestedRoomNums = mapper.readValue(request.getParameter("req_rooms"),
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
		ArrayList<Room> requestedRooms = new ArrayList<Room>();
		ArrayList<Card> creditCards = new ArrayList<Card>();
		request.setAttribute("hotel_id", id);
		request.setAttribute("hotel_name", name);
		request.setAttribute("in_date", inDate);
		request.setAttribute("out_date",outDate);
		String retrieveRoomsQry = "Select * from rooms where hotel_id ='"+id+"' and (";
		for(String room : requestedRoomNums){
			retrieveRoomsQry += "room_no = '"+room+"' or ";
		}
		retrieveRoomsQry = retrieveRoomsQry.substring(0, retrieveRoomsQry.lastIndexOf("or"));
		retrieveRoomsQry += ");";
		System.out.println(retrieveRoomsQry);
		try{
			ResultSet rs = LocalDbConnect.executeSelectQuery(retrieveRoomsQry);
			while(rs.next()){
				int room_no = rs.getInt("room_no");
				int floor_num = rs.getInt("floor_no");
				String description = rs.getString("description");
				String type = rs.getString("type");
				int price = rs.getInt("price"); 
				int cap = rs.getInt("capacity");
				Room room = new Room(id, room_no, floor_num, price, description, type, cap);
				System.out.println(room);
				requestedRooms.add(room);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		for(Room room : requestedRooms){
			System.out.println(room.getRoomNum());
		}
		String reqRoomString = mapper.writeValueAsString(requestedRoomNums); //JSON String form of arraylist requestedRooms
		System.out.println("JSON String form of req_rooms arraylist " + reqRoomString);
		request.setAttribute("req_rooms", requestedRooms);
		request.setAttribute("req_room_string", reqRoomString);
		String retrieveCardsQry = "select * from credit_cards where user_id='" + user_id + "'";
		System.out.println(retrieveCardsQry);
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.format(today);
		System.out.println(today.toString());
		try{
			ResultSet rs = LocalDbConnect.executeSelectQuery(retrieveCardsQry);
			while(rs.next()){
				String cardName = rs.getString("name");
				int cardNum = rs.getInt("c_number");
				String cardType = rs.getString("type");
				String cardExpDate = rs.getString("exp_date");
				if(today.before(sdf.parse(cardExpDate))){
					Card card = new Card(cardNum, cardType, cardName, cardExpDate);
					creditCards.add(card);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		request.setAttribute("cards", creditCards);
		request.getRequestDispatcher("/confirmRoom.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		String id = request.getParameter("hotel_id");
		String inDate = request.getParameter("in_date");
		String outDate = request.getParameter("out_date");
		String reqRoomString = request.getParameter("req_room_string");
		String cardNum = request.getParameter("credit_card");
		
		System.out.println(reqRoomString);
		ArrayList<String> requestedRoomNums = mapper.readValue(reqRoomString,
				TypeFactory.defaultInstance().constructCollectionType(List.class, String.class)); 
		System.out.println(id);
		for(String room : requestedRoomNums){
			System.out.println(room);
		}
		//if the user chooses to make a new credit card
		if(cardNum.equals("")){
			System.out.println("New credit card");
		}
		else{
			System.out.println("Old credit card");
			//check if rooms have already been reserved
			int numConflicts = getConflictingRoomCount(requestedRoomNums, id, inDate, outDate);
			System.out.println("number of conflicts: " + numConflicts);
			if(numConflicts == 0){
				//if there are no conflicting entries in res_details table, 
				//proceed to make reservations and res_details entries
			}
			else{
				//if there are conflicting entries in res_details table
				//don't make any entries in reservations and res_details entries
			}
		}
	}
	public int getConflictingRoomCount(ArrayList<String> rooms, String hotelId, String inDate, String outDate){
		String checkRoomsQry = "select count(*) as num_conflicts from"
				+ " ((select distinct room_no, hotel_id from res_details"
				+ " where ((in_date <= '"+outDate+"') and (out_date >= '"+inDate+"')) and hotel_id = '"+hotelId+"')t1"
				+ " inner join "
				+ "(";
		for(String roomNum : rooms){
			checkRoomsQry += "select '"+roomNum+"' as 'room_no', '"+hotelId+"' as 'hotel_id' union";
		}
		checkRoomsQry = checkRoomsQry.substring(0, checkRoomsQry.lastIndexOf("union"));
		checkRoomsQry += ") t2 on (t1.room_no = t2.room_no and t1.hotel_id = t2.hotel_id));";
		System.out.println(checkRoomsQry);
		try{
			ResultSet rs = LocalDbConnect.executeSelectQuery(checkRoomsQry);
			if(rs.next()){
				int numConflicts = rs.getInt("num_conflicts");
				return numConflicts;
			}
			else{
				return -1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}

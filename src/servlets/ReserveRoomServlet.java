package servlets;


import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
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
		System.out.println("Entering reserveroomsummary doGet");
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
				
				//Checking if discount exists given in and out date.
				 
				
				
				
				
				
				
				
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
		double cost = calculateTotalCost(requestedRoomNums,id, inDate, outDate);
		request.setAttribute("cost", cost);
		request.getRequestDispatcher("/confirmRoom.jsp").forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		HttpSession session = request.getSession();
		String id = request.getParameter("hotel_id");
		String name = request.getParameter("hotel_name");
		String inDate = request.getParameter("in_date");
		String outDate = request.getParameter("out_date");
		String reqRoomString = request.getParameter("req_room_string");
		String cardNum = request.getParameter("credit_card");
		String userId = (String) session.getAttribute("user_id");
		
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
			//check if inputted parameters for the credit card are valid
			String cardName = request.getParameter("card_name");
			String cardAddress = request.getParameter("card_addr");
			cardNum = request.getParameter("card_num");
			String cardSec = request.getParameter("card_sec");
			String cardType = request.getParameter("card_type");
			String cardExpDate = request.getParameter("card_exp_date");
			//check if expiration date is at least after today
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayDate = sdf.format(today);
			try{
				if(today.after(sdf.parse(cardExpDate))){
					//tell the user that the card is expired
					System.out.println("Card is expired");
					String message = "Card is expired";
					response.sendRedirect("/HotelReservations/ReservedRoomSummary?hotel_id="+id+"&name="+name+
							"&in_date="+inDate+"&out_date="+outDate+"&req_rooms="+reqRoomString+"&exp_message="+message);
					return;
				}
				//run a query that checks if the inputted cnumber already exists with the user_id
				//in an entry in the credit_cards table
				String cardDuplicateQry = "select count(*) as count from credit_cards "
						+ "where c_number = '"+cardNum+"' and user_id = '"+userId+"';";
				try{
					ResultSet duplicateRs = LocalDbConnect.executeSelectQuery(cardDuplicateQry);
					int count = 0;
					if(duplicateRs.next()){
						count = duplicateRs.getInt("count");
						System.out.println("number of cards:" + count);
					}
					if(count > 0){
						//card already exists
						System.out.println("Card already exists");
						String message = "Card already exists (number duplicate)";
						response.sendRedirect("/HotelReservations/ReservedRoomSummary?hotel_id="+id+"&name="+name+
								"&in_date="+inDate+"&out_date="+outDate+"&req_rooms="+reqRoomString+"&dupl_message="+message);
					}
					else{
						//add the card into the database
						System.out.println("adding card into the database");
						String insertCardQry ="insert into credit_cards(exp_date,type,sec_code,name,billing_addr,c_number,user_id) "
								+ "values('"+cardExpDate+"','"+cardType+"','"+cardSec+"','"+cardName+"','"+cardAddress+"','"+cardNum+"','"+userId+"');";
						int cardInserted = LocalDbConnect.executeInsertQuery(insertCardQry);
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
		//System.out.println("Old credit card");
		//check if rooms have already been reserved
		int numConflicts = getConflictingRoomCount(requestedRoomNums, id, inDate, outDate);
		System.out.println("number of conflicts: " + numConflicts);
		if(numConflicts == 0){
			//if there are no conflicting entries in res_details table, 
			//proceed to make reservations and res_details entries
			Date today = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String todayDate = sdf.format(today);
			System.out.println(todayDate);
			String insertResQry = "insert into reservations (Res_Date,user_id,c_number) values('"+todayDate+"'"
					+ ",'"+userId+"','"+cardNum+"');";
			try{
				ResultSet rs = LocalDbConnect.executeInsertAndRetrieveKeys(insertResQry);
				if(rs.next()){
					int invoice = rs.getInt(1); //retrieves the first column of the primary key, which is the invoice_no
					System.out.println(invoice);
					//insert entries into res_details
					try{
						for(String room: requestedRoomNums){
							String insertRes ="insert into res_details values('"+inDate+"','"+outDate+"','"+room+"','"+id+"','"+invoice+"')";
							LocalDbConnect.executeInsertQuery(insertRes);
						}
						System.out.println("Redirecting to breakfastservice servlet");
						response.sendRedirect("/HotelReservations/BreakfastServiceSelect?invoice_no="+invoice+"&in_date="+inDate+"&out_date="
								+"&hotel_id="+id+"&req_rooms="+reqRoomString+"&card_num="+cardNum);
					}catch(Exception e){
						e.printStackTrace();
					}
					
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			//if there are conflicting entries in res_details table
			//don't make any entries in reservations and res_details entries
			//and tell the user that the rooms can not be reserved
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
	public double calculateTotalCost(ArrayList<String> roomNums, int hotelId, String inDate, String outDate){
		ArrayList<Integer> prices = new ArrayList<Integer>();
		//retrieve each room's cost
		for(String num : roomNums){
			String getCostQry = "select price from rooms where room_no = '"+num+"' and hotel_id ='"+hotelId+"';";
			try{
				ResultSet cost = LocalDbConnect.executeSelectQuery(getCostQry);
				if(cost.next()){
					prices.add(cost.getInt("price"));
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		//get the number of days between the indate and outdate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int days = 0;
		try {
			Date in = sdf.parse(inDate);
			Date out = sdf.parse(outDate);
			days = daysBetween(in, out);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int total = 0;
		for(Integer price : prices){
			total += days * price;
		}
		System.out.println(total);
		return total;
	}
	public int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
}
}

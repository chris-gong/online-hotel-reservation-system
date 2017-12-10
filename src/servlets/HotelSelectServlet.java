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

import entities.Hotel;
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
		/*System.out.println("Hotel Select doGet method");
		ObjectMapper mapper = new ObjectMapper();
		String[] caps =mapper.readValue(request.getParameter("caps"),String[].class);
		String id = request.getParameter("hotel_id");
		String name = request.getParameter("name");
		String roomNum = request.getParameter("room_num");
		//check if user is on the last room
		if(caps.length == Integer.parseInt(roomNum)){
			
		}
		else{
			
		}*/
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
		System.out.println(inDate + " " + outDate);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
		String[] caps = mapper.readValue(request.getParameter("caps"),String[].class);
		int numRooms = caps.length;
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
					String url = "/HotelReservations/HotelSelect?hotel_id="+id+"&name="+name+"&caps="+"&in_date="+inDate
							+"&out_date="+outDate+"&caps="+caps+"&room_num=1";
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

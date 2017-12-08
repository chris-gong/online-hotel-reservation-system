package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.LocalDbConnect;

/**
 * Servlet implementation class StatsServlet
 */
@WebServlet("/StatsServlet")
public class StatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StatsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		/*String query1 = "Select t4.type, t6.hotel_id, t6.maxAV from\n" + 
				"(Select t3.hotel_id, MAX(av) as maxAV from\n" + 
				"(Select t2.type,t2.hotel_id, avg(rating) AS av from\n" + 
				"    (Select r.type, rr.* from rooms r, room_review rr\n" + 
				"    where r.hotel_id=rr.hotel_id AND\n" + 
				"    r.room_no=rr.room_no \n" + 
				"    )t2\n" + 
				"group by t2.type, t2.hotel_id) t3\n" + 
				"group by t3.hotel_id) t6\n" + 
				"join \n" + 
				"(Select t5.type,t5.hotel_id, avg(rating) AS av from\n" + 
				"(Select r.type, rr.* from rooms r, room_review rr\n" + 
				"where r.hotel_id=rr.hotel_id AND\n" + 
				"r.room_no=rr.room_no \n" + 
				")t5\n" + 
				"group by t5.type, t5.hotel_id)t4\n" + 
				"on( t6.hotel_id=t4.hotel_id AND t6.maxAV=t4.av)";*/
		
		String test = "select * from rooms;";
		
		ResultSet first = LocalDbConnect.executeSelectQuery(test);
		String s = "";
		try {
			s = first.getString("price");
			System.out.println(s);
			System.out.println("in try");
			//System.out.println(first.getString("firstname")); //type
			System.out.println("after trying to print");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch");
			e.printStackTrace();
		}
		
		
		
	}

}

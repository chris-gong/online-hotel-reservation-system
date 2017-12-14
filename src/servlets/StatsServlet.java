package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Customer;
import server.LocalDbConnect;

/**
 * Servlet implementation class StatsServlet
 */
@WebServlet(urlPatterns="/StatsServlet")
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
		
		String temp1 = (String)request.getAttribute("indate");
		String temp2 = (String)request.getAttribute("outdate");
		
		
		String inDate = "'" + temp1 + "'";
		String outDate = "'" + temp2 + "'";
		System.out.println(inDate + " " + outDate);

		ArrayList<Customer> customers = new ArrayList<Customer> ();
		HttpSession sesh = request.getSession(true);
		
		ArrayList<String> userIds = new ArrayList<String>();
		
		String h1 = "select user_id from users";
		//String h2 = 
		
		ResultSet hresult = LocalDbConnect.executeSelectQuery(h1);
		try {
			while (hresult.next()) {
				String userid = hresult.getString("user_id");
				String h2 = "select user_id, invoice_no from reservations where user_id =  '" + userid + "'";
				ResultSet h2result = LocalDbConnect.executeSelectQuery(h2);
				
				while (h2result.next()) {
					String invoiceNo = h2result.getString("invoice_no");
					
					String h3 = "select in_date, out_date, room_no, hotel_id from res_details where invoice_no = '"+invoiceNo+"'";
					
					ResultSet h3result = LocalDbConnect.executeSelectQuery(h3);
					while(h3result.next()) {
						String hinDate = h3result.getString("in_date");
						String houtDate = h3result.getString("out_date");
						//get the number of days between the indate and outdate
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						int days = 0;
						try {
							Date in = sdf.parse(hinDate);
							Date out = sdf.parse(houtDate);
							LocalDate inD = in.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
							LocalDate outD = out.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

							days = daysBetween(inD, outD);
							System.out.println(days);
							
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String room_no = h3result.getString("room_no");
						String hotel_id = h3result.getString("hotel_id");
						String h4 = "select price from rooms where hotel_id = '"+hotel_id+"' and room_no = '"+room_no+"'";
						ResultSet h4result = LocalDbConnect.executeSelectQuery(h4);
						int price = 0;
						if(h4result.next()) {
							price = h4result.getInt("price");
						}
						int total = price * days;
						boolean alreadyExists = false;
						Customer cust = null;
						for(Customer c : customers) {
							if(c.getUserId() == Integer.parseInt(userid)) {
								alreadyExists = true;
								cust = c;
								break;
							}
						}
						if(alreadyExists) {
							cust.spendMore(total);
						}
						else {
							customers.add(new Customer(total,Integer.parseInt(userid)));
						}
					}
					
					
				}
				
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Collections.sort((List<Customer>) customers);
		request.setAttribute("customers", customers);
		String query1 = "\n" + 
				"\n" + 
				"Select t4.type, t6.hotel_id, t6.maxAV from\n" + 
				"(Select t3.hotel_id, MAX(av) as maxAV from\n" + 
				"(Select t2.type,t2.hotel_id, avg(rating) AS av from\n" + 
				"    (Select r.type, rr.* from rooms r, room_review rr\n" + 
				"      where r.hotel_id=rr.hotel_id AND\n" + 
				"      r.room_no=rr.room_no AND\n" + 
				"      rr.inDate between  " + inDate +"and " +  outDate + " AND\n" + 
				"      rr.outDate between " + inDate + " and " + outDate + " \n" + 
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
				"on( t6.hotel_id=t4.hotel_id AND t6.maxAV=t4.av)";
		
		
		
		
		
		String query3 = "Select t4.b_type,t4.hotel_id,t5.mx from \n" + 
				"(Select t3.hotel_id, max(avg) as mx from\n" + 
				"	(Select t2.b_type,t2.hotel_id, avg(rating) as avg from\n" + 
				"		(Select br.rating, br.b_type,br.hotel_id from breakfast_reviews br where\n" + 
				"		br.inDate between " + inDate +  " and " + outDate + " AND\n" + 
				"		br.outDate between " + inDate + " and " + outDate + " \n" + 
				"		)t2\n" + 
				"	group by t2.b_type , t2.hotel_id )t3\n" + 
				"group by t3.hotel_id\n" + 
				")t5\n" + 
				"join\n" + 
				"(select brr.b_type, brr.hotel_id,avg(rating) as avg  from breakfast_reviews brr where\n" + 
				"brr.inDate between " + inDate + " and " + outDate + " AND\n" + 
				"brr.outDate between " + inDate + " and " + outDate + "\n" + 
				"group by brr.hotel_id , brr.b_type)t4\n" + 
				"on t4.hotel_id=t5.hotel_id AND t4.avg=t5.mx";

		
		String query4 = "Select t4.s_type,t4.hotel_id,t5.mx from \n" + 
				"(Select t3.hotel_id, max(avg) as mx from\n" + 
				"	(Select t2.s_type,t2.hotel_id, avg(rating) as avg from\n" + 
				"		(Select sr.rating, sr.s_type,sr.hotel_id from service_reviews sr where\n" + 
				"		sr.inDate between " + inDate + " and " + outDate + " AND\n" + 
				"		sr.outDate between " + inDate + " and " + outDate + " \n" + 
				"		)t2\n" + 
				"	group by t2.s_type , t2.hotel_id )t3\n" + 
				"group by t3.hotel_id\n" + 
				")t5\n" + 
				"join\n" + 
				"(select srr.s_type, srr.hotel_id,avg(rating) as avg  from service_reviews srr where\n" + 
				"srr.inDate between " + inDate + " and " + outDate + " AND\n" + 
				"srr.outDate between " + inDate + " and " + outDate + "\n" + 
				"group by srr.hotel_id , srr.s_type)t4\n" + 
				"on t4.hotel_id=t5.hotel_id AND t4.avg=t5.mx";
		
		
		ResultSet first = LocalDbConnect.executeSelectQuery(query1);
	
		
		//for first query:
		ArrayList <String> types = new ArrayList<String>();
		ArrayList <String> hotelids = new ArrayList<String>();
		ArrayList <String> maxs = new ArrayList<String>();
		
		try {
			while (first.next()) {
				//System.out.println(first.getString("maxAV")); //type
				//request.setAttribute("type", first.getString("type"));
				
				types.add(first.getString("type"));
				hotelids.add(first.getString("hotel_id"));
				maxs.add(first.getString("maxAV"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch");
			e.printStackTrace();
		}
		
		
		
		
		ResultSet third = LocalDbConnect.executeSelectQuery(query3);
		
		//for third query
		ArrayList <String> b_type = new ArrayList<String>();
		ArrayList <String> hotel_id = new ArrayList<String>();
		ArrayList <String> mx = new ArrayList<String>();
		
		try {
			while (third.next()) {
				//change getString() to include column names
				b_type.add(third.getString("b_type"));
				hotel_id.add(third.getString("hotel_id"));
				mx.add(third.getString("mx"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch");
			e.printStackTrace();
		}
		
		request.setAttribute("b_type", b_type);
		request.setAttribute("hotel_id", hotel_id);
		request.setAttribute("mx", mx);
		
		

		ResultSet fourth = LocalDbConnect.executeSelectQuery(query4);
		
		//for third query
		ArrayList <String> b_type4 = new ArrayList<String>();
		ArrayList <String> hotel_id4 = new ArrayList<String>();
		ArrayList <String> mx4 = new ArrayList<String>();
		
		try {
			while (fourth.next()) {
				//change getString() to include column names
				b_type4.add(fourth.getString("s_type"));
				hotel_id4.add(fourth.getString("hotel_id"));
				mx4.add(fourth.getString("mx"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("in catch");
			e.printStackTrace();
		}
		
		request.setAttribute("b_type4", b_type4);
		request.setAttribute("hotel_id4", hotel_id4);
		request.setAttribute("mx4", mx4);
		
		
		for (int i = 0; i < types.size(); i++) {
			System.out.println(types.get(i) + " " + hotelids.get(i) + " " + maxs.get(i));
		}
		
			
		
		request.setAttribute("types", types);
		request.setAttribute("hotelids", hotelids);
		request.setAttribute("maxs", maxs);
		
		
		request.getRequestDispatcher("/stats.jsp").forward(request,response);		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		
		
		
		
	}
	public int daysBetween(LocalDate d1, LocalDate d2){
        return (int) ChronoUnit.DAYS.between(d1,d2);
}
}

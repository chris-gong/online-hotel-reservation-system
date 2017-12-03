package servlets;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.*;
/**
 * Servlet implementation class RoomLookupServlet
 */
@WebServlet(urlPatterns = "/RoomLookup")
public class RoomLookupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomLookupServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Reached RoomLookupServlet via Get request");
		//request dispatcher is needed to get jsp file after get request for room lookup servlet is done
		String qry="Select distinct country from hotels h ;";
		ArrayList<String> countries = new ArrayList<String>();
		try {
			ResultSet rs=LocalDbConnect.executeSelectQuery(qry);
			while(rs.next()) {
				String country = rs.getString("country");
				countries.add(country);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("countries",countries);
		request.getRequestDispatcher("/searchHotel.jsp").forward(request, response);
	}
	public HashMap<String,ArrayList <String>> getStates(){
		HashMap<String,ArrayList<String>> countryStates = new HashMap<String,ArrayList<String>>();
		String qry="Select distinct country, state from hotels h;";
		System.out.println("getStates method was called");
		ResultSet rs=LocalDbConnect.executeSelectQuery(qry);
		try {
			while(rs.next()) {
				String country = rs.getString("country");
				String state = rs.getString("state");
				if(!countryStates.containsKey(country)) {
					countryStates.put(country, new ArrayList<String>());
				}
				countryStates.get(country).add(state);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return countryStates;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		if(country.equals("America")) {
			state=request.getParameter("state1");
		}
		else if(country.equals("India")) {
			state=request.getParameter("state2");
		}
		else {
			state=request.getParameter("state3");
		}
		System.out.println(country);
		System.out.println(state);
		//String rType = request.getParameter("rType");
		//String[] inDate = request.getParameterValues("inDate");
		//String[] outDate = request.getParameterValues("outDate");
		//SimpleDateFormat availDate = new SimpleDateFormat("yyyy-MM-dd");
		/*
		try {
			Date chosenInDate = availDate.parse(inDate[0]);
			Date chosenOutDate = availDate.parse(outDate[0]);
			System.out.println(chosenInDate);
			System.out.println(chosenOutDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//response.sendRedirect("selectHotel.jsp");
	}

}

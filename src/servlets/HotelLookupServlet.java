package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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

import entities.Hotel;
import server.*;

/**
 * Servlet implementation class HotelLookupServlet
 */
@WebServlet(urlPatterns = "/HotelLookup")
public class HotelLookupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HotelLookupServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Reached HotelLookupServlet via Get request");
		String countrySelection = request.getParameter("country");
		String stateSelection = request.getParameter("state");
		if (countrySelection != null && stateSelection != null) {
			System.out.println(countrySelection);
			System.out.println(stateSelection);
			ArrayList<String> cities = getCities(countrySelection, stateSelection);
			System.out.println("getting cities...");
			response.setContentType("text/html);charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<option value='' > Choose a city </option>");
			for (String city : cities) {
				out.println("<option value='" + city + "'>" + city + "</option>");
			}
			return;

		} else if (countrySelection != null) {
			ArrayList<String> states = getStates(countrySelection);
			response.setContentType("text/html);charset=UTF-8");
			System.out.println("getting states...");
			PrintWriter out = response.getWriter();
			out.println("<option value='' > Choose a state</option>");
			for (String state : states) {
				out.println("<option value='" + state + "'>" + state + "</option>");
			}
			return;
		}
		// request dispatcher is needed to get jsp file after get request for room
		// lookup servlet is done
		String qry = "Select distinct country from hotels h ;";
		ArrayList<String> countries = new ArrayList<String>();
		try {
			ResultSet rs = LocalDbConnect.executeSelectQuery(qry);
			while (rs.next()) {
				String country = rs.getString("country");
				countries.add(country);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("countries", countries);
		request.getRequestDispatcher("/searchHotel.jsp").forward(request, response);
	}

	public ArrayList<String> getStates(String country) {
		ArrayList<String> states = new ArrayList<String>();
		String qry = "Select distinct state from hotels h where h.country='" + country + "';";
		ResultSet rs = LocalDbConnect.executeSelectQuery(qry);
		try {
			while (rs.next()) {
				String state = rs.getString("state");
				states.add(state);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return states;
	}

	public ArrayList<String> getCities(String country, String state) {
		System.out.println("get cities called");
		ArrayList<String> cities = new ArrayList<String>();
		String qry = "Select distinct city from hotels h where h.country='" + country + "' and h.state='" + state + "'";
		ResultSet rs = LocalDbConnect.executeSelectQuery(qry);
		try {
			while (rs.next()) {
				String city = rs.getString("city");
				System.out.println("city:" + city);
				cities.add(city);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cities;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		String city = request.getParameter("city");
		String[] numPeople = request.getParameterValues("num_people");
		int numRooms = numPeople.length;
		String inDate = request.getParameter("inDate");
		String outDate = request.getParameter("outDate");
		//check if address has any nearby hotels
		String locQry = "select hotel_id, name from hotels where country='" + country + "'" + " and state='" + state + "'"
				+ " and city='" + city + "';";
		ArrayList<Hotel> hotels = new ArrayList<Hotel>();
		try {
			ResultSet rs = LocalDbConnect.executeSelectQuery(locQry);
			String id;
			String name;
			if (rs.next()) {
				id = rs.getString("hotel_id");
				name =rs.getString("name");
				Hotel h = new Hotel(Integer.parseInt(id),name);
				hotels.add(h);
				while (rs.next()) {
					id = rs.getString("hotel_id");
					name =rs.getString("name");
					h = new Hotel(Integer.parseInt(id),name);
					hotels.add(h);
				}
				request.setAttribute("hotels", hotels);
				request.setAttribute("caps", numPeople);
				request.setAttribute("address", city+","+state+","+country);
				request.setAttribute("room_count", numRooms);
				request.setAttribute("in_date", inDate);
				request.setAttribute("out_date", outDate);
				request.getRequestDispatcher("/selectHotel.jsp").forward(request, response);
			} else {
				// if there are no hotels near the inputted address, tell the user
				request.setAttribute("message", "No hotels are nearby");
				request.getRequestDispatcher("/HotelLookup").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

		// response.sendRedirect("selectHotel.jsp");
	}

}

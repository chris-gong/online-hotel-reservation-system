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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Reached RoomLookupServlet via Get request");
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
		// TODO Auto-generated method stub
		doGet(request, response);
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		if (country.equals("America")) {
			state = request.getParameter("state1");
		} else if (country.equals("India")) {
			state = request.getParameter("state2");
		} else {
			state = request.getParameter("state3");
		}
		System.out.println(country);
		System.out.println(state);
		// String rType = request.getParameter("rType");
		// String[] inDate = request.getParameterValues("inDate");
		// String[] outDate = request.getParameterValues("outDate");
		// SimpleDateFormat availDate = new SimpleDateFormat("yyyy-MM-dd");
		/*
		 * try { Date chosenInDate = availDate.parse(inDate[0]); Date chosenOutDate =
		 * availDate.parse(outDate[0]); System.out.println(chosenInDate);
		 * System.out.println(chosenOutDate); } catch (ParseException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		// response.sendRedirect("selectHotel.jsp");
	}

}

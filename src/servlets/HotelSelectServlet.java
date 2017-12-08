package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import entities.Hotel;

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
		ObjectMapper mapper = new ObjectMapper();
		String[] caps =mapper.readValue(request.getParameter("caps"),String[].class);
		String id = request.getParameter("hotel_id");
		String name = request.getParameter("name");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String caps = request.getParameter("caps");
		String url = "/HotelReservations/HotelSelect?hotel_id="+id+"&name="+name+"&caps="+caps;
		PrintWriter out = response.getWriter();
		out.println(url);
		return;
		//System.out.println(name+":"+id);
		
	}

}

package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		ArrayList<Hotel> hotels= (ArrayList<Hotel>)request.getAttribute("hotels");
		String address =(String) request.getAttribute("address");
		String numRooms =(String) request.getAttribute("num_rooms");
		request.setAttribute("hotels", hotels);
		request.setAttribute("address", address);
		request.setAttribute("room_count", numRooms);
		request.getRequestDispatcher("/selectHotel.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

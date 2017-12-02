package servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RoomLookupServlet
 */
@WebServlet("/RoomLookupServlet")
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
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String country = request.getParameter("country");
		String state = request.getParameter("state");
		String rType = request.getParameter("rType");
		String[] inDate = request.getParameterValues("inDate");
		String[] outDate = request.getParameterValues("outDate");
		SimpleDateFormat availDate = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date chosenInDate = availDate.parse(inDate[0]);
			Date chosenOutDate = availDate.parse(outDate[0]);
			System.out.println(chosenInDate);
			System.out.println(chosenOutDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//response.sendRedirect("selectHotel.jsp");
	}

}

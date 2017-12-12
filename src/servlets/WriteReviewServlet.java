package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WriteReviewServlet
 */
@WebServlet("/WriteReviewServlet")
public class WriteReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WriteReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    String breakfasttype = "";
    String invoiceNum = "";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		 breakfasttype = request.getParameter("breakfast");
		//service review =request
		
			invoiceNum = (String) request.getParameter("in2");
			System.out.println("Invoice num " + invoiceNum);
		
		request.getRequestDispatcher("/writereview.jsp").forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
	
		System.out.println("breakfast type : " + breakfasttype);
		
		String description = request.getParameter("description");
		String rating = request.getParameter("rating");
		
		System.out.println("description: " + description);
		System.out.println("rating : " + rating);
		//insert query in here
	
	
		
		if (breakfasttype != null) {
			//insert query
			
		}
		
		
		
		request.setAttribute("invoiceNum", invoiceNum);
		//response.sendRedirect("/HotelReservations/MakeReviewServlet");
		request.getRequestDispatcher("/MakeReviewServlet").forward(request,response);

		
	}

}

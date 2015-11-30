package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import cloud.WriteFile;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class Checkin extends HttpServlet {

	public void init() throws ServletException {
		super.init();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("id");
		System.out.println(userid);
		WriteFile file = new WriteFile();
		
		
		
	}

	//same as post method
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void destroy() {
		super.destroy();
	}

}
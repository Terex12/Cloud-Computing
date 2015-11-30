package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import cloud.WriteFile;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class HotPlace extends HttpServlet {

	public void init() throws ServletException {
		super.init();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		System.out.println(userid);
		WriteFile file = new WriteFile();
		String s = file.writeFile5(userid);
		
		
		
		if (s != null){ 
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    out.write("<!DOCTYPE html>");
		    out.write("<html>");
		    out.write("<head>");
		    out.write("<meta charset=\"UTF-8\">");
		    out.write("<title>s</title>");
		    out.write("<style type=\"text/css\">");
		    out.write("#chartdiv {width		: 100%;height		: 800px;font-size	: 11px;}");
		    out.write("</style>");
		    out.write("</head>");
		    out.write("<body>");
		    out.write("<div id=\"chartdiv\"></div>");
		    out.write("<script src=\"http://www.amcharts.com/lib/3/ammap.js\"></script>");
		    out.write("<script src=\"http://www.amcharts.com/lib/3/maps/js/worldLow.js\"></script>");
		    out.write("<script src=\"http://www.amcharts.com/lib/3/themes/dark.js\"></script>");
		    out.write("<script type=\"text/javascript\" src=\"https://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>");
		    out.write("<script>");
		    out.write(s);
		    out.write("</script>");
		    out.write("</body>");
		    out.write("</html>");
		    out.close();
			
		}
	}

	//same as post method
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void destroy() {
		super.destroy();
	}

}
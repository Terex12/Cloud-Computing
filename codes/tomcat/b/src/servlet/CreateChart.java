package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cloud.GetDate;
import cloud.MongoInfo;
import cloud.WriteFile;

public class CreateChart extends HttpServlet {

	public void init() throws ServletException {
		super.init();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userid = request.getParameter("userid");
		String start = request.getParameter("startdate");
		String end = request.getParameter("enddate");
		
		//start = "20100701";
		//end = "20100801";
		
		
		System.out.println(userid);
		System.out.println(start);
		System.out.println(end);
		
		MongoInfo mi = new MongoInfo();
		ArrayList<String> data = new ArrayList<String>();
		data = mi.MongoInfo2(userid);
		ArrayList<String> list = new ArrayList<String>();
		//get data from mongoDat
		for (int i = 0; i < data.size(); i = i+3){
			//System.out.println(data.get(i));
			if (Integer.valueOf(data.get(i)) >= Integer.valueOf(start) && Integer.valueOf(data.get(i)) <= Integer.valueOf(end))
				list.add(data.get(i));
		}
		int[] check = new int[7];
		for (String s : list){
			try {
				String day = GetDate.getDate(s);
				switch(day){
					case "Mon" : check[0]++;
								break;
					case "Tue" : check[1]++;
					 			break;
					case "Wed" : check[2]++;
					 			break;
					case "Thu" : check[3]++;
					 			break;
					case "Fri" : check[4]++;
					 			break;
					case "Sat" : check[5]++;
					 			break;
					case "Sun" : check[6]++;
					 			break;
				}	
			} 
			catch (ParseException e){ 
				e.printStackTrace();
			}
		}
		
		
		WriteFile file = new WriteFile();
		String s = file.writeFile2(userid, start, end, check);
		
		if (s != null){ 
			response.setContentType("text/html");
		    PrintWriter out = response.getWriter();
		    out.write("<!DOCTYPE HTML>");
		    out.write("<html>");
		    out.write("<head>");
		    out.write("<meta charset=\"UTF-8\">");
		    out.write("<title>s</title>");
		    out.write("<link rel=\"stylesheet\" href=\"assets/css/style2.css\" type=\"text/css\">");
		    out.write("</head>");
		    out.write("<body>");
		    out.write("<div id=\"chartdiv\" style=\"width: 100%; height: 600px;\"></div>");
		    out.write("</body>");
		    
		    
		    out.write("<script src=\"assets/js/amcharts.js\" type=\"text/javascript\"></script>");
		    out.write("<script src=\"assets/js/serial.js\" type=\"text/javascript\"></script>");
		    out.write("<script>");
		    out.write(s);
		    out.write("</script>");

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
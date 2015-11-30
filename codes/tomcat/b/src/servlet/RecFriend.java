package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;

import cloud.MongoInfo;
import cloud.WriteFile;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class RecFriend extends HttpServlet {

	public void init() throws ServletException {
		super.init();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String userid = request.getParameter("userid");
		System.out.println(userid);
		WriteFile file = new WriteFile();
		//String s = file.writeFile3(userid);
		
		MongoInfo mi = new MongoInfo();
		ArrayList<String> data = new ArrayList<String>();
		data = mi.MongoInfo3(userid);
		ArrayList<String> friendlist = new ArrayList<String>();
		ArrayList<String> intimacylist = new ArrayList<String>();
		//get data from mongoDat
		for (int i = 0; i < data.size(); i = i + 2){
			friendlist.add(data.get(i));
			if(Double.parseDouble(data.get(i+1))!=0)
			  intimacylist.add(data.get(i+1).substring(0, 4));
			else
			  intimacylist.add(data.get(i+1));
		}
		
		PrintWriter out = response.getWriter();
	    out.write("<!DOCTYPE HTML>");
	    out.write("<html>");
	    out.write("<head>");
	    out.write("<meta charset=\"UTF-8\">");
	    out.write("<title>s</title>");
	    out.write("<link rel=\"stylesheet\" href=\"assets/css/style2.css\" type=\"text/css\">");
	    out.write("</head>");
	    out.write("<body>");
	    out.write("<br>");
	    //out.write("<div id=\"chartdiv\" style=\"width: 100%; height: 400px;\"></div>");
	    out.write("<table style=\"width:80%\" ");
	    for(int i=0; i<friendlist.size(); i++){
	    	out.write("<tr>");
	    	out.write("<th><img src=\"picture/2.jpg\" alt=\"This is a picture\" width='100px'></th>");
	    	out.write("<th> user: " + friendlist.get(i)+" &nbsp;&nbsp  inimacy level: "+ intimacylist.get(i)+"</th>");
	    	out.write("</tr>");
	    }
	    out.write("</table>");
	    out.write("</body>");
	    
	    
	    out.write("<style>");
	    out.write("table {");
	    out.write("color: #333; font-family: Helvetica, Arial, sans-serif; font-size: 30px; width: 400px; border-collapse: collapse; border-spacing: 0; ");
	    out.write("}");
	    out.write("th, td { border: 1px solid #CCC; height: 30px; width: 400px}");
	    out.write("th { text-align: center; background: #F3F3F3;  font-weight: bold;}");
	    out.write("th { background: #FAFAFA;  }");
	    out.write("</style>");
	    out.write("<script>");
	    for (int i=0;i<friendlist.size();i++){
			System.out.println(friendlist.get(i));
			out.write(friendlist.get(i));
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
package servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.ArrayList;

import cloud.MongoInfo;
import cloud.WriteFile;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class FriendIntimacy extends HttpServlet {

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
		data = mi.MongoInfo6(userid);
		ArrayList<String> friendlist = new ArrayList<String>();
		ArrayList<String> intimacylist = new ArrayList<String>();
		String[][] top5 = new String[5][2];
		int nodenum = 0;
		//get data from mongoDat
		for (int i = 0; i < data.size(); i = i + 2){
			
			if(!data.get(i+1).equals("NaN")){
				friendlist.add(data.get(i));
				if(Double.parseDouble(data.get(i+1))!=0)
					
					  intimacylist.add(data.get(i+1).substring(0, 4));
			    else
					  intimacylist.add(data.get(i+1));
			}
			
		}
		
		for(int i=0; i<friendlist.size();i++){
            String friendid = friendlist.get(i);
            double fr = Double.parseDouble(intimacylist.get(i));
            if(!intimacylist.get(i).equals("NaN")){
            	if(nodenum<5){
        			top5[nodenum][0] = friendid;
        			top5[nodenum][1] = Double.toString(fr);
        			nodenum++;	
        		}
                
                else{
        			String tempnode = friendid;
        			double temppr = fr;
        			for(int j=0;j<5;j++){
        				if(temppr>Double.parseDouble(top5[j][1])){
        					String tempNode = tempnode;
        					double tempPR = temppr;
        					tempnode = top5[j][0];
        					temppr = Double.parseDouble(top5[j][1]);	
        					top5[j][0] = tempNode;
        					top5[j][1] = Double.toString(tempPR);
        				}
        			}
        		}
            }  

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
	    for(int i=0; i<top5.length; i++){
	    	out.write("<tr>");
	    	out.write("<th><img src=\"picture/2.jpg\" alt=\"This is a picture\" width='100px'></th>");
	    	out.write("<th> user: " + top5[i][0]+" &nbsp;&nbsp  inimacy level: "+ top5[i][1]+"</th>");
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
	}

	//same as post method
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void destroy() {
		super.destroy();
	}

}
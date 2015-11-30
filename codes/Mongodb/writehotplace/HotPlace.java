package ccpro3;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class HotPlace {
	
	public static void hotplace(DB db, File file){
		File f = file;
		double min=0;
		int nodenum = 0;
		int currMinIndex = 0;
		String[][] top10 = new String[10][2];
		
		 try {
	            FileInputStream out = new FileInputStream(file);
	            InputStreamReader isr = new InputStreamReader(out);
	            BufferedReader br = new BufferedReader(isr);
	            String line = null;
	            while((line=br.readLine())!=null){
	                 StringTokenizer itrt = new StringTokenizer(line);
	                 String locid = itrt.nextToken()+" "+itrt.nextToken();
	                 int fr = Integer.parseInt(itrt.nextToken());
	                 if(nodenum<10){
	         			top10[nodenum][0] = locid;
	         			top10[nodenum][1] = Integer.toString(fr);
	         			nodenum++;	
	         		}
	                 
	                 else{
	         			String tempnode = locid;
	         			int temppr = fr;
	         			for(int i=0;i<10;i++){
	         				if(temppr>Integer.parseInt(top10[i][1])){
	         					String tempNode = tempnode;
	         					int tempPR = temppr;
	         					tempnode = top10[i][0];
	         					temppr = Integer.parseInt(top10[i][1]);	
	         					top10[i][0] = tempNode;
	         					top10[i][1] = Integer.toString(tempPR);
	         				}
	         			}
	         		}

	               }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
		for(int i=0;i<10;i++){
			String tempnode = top10[i][0];
			int temppr = Integer.parseInt(top10[i][1]);
			for(int j=i+1;j<10;j++){
				if(temppr < Integer.parseInt(top10[j][1])){
					top10[i][0] = top10[j][0];
					top10[i][1] = top10[j][1];
					top10[j][0] = tempnode;
					top10[j][1] = Integer.toString(temppr);
					tempnode = top10[i][0];
					temppr = Integer.parseInt(top10[i][1]);
				}
			}
		}
		
		DB tdb = db;
		DBCollection tdbcol = tdb.getCollection("hotplace");
		BasicDBObject document = new BasicDBObject();
		ArrayList<String> list = new ArrayList<>();
		for(int i=0;i<10;i++){
			list.add(top10[i][0]);
			list.add(top10[i][1]);
	       
		}
		document.put("id", "top10");
		document.put("info", list);
		tdbcol.insert(document);
		
	}

	public static void main(String[] args) {
		Mongo mongo = new Mongo("localhost", 27017);
        DB db = mongo.getDB("test");
        File file = new File("d:/file/result1");
        hotplace(db,file);
		// TODO Auto-generated method stub

	}

}

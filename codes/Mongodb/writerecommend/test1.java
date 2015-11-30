
import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.mongodb.*;

import org.bson.*;
import org.bson.io.*;
import org.bson.types.*;
/**
* Java + MongoDB Hello world Example
* 
*/
public class test1 {
	
	public static void writefoot(DB db,String userid) throws IOException{
		DB tdb = db;
		DBCollection tdbcol = tdb.getCollection("RecFriend");
		//System.out.println("write into db");

		ArrayList<String> list = new ArrayList<>();
		FindUsers fu = new FindUsers();
		String[][] info = fu.finduser(tdb,userid);
		for(int i =0;i<info.length;i++){
			list.add(info[i][0]);
			list.add(info[i][1]);
		}
		System.out.println(userid);
		BasicDBObject document = new BasicDBObject();
        document.put("id", userid);
		//document.put("date", date);
		document.put("info", list);
		tdbcol.insert(document);
		System.out.println(userid);
	
	}
	
	public static void writefriend(DB db,String userid) throws IOException{
		DB tdb = db;
		DBCollection tdbcol = tdb.getCollection("friend");
		//System.out.println("write into db");
		
		String path = "d:/file/Gowalla_edges.txt";
		
		File file = new File(path);
		FileInputStream out;
		ArrayList<String> list = new ArrayList<>();
		try {
			out = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(out);
	        BufferedReader br = new BufferedReader(isr);
	        String line = null;
	        while((line=br.readLine())!=null){
				   StringTokenizer itrt = new StringTokenizer(line);
				   String uid = itrt.nextToken();
				   String friend = itrt.nextToken();
				   //String locinfo = latitude + " "+longtitude;
				   if(uid.equals(userid)){
					   list.add(friend);
					   //System.out.println("write");   
					  
				   }
				   
				}
	        BasicDBObject document = new BasicDBObject();
	        document.put("id", userid);
			//document.put("date", date);
			document.put("info", list);
			tdbcol.insert(document);
			System.out.println(userid);
	        
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void getfoot(DB db,String userid) {
		DB tdb = db;
		DBCollection tdbcol = tdb.getCollection("friend");
		BasicDBObject searchQuery = new BasicDBObject();
		BasicDBObject findinfo = new BasicDBObject("info",1);
		searchQuery.put("id", userid);
		//DBCursor cursor = tdbcol.find(searchQuery,findinfo);
		DBObject searchinfo = tdbcol.findOne(searchQuery);
		Object info = searchinfo.get("info");
		ArrayList<String> list = new ArrayList<>();
		if (info instanceof ArrayList<?>){
			list = (ArrayList<String>)info;
	    }
		for (int i = 0; i < list.size(); i = i + 3){
			String latitude = list.get(i+1);
			System.out.println(latitude);
			String longitude = list.get(i+2);
			System.out.println(longitude);
		}
		
	}
    public static void main(String[] args) {
        try {
            Mongo mongo = new Mongo("localhost", 27017);
            DB db = mongo.getDB("test");
            try {
                   File file = new File("d:/file/part-r-00001");
                              FileInputStream out = new FileInputStream(file);
                      InputStreamReader isr = new InputStreamReader(out);
                      BufferedReader br = new BufferedReader(isr);
                      String line = null;
                      while((line=br.readLine())!=null){
                              StringTokenizer itrt = new StringTokenizer(line);
                              String userid = itrt.nextToken();
                              String datapath = "data"+userid;
            	
				              writefoot(db, userid);
				              FindPlace fp = new FindPlace();
				              fp.place(db,userid);
                      }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
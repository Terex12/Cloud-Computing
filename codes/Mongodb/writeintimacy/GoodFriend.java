package ccpro4;


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
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class GoodFriend {
	
	public static void writefriend(DB db,String userid) throws IOException{
		DB tdb = db;
		DBCollection tdbcol = tdb.getCollection("GoodFriend");
		//System.out.println("write into db");

		ArrayList<String> list = new ArrayList<>();
		String[][] info = finduser(tdb,userid);
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
	
	public static String[][] finduser(DB db,String userid){
		//Mongo mongo = new Mongo("localhost", 27017);
		DB tdb = db;
		DBCollection tdbcol = tdb.getCollection("friend");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", userid);
		//DBCursor cursor = tdbcol.find(searchQuery,findinfo);
		DBObject searchinfo = tdbcol.findOne(searchQuery);
		Object info = searchinfo.get("info");
		double result = 0;
		ArrayList<String> friendlist = new ArrayList<>();
		if (info instanceof ArrayList<?>){
			friendlist = (ArrayList<String>)info;
	    }
		String[][] info1 = new String[friendlist.size()][2];
		for(int i=0;i<friendlist.size();i++){

			result = algorithm(tdb,userid,friendlist.get(i));
			info1[i][0] = friendlist.get(i).toString();
			info1[i][1] = Double.toString(result);
		}
		
		return info1;
		

                
	}
	
	public static double algorithm(DB db,String uid1, String uid2){
		float c = 0;
		float a = 0;
		float b = 0;
		double cos = 0;
		ArrayList<String> locinfo = new ArrayList<String>();
		
		
		MongoInfo m1 = new MongoInfo();
		ArrayList<String> loc1 =  m1.MongoInfo2(db,uid1);
		MongoInfo m2 = new MongoInfo();
		ArrayList<String> loc2 =  m2.MongoInfo2(db,uid2);
		ArrayList<String> l1 = new ArrayList<String>();
		ArrayList<String> l2 = new ArrayList<String>();
		for (int i = 0; i < loc1.size(); i = i + 3){
		  String[] la = loc1.get(i+1).split("\\.");
		  String[] lo = loc1.get(i+2).split("\\.");
		  //String loc = loc1.get(i+1)+" "+loc1.get(i+2);
		  String loc = la[0]+" "+lo[0];
		  l1.add(loc);
		  if(!locinfo.contains(loc)){
			  locinfo.add(loc);
		  }
		}
		for (int i = 0; i < loc2.size(); i = i + 3){
			String[] la = loc2.get(i+1).split("\\.");
			String[] lo = loc2.get(i+2).split("\\.");
			//String loc = loc2.get(i+1)+" "+loc2.get(i+2);
			String loc = la[0]+" "+lo[0];
			l2.add(loc);
			if(!locinfo.contains(loc)){
				locinfo.add(loc);
			}
	    }
		
		float[] vertex1 = new float[locinfo.size()];
		float[] vertex2 = new float[locinfo.size()];
		for(int i=0;i<locinfo.size();i++){
			
			if(l1.contains(locinfo.get(i)))
				vertex1[i] = 1;
			else
				vertex1[i] = 0;
			if(l2.contains(locinfo.get(i)))
				vertex2[i] = 1;
			else
				vertex2[i] = 0;
			c += vertex1[i]*vertex2[i];
			a += vertex1[i]*vertex1[i];
			b += vertex2[i]*vertex2[i];
		}
		//System.out.println(c);
		
		cos = c/(Math.sqrt(a)*Math.sqrt(b));
		//System.out.println(cos);
		
		return cos;
	}
	
	public static double friend(DB db,String userid, String uid){
		//Mongo mongo = new Mongo("localhost", 27017);
		DB tdb = db;
		DBCollection tdbcol = tdb.getCollection("friend");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", userid);
		//DBCursor cursor = tdbcol.find(searchQuery,findinfo);
		DBObject searchinfo = tdbcol.findOne(searchQuery);
		Object info = searchinfo.get("info");
		double result = 0;
		ArrayList<String> friendlist = new ArrayList<>();
		if (info instanceof ArrayList<?>){
			friendlist = (ArrayList<String>)info;
	    }
		if(!friendlist.contains(uid)){
			result = algorithm(tdb,userid,uid);
		}
		return result;
	}

	public static void main(String[] args) {
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
                           writefriend(db,userid);

                   }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}

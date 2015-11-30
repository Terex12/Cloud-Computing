package cloud;
import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class RecommendFriend {
	public static double algorithm(String uid1, String uid2){
		float c = 0;
		float a = 0;
		float b = 0;
		double cos = 0;
		ArrayList<String> locinfo = new ArrayList<String>();
		
		MongoInfo m1 = new MongoInfo();
		ArrayList<String> loc1 =  m1.MongoInfo2(uid1);
		MongoInfo m2 = new MongoInfo();
		ArrayList<String> loc2 =  m2.MongoInfo2(uid2);
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
		System.out.println(c);
		
		cos = c/(Math.sqrt(a)*Math.sqrt(b));
		System.out.println(cos);
		
		return cos;
	}
	
	public static void friend(String userid, String uid){
		Mongo mongo = new Mongo("localhost", 27017);
		DB tdb = mongo.getDB("test");
		DBCollection tdbcol = tdb.getCollection("friend");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("id", userid);
		//DBCursor cursor = tdbcol.find(searchQuery,findinfo);
		DBObject searchinfo = tdbcol.findOne(searchQuery);
		Object info = searchinfo.get("friend");
		ArrayList<String> friendlist = new ArrayList<>();
		if (info instanceof ArrayList<?>){
			friendlist = (ArrayList<String>)info;
	    }
		if(!friendlist.contains(uid)){
			algorithm(userid,uid);
		}
	}
	
	public static void main(String[] args){
		algorithm("41","42");
	}

}

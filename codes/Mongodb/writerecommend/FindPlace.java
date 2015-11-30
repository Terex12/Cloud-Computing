import java.util.ArrayList;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class FindPlace {
  public static void place(DB db,String userid){
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
		ArrayList<String> placelist = new ArrayList<>();
		if (info instanceof ArrayList<?>){
			friendlist = (ArrayList<String>)info;
	    }
		for(int i=0;i<friendlist.size();i++){
			String uid = friendlist.get(i).toString();
			DBCollection pdbcol = tdb.getCollection("mostvisit");
			BasicDBObject searchQuery2 = new BasicDBObject();
			//BasicDBObject findinfo = new BasicDBObject("latitude",1);
			searchQuery2.put("id", uid);
			//DBCursor cursor = tdbcol.find(searchQuery,findinfo);
			DBObject searchla = pdbcol.findOne(searchQuery2);
			if(searchla!=null){
				
				Object la = searchla.get("latitude");
				DBObject searchlo = pdbcol.findOne(searchQuery2);
				Object lo = searchlo.get("longitude");
				placelist.add(la.toString());
				placelist.add(lo.toString());
			}
		}
		
		DBCollection wdbcol = tdb.getCollection("RecPlace");
		BasicDBObject document = new BasicDBObject();
		System.out.println(userid);
        document.put("id", userid);
		//document.put("date", date);
		document.put("info", placelist);
		wdbcol.insert(document);
	}
  
}
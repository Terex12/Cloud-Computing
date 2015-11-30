package ccpro;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class WriteMongo {
	public void WriteMongo(DB db,String userid, String latitude, String longitude){
		//Mongo mongo = new Mongo("localhost", 27017);
		//DB wdb = mongo.getDB("test");
		DB wdb = db;
		DBCollection wdbcol = wdb.getCollection("mostvisit");
		BasicDBObject document = new BasicDBObject();
		System.out.println(userid);
		//System.out.println(list1.get(2));
		System.out.println(latitude);
		System.out.println(longitude);
		document.put("id", userid);
		document.put("latitude", latitude);
		document.put("longitude", longitude);
		wdbcol.insert(document);
		
	}

}

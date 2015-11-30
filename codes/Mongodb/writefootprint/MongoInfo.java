package mongodb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class MongoInfo {
	public ArrayList<String> MongoInfo(String userid) throws IOException{
		Mongo mongo = new Mongo("localhost", 27017);
		DB tdb = mongo.getDB("test");
		DBCollection tdbcol = tdb.getCollection("footcol");
		BasicDBObject searchQuery = new BasicDBObject();
		BasicDBObject findinfo = new BasicDBObject("info",1);
		searchQuery.put("id", "43");
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
		return list;
	}
}

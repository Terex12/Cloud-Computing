package mongodb;
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
public class writemongo {
	
	
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
		
	}
    public static void main(String[] args) {
        try {
            //实例化Mongo对象，连接27017端口
            Mongo mongo = new Mongo("localhost", 27017);
                               //连接名为yourdb的数据库，假如数据库不存在的话，mongodb会自动建立
            DB db = mongo.getDB("test");
            //getfoot(db,"40");
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
            	
				              //writefoot(db, userid);
                              writefriend(db,userid);
                      }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            /* Get collection from MongoDB, database named "yourDB"
//从Mongodb中获得名为yourColleection的数据集合，如果该数据集合不存在，Mongodb会为其新建立
           // DBCollection collection = db.getCollection("footcol");
    // 使用BasicDBObject对象创建一个mongodb的document,并给予赋值。
            BasicDBObject document = new BasicDBObject();

            document.put("id", 1001);
            document.put("msg", "hello world mongoDB in Java");
            //将新建立的document保存到collection中去
            collection.insert(document);
            // 创建要查询的document
            BasicDBObject searchQuery = new BasicDBObject();
            searchQuery.put("id", 1001);
            // 使用collection的find方法查找document
            DBCursor cursor = collection.find(searchQuery);
            //循环输出结果
            while (cursor.hasNext()) {
            System.out.println(cursor.next());
            }
            System.out.println("Done");*/ 
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
package ccpro;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.StringTokenizer;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

class Location{
	private float latitude;
	private float longitude;
	public Location(float la, float lo){
		this.latitude = la;
		this.longitude = lo;
	}
	public String toString(){
		return "latitude : " + this.latitude + " longitude : " + this.longitude;
	}
	public float getLatitude(){
		return this.latitude;
	}
	public float getLongitude(){
		return this.longitude;
	}
	@Override
	public boolean equals(Object obj){
		if (!(obj instanceof Location))
			return false;
		if (obj == this)
			return true;
		Location l = (Location)obj;
		if (this.latitude == l.getLatitude() && this.longitude == l.getLongitude())
			return true;
		else
			return false;
	}
	public int hashCode(){
		return (int)(latitude+longitude);
	}
}

class Elem {
	private Location location;
	private int rank;
	public Elem(Location loc, int r){
		this.location = loc;
		this.rank = r;
	}
	public Location getLoc(){
		return location;
	}
	public int getRank(){
		return rank;
	}
	public void increaseRank(){
		rank++;
	}
	public String toString(){
		return getLoc().toString() + " Rank : " + this.rank;
	}
	
	public String getLatitude(){
		return Float.toString(location.getLatitude());
	}
	
	public String getLongitude(){
		return Float.toString(location.getLongitude());
	}
}

public class MostVisit{
	//parameter is UserID
	public static LinkedList<Elem> getMostPlace(ArrayList<String> list) {
		//data from MongoDB
		//ArrayList<String> list = new ArrayList<>();
		LinkedList<Elem> elements = new LinkedList<>();
		HashSet<Location> set = new HashSet<>();

		for (int i = 0; i < list.size(); i=i+3){
			//String[] array = list.get(i).trim().split(" ");
			//if (array.length == 3){
				//float la = Float.valueOf(array[1]);
				//float lo = Float.valueOf(array[2]);
			float la = Float.valueOf(list.get(i+1));
			float lo = Float.valueOf(list.get(i+2));
				//System.out.println(la+" "+lo);
				Location loc = new Location(la, lo);
				if (set.contains(loc)){
					for (Elem x : elements){
						if (x.getLoc().equals(loc)){
							x.increaseRank();
						}
					}
				}
				else{
					set.add(loc);
					Elem e = new Elem(loc,1);
					elements.add(e);
				}
			}
			//else
				//continue;
		//}
		
		elements.sort(new Comparator<Elem>(){
			public int compare(Elem e1, Elem e2){
				return e1.getRank()-e2.getRank();
			}
		});
		return elements;
	}
			
	
	public static void main(String[] args) throws ParseException{
		//ArrayList<String> list = new ArrayList<>();
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("test");
		File file = new File("d:/file/part-r-00001");
		try{
			FileInputStream out = new FileInputStream(file);  
	        InputStreamReader isr = new InputStreamReader(out);
	        BufferedReader br = new BufferedReader(isr);
	        String line = null;
	        while((line=br.readLine())!=null){
	        	StringTokenizer itrt = new StringTokenizer(line);
                String userid = itrt.nextToken();
                MongoInfo mi = new MongoInfo();
                ArrayList<String> list1 = new ArrayList<>();
                try {
        			list1 = mi.MongoInfo(db,userid);
        		} catch (IOException e1) {
        			// TODO Auto-generated catch block
        			e1.printStackTrace();
        		}
                LinkedList<Elem> array = new LinkedList<>();
                array = getMostPlace(list1);
        		Elem e = array.getLast();
        		
        		
        		WriteMongo wm = new WriteMongo();
        		wm.WriteMongo(db,userid, e.getLatitude(), e.getLongitude());
	        }	
		}catch (Exception e){}
	}
}
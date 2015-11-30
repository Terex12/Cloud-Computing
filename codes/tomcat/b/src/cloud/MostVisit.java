package cloud;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;

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
}

public class MostVisit{
	//parameter is UserID
	public static LinkedList<Elem> getMostPlace(int userID, ArrayList<String> list) {
		//data from MongoDB
		//ArrayList<String> list = new ArrayList<>();
		LinkedList<Elem> elements = new LinkedList<>();
		HashSet<Location> set = new HashSet<>();
		
		for (int i = 0; i < list.size(); i++){
			String[] array = list.get(i).trim().split(" ");
			if (array.length == 3){
				float la = Float.valueOf(array[1]);
				float lo = Float.valueOf(array[2]);
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
			else
				continue;
		}
		
		elements.sort(new Comparator<Elem>(){
			public int compare(Elem e1, Elem e2){
				return e1.getRank()-e2.getRank();
			}
		});
		return elements;
	}
			
	
	public static void main(String[] args) throws ParseException{
		ArrayList<String> list = new ArrayList<>();
		String s1 = "  123 12.33 78.7  ";
		String s2 = " 123 12.33 78.7";
		String s3 = "123 12.33 78.7  ";
		String s4 = "123 15.33 7.0";
		String s5 = "123 12.33 78.7";
		String s6 = "123";
		String s7 = "123 -1 -1";
		String s8 = "123 -1 -1 ";
		list.add(s1);
		list.add(s2);
		list.add(s3);
		list.add(s4);
		list.add(s5);
		list.add(s6);
		list.add(s7);
		list.add(s8);
		
		
		LinkedList<Elem> array = getMostPlace(123, list);
		for (Elem e : array){
			System.out.println(array.getLast().toString());
		}
	}
}
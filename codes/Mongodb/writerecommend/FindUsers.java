import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;


public class FindUsers {
	
	public static String[][] finduser(DB tdb,String userid){
		//Mongo mongo = new Mongo("localhost", 27017);
		MongoInfo m = new MongoInfo();
		
		String[] location = m.MongoInfo1(tdb,userid);
		float latitude = Float.parseFloat(location[0]);
		float longitude = Float.parseFloat(location[1]);
		ArrayList list = new ArrayList();
		File file = new File("d:/file/test.txt");
		try{
			FileInputStream out = new FileInputStream(file);  
	        InputStreamReader isr = new InputStreamReader(out);
	        BufferedReader br = new BufferedReader(isr);
	        String line = null;
	        while((line=br.readLine())!=null){
	        	StringTokenizer itrt = new StringTokenizer(line);
                String uid = itrt.nextToken();
                if(!uid.equals(userid)){
                	MongoInfo mi = new MongoInfo();
                    String[] loc = mi.MongoInfo1(tdb,uid);
                    float la = Float.parseFloat(loc[0]);
                    float lo = Float.parseFloat(loc[1]);
                    if(Math.abs(latitude-la)<5 && Math.abs(longitude-lo)<5){
                    	list.add(uid);
                    }
                    	
                }
	        }
		}catch(Exception e){}
		String[][] info = new String[list.size()][2];
		for(int i=0;i<list.size();i++){
			RecommendFriend rf = new RecommendFriend();
			double result = rf.friend(tdb,userid, list.get(i).toString());
			info[i][0] = list.get(i).toString();
			info[i][1] = Double.toString(result);
		}
		toprec tp = new toprec();
		String[][] finfo = tp.toprec(info);
		return finfo;
                
	}

	public static void main(String[] args) {
		//finduser("36");// TODO Auto-generated method stub

	}

}

package recommend;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class itemBasedRecommend {

	public static String inputFileAddress = "D:\\Eclipse_workspace\\result1.csv";
	public static String locationFile = "D:\\Eclipse_workspace\\result_ori.csv";
	
	public static String convertHash(Long hash) throws IOException{   //convert hash to location pair
		String hashCode = hash.toString();   
        InputStream is = new FileInputStream(locationFile);
        String line; 
        BufferedReader reader = new BufferedReader(new InputStreamReader(is)); 
        line = reader.readLine(); //open file
        String result = null;
        while (line != null) { 
        	String[] list = line.split(",");   //split line (userID, hash, value, location pair)
        	if (list[1].equals(hashCode)){     //each line is one list ,contains 4 string
        		result = list[3];				
        		break;
        	}
        	else {
        		line = reader.readLine(); 
        	}	
        }
        reader.close();
        is.close();
		return result;
	}
	
	public static void main(String[] args) throws Exception {
		
	    final int RECOMMENDER_NUM = 3;
	    long userId = 0;
	    String Loc = null;
	    
	    DataModel model = new FileDataModel(new File(inputFileAddress));    
	    ItemSimilarity user = new EuclideanDistanceSimilarity(model);                       
	    Recommender r = new GenericItemBasedRecommender(model, user);    
	    LongPrimitiveIterator iter = model.getUserIDs();        
	    while (iter.hasNext()) {
	        long uid = iter.nextLong();        
	        java.util.List<RecommendedItem> list = r.recommend(uid, RECOMMENDER_NUM);        
	        System.out.printf("uid:%s", uid);        
	        for (RecommendedItem ritem : list)
	        {           
	        	   userId = ritem.getItemID();
	        	   Loc = convertHash(userId);
		           System.out.printf("(%s,%f)",Loc, ritem.getValue());            
	         }            
	        System.out.println();    
	    }
	    }
}



	
	

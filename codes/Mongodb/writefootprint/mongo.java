package mongodb;

import java.io.*;
import java.util.StringTokenizer;

import com.mongodb.*;
import com.mongodb.gridfs.*;
import com.mongodb.util.*;
import com.mongodb.client.*;

//import org.springframework.*;
import org.springframework.data.mongodb.core.MongoTemplate;

public class mongo {

	public void SaveFile(String collectionName, File file, String fileid, String companyid, String filename) {  
        /*try {  
        	//Mongo mg = new Mongo();
            //DB db = mongoTemplate.getDb();  
        	//DB db = mg.getDB("pr");
            // 存储fs的根节点  
            GridFS gridFS = new GridFS(db, collectionName);  
            GridFSInputFile gfs = gridFS.createFile(file);  
            gfs.put("aliases", companyid);  
            gfs.put("filename", fileid);  
            gfs.put("contentType", filename.substring(filename.lastIndexOf(".")));  
            gfs.save();  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("存储文件时发生错误！！！");  
        }  */
    }  
	public static void gridFSInput(DB db,String inputFilepath) {  
        
        File inputFile = new File(inputFilepath);  
        GridFSInputFile gfsInput;  
        try {  
            gfsInput = new GridFS(db, "fs")  
                    .createFile(inputFile);  
            gfsInput.setFilename("test1"); 
            gfsInput.save();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
	}
	
	public static void gridFSOutput(DB db) throws IOException{
		 GridFS gridFS = new GridFS(db, "fs");  
         GridFSDBFile dbfile = gridFS.findOne("data35");
         //FileInputStream fin = new FileInputStream(dbfile);
         File fs = new File("d:/file/result1.txt");
         dbfile.writeTo(fs);
	}
	
	public static void input(DB db,String userid){
		DB d = db;
		File file1 = new File("d:/file/data");
		try {
	    	FileInputStream out = new FileInputStream(file1);
            InputStreamReader isr = new InputStreamReader(out);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while((line=br.readLine())!=null){
                StringTokenizer itrt = new StringTokenizer(line);
                String uid = itrt.nextToken();
                if(uid.equals(userid)){
                	
                }
                    //gridFSInput(db,"/usr/footprint/data/"+datapath,userid);
            }

			//gridFSOutput(db);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	
	
	
  
	public static void main(String[] args) {
		 Mongo m = new Mongo("ec2-52-10-159-171.us-west-2.compute.amazonaws.com", 27017);
	     DB db = m.getDB("foottracker");
	     
	     File file1 = new File("d:/file/userid");
	     //File file2 = new File("d:/file/data");
	     try {
	    	 FileInputStream out = new FileInputStream(file1);
             InputStreamReader isr = new InputStreamReader(out);
             BufferedReader br = new BufferedReader(isr);
             String line = null;
             while((line=br.readLine())!=null){
                     StringTokenizer itrt = new StringTokenizer(line);
                     String userid = itrt.nextToken();
                     //gridFSInput(db,"/usr/footprint/data/"+datapath,userid);
             }

			//gridFSOutput(db);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}

}

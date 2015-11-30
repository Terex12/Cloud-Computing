

import java.io.*;
import java.util.StringTokenizer;

import com.mongodb.*;
import com.mongodb.gridfs.*;
import com.mongodb.util.*;
import com.mongodb.client.*;

//import org.springframework.*;
//import org.springframework.data.mongodb.core.MongoTemplate;

public class mongo {

	/*public void SaveFile(String collectionName, File file, String fileid, String companyid, String filename) {  
        try {  
        	//Mongo mg = new Mongo();
            DB db = mongoTemplate.getDb();  
        	//DB db = mg.getDB("pr")
            GridFS gridFS = new GridFS(db, collectionName);  
            GridFSInputFile gfs = gridFS.createFile(file);  
            gfs.put("aliases", companyid);  
            gfs.put("filename", fileid);  
            gfs.put("contentType", filename.substring(filename.lastIndexOf(".")));  
            gfs.save();  
        } catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("save error");  
        }  
    } */ 
	public static void gridFSInput(DB db,String inputFilepath,String userid) {  
        
        File inputFile = new File(inputFilepath);  
        GridFSInputFile gfsInput;  
        try {  
            gfsInput = new GridFS(db, "fs")  
                    .createFile(inputFile);  
            //gfsInput.setFilename(inputFile); 
            gfsInput.put("userid",userid);
            gfsInput.save();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
          
	}
	
	public static void gridFSOutput(DB db) throws IOException{
		 GridFS gridFS = new GridFS(db, "fs");  
         GridFSDBFile dbfile = gridFS.findOne("test1");  
         File fs = new File("d:/file/result1.txt");
         dbfile.writeTo(fs);
	}
	
  
	public static void main(String[] args) {
		 Mongo m = new Mongo("master", 27017);
	     DB db = m.getDB("test");
             File file = new File("/usr/footprint/userloc/test.txt");
try{
			FileInputStream out = new FileInputStream(file);  
	        InputStreamReader isr = new InputStreamReader(out);
	        BufferedReader br = new BufferedReader(isr);
	        String line = null;
	        while((line=br.readLine())!=null){
	        	StringTokenizer itrt = new StringTokenizer(line);
                        String userid = itrt.nextToken();
                        String datapath = "data"+userid;
			gridFSInput(db,"/usr/footprint/data/"+datapath,userid);
	        }
		}
		catch(Exception e){}
//gridFSInput(db,"/usr/mongoInput/test.txt");
	    /* try {
			//gridFSOutput(db);
gridFSInput(db,"/usr/mongoInput/part-r-00000");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		// TODO Auto-generated method stub

	}

}

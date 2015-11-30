import java.io.*;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class footmain {

	public static void main(String[] args) {
		String input = args[0];
		String output = args[1];
		//String startDate = args[2];
		//String endDate = args[3];
		File file = new File("/usr/footprint/userloc/test.txt");
		try{
			FileInputStream out = new FileInputStream(file);  
	        InputStreamReader isr = new InputStreamReader(out);
	        BufferedReader br = new BufferedReader(isr);
	        String line = null;
	        while((line=br.readLine())!=null){
	        	StringTokenizer itrt = new StringTokenizer(line);
                        String userid = itrt.nextToken();
				UserLoc ul = new UserLoc();
				ul.UserLocjob(input,output,userid);
				String outputpath = output + userid;
				String uri = "hdfs://master:9000/output/"+outputpath+"/part-r-00000";
				Configuration conf = new Configuration();
                FileSystem fs = FileSystem.get(URI.create(uri), conf);
				try {
					InputStream is=fs.open(new Path(uri));
				    String datapath = "data"+userid;
					FileOutputStream fos = new FileOutputStream(new File("/usr/footprint/data/"+datapath));
                    IOUtils.copyBytes(is, fos,2048, true);
                } finally {	
                    IOUtils.closeStream(is);
                    IOUtils.closeStream(fos);
                }
	        }
		}
		catch(Exception e){}
		 

	}

}

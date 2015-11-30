import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class Friend {

  public static class FMapper
       extends Mapper<Object, Text, Text, Text>{

    private Text userid = new Text(); 
    private Text info = new Text();
    private Path path[]=null;
    LinkedList<String> list = new LinkedList<String>();
        protected void setup(Context context) throws IOException,
                InterruptedException {
            Configuration conf=context.getConfiguration();
			String uid = conf.get("userid");
            path=DistributedCache.getLocalCacheFiles(conf);
            FileSystem fsopen=FileSystem.getLocal(conf);
            FSDataInputStream in=fsopen.open(path[0]);
			String line=null;
			while((line=in.readLine()) != null){
			    StringTokenizer itrt = new StringTokenizer(line);
			    if (itrt.nextToken().equals(uid)) {
				   list.add(itrt.nextToken());
			    }
			}
        }

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer st = new StringTokenizer(value.toString());
	  ArrayList<String> temp = new ArrayList<>();
      Configuration conf = context.getConfiguration();
	  String uid = conf.get("userid");
	  while (st.hasMoreTokens()){
			  String token = st.nextToken().toString();
			  if (token != " " && token != ""){
				  temp.add(token);
			  }
		  }
      String UID = temp.get(0).toString();
      userid.set(UID);
     
	  StringBuilder sb = new StringBuilder();
	  for (int i = 1; i < temp.size(); i++){
			  sb.append(temp.get(i).toString());
			  if (i != temp.size()-1)
				  sb.append(" ");
		  }
	  info.set(sb.toString());
	  if(list.contains(UID)){
	    context.write(userid, info);
      }
    }
  }

  public static class FReducer
       extends Reducer<Text,Text,Text,Text> {

    public void reduce(Text key, Text values,
                       Context context
                       ) throws IOException, InterruptedException {
      context.write(key, values);

	  }
	  
    }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
	String path = args[0];
	DistributedCache.addCacheFile(new URI(path), conf);
	
    Job job = Job.getInstance(conf, "Friend");
    job.setJarByClass(Friend.class);
    job.setMapperClass(FMapper.class);
    //job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(FReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[1]));
    FileOutputFormat.setOutputPath(job, new Path(args[2]));
	
	job.getConfiguration().set("userid", args[3]);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

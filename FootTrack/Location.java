import java.io.IOException;
import java.util.StringTokenizer;
import java.util.ArrayList;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Location {

  public static class LocMapper
       extends Mapper<Object, Text, Text, Text>{

    private Text userid = new Text();
	private Text info = new Text();

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
				  //System.out.println("Tokens: " + token);
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
	  //info.set(st.countTokens()-1);
	  if(uid.equals(UID)){
	    context.write(userid, info);
}
    }
  }

  public static class LocReducer
       extends Reducer<Text,Text,Text,Text> {
    private Text Locinfo = new Text();

    public void reduce(Text key, Iterable<Text> values,
                       Context context
                       ) throws IOException, InterruptedException {
      Configuration conf = context.getConfiguration();
	  String userid = conf.get("userid");
	  String startDate = conf.get("startDate");
	  int sd = Integer.parseInt(startDate);
	  String endDate = conf.get("endDate");
	  int ed = Integer.parseInt(endDate);
          int date = 0;
	  //String locinfo = null;
	  
      for (Text val : values) {
		StringTokenizer st = new StringTokenizer(val.toString());
		String Date = st.nextToken();
		date = Integer.parseInt(Date);
		String longitude = st.nextToken();
		String latitude = st.nextToken();
		String locationid = st.nextToken();
		String locinfo = longitude+" "+latitude;
		Locinfo.set(locinfo);
          if(date>=sd && date<=ed ){
                 context.write(key, Locinfo);
          }

	  }
	  
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Location");
    job.setJarByClass(Location.class);
    job.setMapperClass(LocMapper.class);
    //job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(LocReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	
	job.getConfiguration().set("userid", args[2]);
	job.getConfiguration().set("startDate", args[3]);
	job.getConfiguration().set("endDate", args[4]);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

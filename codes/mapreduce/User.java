import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class User {
	  public static class UserMapper extends Mapper<Object, Text, Text, IntWritable>{
		  private final static IntWritable one = new IntWritable(1);
		  public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
			  StringTokenizer line = new StringTokenizer(value.toString());
			  ArrayList<String> temp = new ArrayList<>();
			  while (line.hasMoreTokens()){
				  String token = line.nextToken().toString();
				  if (token != " " && token != ""){
					  temp.add(token);
					  System.out.println("Tokens: " + token);
				  }
			  }
			  Text userId = new Text(temp.get(0).toString());
			  context.write(userId, one);
		  }

	  }
	  public static class UserReducer extends Reducer<Text, IntWritable, Text, Text> {
		  public void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException {
			  for (IntWritable v : value) {}
			  context.write(key, new Text(""));
		  }
	  }
	  public static void main(String[] args) throws Exception {
	    Configuration conf = new Configuration();
	     if (args.length != 2) {
	       System.err.println("Please check input");
	       System.exit(2);
	     }
	    Job job = Job.getInstance(conf, "userId");
	    job.setJarByClass(User.class);
	    job.setMapperClass(UserMapper.class);
	    job.setReducerClass(UserReducer.class);
	    job.setMapOutputValueClass(IntWritable.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	  }
	}
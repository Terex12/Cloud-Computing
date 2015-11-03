import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Checkin {

  public static class CMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private Text Date = new Text();
	private final static IntWritable one = new IntWritable(1);

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer st = new StringTokenizer(value.toString());
      Configuration conf = context.getConfiguration();
	  String uid = conf.get("userid");
	  String startDate = conf.get("startDate");
	  int sd = Integer.parseInt(startDate);
	  String endDate = conf.get("endDate");
	  int ed = Integer.parseInt(endDate);
	  
	  String userid = st.nextToken();
      int date = Integer.parseInt(st.nextToken());

	  if(userid.equals(uid) && date>=sd && date<=ed){
		Date.set(Integer.toString(date));
	    context.write(Date, one);
      }
    }
  }

  public static class CReducer
       extends Reducer<Text,IntWritable,Text,IntWritable> {
    private IntWritable result = new IntWritable();

    public void reduce(Text key, Iterable<IntWritable> values,
                       Context context
                       ) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values) {
        sum += val.get();
      }
      result.set(sum);
      context.write(key, result);
	  
    }
  }

  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    Job job = Job.getInstance(conf, "Checkin");
    job.setJarByClass(Checkin.class);
    job.setMapperClass(CMapper.class);
    //job.setCombinerClass(CReducer.class);
    job.setReducerClass(CReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	
	job.getConfiguration().set("userid", args[2]);
	job.getConfiguration().set("startDate", args[3]);
	job.getConfiguration().set("endDate", args[4]);
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

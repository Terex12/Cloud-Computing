import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HotPlace {

  public static class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();

    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      StringTokenizer itr = new StringTokenizer(value.toString());
	  System.out.println(itr.nextToken());
	  itr.nextToken();
	  String latitude = itr.nextToken();
	  String longitude = itr.nextToken();
	  int dotIndex = latitude.indexOf('.');
	  int dotIndex2 = longitude.indexOf('.');
	  int accur = 1;
	  if(dotIndex >0 && dotIndex2>0 && (dotIndex+accur) <= latitude.length() && (dotIndex2+accur) <= latitude.length()){
		  latitude = latitude.substring(0, dotIndex);
		  longitude = longitude.substring(0, dotIndex2);
		  String location = latitude +" "+ longitude;
	      //System.out.println(location);
          word.set(location);
          context.write(word, one);
		  
	  }  	  
    }
  }

  public static class IntSumReducer
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
    Job job = Job.getInstance(conf, "hot place");
    job.setJarByClass(HotPlace.class);
    job.setMapperClass(TokenizerMapper.class);
    job.setCombinerClass(IntSumReducer.class);
    job.setReducerClass(IntSumReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

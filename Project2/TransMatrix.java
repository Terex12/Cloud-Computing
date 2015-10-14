//package pagerank;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;


public class TransMatrix{
	public static class Map extends Mapper<LongWritable, Text, Text, Text>{
		private Text NID = new Text();
		private Text neighbours = new Text();
		
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException{
			String line = value.toString();
			StringTokenizer st = new StringTokenizer(line);

			String node = st.nextToken();
			String initialRank = "1.0 ";

			while (st.hasMoreTokens()) {
				initialRank += st.nextToken();
				initialRank += " ";
			}
			NID.set(node);
			neighbours.set(initialRank);

			//<NID 1.0 n1, n2, n3...>
			context.write(NID, neighbours);
		}
	}
	
	public static class Reduce extends Reducer<Text, Text, Text, Text>{
		public void reduce(Text key, Text value, Context context) throws IOException, InterruptedException {
			//just write in a temporary file
			context.write(key, value);
		}
	}
	
	public void build(String input, String temp) throws IOException, InterruptedException, ClassNotFoundException{
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "BuildTransMatrix");
		job.setJarByClass(TransMatrix.class);
	    job.setMapperClass(Map.class);
	    job.setCombinerClass(Reduce.class);
	    job.setReducerClass(Reduce.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    //input: file	output: temp.file
	    FileInputFormat.addInputPath(job, new Path(input));
	    FileOutputFormat.setOutputPath(job, new Path(temp));
		job.waitForCompletion(true);
	}
}
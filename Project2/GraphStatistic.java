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

public class GraphStatistic{
	public static class Map extends Mapper<LongWritable, Text , Text, IntWritable>{
		 public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			 String line = value.toString();
			 StringTokenizer st = new StringTokenizer(line);
			 IntWritable res = new IntWritable();
			 res.set(st.countTokens()-1);
			 //<node, NumofNeighbour>
			 context.write(new Text("node"), res);
		 }
	}
	
	public static class Reduce extends Reducer<Text, IntWritable, Text, Text>{
		public void reduce(Text key, Iterable<IntWritable> value, Context context) throws IOException, InterruptedException {
			int numOfNode = 0;
			int numOfEdge = 0;
			int minDegree = Integer.MAX_VALUE;
			int maxDegree = Integer.MIN_VALUE;
			
			for (IntWritable v : value){
				int temp = v.get();
				numOfNode++;
				numOfEdge += temp;
				minDegree = Math.min(temp, minDegree);
				maxDegree = Math.max(temp, maxDegree);
			}
			
	    	float avg = 0;
	    	if(numOfNode != 0)
	    		avg = (float) ((numOfEdge)/numOfNode);
	    	
	    	context.write(new Text("Number of edges : "), new Text(Integer.toString(numOfEdge)));
	    	context.write(new Text("Number of nodes : "), new Text(Integer.toString(numOfNode)));
	    	context.write(new Text("Minimum out-degree : "), new Text(Integer.toString(minDegree)));
	    	context.write(new Text("Maximum out-degree : "), new Text(Integer.toString(maxDegree)));
	    	context.write(new Text("Average out-degree : "), new Text(Float.toString(avg)));
		}
	}
	
	public static void main(String[] args) throws Exception {
		long startTime = System.currentTimeMillis();
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Graph");
		job.setJarByClass(GraphStatistic.class);
		job.setMapOutputValueClass(IntWritable.class);
	    job.setMapperClass(Map.class);
	    job.setCombinerClass(Reduce.class);
	    job.setReducerClass(Reduce.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(job, new Path(args[0]));
	    FileOutputFormat.setOutputPath(job, new Path(args[1]));
	    long endTime = System.currentTimeMillis();
	    System.out.println("Time for Graph statistic " + (endTime-startTime) + " milliSeconds");
	    System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}
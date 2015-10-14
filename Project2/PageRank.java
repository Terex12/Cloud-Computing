//package pagerank;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Counters;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRank{
	
	public static enum PAGERANKCOUNTER { Count }
	
	public static class Map extends Mapper<LongWritable, Text, Text, Text>{
		private Text nodeID = new Text();
		
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
			String line = value.toString();
			StringTokenizer st = new StringTokenizer(line);
			nodeID.set(st.nextToken());
			float rank = new Float(st.nextToken());
			int numOfNeighbours = st.countTokens();
			
			float rankEach = 0.00f;
			if (numOfNeighbours == 0)
				rankEach = rank;
			else 
				rankEach = rank / numOfNeighbours;
			String emitStr = Float.toString(rank)+" ";
			while (st.hasMoreTokens()) {
				Text next = new Text(st.nextToken());
				emitStr += next.toString();
				emitStr += " ";
				// add "#" symbol to let reducer calculate
				//<NID, rank#>
				context.write(next, new Text(Float.toString(rankEach)+"#"));
			}
			//keep matrix and old rank
			//<NID oldrank n1, n2, n3...>
			context.write(nodeID, new Text(emitStr));

		}
	}
	
	public static class Reduce extends Reducer<Text, Text, Text, Text>{
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			float sumRank = 0.00f;
			String neighbours = "";
			
			Configuration conf = context.getConfiguration();
			String damp = conf.get("damp");	
			Float df = Float.parseFloat(damp);
			
			float oldRank = 0.00f;
			for (Text value : values) {
				if (value.toString().endsWith("#")) {
					StringTokenizer st = new StringTokenizer(value.toString());
					sumRank += Float.parseFloat(st.nextToken("#"));
				} 
				else {
					String str = value.toString();
					String[] ss = str.split(" ", 2);
					oldRank= Float.parseFloat(ss[0]);
					neighbours = ss[1];
				}
			}
			
			float newRank = sumRank * df + (1 - df);
			if ((Math.abs(newRank - oldRank) > 0.001))
				context.getCounter(PAGERANKCOUNTER.Count).increment(1);
			//write into new temporary file
			//<NID newRank n1, n2, n3...>
			context.write(key, new Text(Float.toString(newRank) + " " + neighbours));
		}
	}
	
	public int doPageRank(String input, String output, String damp) throws IOException, InterruptedException, ClassNotFoundException {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "PageRank");
		job.setJarByClass(PageRank.class);
		job.getConfiguration().set("damp", damp);
	    job.setMapperClass(Map.class);
	    job.setReducerClass(Reduce.class);
	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);
	    FileInputFormat.addInputPath(job, new Path(input));
	    FileOutputFormat.setOutputPath(job, new Path(output));
	    job.waitForCompletion(true);
	    Counters counters = job.getCounters();
		Counter countIterations = counters.findCounter(PAGERANKCOUNTER.Count);		
		return (int) countIterations.getValue();
	}
}
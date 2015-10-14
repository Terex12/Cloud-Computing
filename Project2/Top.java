//package pagerank;

import java.io.IOException;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;


public class Top {
	public static class Map extends Mapper<LongWritable, Text, Text, Text> {
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

			String line = value.toString();
			StringTokenizer st = new StringTokenizer(line, "\n");

			while (st.hasMoreTokens()) {
				String str = st.nextToken();
				StringTokenizer tokens = new StringTokenizer(str);		
				String NID = tokens.nextToken();
				String pageRank = tokens.nextToken();

				String nodeRank = NID + "#" + pageRank;
				Text word = new Text("r");
				
				context.write(word, new Text(nodeRank));
			}
		}
	}
	
	public static class Reduce extends Reducer<Text, Text, Text, FloatWritable> {

		private Text NID = new Text();
		private FloatWritable rank = new FloatWritable();
		
		public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
			//make pair <Rank, NodeID>, always keep 10 biggest rank
			LinkedList<Float> PageRanks = new LinkedList<Float>();
			LinkedList<String> Nodes = new LinkedList<String>();
			
			float minRank = Float.MAX_VALUE;
			int currMinIndex = 0;
			
			for (Text v : values) {
				String str = v.toString();
				String[] strArray = str.split("#");
				String NID = strArray[0];
				float pageRank = Float.parseFloat(strArray[1]);
				if (PageRanks.size() >= 10) {
					if (pageRank > minRank) {
						Nodes.remove(currMinIndex);
						PageRanks.remove(currMinIndex);
						Nodes.add(NID);
						PageRanks.add(pageRank);
						minRank = Float.MAX_VALUE;
						for (int i = 0; i < PageRanks.size(); i++) {
							if (PageRanks.get(i) < minRank) {
								minRank = PageRanks.get(i);
								currMinIndex = i;
							}
						}
					}
				} 
				else {
					Nodes.add(NID);
					PageRanks.add(pageRank);
					if (pageRank < minRank) {
						minRank = pageRank;
						currMinIndex = PageRanks.size() - 1;
					}
				}
			}

			//retrieve from biggest to smallest
			for (int i = 0; i < 10; i++) {
				Float max = Float.MIN_VALUE;
				for (int j = 0; j < PageRanks.size(); j++) {
					if (PageRanks.get(j) > max) {
						max = PageRanks.get(j);
					}
				}
				int maxIndex = PageRanks.indexOf(max);
				NID.set(Nodes.remove(maxIndex));
				rank.set(PageRanks.remove(maxIndex));
				context.write(NID, rank);
			}
		}
	}
	
	public void getTop10(String input, String output) throws Exception {
		Configuration conf = new Configuration();
		Job job = Job.getInstance(conf, "Top");
		job.setJarByClass(Top.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		//input: file	output: temp.file
		FileInputFormat.addInputPath(job, new Path(input));
		FileOutputFormat.setOutputPath(job, new Path(output));
		job.waitForCompletion(true);	
	}
}

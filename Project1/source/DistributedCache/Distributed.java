//package test;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.util.LinkedList;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.filecache.DistributedCache;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;


public class Distributed  {
	public static class WordMapper extends Mapper<Object, Text, Text, IntWritable> {
		private final static IntWritable one = new IntWritable(1);
		private Text word = new Text();
		LinkedList<String> list = new LinkedList<String>(); 
		private Path path[]=null;
	
		@SuppressWarnings("deprecation")
		protected void setup(Context context) throws IOException,
                InterruptedException {
            Configuration conf = context.getConfiguration();
            path = DistributedCache.getLocalCacheFiles(conf);
            //System.out.println("Cache path is: "+path[0].toString());
            FileSystem fsopen = FileSystem.getLocal(conf);
            FSDataInputStream in = fsopen.open(path[0]);
			String line = null;
			while((line = in.readLine()) != null){
			    StringTokenizer itr = new StringTokenizer(line);
			    while (itr.hasMoreTokens()) {
				   list.add(itr.nextToken());
			    }
			}
        }

		public void map(Object key, Text value, Context contex) throws IOException, InterruptedException {
			StringTokenizer itr = new StringTokenizer(value.toString());
			String currentToken = null;
			while (itr.hasMoreTokens()) {
				currentToken = itr.nextToken();
				if (list.contains(currentToken)) {
					word.set(currentToken);
					contex.write(word, one);
				}
			}
		}	
	}

	
	public static class SumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
		private IntWritable result = new IntWritable();
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
			int sum = 0;
			for (IntWritable val : values) 
		        sum += val.get();
		    result.set(sum);
		    context.write(key, result);
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
        DistributedCache.createSymlink(conf);
        String path = args[0];
        DistributedCache.addCacheFile(new URI(path), conf);

        Job job = new Job(conf, "Distributed");
        job.setJarByClass(Distributed.class);
        job.setMapperClass(WordMapper.class);
        job.setCombinerClass(SumReducer.class);
        job.setReducerClass(SumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        FileInputFormat.addInputPath(job, new Path(args[1]));
        FileOutputFormat.setOutputPath(job, new Path(args[2]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
}

//package project;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PreProcess {
  public static class ProMapper extends Mapper<Object, Text, Text, Text>{
	  public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
		  int accur = 4;	//accuracy of latitude and altitude
		  
		  StringTokenizer line = new StringTokenizer(value.toString());
		  //ArrayList temp = (ArrayList) Arrays.asList(line);
		  //List<StringTokenizer> temp = Arrays.asList(line);
		  ArrayList<String> temp = new ArrayList<>();
		  while (line.hasMoreTokens()){
			  String token = line.nextToken().toString();
			  if (token != " " && token != ""){
				  temp.add(token);
				  System.out.println("Tokens: " + token);
			  }
		  }		  
		  System.out.println("Number of token : " + temp.size());
		  String[] res = new String[temp.size()];
		  
		  res[0] = temp.get(0).toString();
		  
		  String time = temp.get(1).toString();
		  int tIndex = time.indexOf('T');
		  String[] timeTemp = time.substring(0, tIndex).split("-");
		  StringBuilder sb = new StringBuilder();
		  for (String s : timeTemp)
			  sb.append(s);
		  res[1] = sb.toString();
		  
		  String altitude = temp.get(2).toString();
		  int dotIndex = altitude.indexOf('.');
		  if (dotIndex+accur <= altitude.length())
		  	res[2] = altitude.substring(0, dotIndex+accur);
		  else
			res[2] = altitude;

		  String latitude = temp.get(3).toString();
		  dotIndex = latitude.indexOf('.');
		  if (dotIndex+accur <= latitude.length())		 
 			res[3] = latitude.substring(0, dotIndex+accur);
		  else
			res[3] = latitude;
		  res[4] = temp.get(4).toString();
		  
		  sb = new StringBuilder();
		  for (int i = 0; i < res.length; i++){
			  sb.append(res[i]);
			  if (i != res.length-1)
				  sb.append(" ");
		  }
		  Text newline = new Text(sb.toString());
		  context.write(new Text(""), newline);
	  }

  }
  public static class ProReducer extends Reducer<Text,Text,Text,Text> {
	  public void reduce(Text key, Text value, Context context) throws IOException, InterruptedException {
		  context.write(new Text(""), value);
	  }
  }
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
     if (args.length != 2) {
       System.err.println("Please check input");
       System.exit(2);
     }
    Job job = Job.getInstance(conf, "pre-process");
    job.setJarByClass(PreProcess.class);
    job.setMapperClass(ProMapper.class);
    //job.setReducerClass(ProReducer.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}

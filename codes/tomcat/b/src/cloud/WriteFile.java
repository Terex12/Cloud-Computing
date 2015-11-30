package cloud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class WriteFile{
	public String writeFile1(String userID) throws IOException{
		File f2 = new File("D:/Study/eclipseMars/eclipse/workplace/b/WebContent/assets/pattern/pattern.txt");
		System.out.println(f2.getAbsolutePath());
		FileInputStream fin = new FileInputStream(f2);
		InputStreamReader reader = new InputStreamReader(fin);
		
		StringBuilder sb = new StringBuilder();
		
		MongoInfo mi = new MongoInfo();
        ArrayList<String> list = new ArrayList<>();
		list = mi.MongoInfo2(userID);

		sb.append("var latlong = {};\n");
		for (int i = 0; i < list.size(); i = i + 3){
			int AD = i;
			String latitude = list.get(i+1);
			String longitude = list.get(i+2);
			String s = "latlong[" + AD + "] = {\"latitude\":" + latitude + ", \"longitude\":" + longitude + "};\n";
			System.out.println(s);
			sb.append(s);
		}
		
		sb.append("var mapData = [\n");
		for (int i = 0; i < list.size(); i = i + 3){
			String s = "";
			String time = list.get(i).substring(0, 4)+"/"+list.get(i).substring(4, 6)+"/"+list.get(i).substring(6, 8);
			if (i == list.size()-3){
				
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + time +"\", \"value\":1, \"color\":\"#de4c4f\"}\n";
				sb.append(s);
			}
			else{
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + time +"\", \"value\":1, \"color\":\"#de4c4f\"},\n";
				sb.append(s);
			}
		}
		sb.append("];\n");
		
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	public String writeFile2(String userid, String start, String end, int[] check) throws IOException{
		File f2 = new File("D:/Study/eclipseMars/eclipse/workplace/b/WebContent/assets/pattern/pattern1.txt");
		FileInputStream fin = new FileInputStream(f2);
		InputStreamReader reader = new InputStreamReader(fin);
		StringBuilder sb = new StringBuilder();
		
		//test
		//for (int i = 0; i < check.length; i++)
			//System.out.println(check[i]);
		
		
		sb.append("var chartData = [\n");
		for (int i = 0; i < check.length; i++){
			String s = "";
			if (i == check.length-1){
				s = "{\"country\":\"" + i +"\" , \"visits\":" + check[i] + "}\n";
				sb.append(s);
			}
			else{
				s = "{\"country\":\"" + i +"\" , \"visits\":" + check[i] + "},\n";
				sb.append(s);
			}
		}
		sb.append("];\n");
		
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String writeFile3(String userID) throws IOException{
		File f2 = new File("D:/Study/eclipseMars/eclipse/workplace/b/WebContent/assets/pattern/pattern.txt");
		System.out.println(f2.getAbsolutePath());
		FileInputStream fin = new FileInputStream(f2);
		InputStreamReader reader = new InputStreamReader(fin);
		
		StringBuilder sb = new StringBuilder();
		
		//list get data from mongpDB;
		MongoInfo mi = new MongoInfo();
        ArrayList<String> list = new ArrayList<>();
		list = mi.MongoInfo3(userID);

		sb.append("var latlong = {};\n");
		for (int i = 0; i < list.size(); i = i + 3){
			int AD = i;
			String latitude = list.get(i+1);
			String longitude = list.get(i+2);
			String s = "latlong[" + AD + "] = {\"latitude\":" + latitude + ", \"longitude\":" + longitude + "};\n";
			System.out.println(s);
			sb.append(s);
		}
		
		sb.append("var mapData = [\n");
		for (int i = 0; i < list.size(); i = i + 3){
			String s = "";
			if (i == list.size()-3){
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + list.get(i) +"\", \"value\":1, \"color\":\"#de4c4f\"}\n";
				sb.append(s);
			}
			else{
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + list.get(i) +"\", \"value\":1, \"color\":\"#de4c4f\"},\n";
				sb.append(s);
			}
		}
		sb.append("];\n");
		
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String writeFile4(String userID) throws IOException{
		File f2 = new File("D:/Study/eclipseMars/eclipse/workplace/b/WebContent/assets/pattern/pattern.txt");
		System.out.println(f2.getAbsolutePath());
		FileInputStream fin = new FileInputStream(f2);
		InputStreamReader reader = new InputStreamReader(fin);
		
		StringBuilder sb = new StringBuilder();
		
		MongoInfo mi = new MongoInfo();
        ArrayList<String> li = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
		li = mi.MongoInfo4(userID);
		System.out.println(li.size());
		if(li.size()>10){
			for(int i = 0;i<10;i++)
				list.add(li.get(i));
		}

		sb.append("var latlong = {};\n");
		for (int i = 0; i < list.size(); i = i + 2){
			int AD = i;
			
			String latitude = list.get(i);
			String longitude = list.get(i+1);
			String s = "latlong[" + AD + "] = {\"latitude\":" + latitude + ", \"longitude\":" + longitude + "};\n";
			System.out.println(s);
			sb.append(s);
		}
		
		sb.append("var mapData = [\n");
		for (int i = 0; i < list.size(); i = i + 2){
			String s = "";
			if (i == list.size()-2){
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + list.get(i) +" "+list.get(i+1)+"\", \"value\":1, \"color\":\"#de4c4f\"}\n";
				sb.append(s);
			}
			else{
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + list.get(i)+" "+list.get(i+1) +"\", \"value\":1, \"color\":\"#de4c4f\"},\n";
				sb.append(s);
			}
		}
		sb.append("];\n");
		
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public String writeFile5(String userID) throws IOException{
		File f2 = new File("D:/Study/eclipseMars/eclipse/workplace/b/WebContent/assets/pattern/pattern.txt");
		System.out.println(f2.getAbsolutePath());
		FileInputStream fin = new FileInputStream(f2);
		InputStreamReader reader = new InputStreamReader(fin);
		
		StringBuilder sb = new StringBuilder();
		
		MongoInfo mi = new MongoInfo();
        ArrayList<String> list = new ArrayList<>();
		list = mi.MongoInfo5("top10");

		sb.append("var latlong = {};\n");
		for (int i = 0; i < list.size(); i = i + 2){
			int AD = i;
			StringTokenizer itrt = new StringTokenizer(list.get(i));
			String latitude = itrt.nextToken();
			String longitude = itrt.nextToken();
			String s = "latlong[" + AD + "] = {\"latitude\":" + latitude + ", \"longitude\":" + longitude + "};\n";
			System.out.println(s);
			sb.append(s);
		}
		
		sb.append("var mapData = [\n");
		
		int count = 10;
		for (int i = 0; i < list.size(); i = i + 2){
			
			String s = "";
			if (i == list.size()-2){
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + list.get(i) +"\", \"value\":1, \"color\":\"#de4c4f\"}\n";
				sb.append(s);
			}
			else{
				s = "{\"code\":\"" + i +"\" , \"name\":\"" + list.get(i)+"\", \"value\":1, \"color\":\"#de4c4f\"},\n";
				sb.append(s);
			}
			count--;
		}
		sb.append("];\n");
		
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}

		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	
	
	
}
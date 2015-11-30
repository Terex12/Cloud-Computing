package cloud;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetDate{
	public static String getDate(String s) throws ParseException{
		StringBuilder sb = new StringBuilder();
		sb.append(s.substring(0,4));
		sb.append("/");
		sb.append(s.substring(4,6));
		sb.append("/");
		sb.append(s.substring(6,s.length()));
		SimpleDateFormat df = new SimpleDateFormat( "yyyy/MM/dd" );  
		Date d = df.parse(sb.toString());
		df.applyPattern( "EEE" );  
        String day= df.format(d); 
        
        String date = null;
        switch(day){
        case "����һ":
        	date = "Mon";break;
        case "���ڶ�":
        	date = "Tue";break;
        case "������":
        	date = "Wed";break;
        case "������":
        	date = "Thu";break;
        case "������":
        	date = "Fri";break;
        case "������":
        	date = "Sat";break;
        case "������":
        	date = "Sun";break;

        }
        
        return date;
	}
}
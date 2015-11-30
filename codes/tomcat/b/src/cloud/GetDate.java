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
        case "星期一":
        	date = "Mon";break;
        case "星期二":
        	date = "Tue";break;
        case "星期三":
        	date = "Wed";break;
        case "星期四":
        	date = "Thu";break;
        case "星期五":
        	date = "Fri";break;
        case "星期六":
        	date = "Sat";break;
        case "星期日":
        	date = "Sun";break;

        }
        
        return date;
	}
}
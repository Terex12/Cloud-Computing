
public class toprec {
	public String[][] toprec(String[][] friend){
		String[][] sortfriend = friend;
		double min=0;
		int nodenum = 0;
		int currMinIndex = 0;
		for(int i=0;i<sortfriend.length;i++){
			String tempnode = sortfriend[i][0];
			double temppr = Double.parseDouble(sortfriend[i][1]);
			for(int j=i+1;j<sortfriend.length;j++){
				if(temppr < Double.parseDouble(sortfriend[j][1])){
					sortfriend[i][0] = sortfriend[j][0];
					sortfriend[i][1] = sortfriend[j][1];
					sortfriend[j][0] = tempnode;
					sortfriend[j][1] = Double.toString(temppr);
					tempnode = sortfriend[i][0];
					temppr = Double.parseDouble(sortfriend[i][1]);
				}
			}
		}
		
		return sortfriend;
	}

}

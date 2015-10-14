//package pagerank;

public class Run {
	public static void main(String[] args) throws Exception {   
		String input = args[0];
		String output = args[1];
		String damp = args[2];

		long startTime = System.currentTimeMillis();
		
		//initialize matrix and initial rank of each node
		TransMatrix m = new TransMatrix();
		m.build(input, output+"1/");

		PageRank pr = new PageRank();
		startTime = System.currentTimeMillis();
		int i = 0;
		for (i = 2; i <= 50; i++){
			int counter = pr.doPageRank(output+(i-1)+"/", output+(i)+"/", damp);
			if(counter==0){
				 break;
			 }
		}
		long endTime = System.currentTimeMillis();
		long timeOfPR = endTime-startTime;
		
		Top t = new Top();
		startTime = System.currentTimeMillis();
		t.getTop10(output+i+"/", output+"top");
		endTime = System.currentTimeMillis();
		long timeOfTop = endTime-startTime;


		System.out.println("Time for Calculating Page Rank : " + timeOfPR + " milliSeconds");
		System.out.println("Time Getting Top 10 Nodes "+ timeOfTop + " milliSeconds");
		 
	 }
}

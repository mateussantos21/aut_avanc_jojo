package aut_avanc_jobert;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

public class Chart implements Runnable {
	
	JFreeChartGenerator chartGen; 
	Integer index; 
	ArrayList<String> dates; 
	ArrayList<Double> line1;
	ArrayList<Double> line2;
	ArrayList<Double> line3;
	
	Chart(JFreeChartGenerator chartGen, Integer index, ArrayList<String> dates, ArrayList<Double> line1, ArrayList<Double> line2, ArrayList<Double> line3){
		this.chartGen = chartGen;
		this.index = index;
		this.dates = dates;
		this.line1 = line1;
		this.line2 = line2;
		this.line3 = line3;
		
	}
	
	public void run() {
		updateChart(chartGen, index, dates, line1, line2, line3);
	}
	
	
	void updateChart(JFreeChartGenerator chartGen, Integer index, ArrayList<String> dates, ArrayList<Double> line1, ArrayList<Double> line2, ArrayList<Double> line3) {
   	 
        Double result1 = null;
        Double result2 = null;
   	 	Double result3 = null;

   	 	if (index < (line1.size())) {
   	 		result1 = line1.get(index);
   	 	}
   	 	if (index < (line2.size())) {
   		 result2 = line2.get(index);
   	 	}
   	 	if (index < (line3.size())) {
   		 result3 = line3.get(index);
   	 	}
   	 
   	    chartGen.updateSeries(dates.get(index), result1, result2, result3);
   	    index++;
   	  	}
}


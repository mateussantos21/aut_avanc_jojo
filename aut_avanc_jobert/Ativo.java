package aut_avanc_jobert;

import java.util.ArrayList;

public class Ativo {
	String name;
	ArrayList<String> date;
//	ArrayList<String> time;
	ArrayList<Double> opens;
	ArrayList<Double> highs;
	ArrayList<Double> lows;
	ArrayList<Double> closes;
	ArrayList<Integer> tickvols;
	ArrayList<Double> vols;
	ArrayList<Integer> spreads;
	
	Ativo(String name) {
		this.name = name;
	}
	
	ArrayList<String> getDate() {
		return date;
	}
	
	void setDate(ArrayList<String> date) {
		this.date = date;
	}
	
//	ArrayList<String> getTime() {
//		return time;
//	}
	
//	void setTime(ArrayList<String> time) {
//		this.time = time;
//	}
	
	ArrayList<Double> getOpens() {
		return opens;
	}
	
	void setOpens(ArrayList<Double> opens) {
		this.opens = opens;
	}
	
	ArrayList<Double> getHighs() {
		return highs;
	}
	
	void setHighs(ArrayList<Double> highs) {
		this.highs = highs;
	}
	
	ArrayList<Double> getLows() {
		return lows;
	}
	
	void setLows(ArrayList<Double> lows) {
		this.lows = lows;
	}
	
	ArrayList<Double> getCloses() {
		return closes;
	}
	
	void setCloses(ArrayList<Double> closes) {
		this.closes = closes;
	}
	
	ArrayList<Integer> getTickvols() {
		return tickvols;
	}
	
	void setTickvols(ArrayList<Integer> tickvols) {
		this.tickvols = tickvols;
	}
	
	ArrayList<Double> getVols() {
		return vols;
	}
	
	void setVols(ArrayList<Double> vols) {
		this.vols = vols;
	}
	
	ArrayList<Integer> getSpreads() {
		return spreads;
	}
	
	void setSpreads(ArrayList<Integer> spreads) {
		this.spreads = spreads;
	}
	
	Double addNumbers(ArrayList<Double> numbers, Integer lowerIndex, Integer upperIndex) {
		double sum = 0;
		for(Integer i=lowerIndex; i <= upperIndex; i++) {
			sum += numbers.get(i);
		}
		
		return sum;
	}
	
	ArrayList<Double> movingAverage(ArrayList<Double> rawData, Integer period){
		ArrayList<Double> modifiedData = new ArrayList<>();
		
		for(Integer i=1; i <= rawData.size(); i++) {
			if (i < period) {
				Double numerator = addNumbers(rawData, 0, i-1);
				Integer denominator = i;
				
				modifiedData.add(numerator / denominator);
			} else {
				Double numerator = addNumbers(rawData, i - period, i-1);
				Integer denominator = period;
				
				modifiedData.add(numerator / denominator);
			}
		}
		
		return modifiedData;
	}
	
	ArrayList<Double> exponentialMovingAverage(ArrayList<Double> rawData, Integer period){
		ArrayList<Double> modifiedData = new ArrayList<>();
		
		ArrayList<Double> movAvg = movingAverage(rawData, period);
		
		for(Integer i=1; i <= rawData.size(); i++) {
			if (i == 1) {
				modifiedData.add(rawData.get(i-1));
			}
			else if (i < period) {
				Double current = rawData.get(i-1);
				Double lastMM = movAvg.get(i-2);
				Double k = (double) (2/(i+1));
				
				modifiedData.add(((current - lastMM) * k) + lastMM);
			} else {
				Double current = rawData.get(i-1);
				Double lastMM = movAvg.get(i-2);
				Double k = (double) (2/(period+1));
				
				modifiedData.add(((current - lastMM) * k) + lastMM);
			}
		}
		
		return modifiedData;
	}
	
	ArrayList<Double> standardDeviation(ArrayList<Double> rawData, Integer period){
		ArrayList<Double> movAvg = movingAverage(rawData, period);
		ArrayList<Double> differences = new ArrayList();
		ArrayList<Double> modifiedData = new ArrayList<>();
		
		for(Integer i=0; i < rawData.size(); i++) {
			differences.add(Math.pow(rawData.get(i) - movAvg.get(i), 2));
		}
		
		for(Integer i=1; i <= rawData.size(); i++) {
			if (i < period) {
				Double numerator = addNumbers(differences, 0, i-1);
				Integer denominator = i;
				
				modifiedData.add(Math.sqrt(numerator/denominator));
			} else {
				Double numerator = addNumbers(differences, i-period, i-1);
				Integer denominator = period;
				
				modifiedData.add(Math.sqrt(numerator/denominator));
			}
		}
		
		return modifiedData;
	}
	
}

package aut_avanc_jobert;

import java.util.ArrayList;

class Main {
	public static void main(String args[]) {  
		 Ativo ouro = createAtivo("ouro.csv");
		 
		 ArrayList<Double> emm = ouro.exponentialMovingAverage(ouro.opens, 7);
		 
		 for(Integer i=0; i< emm.size(); i++) {
			 System.out.println(emm.get(i));
		 }
	   }
	
	static Ativo createAtivo(String fileName) {
		ReadMetaTraderCSV reader = new ReadMetaTraderCSV();
		
		return reader.load(fileName);
	}
}

package aut_avanc_jobert;

import java.io.FileReader;
import com.opencsv.CSVReader;
import com.opencsv.CSVParser;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import java.util.ArrayList;

public class ReadMetaTraderCSV {

	private static CSVReader csvReader;
	private static CSVParser parser;
	private static FileReader filereader;
	private Ativo ativo;
	
    public ReadMetaTraderCSV() {
		
    	ativo = new Ativo();
	}
    
	
	public Ativo load(String file){
		
		ArrayList<String> date = new ArrayList<>();
		ArrayList<String> time = new ArrayList<>();
		ArrayList<Double> open = new ArrayList<>();
		ArrayList<Double> high = new ArrayList<>();
		ArrayList<Double> low = new ArrayList<>();
		ArrayList<Double> close = new ArrayList<>();
		ArrayList<Integer> tickvol = new ArrayList<>();
		ArrayList<Double> vol = new ArrayList<>();
		ArrayList<Integer> spread = new ArrayList<>();
		
		try {
	       filereader = new FileReader(file);
	       
	       parser = new CSVParserBuilder().withSeparator('\t').build();
	       
	       csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).withCSVParser(parser).build();
	      
	       String line[];
		
	       while ((line = csvReader.readNext()) != null) {
	    	   
	    	   
	    	   date.add(line[0]);
	    	   time.add(line[1]);
	    	   open.add(Double.parseDouble(line[2]));  
	    	   high.add(Double.parseDouble(line[3])); 
	    	   low.add(Double.parseDouble(line[4])); 
	    	   close.add(Double.parseDouble(line[5]));
	    	   tickvol.add(Integer.parseInt(line[6])); 
	    	   vol.add(Double.parseDouble(line[7]));
	    	   spread.add(Integer.parseInt(line[8]));
	    	   
	       } 
	    }
		catch (Exception e) {
	        e.printStackTrace();
	    }
	
		ativo.setDate(date);
		ativo.setTime(time);
		ativo.setOpens(open);
		ativo.setHighs(high);
		ativo.setLows(low);
		ativo.setCloses(close);
		ativo.setTickvols(tickvol);
		ativo.setVols(vol);
		ativo.setSpreads(spread);
		
		return ativo;
		
	}
}

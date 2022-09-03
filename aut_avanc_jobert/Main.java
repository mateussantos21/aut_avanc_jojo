package aut_avanc_jobert;

//import java.awt.Color;
import java.util.ArrayList;

//import org.jfree.ui.RefineryUtilities;
import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
import java.util.concurrent.Semaphore;

class Main {
	
//    private static JFreeChartGenerator demo;
//    private static ScheduledExecutorService executor;
    
	public static void main(String args[]) {
		
		Semaphore caixa1 = new Semaphore(1);
		Semaphore caixa2 = new Semaphore(1);
		
		Corretora jobert = new Corretora("Jobert Ventures", caixa1, caixa2, "..");
		
		ArrayList<Cliente> customers = new ArrayList();
		
		customers.add(new Cliente("kamikaze", jobert, 100000.0, jobert.ativosList));
		customers.add(new Cliente("pe de pano", jobert, 100000.0, jobert.ativosList));
		customers.add(new Cliente("aquiles", jobert, 100000.0, jobert.ativosList));
		customers.add(new Cliente("pegasus", jobert, 100000.0, jobert.ativosList));
		
		for(Cliente customer : customers) {
			customer.start();
		}
	}
		
//		 Ativo ouro = createAtivo("euro.csv");
//		 
//		 
//		 final JFreeChartGenerator chartGen = new JFreeChartGenerator("Médias Moveis");
//		 chartGen.pack();
//	     RefineryUtilities.centerFrameOnScreen(chartGen);
//	     chartGen.setVisible(true);
//	     
//	     Runnable helloRunnable = new Runnable() {
//	    	 public void run() {
//	    	 
//	         Double result1 = null;
//	    	 Double result2 = null;
//	    	 Double result3 = null;
//
//	    	 if (it < (ouro.date.size())) {
//	    	    result1 = ouro.movingAverage(ouro.closes, 1).get(it);
//	    	 }
//	    	 if (it < (ouro.date.size())) {
//	    		 result2 = ouro.movingAverage(ouro.closes, 7).get(it);
//	    	 }
//	    	 if (it < (ouro.date.size())) {
//	    		 result3 = ouro.movingAverage(ouro.closes, 20).get(it);
//	    	 }
//	    	 
//	    	 chartGen.updateSeries(ouro.date.get(it), result1, result2, result3);
//	    	 
//	    	 it++;
//	    	 
//	    	 if (it >= (ouro.date.size() - 1)) {
//	    	  executor.shutdown();
//	    	  }
//	    	  }
//	    	 };
//
//	    	 executor = Executors.newScheduledThreadPool(1);
//	    	 executor.scheduleAtFixedRate(helloRunnable, 0, 100, TimeUnit.MILLISECONDS);
//
//
//	   }
	

}

   






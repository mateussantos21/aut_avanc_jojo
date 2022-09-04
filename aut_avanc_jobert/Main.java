package aut_avanc_jobert;

//import java.awt.Color;
import java.util.ArrayList;
import org.jfree.ui.RefineryUtilities;

//import org.jfree.ui.RefineryUtilities;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Semaphore;

class Main {
	
//    private static JFreeChartGenerator demo;
    static ScheduledExecutorService executor1;
    static ScheduledExecutorService executor2;
    static ScheduledExecutorService executor3;
    static Integer it1 = 0;
    static Integer it2 = 0;
    static Integer it3 = 0;
    
    
	public static void main(String args[]) {
		
        Semaphore caixa1 = new Semaphore(1);
		Semaphore caixa2 = new Semaphore(1);
		
		Corretora corretora = new Corretora("Jobert Ventures", caixa1, caixa2, "..");
		
		ArrayList<Cliente> customers = new ArrayList();
		
		customers.add(new Cliente("kamikaze", corretora, 100000.0, corretora.ativosList, 1.0, true));
		customers.add(new Cliente("pe de pano", corretora, 100000.0, corretora.ativosList, 0.8, true));
		customers.add(new Cliente("aquiles", corretora, 100000.0, corretora.ativosList, 0.6, true));
		customers.add(new Cliente("pegasus", corretora, 100000.0, corretora.ativosList, 1.2, true));
		customers.add(new Cliente("tesla", corretora, 100000.0, corretora.ativosList, 1.4, true));
		customers.add(new Cliente("cavaco", corretora, 100000.0, corretora.ativosList, 1.0, false));
		customers.add(new Cliente("muricoca", corretora, 100000.0, corretora.ativosList, 0.8, false));
		customers.add(new Cliente("matador", corretora, 100000.0, corretora.ativosList, 0.6, false));
		customers.add(new Cliente("ragnar", corretora, 100000.0, corretora.ativosList, 1.2, false));
		customers.add(new Cliente("furiosa", corretora, 100000.0, corretora.ativosList, 1.4, false));
		
		
		for(Cliente customer : customers) {
			customer.start();
		}
	
		final JFreeChartGenerator mm = new JFreeChartGenerator("Médias Moveis Simples");
		mm.pack();
	    RefineryUtilities.centerFrameOnScreen(mm);
	    mm.setVisible(true);
	    
	    final JFreeChartGenerator mme = new JFreeChartGenerator("Médias Moveis Exponenciais");
		mme.pack();
	    RefineryUtilities.centerFrameOnScreen(mme);
	    mme.setVisible(true);
	    
	    final JFreeChartGenerator dp = new JFreeChartGenerator("Desvio Padrão");
		dp.pack();
	    RefineryUtilities.centerFrameOnScreen(dp);
	    dp.setVisible(true);
	    
	    Ativo ativo = corretora.ativosList.get(0);
	    
	    Chart mmChart = new Chart(mm, it1, ativo.date, ativo.movingAverage(ativo.closes, 3), ativo.movingAverage(ativo.closes, 7), ativo.movingAverage(ativo.closes, 30));
	    Chart mmeChart = new Chart(mme, it2, ativo.date, ativo.exponentialMovingAverage(ativo.closes, 3), ativo.exponentialMovingAverage(ativo.closes, 7), ativo.exponentialMovingAverage(ativo.closes, 30));
	    Chart dpChart = new Chart(dp, it3, ativo.date, ativo.standardDeviation(ativo.closes, 3), ativo.standardDeviation(ativo.closes, 7), ativo.standardDeviation(ativo.closes, 30));
	
	    executor1 = Executors.newScheduledThreadPool(1);
   	    executor1.scheduleAtFixedRate(mmChart, 0, 30, TimeUnit.MILLISECONDS);
   	    
   	    executor2 = Executors.newScheduledThreadPool(2);
	    executor2.scheduleAtFixedRate(mmeChart, 0, 30, TimeUnit.MILLISECONDS);
	    
	    executor3 = Executors.newScheduledThreadPool(3);
   	    executor3.scheduleAtFixedRate(dpChart, 0, 30, TimeUnit.MILLISECONDS);
	}

}






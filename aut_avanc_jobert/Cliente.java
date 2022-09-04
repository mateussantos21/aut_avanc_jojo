package aut_avanc_jobert;

import java.util.ArrayList;

public class Cliente extends Thread{
	
	String name;
	Double balance;
	
	int operations = 0;
	
	boolean hasAtivoA = false;
	boolean hasAtivoB = false;
	boolean hasAtivoC = false;
	boolean hasAtivoD = false;

	Corretora corretora;
	ArrayList<Ativo> ativos;
	
	Double riskMultiplier;
	boolean useSimpleAverage;
	
	Integer numberOfDataPoints;
	
	Cliente(String name, Corretora corretora, Double balance, ArrayList<Ativo> ativos, Double riskMultiplier, boolean useSimpleAverage) {
		
		this.name = name;
		this.corretora = corretora;
		this.balance = balance;
		this.ativos = ativos;
		this.riskMultiplier = riskMultiplier;
		this.useSimpleAverage = useSimpleAverage;
		
		numberOfDataPoints = ativos.get(0).closes.size() - 1;
	}

	@Override
	public void run() {	
		operate();
	}
	
	void redemption(Double value) {
		balance = balance - value;
	}
	
	void deposit(Double value) {
		balance = balance + value;
	}
	
	Boolean hasAtivo(Ativo ativo) {
		if(ativo.name.equals("Dolar Australiano")) {
			return hasAtivoA;
		}
		
		if(ativo.name.equals("Dolar Nova Zelandia")) {
			return hasAtivoB;
		}
		
		if(ativo.name.equals("Euro")) {
			return hasAtivoC;
		}
		
		if(ativo.name.equals("Libra Esterlina")) {
			return hasAtivoD;
		}
		return false;
	}
	
	void buyAtivo(Ativo ativo) {
		if(ativo.name.equals("Dolar Australiano")) {
			hasAtivoA = true;
		}
		
		if(ativo.name.equals("Dolar Nova Zelandia")) {
			hasAtivoB = true;
		}
		
		if(ativo.name.equals("Euro")) {
			hasAtivoC = true;
		}
		
		if(ativo.name.equals("Libra Esterlina")) {
			hasAtivoD = true;
		}
	}
	
	void sellAtivo(Ativo ativo) {
		if(ativo.name.equals("Dolar Australiano")) {
			hasAtivoA = false;
		}
		
		if(ativo.name.equals("Dolar Nova Zelandia")) {
			hasAtivoB = false;
		}
		
		if(ativo.name.equals("Euro")) {
			hasAtivoC = false;
		}
		
		if(ativo.name.equals("Libra Esterlina")) {
			hasAtivoD = false;
		}
	}
	
	void operate() {
		for(Integer i=0; i < numberOfDataPoints; i++) {
			for(Ativo ativo : ativos) {
				analyse(ativo, i);
			}
		}
	}
	
	Double getMaxValue(ArrayList<Double> list, Integer period, Integer index) {
		Integer aux = 0;
		Double max = list.get(index);
		 while(aux < period) {
			 if(index - period < 0) {
				 if(list.get(0 + aux) > max) {
					 max = list.get(0 + aux);
				 }
			 } else {
				 if(list.get(index - period + aux) > max) {
					 max = list.get(index - period + aux);
				 }
			 }
			 aux ++;
		 }
		 return max;
	}
	
	Double getMinValue(ArrayList<Double> list, Integer period, Integer index) {
		Integer aux = 0;
		Double min = list.get(index);
		 while(aux < period) {
			 if(index - period < 0) {
				 if(list.get(0 + aux) < min) {
					 min = list.get(0 + aux);
				 }
			 } else {
				 if(list.get(index - period + aux) < min) {
					 min = list.get(index - period + aux);
				 }
			 }
			 aux ++;
		 }
		 return min;
	}
	
	boolean drawdown(Ativo ativo, Double risk, Integer index) {
		
		this.setPriority(MAX_PRIORITY);
	
		Double drawdown = ((getMaxValue(ativo.closes, 3, index) - getMinValue(ativo.closes, 3, index))/getMaxValue(ativo.closes, 3, index));
		
		if(risk < drawdown) {
			this.setPriority(NORM_PRIORITY);
			return true;
		}
		
		this.setPriority(NORM_PRIORITY);
		return false;
	}
	
	void analyse(Ativo ativo, Integer index) {

		ArrayList<Double> shortTermMA = new ArrayList();
		ArrayList<Double> longTermMA = new ArrayList();
		ArrayList<Double> stdDev = new ArrayList();
		
		if(useSimpleAverage) {
			shortTermMA = ativo.movingAverage(ativo.closes, 3);
			longTermMA = ativo.movingAverage(ativo.closes, 15);
		}
		
		else {
			shortTermMA = ativo.exponentialMovingAverage(ativo.closes, 3);
			longTermMA = ativo.exponentialMovingAverage(ativo.closes, 15);
		}
		
		stdDev = ativo.standardDeviation(ativo.closes, 3);
		
		
		if(shortTermMA.get(index) > longTermMA.get(index) && 
		   !hasAtivo(ativo) &&
		   !drawdown(ativo, stdDev.get(index) * riskMultiplier, index)){
			
		    	if(corretora.requireOperation(this, true, ativo, index)){
			
		    		buyAtivo(ativo);
		    		//salvar extrato
		    		operations++;
		    		try {
		    			Cliente.sleep(500);
		    		} 
		    		catch (InterruptedException e) {
		    			e.printStackTrace();
		    		}
		    }
		}else if(shortTermMA.get(index) < longTermMA.get(index) && 
				hasAtivo(ativo) && 
				drawdown(ativo, stdDev.get(index) * riskMultiplier, index)) {
			
		
					if(corretora.requireOperation(this, false, ativo, index)) {
			
						sellAtivo(ativo);
						//salvar extrado
						operations++;
						try {
							Cliente.sleep(500);
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
				}
		}
	}

}

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
	
	Cliente(String name, Corretora corretora, Double balance, ArrayList<Ativo> ativos) {
		
		this.name = name;
		this.corretora = corretora;
		this.balance = balance;
		this.ativos = ativos;
		
	}

	@Override
	public void run() {
				
		realizarOperacoes();
		
	}
	
	void redemption(Double value) {
		balance = balance - value;
	}
	
	void deposit(Double value) {
		balance = balance + value;
	}
	
	Boolean hasAtivo(Ativo ativo) {
		if(ativo.name == "ouro") {
			return hasAtivoA;
		}
		
		if(ativo.name == "prata") {
			return hasAtivoB;
		}
		
		if(ativo.name == "platina") {
			return hasAtivoC;
		}
		
		if(ativo.name == "paladium") {
			return hasAtivoD;
		}
		return false;
	}
	
	void buyAtivo(Ativo ativo) {
		if(ativo.name == "ouro") {
			hasAtivoA = true;
		}
		
		if(ativo.name == "prata") {
			hasAtivoB = true;
		}
		
		if(ativo.name == "platina") {
			hasAtivoC = true;
		}
		
		if(ativo.name == "paladium") {
			hasAtivoD = true;
		}
	}
	
	void sellAtivo(Ativo ativo) {
		if(ativo.name == "ouro") {
			hasAtivoA = false;
		}
		
		if(ativo.name == "prata") {
			hasAtivoB = false;
		}
		
		if(ativo.name == "platina") {
			hasAtivoC = false;
		}
		
		if(ativo.name == "paladium") {
			hasAtivoD = false;
		}
	}
	
	void realizarOperacoes() {
		for(Integer i=0; i<1000; i++) {
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
	
		Double drawdown = ((getMaxValue(ativo.closes, 7, index) - getMinValue(ativo.closes, 7, index))/getMaxValue(ativo.closes, 7, index));
		
		if(risk < drawdown) {
			return true;
		}
		
		return false;
	}
	
	void analyse(Ativo ativo, Integer index) {
		ArrayList<Double> shortTermMA = ativo.movingAverage(ativo.closes, 7);
		ArrayList<Double> longTermMA = ativo.movingAverage(ativo.closes, 30);
		ArrayList<Double> stdDev = ativo.standardDeviation(ativo.closes, 7);
		
		
		if(shortTermMA.get(index) > longTermMA.get(index) && !hasAtivo(ativo)) {
			if(!drawdown(ativo, stdDev.get(index), index)) {
				this.setPriority(NORM_PRIORITY);
				
				if(corretora.requireOperation(this, true, ativo, index)) {
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
			}
		} else if(shortTermMA.get(index) < longTermMA.get(index) && hasAtivo(ativo)) {
			if(drawdown(ativo, stdDev.get(index), index)) {
				this.setPriority(NORM_PRIORITY);
				
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

}

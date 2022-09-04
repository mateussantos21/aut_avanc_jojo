package aut_avanc_jobert;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Corretora implements Runnable{
	
	private String name;
	
	Ativo ativoA;
	Ativo ativoB;
	Ativo ativoC;
	Ativo ativoD;
	
	int operations = 0;
	
	String pathToCaixa;
	
	Semaphore caixa1;
	Semaphore caixa2;
	
	ArrayList<Ativo> ativosList = new ArrayList();
	
	Corretora(String name, Semaphore semaforo1, Semaphore semaforo2, String pathToCaixa) {
		
		this.name = name;
		
		ativoA = new ReadMetaTraderCSV("Dolar Australiano").load("dolaraustraliano_dolar.csv");
		ativoB = new ReadMetaTraderCSV("Dolar Nova Zelandia").load("dolarnovazelandia_dolar.csv");
		ativoC = new ReadMetaTraderCSV("Euro").load("euro_dolar.csv");
		ativoD = new ReadMetaTraderCSV("Libra Esterlina").load("libraesterlina_dolar.csv");
		
		ativosList.add(ativoA);
		ativosList.add(ativoB);
		ativosList.add(ativoC);
		ativosList.add(ativoD);
		
		this.caixa1 = semaforo1;
		this.caixa2 = semaforo2;
		
		//delete Caixa
//		try {
//			Files.delete( Paths.get(pathToCaixa));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		System.out.println("--- Iniciando operacoes --- \n --- " + name + " --- \n");
		
	}
	
	@Override
	public void run() {}
	

	boolean requireOperation(Cliente cliente, boolean buy, Ativo ativo, int indice) {
		
		boolean operationDone = false;
		
		if(operations < 1000) {
			if(caixa1.tryAcquire()) {
				
				try {
					if(executeOperation(cliente, buy, ativo, indice, "caixa 1")) {
						operations++;
//						caixaGeral.salvarDados(getAtivo(tipo).getDatas().get(indice), getAtivo(tipo).getCloses().get(indice), operacao);
						operationDone = true;
						try {
			    			Cliente.sleep(100);
			    		} 
			    		catch (InterruptedException e) {
			    			e.printStackTrace();
			    		}
					}
				} catch (Exception e) {
						e.printStackTrace();
				} finally {
					caixa1.release();
				}
			} else if(caixa2.tryAcquire()) {
				try {
					if(executeOperation(cliente, buy, ativo, indice, "caixa 2")) {
						operations++;
//						caixaGeral.salvarDados(getAtivo(tipo).getDatas().get(indice), getAtivo(tipo).getCloses().get(indice), operacao);
						operationDone = true;
						try {
			    			Cliente.sleep(100);
			    		} 
			    		catch (InterruptedException e) {
			    			e.printStackTrace();
			    		}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					caixa2.release();
				}
			} 
//			else {
//			System.out.println("\n\n--- não há caixas disponíveis ---");
//			}
		} else {
			System.out.println("\n\n--- Corretora encerrou as operacões ---");
			System.exit(0);
		}		
		return operationDone;
	}
	   
	
    boolean executeOperation(Cliente cliente, boolean buy, Ativo ativo, int index, String caixa) {
		
		if(buy) {
			if(!buy(cliente, ativo, index, caixa)) {
				return false;
			} else {return true;}
		}
		else {
			sell(cliente, ativo, index, caixa);
		}
		return true;	
    }

	
	
	void sell(Cliente cliente, Ativo ativo, int index, String caixa){
        
	   double price = ativo.closes.get(index);
       cliente.deposit(price);

       System.out.println(caixa +": " + cliente.name +" executou operacão de venda do ativo " + ativo.name + " por " + price + ". Novo Saldo: " + cliente.balance);
    }
	
	boolean buy(Cliente cliente, Ativo ativo, int index, String caixa){
		
		double price = ativo.closes.get(index);
		
        if(cliente.balance < price){
        	
             System.out.println("Cliente não tem saldo para comprar o ativo " + ativo.name);
             
             return false;
        }

        else {
        	cliente.redemption(price);
        	System.out.println(caixa +": " + cliente.name +" executou operacão de compra do ativo " + ativo.name + " por " + price + ". Novo Saldo: " + cliente.balance);

        	return true;
        }
    }

}

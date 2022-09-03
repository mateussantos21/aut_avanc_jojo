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
		
		ativoA = new ReadMetaTraderCSV("ouro").load("ouro.csv");
		ativoB = new ReadMetaTraderCSV("prata").load("prata.csv");
		ativoC = new ReadMetaTraderCSV("platina").load("platina.csv");
		ativoD = new ReadMetaTraderCSV("paladium").load("paladium.csv");
		
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
		
		System.out.println("--- Iniciando operacoes --- \n --- " + name + " ---");
		System.out.println("");
		
	}
	
	@Override
	public void run() {
		
		
	}
	

	public synchronized boolean requireOperation(Cliente cliente, boolean buy, Ativo ativo, int indice) {

		if(operations < 1000) {
			if(caixa1.tryAcquire()) {
				try {
					if(operate(cliente, buy, ativo, indice, "caixa 1")) {
						operations++;
//						caixaGeral.salvarDados(getAtivo(tipo).getDatas().get(indice), getAtivo(tipo).getCloses().get(indice), operacao);
						return true;
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					caixa1.release();
					leave(cliente, "caixa 1");
				}
			}

			else if(caixa2.tryAcquire()) {
				try {
					if(operate(cliente, buy, ativo, indice, "caixa 1")) {
						operations++;
//						caixaGeral.salvarDados(getAtivo(tipo).getDatas().get(indice), getAtivo(tipo).getCloses().get(indice), operacao);
						return true;
					}


				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					caixa2.release();
					leave(cliente, "caixa 2");

				}
			}
		}
		else {
			
			System.out.println("");
			System.out.println("Não há operacões disponíveis no momento");
			
			System.exit(0);
		}
		
		return false;
		
	}
	   
	
    private boolean operate(Cliente cliente, boolean buy, Ativo ativo, int indice, String caixa) {
		
    	boolean retorno = true;
    	
		System.out.println(caixa + ": acessado por cliente " + cliente.name);
		
		if(buy) {
			
			if(!buy(cliente, ativo, indice)) {
				return false;
			}
			
		}
		else {
			sell(cliente, ativo, indice);
		}
		
		return retorno;	
        
    }
	
    private void leave(Cliente cliente, String caixa) {
    	
    	System.out.println(caixa + ": encerrou operacão de cliente " + cliente.name);
        System.out.println("");
    }

	
	
	private void sell(Cliente cliente, Ativo ativo, int index){
        
	   double price = ativo.closes.get(index);
        
	   System.out.println("Saldo: " + cliente.balance);
       cliente.deposit(price);
       System.out.println("Vendeu o ativo " + ativo.name);
       System.out.println("Saldo: " + cliente.balance);
    }
	
	private boolean buy(Cliente cliente, Ativo ativo, int index){
		System.out.println("tentando comprar o ativo " + ativo.name);
		double price = ativo.closes.get(index);
		
        if(cliente.balance < price){
        	
             System.out.println("Cliente não tem saldo para comprar o ativo");
             
             return false;
        }

        else {
        	System.out.println("Saldo: " + cliente.balance);
        	cliente.redemption(price);
        	System.out.println("Comprou o ativo " + ativo.name);
        	System.out.println("Saldo: " + cliente.balance);

        	return true;
        }
    }

}

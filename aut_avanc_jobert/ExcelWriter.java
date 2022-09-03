package aut_avanc_jobert;

import java.io.File;

import java.util.ArrayList;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelWriter {
//	private WritableWorkbook workboook;
//	private String path;
//	private String nomeAba;
//	
//	ExcelWriter(String path) {
//        this.path = path;
//        try {
//        	workboook = Workbook.createWorkbook(new File(path));
//        } catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	
//	public void createTab() {
//		
//		try {
//			WritableSheet tab = workboook.createSheet(nomeAba, 0);
//			
//			String cabecalho[] = new String[6];
//			cabecalho[0] = "Media Simples";
//			cabecalho[1] = "Media Exponencial";
//			cabecalho[2] = "Media Curta";
//			cabecalho[3] = "Media Intermediaria";
//			cabecalho[4] = "Media Longa";
//			cabecalho[5] = "Desvio Padrao";
//			
//			WritableCellFormat cellFormat = new WritableCellFormat();
//			cellFormat.setBackground(Colour.GREEN);
//			cellFormat.setAlignment(Alignment.CENTRE);
//			
//
//			WritableFont cellFont = new WritableFont(WritableFont.ARIAL);
//			cellFont.setColour(Colour.BLACK);
//			cellFormat.setFont(cellFont);
//			
//			for (int i = 0; i < cabecalho.length; i++) {
//				//Parâmetro (coluna, linha, conteúdo)
//				Label label = new Label(i, 0, cabecalho[i]);
//				tab.addCell(label);
//				WritableCell cell = tab.getWritableCell(i, 0);
//				cell.setCellFormat(cellFormat);
//			}
//		
//			preencheCelulas(super.getMediaMovelSimplesCIL(10), 0);  //Média Simples
//			preencheCelulas(super.getMediaMovelExponencial(10), 1); //Média Exponencial
//			preencheCelulas(super.getMediaMovelSimplesCIL(15), 2);  //Média Curta
//			preencheCelulas(super.getMediaMovelSimplesCIL(50), 3);  //Média Intermediária
//			preencheCelulas(super.getMediaMovelSimplesCIL(100), 4); //Média Longa
//			preencheCelulas(super.getDesvioPadrao(15), 5);  //Desvio Padrão
//			
//			//Escreve dados na planilha
//			planilha.write();
//			//Fecha o arquivo
//			planilha.close();
//			
//		}
//		catch(Exception e){
//			
//			e.printStackTrace();
//		}
//	}
//	
//	
//	private void preencheCelulas(ArrayList<Double> dados, int coluna) throws RowsExceededException, WriteException {
//		
//		for(int linha=0; linha<dados.size(); linha++) {
//			
//			WritableCellFormat cellFormat = new WritableCellFormat();
//			cellFormat.setAlignment(Alignment.CENTRE);
//			
//			Number number = new Number(coluna, linha+1, dados.get(linha));
//			aba.addCell(number);
//			
//			WritableCell cell = aba.getWritableCell(coluna, linha+1);
//			cell.setCellFormat(cellFormat);
//			
//	     }	
//	}
}

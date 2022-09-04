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
	WritableWorkbook workboook;
	String path;
	String nomeAba;
	
	ExcelWriter(String path) {
        this.path = path;
        try {
        	workboook = Workbook.createWorkbook(new File(path));
        } catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void createTab(Ativo ativo) {
		
		try {
			WritableSheet tab = workboook.createSheet(nomeAba, 0);
			
			String cabecalho[] = new String[8];
			cabecalho[0] = "Data";
			cabecalho[1] = "Fechamento";
			cabecalho[2] = "Media Simples";
			cabecalho[3] = "Media Exponencial";
			cabecalho[4] = "Media Curta";
			cabecalho[5] = "Media Intermediaria";
			cabecalho[6] = "Media Longa";
			cabecalho[7] = "Desvio Padrao";
			
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setBackground(Colour.GREEN);
			cellFormat.setAlignment(Alignment.CENTRE);
			

			WritableFont cellFont = new WritableFont(WritableFont.ARIAL);
			cellFont.setColour(Colour.BLACK);
			cellFormat.setFont(cellFont);
			
			for (int i = 0; i < cabecalho.length; i++) {
				Label label = new Label(i, 0, cabecalho[i]);
				tab.addCell(label);
				WritableCell cell = tab.getWritableCell(i, 0);
				cell.setCellFormat(cellFormat);
			}
		
			inputTextData(ativo.date, 0, tab);
			inputNumericData(ativo.closes, 1, tab);
			inputNumericData(ativo.movingAverage(ativo.closes, 7), 2, tab);
			inputNumericData(ativo.exponentialMovingAverage(ativo.closes, 7), 3, tab);
			inputNumericData(ativo.movingAverage(ativo.closes, 7), 4, tab);
			inputNumericData(ativo.movingAverage(ativo.closes, 30), 5, tab);
			inputNumericData(ativo.movingAverage(ativo.closes, 180), 6, tab);
			inputNumericData(ativo.standardDeviation(ativo.closes, 7), 7, tab);
			
			workboook.write();

//			workboook.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	void inputTextData(ArrayList<String> data, int colIndex, WritableSheet tab) throws RowsExceededException, WriteException {
		
		for(int l=0; l<data.size(); l++) {
			
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setAlignment(Alignment.CENTRE);
			
			Label label = new Label(colIndex, l+1, data.get(l));
			tab.addCell(label);
			
			WritableCell cell = tab.getWritableCell(colIndex, l+1);
			cell.setCellFormat(cellFormat);
	     }	
	}
	
	void inputNumericData(ArrayList<Double> data, int colIndex, WritableSheet tab) throws RowsExceededException, WriteException {
		
		for(int l=0; l<data.size(); l++) {
			
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setAlignment(Alignment.CENTRE);
			
			Number number = new Number(colIndex, l+1, data.get(l));
			tab.addCell(number);
			
			WritableCell cell = tab.getWritableCell(colIndex, l+1);
			cell.setCellFormat(cellFormat);
	     }	
	}
}

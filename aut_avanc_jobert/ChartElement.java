package aut_avanc_jobert;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import java.awt.Color;
import java.util.ArrayList;

public class ChartElement {
	Ativo ativo;
	String name;
	Color color;
	ArrayList<TimeSeries> series;
	TimeSeriesCollection dataset;
	
	ChartElement(Ativo ativo, String name, Color color){
		this.ativo = ativo;
		this.name = name;
		this.color = color;
		series = new TimeSeries(name);
		dataset = new TimeSeriesCollection();
	}
}

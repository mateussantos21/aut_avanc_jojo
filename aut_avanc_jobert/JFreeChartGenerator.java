package aut_avanc_jobert;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;

public class JFreeChartGenerator extends ApplicationFrame implements ActionListener {
 
    Integer aux = 0;
    JFrame frame;
    
    private TimeSeries series1;
    private TimeSeries series2;
    private TimeSeries series3;

    public JFreeChartGenerator(final String title) {

        super(title);
        
        this.series1 = new TimeSeries("Med Curta");
        this.series2 = new TimeSeries("Med Intermediária");
        this.series3 = new TimeSeries("Med Longa");
        
        final TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);
        
        final JPanel content = new JPanel(new BorderLayout());
        
        JFreeChart chart = createChart(title, dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        content.add(chartPanel);
   
        
        setContentPane(content);
    }

    private JFreeChart createChart(String title, final XYDataset dataset) {
    	final JFreeChart result = ChartFactory.createTimeSeriesChart(
	        	title, 
	            "Date", 
	            "Value",
	            dataset, 
	            true, 
	            true, 
	            false
	        );
		
		XYPlot plot = result.getXYPlot();
        
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis = plot.getRangeAxis();
        
        return result;
    }
    
    void updateSeries(String date, Double value1, Double value2, Double value3) {
    	
    	series1.add(new Day(
    			Integer.parseInt(date.substring(8,10)),
    			Integer.parseInt(date.substring(5,7)),
    			Integer.parseInt(date.substring(0,4))
    			), value1);
    	
    	series2.add(new Day(
    			Integer.parseInt(date.substring(8,10)),
    			Integer.parseInt(date.substring(5,7)),
    			Integer.parseInt(date.substring(0,4))
    			), value2);
    	
    	series3.add(new Day(
    			Integer.parseInt(date.substring(8,10)),
    			Integer.parseInt(date.substring(5,7)),
    			Integer.parseInt(date.substring(0,4))
    			), value3);
    }
    
    public void actionPerformed(final ActionEvent e) {} 
    
}

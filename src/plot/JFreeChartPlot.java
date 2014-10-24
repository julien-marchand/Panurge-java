/**
 * Copyright (c) 2014 Julien Marchand.
 */
package plot;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * @author Juju
 *
 */
public class JFreeChartPlot {
	
	private static JPanel createGraph(String title, JFreeChartSerie... datas) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for(JFreeChartSerie data : datas)
        	dataset.addSeries(prepareSerie(data.getData(), data.getName()));
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                "Days",
                "Values",
                dataset, 
                PlotOrientation.VERTICAL,
                true,
                true,
                true
                );
        JPanel panel = new JPanel();
        panel.add(new ChartPanel(chart));
        return panel;
    }
	
	private static XYSeries prepareSerie(double[] data, String name) {
        XYSeries series = new XYSeries(name);
		for(int i = 0; i<data.length; ++i) {
			series.add(i, data[i]);
		}
		return series;
	}
	
	public static void plotGraph(String title, JFreeChartSerie... data) {
        JFrame f = new JFrame(title);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createGraph(title, data));
        f.setSize(800,800);
        f.setLocation(200,200);
        f.setVisible(true);
	}
	
	
	public static void main(String[] args) {
        plotGraph("Test",
        		new JFreeChartSerie("Test1", new double[]{1,4,5,2}),
        		new JFreeChartSerie("Test2", new double[]{1,5,4,3,2})
        		);
    }
}

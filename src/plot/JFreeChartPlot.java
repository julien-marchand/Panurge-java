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
	private static JPanel createGraph() {

        JPanel panel = new JPanel();
        XYSeries series = new XYSeries("MyGraph");
        series.add(0, 4);
        series.add(1, 2);
        series.add(2, 5);
        series.add(7, 8);
        series.add(9, 10);

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "XY Chart",
                "x-axis",
                "y-axis",
                dataset, 
                PlotOrientation.VERTICAL,
                true,
                true,
                false
                );
        ChartPanel chartPanel = new ChartPanel(chart);

        panel.add(chartPanel);

        return panel;
    }
	
	public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(createGraph());
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
    }
}

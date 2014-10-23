/**
 * Copyright (c) 2014 Julien Marchand.
 */
package plot;

/**
 * @author Juju
 *
 */
public class JFreeChartSerie {
	private String name;
	private double[] data;
	
	public JFreeChartSerie(String name, double[] data) {
		this.name = name;
		this.data = data;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the data
	 */
	public double[] getData() {
		return data;
	}
}

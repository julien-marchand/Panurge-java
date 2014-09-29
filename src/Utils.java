/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class Utils {
	public static double round(double number, int i){
		return Math.round(number * Math.pow(10, i)) / Math.pow(10, i);
	}
	
	public static boolean gtgt(double i, double j, double k) {
		return i > j && j > k ;
	}
	
	public static boolean ltlt(double i, double j, double k) {
		return i < j && j < k ;
	}
}

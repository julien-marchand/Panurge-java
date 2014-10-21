package action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

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
	
	public static BufferedReader getURL(String urlString) {
		try {
			URL url = new URL(urlString);
			InputStreamReader inputStream = new InputStreamReader(url.openStream());
			return new BufferedReader(inputStream);
		} catch (IOException e) {
			System.err.println("URL leads nowhere");
			return null;
		}
	}
	
	
	public static int aggregateOrders(int ... buyOrSellOrders){
		int ret = Action.NOTHING_ORDER;
		for(int order:buyOrSellOrders) {
			if(ret==Action.NOTHING_ORDER) {
				ret = order;
			} else if(order != Action.NOTHING_ORDER && ret!=order)
				return 0;
		}
		return ret;
	}
	
	public static Order aggregateOrders(Order ... buyOrSellOrders){
		int ret = Action.NOTHING_ORDER;
		int holdingDuration = 0;
		for(Order order:buyOrSellOrders) {
			if(ret==Action.NOTHING_ORDER) {
				ret = order.getAct();
				holdingDuration = order.getHoldingDuration();
			} else if(order.getAct() != Action.NOTHING_ORDER && ret!=order.getAct())
				return Order.noOrder();
		}
		return new Order(ret, holdingDuration);
	}
	
	public static String concatString(String[] strings, String del) {
		if(strings.length==0)
			return "";
		String retString = strings[0];
		for(int i=0; i<strings.length; ++i)
			retString += del + strings[i];
		return retString;
	}
}

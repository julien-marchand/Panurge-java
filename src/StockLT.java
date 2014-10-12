/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class StockLT implements ChoiceAlgo {
	private double limit = 0;
	
	public StockLT(double limit) {
		this.limit =  limit;
	}
	
	public int buyOrSell(Action action, int time){
		if(action.stocD[time] < limit) {
			return 1;
		}
		return 0;
	}
}

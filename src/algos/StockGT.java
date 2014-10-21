package algos;
import action.Action;

/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class StockGT extends ChoiceAlgo {
	private double limit = 1;
	
	public StockGT(int holdingDuration, double limit) {
		this.holdingDuration = holdingDuration;
		this.limit =  limit;
	}
	
	public int buyOrSell(Action action, int time){
		double[] stoc = action.stocD;
		if(stoc[time] > limit) {
//		if(stoc[time+1] > limit)
			return Action.SELL_ORDER;
		}
		return Action.NOTHING_ORDER;
	}
	
	public void setLimit(double limit) {
		this.limit = limit;
	}
}

package algos;
import fuckGit.Action;

/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class StockLT extends ChoiceAlgo {
	private double limit = 0;
	
	public StockLT(int holdingDuration, double limit) {
		this.holdingDuration = holdingDuration;
		this.limit =  limit;
	}
	
	public int buyOrSell(Action action, int time){
		double[] stoc = action.stocD;
		if(stoc[time] < limit) {
//		if(stoc[time+1] < limit)
			return Action.BUY_ORDER;
		}
		return Action.NOTHING_ORDER;
	}
	
	public void setLimit(double limit) {
		this.limit = limit;
	}
}

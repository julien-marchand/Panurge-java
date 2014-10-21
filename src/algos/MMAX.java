package algos;

import action.Action;

/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class MMAX extends ChoiceAlgo {
	
	private double[] perCents;
	private double[] increase;
	private double[] decrease;
	
	/**
	 * Crossing verifying : increase[t] > decrease[t] + ε and increase[t_older] + ε < decrease[t_older]
	 * @param increase
	 * @param decrease
	 * @param perCents
	 */
	public MMAX(int holdingDuration, double[] increase, double[] decrease, double... perCents){
		this.holdingDuration = holdingDuration;
		this.increase = increase;
		this.decrease = decrease;
		this.perCents = perCents;
	}
	
	public int buyOrSell(Action action, int time) {
		if(!(increase[time] >= decrease[time]*perCents[0]))
			return 0;
		for(int i=1; i<perCents.length; ++i){
			if(!(increase[time+i]*perCents[i] <= decrease[time+1]))
				return 0;
		}
		return 1;
	}

}

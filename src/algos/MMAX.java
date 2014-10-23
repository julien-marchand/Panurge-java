package algos;

import action.Action;

/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class MMAX extends ChoiceAlgo {
	
	private double[] perCents;
	private int nMMAincrease;
	private int nMMAdecrease;
	
	/**
	 * Crossing verifying : increase[t] > decrease[t] + ε and increase[t_older] + ε < decrease[t_older]
	 * @param nMMAincrease
	 * @param nMMAdecrease
	 * @param perCents
	 */
	public MMAX(int holdingDuration, int nMMAincrease, int nMMAdecrease, double... perCents){
		this.holdingDuration = holdingDuration;
		this.nMMAincrease = nMMAincrease;
		this.nMMAdecrease = nMMAdecrease;
		this.perCents = perCents;
	}
	
	public int buyOrSell(Action action, int time) {
		double[] increase = action.getMMA(nMMAincrease);
		double[] decrease = action.getMMA(nMMAdecrease);
		if(!(increase[time] >= decrease[time]*perCents[0]))
			return 0;
		for(int i=1; i<perCents.length; ++i){
			if(!(increase[time+i]*perCents[i] <= decrease[time+1]))
				return 0;
		}
		return 1;
	}
}

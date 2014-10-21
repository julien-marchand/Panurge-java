package algos;


/**
 * Copyright (c) 2014 Julien Marchand.
 */

public abstract class ChoiceAlgo implements IChoiceAlgo{
	/**
	 * Time during the action is hold
	 */
	protected int holdingDuration = 0;
	
	public int getHoldingDuration() {
		return holdingDuration;
	}
}

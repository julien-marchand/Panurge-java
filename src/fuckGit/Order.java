package fuckGit;


/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class Order {
	private int order;
	private int holdingDuration;

	public static Order noOrder() {
		return new Order(0, 0);
	}
	
	public Order(int order, int holdingDuration) {
		this.order = order;
		this.holdingDuration = holdingDuration;
	}

	public int getAct() {
		return order;
	}

	public int getHoldingDuration() {
		return holdingDuration;
	}
}

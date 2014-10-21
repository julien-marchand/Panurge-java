package action;

/**
 * Copyright (c) 2014 Julien Marchand.
 */

public class Main {
	public static void main(String[] args) {
		String[] companySymbols = new String[] {"SAP", "ACE", "MSFT"};
		launchTestProcedure(companySymbols);
	}
	
	public static void launchTestProcedure(String[] companySymbols) {
		System.out.println("Testing: " +  Utils.concatString(companySymbols, " - "));
		double[] earnings = new double[companySymbols.length];
		for (int i=0; i< companySymbols.length; ++i) {
			Action action = new Action(companySymbols[i]);
			boolean built = action.buildAction();
			if (built) {
				earnings[i] = action.getEarningRatio();
			}
		}
		System.out.println("------------------------------------");
		for (int i=0; i< companySymbols.length; ++i) {
			System.out.println(companySymbols[i] + ": " + Utils.round(earnings[i],4) + "%");
		}
	}
}



public class Main {

	//
	
	//
	
	//
	public static void main(String[] args) {
		String[] companySymbols = new String[] {"SAP", "ACE", "MSFT"};
		double[] earnings = new double[companySymbols.length];
		for (int i=0; i< companySymbols.length; ++i) {
			Action action = new Action(companySymbols[i]);
			action.buildAction();
			earnings[i] = action.getEarningRatio();
		}
		System.out.println("------------------------------------");
		for (int i=0; i< companySymbols.length; ++i) {
			System.out.println(companySymbols[i] + ": " + Utils.round(earnings[i],4));
		}
	}
}

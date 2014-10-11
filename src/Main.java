

public class Main {

	public static String[] CAC40_SYMBOLS = new String[] {"AC","AI","ALU","ALO","MTP","CS","BNP","EN","CAP","CA","ACA","BN","EAD","EDF","EF","FTE","GSZ","OR","LG","LR","MC","ML","RI","UG","PP","PUB","RNO","SAF","SGO","SAN","SU","GLE","STM","TEC","FP","UL","VK","VIE","DG","VIV"};
	//
	
	//
	
	//
	public static void main(String[] args) {
		String[] companySymbols = new String[] {"SAP", "ACE", "MSFT"};
		launchTestProcedure(companySymbols);
	}
	
	public static void launchTestProcedure(String[] companySymbols) {
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
			System.out.println(companySymbols[i] + ": " + earnings[i]);
		}
	}
}

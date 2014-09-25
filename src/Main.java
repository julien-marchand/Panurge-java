

public class Main {

	//
	
	//
	
	//
	public static void main(String[] args) {
		String[] companySymbols = new String[] {"SAP", "ACE", "MSFT"};
		
		for (String symbol:companySymbols) {
			Action action = new Action(symbol);
			action.buildAction();
		}
	}

}

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 */

/**
 * @author adrien
 *
 */
public class CompanySymbol {

	public static String[] CAC40_SYMBOLS = new String[] {"AC","AI","ALU","ALO","MTP","CS","BNP","EN","CAP","CA","ACA","BN","EAD","EDF","EF","FTE","GSZ","OR","LG","LR","MC","ML","RI","UG","PP","PUB","RNO","SAF","SGO","SAN","SU","GLE","STM","TEC","FP","UL","VK","VIE","DG","VIV"};

	public static void getNasdaqSymbols() {
		ArrayList<String> symbols = new ArrayList<>();
		for (int page = 1; page < 61; page++) {
			symbols.addAll(vacuumNasdaqPage(page));
		}
		System.out.println(symbols.size());
	}
	
	public static ArrayList<String> vacuumNasdaqPage (int pageNumber) {
		System.out.println("Page number = " + pageNumber);
		ArrayList<String> symbols = new ArrayList<>();
		try{
			String urlString = "http://www.boursorama.com/bourse/actions/inter_az.phtml?PAYS=1&BI=103&validate=&page=" + pageNumber;
			BufferedReader br = Utils.getURL(urlString);
			if (br == null) {
				return symbols;
			}
			String line = null;
			int count = 0;
			while ((line = br.readLine()) != null) {
				if (line.equals("</tbody>")) {
					break;
				}
				if(line.contains("href=\"/cours.phtml?symbole=")) {
					count++;
				}
				if(count == 2){
					String[] t = line.split("=");
					String symbol = t[4].substring(0, t[4].length()-2);
					symbols.add(symbol);
					count = 0;
				}
			} 
			br.close();
		
		} catch (IOException io) {
			io.printStackTrace();
		}
		return symbols;
	}
	
	public static void main(String[] args) {
		getNasdaqSymbols();
	}

}

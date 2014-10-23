package action;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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

	/**
	 * Returns the Nasdaq symbols.
	 * @return
	 */
	public static String[] getNasdaqSymbols() {
		ArrayList<String> symbols = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("res/nasdaqSymbols")));
			String line = br.readLine();
			while (line != null) {
				symbols.add(line);
				line = br.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] symbolTable = new String[symbols.size()];
		for (int i=0; i<symbolTable.length; i++) {
			symbolTable[i] = symbols.get(i);
		}
		return symbolTable;
	}
	
	/**
	 * This method only needs to be run periodically. It fetches
	 * the Nasdaq symbol from Boursorama website. It parses every
	 * page so it is quite slow.
	 */
	public static void fetchNasdaqSymbols() {
		ArrayList<String> symbols = new ArrayList<>();
		for (int page = 1; page < 61; page++) {
			symbols.addAll(vacuumNasdaqPage(page));
		}
		System.out.println(symbols.size());
		saveNasdaqSymbols(symbols);
		System.out.println("saved");
	}
	
	private static void saveNasdaqSymbols(ArrayList<String> symbols) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("res/nasdaqSymbols")));
			for(String symbol : symbols) {
				bw.write(symbol);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static ArrayList<String> vacuumNasdaqPage (int pageNumber) {
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
		String[] symbols = getNasdaqSymbols();
		for (String s : symbols) {
			System.out.println(s);
		}
	}

}

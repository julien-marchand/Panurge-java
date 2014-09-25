import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Data {

	private double[] date;
	private double[] open;
	private double[] high;
	private double[] low;
	private double[] close;
	private double[] volume;
	private double[] adjClose;
	
	private double[][] variablesArray = new double[][]{date, open,high, low, close, volume, adjClose};
	
	public static String[] companySymbols = new String[] {"SAP", "ACE", "BNP", "MSFT"};
	
	public void getData(String companySymbol) throws IOException {
		ArrayList<Double> dateList = new ArrayList<>();
		ArrayList<Double> openList = new ArrayList<>();
		ArrayList<Double> highList = new ArrayList<>();
		ArrayList<Double> lowList = new ArrayList<>();
		ArrayList<Double> closeList = new ArrayList<>();
		ArrayList<Double> volumeList = new ArrayList<>();
		ArrayList<Double> adjCloseList = new ArrayList<>();
		ArrayList[] lists = new ArrayList[]{dateList, openList, highList, lowList, closeList, volumeList, adjCloseList};
		
		String urlString = "http://ichart.yahoo.com/table.csv?s=" + companySymbol;
		URL url = new URL(urlString);
		InputStreamReader inputStream = new InputStreamReader(url.openStream());
		BufferedReader br = new BufferedReader(inputStream);
		String line = br.readLine();
		while ((line = br.readLine()) != null) {
			String[] explodedLine = line.split(",");
			if(explodedLine.length < lists.length) {
				continue;
			}
			stringToTimestamp(explodedLine[0], dateList);
			for (int i=1; i<explodedLine.length; i++) {
				lists[i].add(Double.parseDouble(explodedLine[i]));
			}
		}
		br.close();
		for (int i = 0; i < lists.length; i++) {
			variablesArray[i] = new double[lists[i].size()];
			for (int j = 0; j < variablesArray[i].length; j++) {
				variablesArray[i][j] = (double) lists[i].get(j);
			}
		}
	}
	
	public double[][] getLists() {
		return variablesArray;
	}
	
	private void stringToTimestamp(String dateString, ArrayList<Double> dateList) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date parsedDate = dateFormat.parse(dateString);
			long timestamp = parsedDate.getTime()/1000;
			dateList.add((double) timestamp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Data data = new Data();
		try {
			data.getData("SAP");
			double[][] table = data.getLists();
			System.out.println(table[4][450]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

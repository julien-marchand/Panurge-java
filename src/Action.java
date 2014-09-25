import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Action {
	private String name;

	private double[] date;
	private double[] open;
	private double[] high;
	private double[] low;
	private double[] close;
	private double[] volume;
	private double[] adjClose;
	
	private double[] stocK;
	private double[] stocD;
	private double[] stocDS;
	private double[] stocDSS;
	
	public Action(String name){
		this.name = name;
	}
	
	public void buildAction(){
		getData(name);
		prepareData();
		computeRating();
	}
	
	private void computeRating() {
		// TODO compute a score describing the quality of the algorithm for the past 6-months period
	}

	public void getData(String companySymbol) {
		ArrayList<Double> dateList = new ArrayList<>();
		ArrayList<Double> openList = new ArrayList<>();
		ArrayList<Double> highList = new ArrayList<>();
		ArrayList<Double> lowList = new ArrayList<>();
		ArrayList<Double> closeList = new ArrayList<>();
		ArrayList<Double> volumeList = new ArrayList<>();
		ArrayList<Double> adjCloseList = new ArrayList<>();
		ArrayList[] lists = new ArrayList[]{dateList, openList, highList, lowList, closeList, volumeList, adjCloseList};
		
		try {
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
		} catch (IOException e) {
			// TODO IOException
			e.printStackTrace();
		}
		date = new double[lists[0].size()];
		open = new double[lists[0].size()];
		high = new double[lists[0].size()];
		low = new double[lists[0].size()];
		close = new double[lists[0].size()];
		volume = new double[lists[0].size()];
		adjClose = new double[lists[0].size()];
		double[][] variablesArray = new double[][]{date, open,high, low, close, volume, adjClose};
		for (int i = 0; i < lists.length; i++) {
			for (int j = 0; j < variablesArray[i].length; j++) {
				variablesArray[i][j] = (double) lists[i].get(j);
			}
		}
	}
	
	private void stringToTimestamp(String dateString, ArrayList<Double> dateList) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			Date parsedDate = dateFormat.parse(dateString);
			long timestamp = parsedDate.getTime()/1000;
			dateList.add((double) timestamp);
		} catch (ParseException e) {
			// TODO ParseException
			e.printStackTrace();
		}
	}

	public void prepareData() {
		prepareStockastic();
	}
	
	public void prepareStockastic(){
		prepareStockastic(14, 3, 3, 3);
	}
	
	public void prepareStockastic(int nK, int nD, int nDS, int nDSS) {
		int numberOfDays = date.length;
		
		// (close - minOfPeriodnK) / (maxOfPeriodnK - minOfPeriodnK)
		stocK = new double[numberOfDays-nK];
		
		// average of stocK on a nD-period
		stocD = new double[numberOfDays-nK-nD];

		// average of stocD on a nDS-period
		stocDS = new double[numberOfDays-nK-nD-nDS];

		// average of stocDS on a nDSS-period
		stocDSS = new double[numberOfDays-nK-nD-nDS-nDSS];
		
		for(int i=0; i < numberOfDays-nK; ++i){
			double min = 9999;
			double max = -1;
			for(int j=0; j<nK; ++j){
				min = Math.min(min,low[i+j]);
				max = Math.max(max,high[i+j]);
			}
			stocK[i] = (close[i]-min) / (max-min);
			System.out.println(stocK[i]);
		}
		
		for(int i=0; i < numberOfDays-nK-nD; ++i){
			double sum = 0;
			for(int j=0; j<nD; ++j){
				sum += stocK[i];
			}
			stocD[i] = sum / nD;
		}
		
		for(int i=0; i < numberOfDays-nK-nD-nDS; ++i){
			double sum = 0;
			for(int j=0; j<nDS; ++j){
				sum += stocD[i];
			}
			stocDS[i] = sum / nDS;
		}
		
		for(int i=0; i < numberOfDays-nK-nD-nDS-nDSS; ++i){
			double sum = 0;
			for(int j=0; j<nDSS; ++j){
				sum += stocDS[i];
			}
			stocDSS[i] = sum / nDSS;
		}
	}
}

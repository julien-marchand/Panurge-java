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

	private int n;
	private double[] date;
	private double[] open;
	private double[] high;
	private double[] low;
	private double[] close;
	private double[] volume;
	private double[] adjClose;
	
	public double[] stocK;
	public double[] stocD;
	public double[] stocDS;
	public double[] stocDSS;
	
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
		@SuppressWarnings("unchecked")
		ArrayList<Double>[] lists = new ArrayList[]{dateList, openList, highList, lowList, closeList, volumeList, adjCloseList};
		
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
		n = lists[0].size();
		date = new double[n];
		open = new double[n];
		high = new double[n];
		low = new double[n];
		close = new double[n];
		volume = new double[n];
		adjClose = new double[n];
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
		int numberOfDays = n;
		
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
		}
		
		for(int i=0; i < numberOfDays-nK-nD; ++i){
			double sum = 0;
			for(int j=0; j<nD; ++j){
				sum += stocK[i+j];
			}
			stocD[i] = sum / nD;
		}
		
		for(int i=0; i < numberOfDays-nK-nD-nDS; ++i){
			double sum = 0;
			for(int j=0; j<nDS; ++j){
				sum += stocD[i+j];
			}
			stocDS[i] = sum / nDS;
		}
		
		for(int i=0; i < numberOfDays-nK-nD-nDS-nDSS; ++i){
			double sum = 0;
			for(int j=0; j<nDSS; ++j){
				sum += stocDS[i+j];
			}
			stocDSS[i] = sum / nDSS;
		}
	}
	
	public double getEarningRatio(){
		int interCount = 0, winCount = 0;
		double gain = 1.0;
		
		//Parameters
		double[] sUp = stocK;
		double[] sDown = stocDSS;
		int holdingDuration = 5;
		int analysisLength = 365;
		
		ChoiceAlgo algo = new StockLT(0.1);
		
		// #Obvious #Troll #C'estDégueulasse
		analysisLength = (analysisLength==-1?
				Math.min(sUp.length, sDown.length)-5
				:Math.min(Math.min(sUp.length, sDown.length)-5, analysisLength));
		
		for(int i=holdingDuration; i < analysisLength; ++i){
			int buyOrSell = algo.buyOrSell(this, i);
			if(buyOrSell == 1) {
				++interCount;
				gain = gain * close[i-holdingDuration]/close[i];
				if(close[i-holdingDuration] > close[i]) {
					++winCount;
				}
			}
			else if (buyOrSell == -1) {
				++interCount;
				gain = gain * close[i]/close[i-holdingDuration];
				if(close[i-holdingDuration] < close[i]) {
					++winCount;
				}
			}
	    }
		System.out.println("Validées: " + winCount + "/" + interCount + " = " + Utils.round((double)winCount/interCount,2) + "%");
		System.out.println("Gain: " + Utils.round(gain,4));
		
		if(interCount == 0) return 0;

		System.out.println("Gain moyen: " + Utils.round((Math.pow(gain,1.0*holdingDuration/interCount)-1)*100,4) + "%");
		System.out.println("Gain annuel: " + (gain>1?"+":"") + Utils.round((Math.pow(gain,365.0/holdingDuration/interCount)-1)*100,4) + "%");
		return (Math.pow(gain,1.0/holdingDuration/interCount)-1)*100;
	}
}

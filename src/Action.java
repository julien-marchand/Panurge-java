public class Action {
	private String name;

	private double[] data;
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
	
	public void buildAction(){
		getData();
		prepareData();
		computeRating();
	}
	
	private void computeRating() {
		// TODO compute a score describing the quality of the algorithm for the past 6-months period
	}

	public void getData() {

	}

	public void prepareData() {
		prepareStockastic();
	}
	
	public void prepareStockastic(){
		prepareStockastic(14, 3, 3, 3);
	}
	
	public void prepareStockastic(int nK, int nD, int nDS, int nDSS) {
		int numberOfDays = data.length;
		
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
			System.out.println("Min: "+min + ", max: " + max);
			stocK[i] = (close[i]-min) / (max-min);
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

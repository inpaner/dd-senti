package sentimentanalyzer;

public class SentiAnalyzerApi {
	
	public static SentiAnalyzerApi getAlchemySa() {
		SentiAnalyzerApi result = new SentiAnalyzerApi();
		result.sa = new AlchemySa();
		return result;
	}
	
	
//	public static SentiAnalyzerApi getMonkeyLearnSa() {
//		return new SentiAnalyzerApi();
//	}
	
	
	private SentiAnalyzerI sa;
	
	
	public SentiAnalyzerApi() {
		this.sa = new AlchemySa(); // Default SA
	}

	
	public SaResult getSentiment(String text) {
		return sa.getSentiment(text);
	}
}

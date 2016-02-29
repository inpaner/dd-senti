package sentimentanalyzer;

public class SentiAnalyzerApi {
	
//	public static SentiAnalyzerApi getMonkeyLearnSa() {
//		return new SentiAnalyzerApi();
//	}
	
	
	private SentiAnalyzerI sa;
	
	public static void main(String[] args) {
		new SentiAnalyzerApi().test();
	}
	
	private void test() {
		String[] texts = {"I like to eat pizza", 
				"That hat is ridiculous, Charles.",
				"What do you mean"};
		
		for (String text : texts) {
			SaResult result = this.getSentiment(text);
			System.out.println(text);
			System.out.println(result.getSentiment());
			System.out.println(result.getScore());
			System.out.println();
		}
	}
	
	
	public SentiAnalyzerApi() {
		this.sa = new AlchemySa(); // Default SA
	}

	
	public SaResult getSentiment(String text) {
		return sa.getSentiment(text);
	}
	
	
	public String getAnalyzerName() {
		return sa.getAnalyzerName();
	}
}

package sentimentanalyzer;

import java.util.List;

public class SentiAnalyzerApi {
	
	public static SentiAnalyzerApi getMonkeyLearnSa() {
		SentiAnalyzerApi api = new SentiAnalyzerApi();
		api.sa = new MonkeyLearnSa();
		return api;
	}
	
	
	public static SentiAnalyzerApi getAlchemySa() {
		SentiAnalyzerApi api = new SentiAnalyzerApi();
		api.sa = new AlchemySa();
		return api;
	}
	
	
	public static SentiAnalyzerApi getStanfordSa() {
		SentiAnalyzerApi api = new SentiAnalyzerApi();
		api.sa = new StanfordSa();
		return api;
	}
	

	public static void main(String[] args) {
		SentiAnalyzerApi.getAlchemySa().test();
	}
	
	
	private SentiAnalyzerI sa;
	
	
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
	
	
	private SentiAnalyzerApi() {}

	
	public SaResult getSentiment(String text) {
		return sa.getSentiment(text);
	}
	
	
	public List<SaResult> getSentiments(List<String> texts) {
		return sa.getSentiments(texts);
	}
	
	
	public String getAnalyzerName() {
		return sa.getAnalyzerName();
	}
}

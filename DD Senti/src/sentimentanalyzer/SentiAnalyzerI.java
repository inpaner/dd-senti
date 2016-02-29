package sentimentanalyzer;

public interface SentiAnalyzerI {
	public SaResult getSentiment(String text);
	public String getAnalyzerName();
}

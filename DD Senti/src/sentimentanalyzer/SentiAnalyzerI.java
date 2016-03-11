package sentimentanalyzer;

import java.util.List;

public interface SentiAnalyzerI {
	public SaResult getSentiment(String text);
	public String getAnalyzerName();
	public List<SaResult> getSentiments(List<String> texts);
}

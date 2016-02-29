package sentimentanalyzer;

public class SaResult {
	private Sentiment sentiment;
	private double score;
	
	SaResult(Sentiment sentiment, double score) {
		this.sentiment = sentiment;
		this.score = score;
	}
	
	
	public Sentiment getSentiment() {
		return sentiment;
	}
		
	
	public double getScore() {
		return score;
	}
	
	
	public String toString() {
		return sentiment.toString();
	}
}

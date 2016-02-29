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
	
	
	public boolean isPositive() {
		return sentiment.equals(Sentiment.POSITIVE);
	}
	
	
	public double getScore() {
		return score;
	}
}

package sentimentanalyzer;

public class SaResult {
	private Sentiment sentiment;
	private String tweet;
	private double score;
	
	SaResult(Sentiment sentiment, double score) {
		this.sentiment = sentiment;
		this.score = score;
	}
	
	SaResult(Sentiment sentiment, double score, String tweet) {
		this.sentiment = sentiment;
		this.score = score;
		this.tweet = tweet;
	}
	
	public String getTweet(){
		return tweet;
	}
		
	public Sentiment getSentiment() {
		return sentiment;
	}
		
	
	public double getScore() {
		return score;
	}
	
	
	@Override
	public String toString() {
		return sentiment.toString();
	}
}

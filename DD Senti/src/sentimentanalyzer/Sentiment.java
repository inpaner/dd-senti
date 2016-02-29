package sentimentanalyzer;

public enum Sentiment {
	
	POSITIVE("Positive"), 
	NEGATIVE("Negative"),
	NEUTRAL("Neutral");
	
	static final Sentiment getSentiment(String value) {
		Sentiment result = Sentiment.NEUTRAL;
		
		if (value.toLowerCase().trim().equals("positive")) {
			result = Sentiment.POSITIVE;
		} else if (value.toLowerCase().trim().equals("negative")) {
			result = Sentiment.NEGATIVE;
		}
		
		return result;
	}
	
	private final String value;
	
	
	Sentiment(String value) {
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return value;
	}
}

package sentimentanalyzer;

public enum Sentiment {
	
	POSITIVE("Positive"), 
	NEGATIVE("Negative");
	
	static final Sentiment getSentiment(String value) {
		Sentiment result = Sentiment.POSITIVE;
		
		if (value.toLowerCase().trim().equals("negative")) {
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

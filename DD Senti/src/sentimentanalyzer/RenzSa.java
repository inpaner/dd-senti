package sentimentanalyzer;

import java.util.ArrayList;
import java.util.List;

import sentimentanalyzer.renzapi.RenzApi;

public class RenzSa implements SentiAnalyzerI {
	private final String ANALYZER_NAME = "Renz";
	private RenzApi renzApi;
	
	public RenzSa() {
		renzApi = new RenzApi();
	}
	
	@Override
	public SaResult getSentiment(String text) {
		int score = 0;
		Sentiment sentiment = Sentiment.NEUTRAL;
		
		score = renzApi.scoreTweet(text);
		if(score == 1){
			sentiment = Sentiment.POSITIVE;
        }
        else if(score == -1){
        	sentiment = Sentiment.NEGATIVE;
        }
		
		return new SaResult(sentiment, score, text);
	}

	@Override
	public String getAnalyzerName() {
		return ANALYZER_NAME;
	}
	
	public static void main(String[] args) {
		RenzSa sa = new RenzSa();
		System.out.println(sa.getSentiment("sad reality"));
	}

	@Override
	public List<SaResult> getSentiments(List<String> texts) {
		ArrayList<SaResult> results = new ArrayList<>(); 
		for (String text : texts) {
			results.add( this.getSentiment(text) );
		}
		
		return results;
	}

}

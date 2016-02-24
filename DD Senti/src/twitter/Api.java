package twitter;

import java.util.ArrayList;

public class Api {
	
	public void analyzeTweets(String keywordsCsv) {
		// add keywords
		String[] keywords = keywordsCsv.split(",");
		TweetManager manager = new TweetManager();
		ArrayList<Tweet> tweets = new ArrayList<>();
		for (String word : keywords) {
			tweets.addAll( manager.getAllByKeyword(word) );
		}
		
		// preprocess tweets
		// feed into senti analyzer
 
	}	
}

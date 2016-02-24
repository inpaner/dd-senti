package twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class Api {
	public static void main(String[] args) {
		Api api = new Api();
//		api.testKeywordManager();
		api.testCrawler();
	}
	
	
	public void analyzeTweets(String keywordsCsv) {
		String[] keywords = keywordsCsv.split(",");
		
		KeywordManager keywordmanager = new KeywordManager();
		for (String word : keywords) {
			keywordmanager.addKeyword(word);
		}
		
		TweetManager manager = new TweetManager();
		ArrayList<Tweet> tweets = new ArrayList<>();
		for (String word : keywords) {
			tweets.addAll( manager.getAllByKeyword(word) );
		}
		
		// preprocess tweets
		// feed into senti analyzer
	}
	
	/* Tests */
	
	private void testKeywordManager() {
		String keywordsCsv = "burger, chocolate, donut, almond";
		String[] keywords = keywordsCsv.split(",");
		
		KeywordManager keywordmanager = new KeywordManager();
		for (String word : keywords) {
			keywordmanager.addKeyword(word);
		}
	}
	
	
	private void testCrawler() {
		String keyword = "hybrid cloud";
		Crawler crawler = new Crawler();
		
		List<Status> tweets = crawler.mine(keyword);
		TweetManager manager = new TweetManager();
		manager.createAll(tweets, keyword);
	}
}

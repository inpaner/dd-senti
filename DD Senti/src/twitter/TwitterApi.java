package twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class TwitterApi {
	public static void main(String[] args) {
		TwitterApi api = new TwitterApi();
//		api.testKeywordManager();
//		api.testCrawler();
//		api.analyzeTweets("cybersecurity");
//		api.directAnalyzeTweets("cybersecurity");
		api.testKeywordDelete();
//		api.runCrawler();
	}
	
	
	private void internalAnalyzeTweets(String keywordsCsv, boolean crawl) {
		String[] keywords = keywordsCsv.split(",");
		
		KeywordManager keywordmanager = new KeywordManager();
		for (String word : keywords) {
			keywordmanager.addKeyword(word);
		}
		
		if (crawl) {
			Crawler crawler = new Crawler();
			crawler.runOnce();	
		}
		
		TweetManager manager = new TweetManager();
		ArrayList<Tweet> tweets = new ArrayList<>();
		for (String word : keywords) {
			tweets.addAll( manager.getAllByKeyword(word) );
		}
		
		// preprocess tweets
		// feed into senti analyzer
		for (Tweet tweet : tweets) {
			System.out.println(tweet);
		}
	}
	
	
	/**
	 * Performs one initial crawl then analyzes all tweets.
	 * @param keywordsCsv
	 */
	public void analyzeTweets(String keywordsCsv) {
		this.internalAnalyzeTweets(keywordsCsv, true);
	}
	
	
	/**
	 * Analyzes all tweets without performing an initial crawl.
	 * @param keywordsCsv
	 */
	public void directAnalyzeTweets(String keywordsCsv) {
		this.internalAnalyzeTweets(keywordsCsv, false);
	}
	
	
	public void runCrawler() {
		Crawler crawler = new Crawler();
		crawler.run();
	}
	
	
	/* Tests */
	private void testKeywordManager() {
		String keywordsCsv = "burger, chocolate, donut, almond";
		String[] keywords = keywordsCsv.split(",");
		
		KeywordManager keywordmanager = new KeywordManager();
		for (String word : keywords) {
			keywordmanager.addKeyword(word);
		}
		
		System.out.println(keywordmanager.getAll());
		
		keywordmanager.getAll();
	}
	
	
	private void testKeywordDelete() {
		KeywordManager km = new KeywordManager();
		km.deleteKeyword("interior");
	}
	
	private void testCrawler() {
		String keyword = "hybrid cloud";
		Crawler crawler = new Crawler();
		
		List<Status> tweets = crawler.mine(keyword);
		TweetManager manager = new TweetManager();
		manager.createAll(tweets, keyword);
	}
}

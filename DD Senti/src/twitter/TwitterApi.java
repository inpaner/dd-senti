package twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Status;

public class TwitterApi {
	private Crawler crawler = new Crawler();

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
	
	
	public List<Tweet> getTweetsByKeyword(String keyword) {
		TweetManager manager = new TweetManager();
		return manager.getAllByKeyword(keyword);
	}
	
	
	public List<String> getKeywords() {
		KeywordManager manager = new KeywordManager();
		return manager.getAll();
	}
	
	
	public void addKeyword(String keyword) {
		KeywordManager manager = new KeywordManager();
		manager.addKeyword(keyword);
	}
	
	
	public void removeKeyword(String keyword) {
		KeywordManager manager = new KeywordManager();
		manager.deleteKeyword(keyword);
	}
	
	
	public void runCrawler() {
		crawler.run();
	}
	
	
	public void stopCrawler() {
		crawler.stop();
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

package main;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import sentimentanalyzer.SaResult;
import sentimentanalyzer.SentiAnalyzerApi;
import sentimentanalyzer.Sentiment;
import twitter.Tweet;
import twitter.TwitterApi;
import ui.MainPanel;
import ui.UiApi;

public class Main {
	private SentiAnalyzerApi sa;
	private UiApi ui;
	private TwitterApi twitter;


	public static void main(String[] args) {
//		new Main().crawl();
		new Main();
	}
	
	
	private void crawl() {
		twitter = new TwitterApi();
		twitter.runCrawler();
	}
	
	public Main() {
		sa = SentiAnalyzerApi.getStanfordSa();
		ui = new UiApi();
		ui.addListener(new UiListener());
		twitter = new TwitterApi();
		ui.addWordsToLeftPanel( twitter.getKeywords() );
	}
	
	
	private void analyzeWords(List<String> words) {
		/* Sentiment */
		System.out.println("Analyzing words");
		JsonBuilder jsonBuilder = new JsonBuilder();
		for (String keyword : words) {
			System.out.println("Analyzing " + keyword);
			List<Tweet> tweets = twitter.getTweetsByKeyword(keyword);
			List<String> tweetTexts = new ArrayList<>();
			for (Tweet tweet : tweets) {
				tweetTexts.add(tweet.getText());
			}
			System.out.println("Getting sentiment of " + tweetTexts.size() + " tweets.");
			List<SaResult> sentiments = sa.getSentiments(tweetTexts);
			int positive = 0;
			int negative = 0;
			int neutral = 0;
			for (SaResult sentiment : sentiments) {
				if (sentiment.getSentiment().equals(Sentiment.POSITIVE)) {
					positive++;
				} else if (sentiment.getSentiment().equals(Sentiment.NEGATIVE)) {
					negative++;
				} else if (sentiment.getSentiment().equals(Sentiment.NEUTRAL)) {
					neutral++;
				}
			}
			
			JsonObject sentimentJson = 
					jsonBuilder.buildSentiment(keyword, positive, negative, neutral);
		}
		System.out.println("Done.");
		
		/* Tweet count */
		
	}
	
	
	private class UiListener implements MainPanel.Listener {

		@Override
		public void addWord(String word) {
			twitter = new TwitterApi();
			twitter.addKeyword(word);
			ui.clearAddWordField();
			ui.addWord(word);
		}

		@Override
		public void removeWord(String word) {
			twitter = new TwitterApi();
			twitter.removeKeyword(word);
			ui.removeWord(word);
		}

		
		@Override
		public void analyzeWords(List<String> words) {
			Main.this.analyzeWords(words);
		}

		@Override
		public void crawlWordsOn() {
			twitter.runCrawler();
			
		}

		@Override
		public void crawlWordOff() {
			twitter.stopCrawler();
		}	
	}
}

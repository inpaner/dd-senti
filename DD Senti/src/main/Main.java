package main;

import java.util.ArrayList;
import java.util.List;

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
			System.out.println("Analyzing words");
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
				System.out.println("Keyword: " + keyword);
				System.out.println("Positive: " + positive);
				System.out.println("Negative: " + negative);
				System.out.println("Neutral: " + neutral);
				System.out.println();
			}
			System.out.println("Done.");
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

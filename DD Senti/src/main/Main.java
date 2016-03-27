package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;

import preprocessor.PreprocessorManager;
import sentimentanalyzer.NGramAnalyzer;
import sentimentanalyzer.SaResult;
import sentimentanalyzer.SentiAnalyzerApi;
import sentimentanalyzer.Sentiment;
import twitter.Tweet;
import twitter.TweetCount;
import twitter.TwitterApi;
import ui.MainPanel;
import ui.UiApi;

public class Main {
	private SentiAnalyzerApi sa;
	private UiApi ui;
	private TwitterApi twitter;
	private PreprocessorManager preprocessor;

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
		preprocessor = new PreprocessorManager();
		ui = new UiApi();
		ui.addListener(new UiListener());
		twitter = new TwitterApi();
		ui.addWordsToLeftPanel(twitter.getKeywords());
		
	}
	
	
	private void analyzeWords(List<String> words) {
		System.out.println("Analyzing keywords");
		JsonBuilder jsonBuilder = new JsonBuilder();
		List<JsonObject> keywordSets = new ArrayList<>();
		for (String keyword : words) {
			/* Sentiments */
			System.out.println("Analyzing " + keyword);
			List<Tweet> tweets = twitter.getTweetsByKeyword(keyword);
			List<String> tweetTexts = new ArrayList<>();
			for (Tweet tweet : tweets) {
				String text = preprocessor.preprocess(tweet.getText());
				tweet.setText(text);
				tweetTexts.add(tweet.getText());
			}
			System.out.println("Getting sentiment of " + tweetTexts.size() + " tweets.");
			List<SaResult> sentiments = sa.getSentiments(tweetTexts);
			List<String> positiveTexts = new ArrayList<>();
			List<String> negativeTexts = new ArrayList<>();
			List<String> neutralTexts = new ArrayList<>();
			for (SaResult sentiment : sentiments) {
				if (sentiment.getSentiment().equals(Sentiment.POSITIVE)) {
					positiveTexts.add(sentiment.getTweet());
				} else if (sentiment.getSentiment().equals(Sentiment.NEGATIVE)) {
					negativeTexts.add(sentiment.getTweet());
				} else if (sentiment.getSentiment().equals(Sentiment.NEUTRAL)) {
					neutralTexts.add(sentiment.getTweet());
				}
			}
			
			JsonObject sentimentJson = jsonBuilder.buildSentiment(positiveTexts.size(), 
					negativeTexts.size(), neutralTexts.size());
			System.out.println("Done analyzing.");
			
			/* Tweet count */
			List<TweetCount> tweetCounts = twitter.getTweetCounts(keyword);
			JsonArray tweetCountArray = jsonBuilder.buildTweetCount(tweetCounts);
			
			/* Word cloud */
			NGramAnalyzer ngram = new NGramAnalyzer(positiveTexts);
			Map<String, Integer> positiveNgram = ngram.getNgram();
			
			ngram = new NGramAnalyzer(negativeTexts);
			Map<String, Integer> negativeNgram = ngram.getNgram();
			
			ngram = new NGramAnalyzer(neutralTexts);
			Map<String, Integer> neutralNgram = ngram.getNgram();
			JsonArray wordcloudArray = 
					jsonBuilder.buildWordcloud(positiveNgram, negativeNgram, neutralNgram);
			
			JsonObject keywordSet = jsonBuilder.buildKeywordSet(
					keyword, sentimentJson, tweetCountArray, wordcloudArray);
			keywordSets.add(keywordSet);
		}
		JsonObject result = jsonBuilder.buildAll(keywordSets);
		jsonBuilder.saveOutput(result);
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

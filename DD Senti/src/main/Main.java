package main;

import java.util.List;

import sentimentanalyzer.SentiAnalyzerApi;
import twitter.Tweet;
import twitter.TwitterApi;
import ui.MainPanel;
import ui.UiApi;

public class Main {
	private SentiAnalyzerApi sa;
	private UiApi ui;


	public static void main(String[] args) {
		new Main();
	}
	
	
	public Main() {
		sa = SentiAnalyzerApi.getStanfordSa();
		ui = new UiApi();
		ui.addListener(new UiListener());
	}
	
	
	private class UiListener implements MainPanel.Listener {

		@Override
		public void addWord(String word) {
			TwitterApi twitter = new TwitterApi();
			twitter.addKeyword(word);
			ui.clearAddWordField();
			ui.addWord(word);
		}

		@Override
		public void removeWord(String word) {
			TwitterApi twitter = new TwitterApi();
			twitter.removeKeyword(word);
			ui.removeWord(word);
		}

		@Override
		public void analyzeWords(List<String> words) {
			TwitterApi twitter = new TwitterApi();
			
			for (String keyword : words) {
				List<Tweet> tweets = twitter.getTweetsByKeyword(keyword);
				
			}
		}
		
	}
}

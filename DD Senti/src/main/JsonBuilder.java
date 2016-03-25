package main;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import twitter.TweetCount;
import twitter.TweetManager;

class JsonBuilder {
	private final String SENTIMENT_KEY = "sentiment";
	private final String KEYWORD_KEY = "keyword";
	private final String POSITIVE_KEY = "positive";
	private final String NEGATIVE_KEY = "negative";
	private final String NEUTRAL_KEY = "neutral";
	
	private final String TWEET_COUNT_KEY = "tweetcount";
	private final String DATE_KEY = "date";
	private final String COUNT_KEY = "count";
	
	
	public static void main(String[] args) {
	}
	
	JsonArray buildTweetCount(List<TweetCount> tweetCounts) {
		JsonArrayBuilder countsArray = Json.createArrayBuilder();
		for (TweetCount tweetCount : tweetCounts) {
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add(DATE_KEY, tweetCount.date.toString());
			builder.add(COUNT_KEY, tweetCount.count);
			countsArray.add(builder);
		}
		return countsArray.build();
	}
	
	
	JsonArray buildSentiments(List<JsonObject> sentimentObjects) {
		JsonArrayBuilder keywordArray = Json.createArrayBuilder();
		for (JsonObject keywordObject : sentimentObjects) {
			keywordArray.add(keywordObject);
		}	
		return keywordArray.build();
	}
	
	
	JsonObject buildSentiment(String keyword, int positive, int negative, int neutral) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add(KEYWORD_KEY, keyword);
		builder.add(POSITIVE_KEY, positive);
		builder.add(NEGATIVE_KEY, negative);
		builder.add(NEUTRAL_KEY, neutral);
		return builder.build();
	}
	
	
	JsonObject buildAll(JsonArray sentiment, JsonArray tweetCount) {
		JsonObjectBuilder result = Json.createObjectBuilder();
		result.add(SENTIMENT_KEY, sentiment);
		result.add(TWEET_COUNT_KEY, tweetCount);
		return result.build();
	}
}

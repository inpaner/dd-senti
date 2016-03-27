package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import twitter.TweetCount;
import twitter.TweetManager;

class JsonBuilder {
	private final String OUTPUT_LOCATION = "json/output.json";
	private final String DATA_KEY = "data";
	private final String SENTIMENT_KEY = "sentiment";
	private final String KEYWORD_KEY = "keyword";
	private final String POSITIVE_KEY = "positive";
	private final String NEGATIVE_KEY = "negative";
	private final String NEUTRAL_KEY = "neutral";
	private final String TWEET_COUNT_KEY = "tweetcount";
	private final String DATE_KEY = "date";
	private final String COUNT_KEY = "count";
	private final String WORDCLOUD_KEY = "wordcloud";
	private final String TEXT_KEY = "text";
	private final String WEIGHT_KEY = "weight";
	
	private final String COLOR_KEY = "color";
	private final String POSITIVE_COLOR = "blue";
	private final String NEGATIVE_COLOR = "red";
	private final String NEUTRAL_COLOR = "black";
	
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

	
	JsonObject buildSentiment(int positive, int negative, int neutral) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add(POSITIVE_KEY, positive);
		builder.add(NEGATIVE_KEY, negative);
		builder.add(NEUTRAL_KEY, neutral);
		return builder.build();
	}
	
	
	JsonObject buildKeywordSet(String keyword, JsonObject sentiment, JsonArray tweetCount, JsonArray wordcloud) {
		JsonObjectBuilder result = Json.createObjectBuilder();
		result.add(KEYWORD_KEY, keyword);
		result.add(SENTIMENT_KEY, sentiment);
		result.add(TWEET_COUNT_KEY, tweetCount);
		result.add(WORDCLOUD_KEY, wordcloud);
		return result.build();
	}
	
	
	JsonArray buildWordcloud(Map<String, Integer> positive, Map<String, Integer> negative,
			Map<String, Integer> neutral) {
		JsonArrayBuilder result = Json.createArrayBuilder();
		result = this.buildIndividualWordCloud(result, positive, POSITIVE_COLOR);
		result = this.buildIndividualWordCloud(result, negative, NEGATIVE_COLOR);
		result = this.buildIndividualWordCloud(result, neutral, NEUTRAL_COLOR);	
		return result.build();
	}
	
	
	private JsonArrayBuilder buildIndividualWordCloud(JsonArrayBuilder builder, 
			Map<String, Integer> wordcloud, String color) {
		for (String word : wordcloud.keySet()) {
			JsonObjectBuilder json = Json.createObjectBuilder();
			int weight = wordcloud.get(word);
			json.add(TEXT_KEY, word);
			json.add(WEIGHT_KEY, weight);
			json.add(COLOR_KEY, color);
			builder.add(json);
		}
		return builder;
	}
	
	
	JsonObject buildAll(List<JsonObject> keywordSets) {
		JsonArrayBuilder keywordSetsArray = Json.createArrayBuilder();
		for (JsonObject keywordSet : keywordSets) {
			keywordSetsArray.add(keywordSet);
		}
		JsonObjectBuilder result = Json.createObjectBuilder();
		result.add(DATA_KEY, keywordSetsArray);
		return result.build();
	}
	
	
	void saveOutput(JsonObject json) {
		String output = json.toString();
		File file = new File(OUTPUT_LOCATION);
		Writer writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(output);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

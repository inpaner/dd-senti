package twitter;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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
		JsonBuilder builder = new JsonBuilder();
		TweetManager manager = new TweetManager();
		JsonObject x = builder.build(manager.getTweetCounts());
		System.out.println(x.toString());
	}
	
	JsonObject build(List<TweetCount> tweetCounts) {
		JsonArrayBuilder countsArray = Json.createArrayBuilder();
		for (TweetCount tweetCount : tweetCounts) {
			JsonObjectBuilder builder = Json.createObjectBuilder();
			builder.add(DATE_KEY, tweetCount.date.toString());
			builder.add(COUNT_KEY, tweetCount.count);
			countsArray.add(builder);
		}
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add(TWEET_COUNT_KEY, countsArray);	
		return builder.build();
	}
	
	
	JsonObject buildSentiments(List<JsonObject> keywordObjects) {
		JsonArrayBuilder keywordArray = Json.createArrayBuilder();
		for (JsonObject keywordObject : keywordObjects) {
			keywordArray.add(keywordObject);
		}
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add(SENTIMENT_KEY, keywordArray);
		return builder.build();
	}
	
	
	JsonObject build(String keyword, int positive, int negative, int neutral) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add(KEYWORD_KEY, keyword);
		builder.add(POSITIVE_KEY, positive);
		builder.add(NEGATIVE_KEY, negative);
		builder.add(NEUTRAL_KEY, neutral);
		return builder.build();
	}
}

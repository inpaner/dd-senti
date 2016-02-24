package twitter;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * @author Yusuke Yamamoto - yusuke at mac.com
 * @since Twitter4J 2.1.7
 */
public class Crawler {
	private final boolean DEBUG = false;
	
	public static final int SEARCH_COUNT = 10000;
	//public static final String SEARCH_SINCE = "2015-10-01"; 
	public static final String SEARCH_SINCE = "";
	//public static final String SEARCH_UNTIL = "2015-10-13";
	public static final String SEARCH_UNTIL = "";
	
    public static final String CONSUMER_KEY = "fwbtkGf8N97yyUZyH5YzLw";
    public static final String CONSUMER_SECRET = "oQA5DunUy89Co5Hr7p4O2WmdzqiGTzssn2kMphKc8g";
    public static final String OAUTH_ACCESS_TOKEN = "461053984-aww1IbpSVcxUE2jN8VqsOkEw8IQeEMusx4IdPM9p";
    public static final String OAUTH_ACCESS_TOKEN_SECRET = "WGsbat8P8flqKqyAymnWnTnAGI5hZkgdaQSE8XALs7ZEp";
    
    
	public void addKeyword(String word) {
		// add keyword to db
	}
	
	
	public void run() {
		// thread ba to
		// loop lang
		
		// get keywords from db
		// actually crawl for each	
	}
	
	
    /**
     * Usage: java twitter4j.examples.search.SearchTweets [query]
     *
     * @param keyword  
     */
    public List<Status> mine(String keyword) {
        List<Status> results = new ArrayList<Status>();
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
          .setOAuthConsumerKey(CONSUMER_KEY)
          .setOAuthConsumerSecret(CONSUMER_SECRET)
          .setOAuthAccessToken(OAUTH_ACCESS_TOKEN)
          .setOAuthAccessTokenSecret(OAUTH_ACCESS_TOKEN_SECRET);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
 
        try {           
            Query query = new Query(keyword);
            query.setCount(SEARCH_COUNT);
            if (!SEARCH_SINCE.isEmpty()) {
            	query.setSince(SEARCH_SINCE);
            } 
            if (!SEARCH_UNTIL.isEmpty()) {
            	query.setUntil(SEARCH_UNTIL);
            }
            
            QueryResult result;
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();
                results.addAll(result.getTweets());
                if (DEBUG) {
                	for (Status tweet : tweets) {
                        System.out.println(tweet.getId() + " @ " + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    }	
                }
            } while ((query = result.nextQuery()) != null);
        } catch (TwitterException e) {
            e.printStackTrace();
            System.out.println("Failed to search tweets: " + e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
        }
        return results;
    }
}

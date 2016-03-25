package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import db.DAOFactory;
import db.DAOUtil;
import twitter4j.Status;

public class TweetManager {
    private DAOFactory factory;
    private static List<String> months;
    private final int TWEET_COUNT_DAYS = 7;
    
    static {
    	String[] monthList = {"jan", "feb", "mar", "apr", "may", "jun",
    			"jul", "aug", "sep", "oct", "nov", "dec"};
    	months = Arrays.asList(monthList);
    }

    
    public TweetManager() {
        factory = DAOFactory.getInstance();
    }
    
    private static final String SQL_CREATE = 
            "INSERT INTO Tweets(id, username, text, date, latitude, longitude, keyword_fk) " +
            " VALUES (?, ?, ?, ?, ?, ?, ?) ";

    
    private static final String SQL_GET_BY_KEYWORD = 
            "SELECT id, username, text, date, latitude, longitude, keyword_fk " +
            " FROM Tweets " +
            " WHERE keyword_fk = ? ";
    
    
    private static final String SQL_GET_ALL =
    		"SELECT id, username, text, date, latitude, longitude, keyword_fk " +
            " FROM Tweets ";
    
    
    private static final String SQL_UPDATE_DATE = 
    		"UPDATE Tweets "
    		+ "SET date = ? "
    		+ "WHERE id = ? ";
    
    
    private static final String SQL_TWEET_COUNT =
    		"SELECT COUNT(*) AS count "
    		+ "FROM Tweets "
    		+ "WHERE date like ? ";
    
    
    public static void main(String[] args) {
		// new TweetManager().fixDates();
    	new TweetManager().getTweetCounts();
	}
    
    
    List<TweetCount> getTweetCounts() {
    	List<TweetCount> tweetCounts = new ArrayList<>();
    	Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // LocalDate date = LocalDate.now();
        LocalDate date = LocalDate.of(2016, 1, 23);
        for (int i = 0; i < TWEET_COUNT_DAYS; i++) {
        	try {
        		String dateStr = date.toString() + "%";
            	Object[] values = {dateStr};
                conn = factory.getConnection();
                ps = DAOUtil.prepareStatement(conn, SQL_TWEET_COUNT, false, values);        
                rs = ps.executeQuery();
                while (rs.next()) {
                	TweetCount count = new TweetCount(date, rs.getInt("count"));
                	tweetCounts.add(count);
                }
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            } finally {
                DAOUtil.close(conn, ps, rs);
            }
        	date = date.minusDays(1);
        }
        return tweetCounts;
    }
    
    
    private void fixDates() {
    	List<Tweet> tweets = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
        	Object[] values = {};
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, SQL_GET_ALL, false, values);        
            rs = ps.executeQuery();
            // tweet.fixDate();
            while (rs.next()) {
                Tweet tweet = map(rs);
                tweets.add(tweet);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DAOUtil.close(conn, ps, rs);
        }
        
        try {
        	conn = factory.getConnection();
        	for (Tweet tweet : tweets) {
        		// tweet.fixDate();
            	Object[] values = {tweet.getDate(), tweet.getId()};
            	ps = DAOUtil.prepareStatement(conn, SQL_UPDATE_DATE, false, values);        
            	ps.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DAOUtil.close(conn, ps, rs);
        }    
    }
    
    
    public List<Tweet> getAllByKeyword(String keyword) {
        List<Tweet> result = new ArrayList<>();
        if (keyword.isEmpty()) {
        	return result;
        }
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object[] values = {keyword};
        try {
            conn = factory.getConnection();
            ps = DAOUtil.prepareStatement(conn, SQL_GET_BY_KEYWORD, false, values);        
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Tweet tweet = map(rs);
                result.add(tweet);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DAOUtil.close(conn, ps, rs);
        }
        return result;
    }
    
    
    private String fixDate(String date) {
    	String[] twitterDates = date.split(" ");
    	int month = months.indexOf(twitterDates[1].toLowerCase()) + 1;
    	int day = Integer.valueOf(twitterDates[2]);
    	int year = Integer.valueOf(twitterDates[5]);
    	String time = twitterDates[3];
    	LocalTime fixedTime = LocalTime.parse(time);
    	LocalDate fixedDate = LocalDate.of(year, month, day);
    	LocalDateTime dateTime = LocalDateTime.of(fixedDate, fixedTime);
    	return dateTime.toString();
    }
    
    
    public void createAll(List<Status> tweets, String keyword) {        
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = factory.getConnection();
            
            for (Status tweet : tweets) {
                String latitude = "";
                String longitude = "";
                if (tweet.getGeoLocation() != null) {
                    latitude = String.valueOf(tweet.getGeoLocation().getLatitude());
                    longitude = String.valueOf(tweet.getGeoLocation().getLongitude());
                }
                
                String text = tweet.getText();
                
                try {
            		text.replaceAll("\n", " ");
            	} catch (NullPointerException ex) {
            	}
                String date = this.fixDate(tweet.getCreatedAt().toString()); 
                Object[] values = {
                        tweet.getId(),
                        tweet.getUser().getScreenName(),
                        text,
                        date,
                        latitude,
                        longitude,
                        keyword
                };
                ps = DAOUtil.prepareStatement(conn, SQL_CREATE, false, values);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            DAOUtil.close(conn, ps);
        }
    }
    
    
    private Tweet map(ResultSet rs) {
        Tweet result = null;
        try {
            result = new Tweet(
                rs.getLong("id"),
                rs.getString("username"),
                rs.getString("text"),
                rs.getString("date"),
                rs.getString("latitude"),
                rs.getString("longitude"),
                rs.getString("keyword_fk")
            );
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
}

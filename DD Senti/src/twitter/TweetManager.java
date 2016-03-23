package twitter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.DAOFactory;
import db.DAOUtil;
import twitter4j.Status;

public class TweetManager {
    private DAOFactory factory;
    
    
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
    
    
    private static final String UPDATE_DATE = 
    		"UPDATE Tweets "
    		+ "SET date = ? "
    		+ "WHERE id = ? ";
    
    public static void main(String[] args) {
		// new TweetManager().fixDates();
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
            System.out.println("here");
            rs = ps.executeQuery();
            
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
        		System.out.println("here2");
            	tweet.fixDate();
            	Object[] values = {tweet.getDate(), tweet.getId()};
            	ps = DAOUtil.prepareStatement(conn, UPDATE_DATE, false, values);        
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
                
                Object[] values = {
                        tweet.getId(),
                        tweet.getUser().getScreenName(),
                        text,
                        tweet.getCreatedAt().toString(),
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

package twitter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tweet {
	
    private long id;
    private String username;
    private String text;
    private String date;
    private Double latitude;
    private Double longitude;
    private String keyword;
    
    public Tweet(long id, String username, String text, 
                String date, Double latitude, Double longitude, String keyword) {
    	this.id = id;
        this.username = username;
        this.text = text;
        this.date = date;
        this.latitude = latitude;
        this.longitude = longitude;
        this.keyword = keyword;
    }
    
    
    public Tweet(String text) {
    	this.text = text;
    }
    
    
    public Tweet(long id, String username, String text, 
                String date, String latitude, String longitude, String keyword) {
        this(id, username, text, date, (Double) null, (Double) null, keyword);
        
        if (latitude != null && !latitude.isEmpty()) {
            this.latitude = Double.valueOf(latitude);
        }
        if (latitude != null && !longitude.isEmpty()) {
            this.longitude = Double.valueOf(longitude);
        }
    }

    public String getUsername() {
        return username;
    }
    
    public long getId() {
    	return id;
    }
    
    public String getText() {
        return text;
    }
    
    public String getCleanText() {
        return text.toLowerCase();
    }
    
    public String getDate() {
        return date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
        
    public String getKeyword() {
        return keyword;
    }
    
    
    public void setText(String text) {
    	this.text = text;
    }
    
    
    public static void main(String[] args) {
	}
    
    
    @Override
    public String toString() {
    	return "@" + this.getUsername() + " - " + this.getText();
    }
}

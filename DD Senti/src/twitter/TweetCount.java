package twitter;

import java.time.LocalDate;

public final class TweetCount {
	public LocalDate date;
	public int count;  
	
	public TweetCount(LocalDate date, int count) {
		this.date = date;
		this.count = count;
	}
}

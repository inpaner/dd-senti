package twitter;

import java.time.LocalDate;

final class TweetCount {
	LocalDate date;
	int count;  
	
	TweetCount(LocalDate date, int count) {
		this.date = date;
		this.count = count;
	}
}

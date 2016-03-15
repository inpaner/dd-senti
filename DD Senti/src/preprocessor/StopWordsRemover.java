package preprocessor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Load the list of stop words into a HashSet then use it for scanning and removing stop words in a line (tweet)
 * English stop words only
 */
public class StopWordsRemover {
private static Set<String> stopwords;
    
    static {
        stopwords = new HashSet<>();
        try {
            Scanner stopWordsScanner = new Scanner(new FileInputStream("filters/englishstopwords.txt"));
            while (stopWordsScanner.hasNext()) {
                stopwords.add(stopWordsScanner.next());
            }
            stopWordsScanner.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StopWordsRemover.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    StringBuilder nTweet;

    private void checkStopWords(String word) {
            if (!stopwords.contains(word)) {
                if (nTweet.length() != 0) {
                    nTweet.append(" ");
                }
                nTweet.append(word);
            }
    }

    public String removeStopWords(String tweet) {
        nTweet = new StringBuilder();
        Scanner tweetScanner = new Scanner(tweet);
        String s;

        while (tweetScanner.hasNext()) {
            s = tweetScanner.next();
            checkStopWords(s);
        }
        tweetScanner.close();
        return nTweet.toString();
    }
}

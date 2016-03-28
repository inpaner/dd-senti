package sentimentanalyzer.renzapi;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class RenzApi {
    
    private static Map<String, Double> positiveWords;
    private static Map<String, Double> negativeWords;
    
    public RenzApi() {
    	initialize();
    }
    
    private void initialize() {
    	positiveWords = new HashMap<>();
        negativeWords = new HashMap<>();
        
        
        try {
            Scanner positiveScanner = new Scanner(new File("renzmodel/Positive.txt"));
            while (positiveScanner.hasNextLine()) {
                Scanner positiveLine = new Scanner(positiveScanner.nextLine());
                String word = positiveLine.next();
                Double score = Double.parseDouble(positiveLine.next());
                positiveWords.put(word, score);
                
                positiveLine.close();
            }
            positiveScanner.close();
            
            Scanner negativeScanner = new Scanner(new File("renzmodel/Negative.txt"));
            while (negativeScanner.hasNextLine()) {
                Scanner negativeLine = new Scanner(negativeScanner.nextLine());
                String word = negativeLine.next();
                Double score = Double.parseDouble(negativeLine.next());
                negativeWords.put(word, score);
                negativeLine.close();
            }
            negativeScanner.close();
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    private double positive;
    private double negative;
    private ArrayList<Double> posScoreList = new ArrayList<>();
    private ArrayList<Double> negScoreList = new ArrayList<>();

    private void checkWordValue(String word) {
        //Read Line ( [word] [weight / value] )
        boolean wordIsSeenPos = false;
        boolean wordIsSeenNeg = false;
        
        if (positiveWords.containsKey(word)) {
            posScoreList.add(positiveWords.get(word)); //add all positive scores to arrayList
            wordIsSeenPos = true;
        }
        
        if (negativeWords.containsKey(word)) {
            negScoreList.add(negativeWords.get(word)); //add all negative scores to arrayList
            wordIsSeenNeg = true;
        }
            
        //assign 0.0001 as score to word if the word does not exist in the trained data set
        if (wordIsSeenPos == false) {
            posScoreList.add(0.0001);
        }

        if (wordIsSeenNeg == false) {
            negScoreList.add(0.0001);
        }
    }

    
    public int scoreTweet(String tweet) {
        positive = 0.0;
        negative = 0.0;
        Scanner tweetScanner = new Scanner(tweet);
        String s;

        //Scan through words.
        while (tweetScanner.hasNext()) {
            s = tweetScanner.next();
            checkWordValue(s);
        }
        tweetScanner.close();
        
        positive = getProductPositive();
        negative = getProductNegative();

        //System.out.println("Positive: " + positive + " Negative: " + negative);
        
        
        if (positive > negative) {
            return 1;
        } else if (positive == negative) {
            return 0;
        } else {
            return -1;
        }
    }

    public double getProductPositive() {
        double posProduct = 1.0;
        for (int ctr = 0; ctr < posScoreList.size(); ctr++) {
            posProduct = posProduct * posScoreList.get(ctr);
        }
        return posProduct;
    }

    //returns the product of all negative scores
    public double getProductNegative() {
        double negProduct = 1.0;
        for (int ctr = 0; ctr < negScoreList.size(); ctr++) {
            negProduct = negProduct * negScoreList.get(ctr);
        }
        return negProduct;
    }
}

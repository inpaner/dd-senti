package SentimentAnalyzer;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class StanfordSA {
	
	StanfordCoreNLP pipeline; 
	
	public String getSentiment(String text){
		
		String sentiment = null;
		Annotation annotation;
		System.out.println("Annotating...");
	    annotation = new Annotation(text);

	    pipeline.annotate(annotation);

	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	    
	    for (CoreMap sentence : sentences) {
	      sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	      //System.out.println(sentiment + "\t" + sentence);
	    }
	    
	    return sentiment;
//	    PrintWriter out = new PrintWriter(System.out);
//	    pipeline.prettyPrint(annotation, out);
	}
 
	
	public StanfordSA(){
	    System.out.println("Initializing...");
	    Properties props = new Properties();
	    props.put("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
	    
		pipeline = new StanfordCoreNLP(props);
	}
	
	public static void main(String args[]){
	  StanfordSA SA = new StanfordSA();
	  //input is in per tweet
	  String tweet = "He is a happy person.";
	  System.out.println(SA.getSentiment(tweet));
	}
}

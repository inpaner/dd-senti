package sentimentanalyzer;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

public class StanfordSa implements SentiAnalyzerI {
	private final String ANALYZER_NAME = "Stanford NLP";
	private StanfordCoreNLP stanfordApi; 
	
	
	public static void main(String args[]){

	}

	
	private void test() {
		String tweet = "He is a happy person.";
		System.out.println(this.getSentiment(tweet));
	}
	
	
	StanfordSa(){
	    Properties props = new Properties();
	    props.put("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
		stanfordApi = new StanfordCoreNLP(props);
	}


	public SaResult getSentiment(String text){
		Annotation annotation = new Annotation(text);
	    stanfordApi.annotate(annotation);
	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);    
	    String sentimentStr = "";
	    for (CoreMap sentence : sentences) {
	    	sentimentStr = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	    }
	    Sentiment sentiment = Sentiment.getSentiment(sentimentStr);
	    return new SaResult(sentiment, 0.0, text);
	}
 
	
	@Override
	public String getAnalyzerName() {
		return ANALYZER_NAME;
	}
}

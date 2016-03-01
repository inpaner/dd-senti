package sentimentanalyzer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnSa implements SentiAnalyzerI {
	private final String ANALYZER_NAME = "Monkey Learn";
	private final String API_KEY = "14398e19f54c7f93a4d3ffaaa303c7985e92ac55";
	private final String API_MODULE_ID = "cl_aHGbA5bY";
	private MonkeyLearn ml;
	
	
	public MonkeyLearnSa(){
		ml = new MonkeyLearn(API_KEY);
	}
	
	
	public MonkeyLearnSa(String apiKey){
		ml = new MonkeyLearn(apiKey);
	}
	
	
	public JSONArray getSentiment(String[] textList){
		MonkeyLearnResponse res = null;
		try {
			res = ml.classifiers.classify(API_MODULE_ID, textList);
		} catch (MonkeyLearnException e) {
			e.printStackTrace();
		}
		return res.arrayResult;
	}
	
	
	public void printJSONArr(JSONArray arr){
		for(int i = 0; i < arr.size(); i++){
			JSONArray sentArr = (JSONArray)arr.get(i);
			JSONObject arr1 = (JSONObject)sentArr.get(0);
			System.out.println("Probability: " + arr1.get("probability") + "  Sentiment: " + arr1.get("label"));
		}
	}
	
	
    public static void main( String[] args ) throws MonkeyLearnException {
    	long start = System.currentTimeMillis();
    	new MonkeyLearnSa().test();
    	long end = System.currentTimeMillis();
    	System.out.println(end - start);
    	
    	start = System.currentTimeMillis();
    	new MonkeyLearnSa().testSeparate();
    	end = System.currentTimeMillis();
    	System.out.println(end - start);
    }

    
    private void test() {
    	//sample tweets
        String[] textList = {
        		"thanks to all the haters up in my face all day! 112-102",
        		"very sad about iran.",
        		"boring   ): whats wrong with him??     please tell me........   :-/",
        		"i didn't realize it was that deep. geez give a girl a warning atleast!",
        		"thank god today is friday !!! love ti! ti!"
        };
        
        //generate sentiment
        JSONArray result = this.getSentiment(textList);
        this.printJSONArr(result);
    }

    
    private void testSeparate() {
    	// sample tweets
        String[] textList = {
        		"thanks to all the haters up in my face all day! 112-102",
        		"very sad about iran.",
        		"boring   ): whats wrong with him??     please tell me........   :-/",
        		"i didn't realize it was that deep. geez give a girl a warning atleast!",
        		"thank god today is friday !!! love ti! ti!"
        };
        
        // generate sentiment
        for (String text : textList) {
        	System.out.println( this.getSentiment(text) );
        }
    }
    
    
	@Override
	public SaResult getSentiment(String text) {
		MonkeyLearnResponse result = null;
		String[] textList = {text}; // cute!
		
		try {
			result = ml.classifiers.classify(API_MODULE_ID, textList);
		} catch (MonkeyLearnException e) {
			e.printStackTrace();
		}
		JSONArray arrayResult = result.arrayResult;
		JSONArray sentimentArray = (JSONArray) arrayResult.get(0);
		JSONObject sentimentJson = (JSONObject) sentimentArray.get(0);
		
		Double score = Double.valueOf( sentimentJson.get("probability").toString() );
		Sentiment sentiment = Sentiment.getSentiment( sentimentJson.get("label").toString() );
		return new SaResult(sentiment, score);
	}

	
	@Override
	public String getAnalyzerName() {
		return ANALYZER_NAME;
	}
}

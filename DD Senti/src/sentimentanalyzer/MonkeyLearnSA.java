package sentimentanalyzer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.monkeylearn.MonkeyLearn;
import com.monkeylearn.MonkeyLearnException;
import com.monkeylearn.MonkeyLearnResponse;

public class MonkeyLearnSA {
	
	//Use the API key from your account
	private String apiKey = "14398e19f54c7f93a4d3ffaaa303c7985e92ac55";
	//Module for sentiment analysis
	private String apiModuleId = "cl_aHGbA5bY";
	private MonkeyLearn ml;
	
	public MonkeyLearnSA(){
		ml = new MonkeyLearn(this.apiKey);
	}
	
	public MonkeyLearnSA(String apiKey){
		ml = new MonkeyLearn(apiKey);
	}
	
	public JSONArray getSentiment(String[] textList){
		MonkeyLearnResponse res = null;
		try {
			res = ml.classifiers.classify(apiModuleId, textList);
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

        MonkeyLearnSA mlsa = new MonkeyLearnSA();
        
        //sample tweets
        String[] textList = {
        		"thanks to all the haters up in my face all day! 112-102",
        		"very sad about iran.",
        		"boring   ): whats wrong with him??     please tell me........   :-/",
        		"i didn't realize it was that deep. geez give a girl a warning atleast!",
        		"thank god today is friday !!! love ti! ti!"
        };
        //generate sentiment
        JSONArray result = mlsa.getSentiment(textList);
        mlsa.printJSONArr(result);
    }
}

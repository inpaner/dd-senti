package sentimentanalyzer;

import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		StanfordSa stanfordSa = new StanfordSa();
		MonkeyLearnSa monkeySa = new MonkeyLearnSa();
		AlchemySa alchemySa = new AlchemySa();
		
		FileIO io = new FileIO();
		String inFile =  "src/sentimentanalyzer/SampleTweet";
		String sOutFile = "src/sentimentanalyzer/stanfordresult.csv";
		String mOutFile = "src/sentimentanalyzer/monkeylearnresult.csv";
		String aOutFile = "src/sentimentanalyzer/alchemyresult.csv";
		
		String[] tweets = io.readTweet(inFile);
		//might store the raw tweet to SaResult
		ArrayList<SaResult> sSentiment = new ArrayList<SaResult>();
		ArrayList<SaResult> mSentiment = new ArrayList<SaResult>();
		ArrayList<SaResult> aSentiment = new ArrayList<SaResult>();
		
		int i = 0;
		for(String s: tweets){
			sSentiment.add(stanfordSa.getSentiment(s));
			mSentiment.add(monkeySa.getSentiment(s));
			aSentiment.add(alchemySa.getSentiment(s));
			System.out.println(i + " " + s);
			i++;
		}
		
		io.writeResult(sSentiment, sOutFile);
		io.writeResult(mSentiment, mOutFile);
		io.writeResult(aSentiment, aOutFile);
		
	}
}

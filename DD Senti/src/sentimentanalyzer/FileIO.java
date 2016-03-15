package sentimentanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class FileIO {
	
	public static void main(String[] args){
//		FileIO io = new FileIO();
//		String[] tweets = io.readTweet("src/sentimentanalyzer/SampleTweet");
//		
//		for(String s: tweets)
//			System.out.println(s);
//		
//		String[] a = {"ab", "bbbbB", "zzzzz", "qwewew"};
//		String[] b = {"2131321", "1122", "0000", "222211"};
//		
//		String filepath = "src/sentimentanalyzer/result.csv";
//		io.writeResult(a, b, filepath);
		
	}
	
	public String[] readTweet(String filepath){
		FileReader fr;
		ArrayList<String> arrTweet = new ArrayList<String>();
		
		try {
			fr = new FileReader(filepath);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null) {
				arrTweet.add(s);
			}
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return arrTweet.toArray(new String[0]);
	}
	
	public void writeResult(ArrayList<SaResult> result, String filepath){
		FileWriter fw;	
		boolean alreadyExists = new File(filepath).exists();
		
		if(alreadyExists){
			File file = new File(filepath);
			try {
				Files.deleteIfExists(file.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			fw = new FileWriter(filepath, true);
			for(int i = 0; i < result.size(); i++)
				fw.write(result.get(i).getSentiment() + "," + result.get(i).getTweet() + "\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}

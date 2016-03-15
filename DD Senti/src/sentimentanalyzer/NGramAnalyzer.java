package sentimentanalyzer;

import java.util.*;

public class NGramAnalyzer {
	String[] tweets;
	
	public NGramAnalyzer(String[] t) {
		tweets = t;
	}
	
	public static void main(String[] args){
		FileIO io = new FileIO();
		String[] tweets = io.readTweet("src/sentimentanalyzer/SampleTweet");
		
		NGramAnalyzer analyzer = new NGramAnalyzer(tweets);
		Map<String, Integer> output = analyzer.NGram(4);
		analyzer.printMap(output);
	}
	
	public Map<String, Integer> NGram(int n) {
		HashMap<String, Integer> ngramMap = new HashMap<String, Integer>();
		
		for (String s : tweets) {
			String[] split = s.split(" +");
			
			if (split.length < n)
				continue;
			
			for (int i = 0; i < split.length-(n-1); i++) {
				// i, i+1
				String ngram = split[i];
				
				for (int j = 1; j < n; j++)
					ngram += " " + split[i+j];
				
				if (ngramMap.containsKey(ngram))
					ngramMap.put(ngram, ngramMap.get(ngram) + 1);
				else
					ngramMap.put(ngram, 1);
			}
		}
		
		Map<String, Integer> sorted = MapUtil.sortByValue(ngramMap);
		
		return sorted;
	}
	
	public void printMap(Map<String, Integer> map) {
		for (Map.Entry entry : map.entrySet())
		    System.out.println(entry.getKey() + ", " + entry.getValue());
	}

}

class MapUtil
{
    public static <K, V extends Comparable<? super V>> Map<K, V> 
        sortByValue( Map<K, V> map )
    {
        List<Map.Entry<K, V>> list =
            new LinkedList<Map.Entry<K, V>>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>()
        {
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return -(o1.getValue()).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list)
        {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }
}
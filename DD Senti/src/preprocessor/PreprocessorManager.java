package preprocessor;

import edu.stanford.nlp.util.StringUtils;

// Serves as driver for preprocessor
public class PreprocessorManager {
	
	/* Pre-processing steps:
	 * 1. Remove URL, Mentions, Retweet Identifier "RT", Symbols (!, @, $, #, etc.)
	 * 2. Fix repeating letters in words
	 * 3. Lemmatize words
	 * 4. Remove Stop Words
	 */
	
	private StanfordLemmatizer slemmatizer;
	private StopWordsRemover swRemover;
	
	public PreprocessorManager () {
		//Initialize APIs
		slemmatizer = new StanfordLemmatizer();
		swRemover = new StopWordsRemover();
	}
	
	public String preprocess(String line) {
		line = line.toLowerCase();
		line = tweetCleaner(line);
		line = removeRepeatingLetters(line);
		line = lemmatizer(line);		
		line = swRemover.removeStopWords(line);
		line = symbolsRemover(line);
		
		return line;
	}
	
	// Removes urls, @mention, whitespaces, symbols and numbers
	private String tweetCleaner(String line) {
		return line.replaceAll("((www\\.[\\s]+)|(https?://[^\\s]+)|(http?://[^\\s]+))", "")
				.replaceAll("[\\s]+", " ")
				.replaceAll("@[^\\s]+", "")
				.replace("rt", "")
				.replace("[", "")
				.replace("]", "")
				.replace("\\", "")
				.replaceAll("[�]", "");
	}
	
	// Once everything is done, remove all symbols
	private String symbolsRemover(String line) {
		return line.replaceAll("[@&)+-=$!/?#(*:;'´~`.0-9%><“”…|^_]", "")
				   .replaceAll("\\s+", " ");
	}
	
	
	// Combines linked list of lemmas into one string
	private String lemmatizer(String line) {
		return StringUtils.join(slemmatizer.lemmatize(line), " ");
	}
	
	
	// Removes letters that repeats in a word (e.g. llloooovvveee = love)
	private String removeRepeatingLetters(String line) {
		return line.replaceAll("(.)\\1\\1+", "$1");
	}
}

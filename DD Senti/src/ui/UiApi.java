package ui;

import java.util.List;

public class UiApi {
	private MainPanel panel = new MainPanel();
	
	public UiApi() {
		MainFrame frame = new MainFrame();
		frame.setPanel(panel);
	}
	
	
	public void addListener(MainPanel.Listener listener) {
		panel.addListener(listener);
	}
	
	
	public void addWord(String word) {
		panel.addWord(word);
	}
	
	
	public void addWordsToLeftPanel(List<String> words) {
		panel.addWordToLeftPanel(words);
	}
	
	
	public void addWordsToRightPanel(List<String> words) {
		panel.addWordsToRightPanel(words);
	}
	
	
	public void removeWord(String word) {
		panel.removeWord(word);
	}
	
	
	public void clearAddWordField() {
		panel.clearAddWordField();
	}
}

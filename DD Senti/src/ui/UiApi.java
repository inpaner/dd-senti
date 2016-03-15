package ui;

public class UiApi {
	private MainPanel panel = new MainPanel();
	
	public UiApi() {
		MainFrame frame = new MainFrame();
		frame.setPanel(panel);
	}
	
	
	public void addListener(MainPanel.Listener listener) {
		panel.addListener(listener);
	}
	
	
	void addWord(String word) {
		panel.addWord(word);
	}
	
	
	void removeWord(String word) {
		panel.removeWord(word);
	}
	
	
	void clearAddWordField() {
		panel.clearAddWordField();
	}
}

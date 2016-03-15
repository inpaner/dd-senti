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
	
	
	public void addWord(String word) {
		panel.addWord(word);
	}
	
	
	public void removeWord(String word) {
		panel.removeWord(word);
	}
	
	
	public void clearAddWordField() {
		panel.clearAddWordField();
	}
}

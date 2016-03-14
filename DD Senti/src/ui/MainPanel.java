package ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel {
	private final int ADD_WORD_FIELD_WIDTH = 15;
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		MainPanel panel = new MainPanel();
		frame.setPanel(panel);
	}
	
	public MainPanel() {
		this.initComponents();
	}
	
	
	private void initComponents() {
		this.setLayout(new MigLayout("wrap 3"));
		
		/* Top Panel */
		JTextField addWordField = new JTextField(ADD_WORD_FIELD_WIDTH);
		JButton addWordBtn = new JButton("Add");
		JPanel topPanel = new JPanel();
		topPanel.add(addWordField);
		topPanel.add(addWordBtn);
		this.add(topPanel, "wrap");
		
		/* Mid Panels */
		// All keywords
		WordlistPanel allWordsPanel = new WordlistPanel();
		allWordsPanel.addWord("hello");
		this.add(allWordsPanel);
		
		// Move buttons
		JButton moveWordLeftBtn = new JButton("<");
		JButton moveWordRightBtn = new JButton(">");
		JPanel moveBtnPanel = new JPanel();
		moveBtnPanel.setLayout(new MigLayout());
		moveBtnPanel.add(moveWordLeftBtn, "wrap");
		moveBtnPanel.add(moveWordRightBtn);
		this.add(moveBtnPanel);
		
		// Keywords to Analyze
		WordlistPanel analyzeWordsPanel = new WordlistPanel();
		analyzeWordsPanel.addWord("digital marketing");
		this.add(analyzeWordsPanel);
		
		/* Bottom panel */
		JButton removeWordBtn = new JButton("Remove");
		this.add(removeWordBtn);
		JButton analyzeBtn = new JButton("Analyze");
		this.add(analyzeBtn, "skip");
		
	}
}

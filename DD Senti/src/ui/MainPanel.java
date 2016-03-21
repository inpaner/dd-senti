package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class MainPanel extends JPanel {
	/**/ private static final long serialVersionUID = 7512659837329002543L;
	private final int ADD_WORD_FIELD_WIDTH = 15;
	private WordlistPanel allWordsPanel;
	private WordlistPanel analyzeWordsPanel;
	private JButton moveWordLeftBtn;
	private JButton moveWordRightBtn;
	private JButton addWordBtn;
	private JButton removeWordBtn;
	private JButton analyzeBtn;
	private List<Listener> listeners = new ArrayList<>();
	private JTextField addWordField;
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		MainPanel panel = new MainPanel();
		frame.setPanel(panel);
	}
	
	
	MainPanel() {
		this.initComponents();
		this.initListeners();
	}
	
	
	private void initComponents() {
		this.setLayout(new MigLayout("wrap 3"));
		
		addWordField = new JTextField(ADD_WORD_FIELD_WIDTH);
		addWordBtn = new JButton("Add");
		JPanel topPanel = new JPanel();
		topPanel.add(addWordField);
		topPanel.add(addWordBtn);
		this.add(topPanel, "wrap");
		
		/* Middle Panel */
		// All words
		allWordsPanel = new WordlistPanel();
		this.add(allWordsPanel);
		
		// Move buttons
		moveWordLeftBtn = new JButton("<");
		moveWordRightBtn = new JButton(">");
		
		JPanel moveBtnPanel = new JPanel();
		moveBtnPanel.setLayout(new MigLayout());
		moveBtnPanel.add(moveWordLeftBtn, "wrap");
		moveBtnPanel.add(moveWordRightBtn);
		this.add(moveBtnPanel);
		
		// Analyze words
		analyzeWordsPanel = new WordlistPanel();
		
		this.add(analyzeWordsPanel);
		
		removeWordBtn = new JButton("Remove");
		this.add(removeWordBtn);
		analyzeBtn = new JButton("Analyze");
		this.add(analyzeBtn, "skip");
	}
	
	
	private void initListeners() {
		addWordBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = addWordField.getText();
				for (Listener listener : listeners) {
					listener.addWord(word);
				}
			}
		});
		
		moveWordLeftBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedWord = analyzeWordsPanel.getSelectedWord();
				if (selectedWord.isEmpty()) {
					return;
				}
				analyzeWordsPanel.removeWord(selectedWord);
				allWordsPanel.addWord(selectedWord);
			}
		});
		
		moveWordRightBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedWord = allWordsPanel.getSelectedWord();
				if (selectedWord.isEmpty()) {
					return;
				}
				allWordsPanel.removeWord(selectedWord);
				analyzeWordsPanel.addWord(selectedWord);
			}
		});
		
		removeWordBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String word = allWordsPanel.getSelectedWord();
				for (Listener listener : listeners) {
					listener.removeWord(word);
				}
			}
		});
		
		analyzeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<String> words = analyzeWordsPanel.getAllWords();
				for (Listener listener : listeners) {
					listener.analyzeWords(words);
				}
			}
		});
		
	}
	
	
	void addWord(String word) {
		allWordsPanel.addWord(word);
	}
	
	
	void addWordToLeftPanel(List<String> words) {
		allWordsPanel.addWords(words);
	}
	
	
	void addWordsToRightPanel(List<String> words) {
		analyzeWordsPanel.addWords(words);
	}
	
	
	void removeWord(String word) {
		allWordsPanel.removeWord(word);
	}
	
	
	void clearAddWordField() {
		addWordField.setText("");
	}
	
	
	void addListener(Listener listener) {
		listeners.add(listener);
	}
	
	
	public interface Listener {
		public void addWord(String word);
		public void removeWord(String word);
		public void analyzeWords(List<String> words);
	}
}

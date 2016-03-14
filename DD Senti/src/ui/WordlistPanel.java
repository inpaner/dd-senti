package ui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

public class WordlistPanel extends JPanel {
	/**/ private static final long serialVersionUID = 306918208695614659L;
	private JTable table; 
	private DefaultTableModel model;
	private final List<String> wordCache = new ArrayList<>();
	private final int LIST_WIDTH = 200;
	private final int LIST_HEIGHT = 300;
	
	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		WordlistPanel panel = new WordlistPanel();
		frame.setPanel(panel);
		panel.addWord("hello");
		panel.addWord("alpha");
		panel.addWord("bravo");
		panel.removeWord("alpha");
	}
	
	public WordlistPanel() {
		this.initComponents();
	}
	
	
	private void initComponents() {
		this.setLayout(new MigLayout("wrap 1"));
		model = new DefaultTableModel(0, 1);
		table = new JTable(model);
		table.setTableHeader(null);
		table.getColumnModel().getColumn(0).setWidth(LIST_WIDTH);
		
		TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
		table.setRowSorter(trs);
		trs.setSortsOnUpdates(true);
		this.add(new JScrollPane(table));
		
		this.setPreferredSize(new Dimension(LIST_WIDTH, LIST_HEIGHT));
	}
	
	
	void addWord(String word) {
		word = word.trim().toLowerCase();
		if (wordCache.contains(word)) {
			return;
		}
		wordCache.add(word);
		model.addRow(new String[]{word});
	}
	
	
	void removeWord(String word) {
		word = word.trim().toLowerCase();
		if (!wordCache.contains(word)) {
			return;
		}
		wordCache.remove(word);
		int index = this.getWordIndex(word);
		model.removeRow(index);
	}
	
	
	String getSelected() {
		String selected = "";
		int row = table.getSelectedRow();
		if (row != -1) {
			selected = (String) table.getValueAt(row, 0);
		}
		return selected;
	}
	
	
	private int getWordIndex(String word) {
		int index = -1;
		for (int i = 0; i < model.getRowCount(); i++) {
			String rowWord = (String) model.getValueAt(i, 0);
			if (word.equals(rowWord)) {
				index = i;
				break;
			}
		}
		return index;
	}
}

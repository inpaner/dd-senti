package ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@SuppressWarnings("serial")
class MainFrame extends JFrame {
	
	public static final Dimension HeaderDimension = new Dimension(600, 30);
	public static final Dimension FooterDimension = new Dimension(600, 30);
	public static final Dimension FrameDimension = new Dimension(600, 550);
	
	
	private final int WIDTH = 640;
	private final int HEIGHT = 480;
	private JComponent component;
	
    public MainFrame() {
        final String look = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(look);
                    UIManager.getDefaults().put("TextArea.font", UIManager.getFont("TextField.font"));
                } 
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        setTitle("DD Senti");
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
    }

    
    public void closeWindow() {
        WindowEvent event = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
    }

    
    public void setPanel(final JComponent component) {
        this.getContentPane().removeAll();
        this.component = component;
        Thread controller = new setThread();
        controller.start();
    }
    
    
    private class setThread extends Thread {
        @Override
        public void run() {
            SwingUtilities.invokeLater(new setInvoke());
        }
    }

    
    private class setInvoke implements Runnable {
        @Override
        public void run() {
            getContentPane().add(component);
            invalidate();
            validate();
            pack();
            setLocationRelativeTo(null);
            repaint();
        }
    }
}

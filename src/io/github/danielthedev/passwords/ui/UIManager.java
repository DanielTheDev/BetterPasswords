package io.github.danielthedev.passwords.ui;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class UIManager {

	private JFrame currentFrame;

	public void start() {
		SwingUtilities.invokeLater(()->{
			this.currentFrame = new MainWindow();
			this.currentFrame.setLocationRelativeTo(null);
			this.currentFrame.setVisible(true);
		});
	}
	
	public void swap(JFrame jframe) {
		this.currentFrame.dispose();
		this.currentFrame = jframe;
		this.currentFrame.setLocationRelativeTo(null);
		this.currentFrame.setVisible(true);
	}
	
}

package io.github.danielthedev.passwords;

import javax.swing.UnsupportedLookAndFeelException;

import io.github.danielthedev.passwords.ui.UIManager;

public class Main {

	public static UIManager UI = new UIManager();
	
	public static void main(String[] args) {
		for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Windows".equals(info.getName())) {
                try {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}
                break;
            }
        }
		UI.start();
		 
	}
	
}

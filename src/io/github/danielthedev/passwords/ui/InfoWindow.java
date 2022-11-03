package io.github.danielthedev.passwords.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import io.github.danielthedev.passwords.Main;
import io.github.danielthedev.passwords.SecureFile;

public class InfoWindow extends JFrame {
	
    private javax.swing.JButton returnBtn;
    private javax.swing.JPanel list;
    private javax.swing.JScrollPane scrollList;
    
    public InfoWindow(File file) {
    	List<String> hints = new ArrayList<>();
    	try {
			hints.addAll(SecureFile.readPasswordHints(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	returnBtn = new javax.swing.JButton();
    	scrollList = new javax.swing.JScrollPane();
    	list = new javax.swing.JPanel();

    	getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Info " + file.getName());
        setMinimumSize(new java.awt.Dimension(640, 360));
        setSize(new java.awt.Dimension(640, 360));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        returnBtn.setText("Go back");
        returnBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnBtn.setMaximumSize(new java.awt.Dimension(144, 44));
        returnBtn.setMinimumSize(new java.awt.Dimension(144, 44));
        returnBtn.setPreferredSize(new java.awt.Dimension(144, 44));
        returnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            	returnBtnMouseClicked(evt);
            }
        });
        getContentPane().add(Box.createRigidArea(new Dimension(0, 10)));
        getContentPane().add(returnBtn);
        
        scrollList.setBackground(Color.WHITE);
        scrollList.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollList.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollList.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        scrollList.setMaximumSize(new java.awt.Dimension(600, 300));
        scrollList.setMinimumSize(new java.awt.Dimension(600, 300));
        scrollList.setPreferredSize(new java.awt.Dimension(600, 300));

        list.setMaximumSize(new java.awt.Dimension(600, 300));
        list.setMinimumSize(new java.awt.Dimension(600, 300));
        list.setPreferredSize(new java.awt.Dimension(600, 300));
        list.setLayout(new java.awt.GridLayout(hints.size() + 5, 0));

        for(final String hint : hints) {
        	JPanel btnPanel = new JPanel();
			JButton btn = new javax.swing.JButton();
			btn.setText("Show");
			btn.setAlignmentX(FlowLayout.CENTER);
			btn.setPreferredSize(new java.awt.Dimension(500, 30));
			btn.addMouseListener(new java.awt.event.MouseAdapter() {
				private final String spoiler = hint;
				
		        public void mousePressed(java.awt.event.MouseEvent evt) {
		            ((JButton)evt.getSource()).setText(spoiler);
		        }
		        public void mouseReleased(java.awt.event.MouseEvent evt) {
		        	((JButton)evt.getSource()).setText("Show");
		        }
		    });
			btnPanel.setBackground(Color.WHITE);
			btnPanel.add(btn);
			btnPanel.setPreferredSize(new java.awt.Dimension(600, 22));
		    list.add(btnPanel);
		}
        


        scrollList.setViewportView(list);
        list.setBackground(Color.WHITE);

        getContentPane().add(scrollList);

        pack();
    }// </editor-fold>                        

    private void returnBtnMouseClicked(java.awt.event.MouseEvent evt) {                                      
    	Main.UI.swap(new MainWindow());
    }                                     
}

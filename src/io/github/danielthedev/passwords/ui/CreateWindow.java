package io.github.danielthedev.passwords.ui;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;

import io.github.danielthedev.passwords.Main;
import io.github.danielthedev.passwords.Password;
import io.github.danielthedev.passwords.Password.Future;
import io.github.danielthedev.passwords.SecureFile;

public class CreateWindow extends JFrame {

    private javax.swing.JButton addRowBtn;
    private javax.swing.JButton createBtn;
    private javax.swing.JButton returnBtn;
    private javax.swing.JPanel footer;
    private javax.swing.JPanel frame;
    private javax.swing.JPanel list;
    private javax.swing.JScrollPane listscroll;
    private javax.swing.JPanel space;
    private final File file;
    private final List<Password.Future> passwords = new ArrayList();;

	public CreateWindow(File file) {
		this.file = file;
        frame = new javax.swing.JPanel();
        listscroll = new javax.swing.JScrollPane();
        list = new javax.swing.JPanel();
        
        space = new javax.swing.JPanel();
        addRowBtn = new javax.swing.JButton();
        footer = new javax.swing.JPanel();
        createBtn = new javax.swing.JButton();
        returnBtn = new javax.swing.JButton();
        
        setMinimumSize(new java.awt.Dimension(640, 360));
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        frame.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        frame.setLayout(new javax.swing.BoxLayout(frame, javax.swing.BoxLayout.LINE_AXIS));

        listscroll.setBorder(null);
        listscroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listscroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        list.setLayout(new java.awt.GridLayout(10, 0));

        this.addRow();

        space.setPreferredSize(new java.awt.Dimension(100, 20));

        addRowBtn.setText("Add password");
        addRowBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addRowBtnMouseClicked(evt);
            }
        });
        space.add(addRowBtn);

        list.add(space);

        listscroll.setViewportView(list);

        frame.add(listscroll);

        getContentPane().add(frame);

        footer.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 10, 0));

        createBtn.setText("Create");
        createBtn.setPreferredSize(new java.awt.Dimension(256, 22));
        createBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createBtnMouseClicked(evt);
            }
        });
        footer.add(createBtn);

        returnBtn.setText("Return");
        returnBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                returnBtnMouseClicked(evt);
            }
        });
        footer.add(returnBtn);

        getContentPane().add(footer);
        
        pack();
    }// </editor-fold>   
	
	public void addRow() {
        java.awt.FlowLayout flowLayout = new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 5);
        flowLayout.setAlignOnBaseline(true);
	    JPanel row = new javax.swing.JPanel();
	    row.setLayout(flowLayout);
	    
	    JLabel passwordLabel = new JLabel();
	    passwordLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
	    passwordLabel.setText("Password");
	    passwordLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 10));
	    passwordLabel.setMaximumSize(new java.awt.Dimension(10000, 16));
	    row.add(passwordLabel);
	
	    JTextField passwordField = new JTextField();
	    passwordField.setMaximumSize(new java.awt.Dimension(150, 22));
	    passwordField.setMinimumSize(new java.awt.Dimension(150, 22));
	    passwordField.setPreferredSize(new java.awt.Dimension(150, 22));
	    row.add(passwordField);
	
	    JLabel hintLabel = new JLabel();
	    hintLabel.setText("Hint");
	    hintLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 40, 0, 10));
	    hintLabel.setMaximumSize(new java.awt.Dimension(100000, 16));
	    row.add(hintLabel);
	
	    JTextField hintField = new JTextField();
	    
	    hintField.setMaximumSize(new java.awt.Dimension(400, 22));
	    hintField.setMinimumSize(new java.awt.Dimension(270, 22));
	    hintField.setPreferredSize(new java.awt.Dimension(270, 22));
	    row.add(hintField);
	
	    list.remove(space);
	    list.add(row);
	    list.add(space);
	    passwords.add(new Password.Future(passwordField::getText, hintField::getText));
    }

    private void addRowBtnMouseClicked(java.awt.event.MouseEvent evt) {    
    	if(this.passwords.size() > 5) {
    		JOptionPane.showMessageDialog(this, "Maximum passwords reached", "Alert", JOptionPane.ERROR_MESSAGE);
    		return;
    	}
        this.addRow();
        this.revalidate();
    }                                      

    private void createBtnMouseClicked(java.awt.event.MouseEvent evt) {                                       
        List<Password> passwords = this.passwords.stream().map(Future::getPassword).toList();
        SecureFile secureFile = new SecureFile(new JSONObject());
        try {
			secureFile.createFile(file, passwords);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        JOptionPane.showMessageDialog(this, "Succesfully created file", "Alert", JOptionPane.INFORMATION_MESSAGE);
        Main.UI.swap(new MainWindow());
    }                                      

    private void returnBtnMouseClicked(java.awt.event.MouseEvent evt) {                                       
        Main.UI.swap(new MainWindow());
    }  
	
}

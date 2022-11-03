package io.github.danielthedev.passwords.ui;

import java.awt.Color;
import java.io.File;

import javax.swing.JFrame;

import io.github.danielthedev.passwords.Files;
import io.github.danielthedev.passwords.Main;

public class MainWindow extends JFrame {

    private javax.swing.JButton createBtn;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton infoBtn;
	
    
    public MainWindow() {
        createBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        infoBtn = new javax.swing.JButton();

        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Encrypted Password File");
        setResizable(false);
        getContentPane().setLayout(new java.awt.FlowLayout());

        createBtn.setText("Create");
        createBtn.setMaximumSize(new java.awt.Dimension(144, 44));
        createBtn.setMinimumSize(new java.awt.Dimension(144, 44));
        createBtn.setPreferredSize(new java.awt.Dimension(144, 44));
        createBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                createBtnMouseClicked(evt);
            }
        });
        getContentPane().add(createBtn);

        editBtn.setText("Edit");
        editBtn.setMaximumSize(new java.awt.Dimension(144, 44));
        editBtn.setMinimumSize(new java.awt.Dimension(144, 44));
        editBtn.setPreferredSize(new java.awt.Dimension(144, 44));
        editBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editBtnMouseClicked(evt);
            }
        });
        getContentPane().add(editBtn);

        infoBtn.setText("Info");
        infoBtn.setMaximumSize(new java.awt.Dimension(144, 44));
        infoBtn.setMinimumSize(new java.awt.Dimension(144, 44));
        infoBtn.setPreferredSize(new java.awt.Dimension(144, 44));
        infoBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoBtnMouseClicked(evt);
            }
        });
        getContentPane().add(infoBtn);
        pack();
    }// </editor-fold>                        

    private void createBtnMouseClicked(java.awt.event.MouseEvent evt) {                                       
        File file = Files.createFile();
        if(file == null) return;
        Main.UI.swap(new CreateWindow(file));
    }                                      

    private void editBtnMouseClicked(java.awt.event.MouseEvent evt) {                                     
    	File file = Files.selectFile();
    	if(file == null) return;
    }                                    

    private void infoBtnMouseClicked(java.awt.event.MouseEvent evt) {                                     
    	File file = Files.selectFile();
    	if(file == null) return;
    	Main.UI.swap(new InfoWindow(file));
    }   
}

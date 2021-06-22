package com.zombiecastlerush.util;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

class UserInterface {

    final static boolean shouldFill = true;
    final static boolean shouldWeightX = true;
    final static boolean RIGHT_TO_LEFT = false;

    static JFrame gameWindow;
    static Container container;
    static Font titleFont = new Font("Impact", Font.PLAIN, 70);
    static Font normalFont = new Font("Impact", Font.PLAIN, 16);
    static JTextPane titlePane;
    static JTextField nameField;
    static JButton newGameButton;

    NewGameHandler ngHandler = new NewGameHandler();

    public static void main(String[] args) {
        new UserInterface();
    }

    public UserInterface() {

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        gameWindow = new JFrame();
        gameWindow.setPreferredSize(new Dimension(800, 700));
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.getContentPane().setBackground(Color.blue);
        gameWindow.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        container = gameWindow.getContentPane();

        titlePane = new JTextPane();
        titlePane.setText("ZOMBIE CASTLE RUSH");
        titlePane.setBackground(Color.black);
        titlePane.setForeground(Color.green);
        titlePane.setFont(titleFont);
        StyledDocument doc = titlePane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.weighty = 0.2;
        c.insets = new Insets(10, 30, 60, 30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        container.add(titlePane, c);
        titlePane.setEditable(false);

        nameField = new JTextField("Enter your name");
        nameField.setFont(normalFont);
        nameField.setBackground(Color.black);
        nameField.setForeground(Color.GREEN);
        nameField.setForeground(Color.gray);
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Enter your name")) {
                    nameField.setText("");
                    nameField.setForeground(Color.green);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setForeground(Color.gray);
                    nameField.setText("Enter your name");
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 300, 30, 300);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 1;
        container.add(nameField, c);

        newGameButton = new JButton("NEW GAME");
        newGameButton.setFont(normalFont);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 1.0;
        c.weightx = 0.0;
        c.insets = new Insets(10, 300, 300, 300);
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 2;
        newGameButton.addActionListener(ngHandler);
        container.add(newGameButton, c);

        gameWindow.pack();
        container.requestFocusInWindow();
        gameWindow.setVisible(true);
    }

    public static void addComponentsToPane(Container pane) {

        titlePane.setVisible(false);
        nameField.setVisible(false);
        newGameButton.setVisible(false);

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        if (RIGHT_TO_LEFT) {
            pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        JButton button;
        JTextField text;
        JTextArea textArea;
        JTextPane textPane;
        pane.setLayout(new GridBagLayout());
        pane.setBackground(Color.blue);
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        textPane = new JTextPane();
        textPane.setText("ZOMBIE CASTLE RUSH");
        textPane.setBackground(Color.black);
        textPane.setForeground(Color.green);
        textPane.setFont(titleFont);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.0;
        c.weighty = 0.2;
        c.insets = new Insets(10, 30, 60, 30);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 5;
        pane.add(textPane, c);
        textPane.setEditable(false);

        textArea = new JTextArea("Combat Text Area");
        textArea.setFont(normalFont);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.green);
        textArea.setPreferredSize(new Dimension (350, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        pane.add(textArea, c);
        textArea.setEditable(false);

        textArea = new JTextArea("Status Text area");
        textArea.setFont(normalFont);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.green);
        textArea.setPreferredSize(new Dimension (350, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 2;
        pane.add(textArea, c);
        textArea.setEditable(false);

        textArea = new JTextArea("Description Text area");
        textArea.setFont(normalFont);
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.green);
        textArea.setPreferredSize(new Dimension (700, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        pane.add(textArea, c);
        textArea.setEditable(false);

        text = new JTextField("Input Command");
        text.setFont(normalFont);
        text.setBackground(Color.black);
        text.setForeground(Color.green);
        text.setPreferredSize(new Dimension(200, 20));
        text.setForeground(Color.gray);
        text.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (text.getText().equals("Input Command")) {
                    text.setText("");
                    text.setForeground(Color.green);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (text.getText().isEmpty()) {
                    text.setForeground(Color.gray);
                    text.setText("Input Command");
                }
            }
        });
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END; //bottom of space
        c.insets = new Insets(10, 10, 10, 10);  //top padding
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 3;
        pane.add(text, c);

        button = new JButton("SUBMIT");
        button.setFont(normalFont);
        button.setPreferredSize(new Dimension(100, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 4;
        c.gridwidth = 1;
        c.gridy = 3;
        pane.add(button, c);
    }

    private static void createAndShowGUI() {
        //Set up the content pane.
        addComponentsToPane(gameWindow.getContentPane());

        //Display the window.
        gameWindow.pack();
        gameWindow.setVisible(true);
    }

    public class NewGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });

        }
    }
}
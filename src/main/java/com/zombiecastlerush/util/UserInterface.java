package com.zombiecastlerush.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zombiecastlerush.building.Castle;
import com.zombiecastlerush.entity.Player;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

import static java.awt.event.KeyEvent.VK_ENTER;

public class UserInterface {

    private final static boolean shouldFill = true;
    private final static boolean shouldWeightX = true;
    private final static boolean RIGHT_TO_LEFT = false;

    private static UserInterface userInterface;
    static JFrame gameWindow;
    private static Container container;
    private static Font titleFont = new Font("Impact", Font.PLAIN, 70);
    private static Font normalFont = new Font("Monospace", Font.PLAIN, 16);
    private static JTextPane titlePane;
    private static JTextField nameField;
    private static JButton newGameButton;
    public static JButton submitButton;
    public static JTextField playerInput;
    public static JTextArea combatTextArea;
    public static JTextArea playerTextArea;
    public static JTextArea descTextArea;
    private static JTextPane textPane;
    private static Player player = Game.player;
    private static Castle castle = Game.castle;
    private static Game game = Game.game;

    NewGameHandler ngHandler = new NewGameHandler();
    PlayerActionHandler paHandler = new PlayerActionHandler();

    private UserInterface() {
    }

    public static UserInterface getInstance() {
        if (UserInterface.userInterface == null){
            UserInterface.userInterface = new UserInterface();
        }
        return UserInterface.userInterface;
    }

    public void startUI() {

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
        nameField.setHorizontalAlignment(JTextField.CENTER);
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

        combatTextArea = new JTextArea("Combat Text Area");
        combatTextArea.setFont(normalFont);
        combatTextArea.setBackground(Color.black);
        combatTextArea.setForeground(Color.green);
        combatTextArea.setPreferredSize(new Dimension (350, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        pane.add(combatTextArea, c);
        combatTextArea.setEditable(false);

        playerTextArea = new JTextArea("Player Name: " + player.getName()
                                + "\nHit Points: " + player.getHealth()
                                + "\nInventory: " + player.getInventory());
        playerTextArea.setFont(normalFont);
        playerTextArea.setBackground(Color.black);
        playerTextArea.setForeground(Color.green);
        playerTextArea.setPreferredSize(new Dimension (350, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 4;
        c.gridy = 1;
        c.gridwidth = 2;
        pane.add(playerTextArea, c);
        playerTextArea.setEditable(false);

        descTextArea = new JTextArea();
        descTextArea.setLineWrap(true);
//        descTextArea.setText(Prompter.displayCurrentScene(player));
        descTextArea.setFont(normalFont);
        descTextArea.setBackground(Color.black);
        descTextArea.setForeground(Color.green);
        descTextArea.setPreferredSize(new Dimension (700, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 5;
        pane.add(descTextArea, c);
        descTextArea.setEditable(false);

        playerInput = new JTextField("Input Command");
        playerInput.setFont(normalFont);
        playerInput.setBackground(Color.black);
        playerInput.setForeground(Color.green);
        playerInput.setPreferredSize(new Dimension(200, 20));
        playerInput.setForeground(Color.gray);
        playerInput.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (playerInput.getText().equals("Input Command")) {
                    playerInput.setText("");
                    playerInput.setForeground(Color.green);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (playerInput.getText().isEmpty()) {
                    playerInput.setForeground(Color.gray);
                    playerInput.setText("Input Command");
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
        pane.add(playerInput, c);

        submitButton = new JButton("SUBMIT");
        submitButton.setFont(normalFont);
        submitButton.setPreferredSize(new Dimension(100, 20));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = 0;
        c.weighty = 1.0;
        c.anchor = GridBagConstraints.PAGE_END;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridx = 4;
        c.gridwidth = 1;
        c.gridy = 3;
        submitButton.addActionListener(userInterface.paHandler);
        pane.add(submitButton, c);
    }

    private static void createAndShowGUI() {
        //Set up the content pane.
        addComponentsToPane(gameWindow.getContentPane());

        //Display the window.
        gameWindow.pack();
        gameWindow.setVisible(true);
    }

    private static boolean checkBlankField() {
        StringBuilder errorBlank = new StringBuilder();
        String defInput = "Enter your name";

        if (defInput.equals(nameField.getText())) {
            nameField.setBackground(Color.red);
            errorBlank.append("Please enter your name");
            Frame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, errorBlank, "Name Required", JOptionPane.WARNING_MESSAGE);
        }
        return errorBlank.length() == 0;
    }

    public class NewGameHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {

            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    boolean valid = checkBlankField();
                    if (valid) {
                        String playerName = nameField.getText();
                        player = new Player(playerName);
                        player.setCurrentPosition(castle.getCastleRooms().get("Castle-Hall"));

                        Frame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, game.printInstructions(), "Game Instructions", JOptionPane.QUESTION_MESSAGE);
                        createAndShowGUI();
                    }
                }
            });
        }
    }

    public class PlayerActionHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            try {
                Prompter.advanceGame(player);
            }
            catch (JsonProcessingException e) {
                System.out.println("Exception advancing game");
            }
        }
    }
}
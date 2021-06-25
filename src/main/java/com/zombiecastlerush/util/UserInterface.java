package com.zombiecastlerush.util;

import com.zombiecastlerush.building.*;
import com.zombiecastlerush.entity.Enemy;
import com.zombiecastlerush.entity.Player;
import com.zombiecastlerush.entity.Role;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/*
 * Class to implement GUI. Game is controlled by buttons, includes creation of
 * text fields, buttons, and all action listeners.
 */

public class UserInterface {

    private final static boolean shouldFill = true;
    private final static boolean RIGHT_TO_LEFT = false;
    private static UserInterface userInterface;
    private static JFrame gameWindow;
    private static final Font titleFont = new Font("Impact", Font.PLAIN, 70);
    private static final Font normalFont = new Font("Monospace", Font.PLAIN, 16);
    private static JTextPane titlePane;
    private static JTextField nameField;
    private static JButton newGameButton;
    private static JPanel mainButtonPanel, buttonPanel;
    private static JTextArea eventTextArea, playerTextArea, descTextArea;
    private static Player player = Game.player;
    private static final Castle castle = Game.castle;
    private static final Game game = Game.getInstance();
    private static GridBagConstraints c;
    private static final Combat combat = new Combat("fight me bro");
    private static Role enemy;
    private static final MusicPlayer maestro = Game.maestro;

    // Action Handlers
    private final NewGameHandler ngHandler = new NewGameHandler();
    private final PlayerActionHandler paHandler = new PlayerActionHandler();
    private final GoActionHandler goHandler = new GoActionHandler();
    private final PickUpActionHandler puHandler = new PickUpActionHandler();
    private final DropActionHandler dropHandler = new DropActionHandler();
    private final BuyActionHandler buyHandler = new BuyActionHandler();
    private final SellActionHandler sellHandler = new SellActionHandler();
    private final OtherActionHandler otherHandler = new OtherActionHandler();
    private final SoundActionHandler soundHandler = new SoundActionHandler();

    private UserInterface() {
    }

    public static UserInterface getInstance() {
        if (UserInterface.userInterface == null) {
            UserInterface.userInterface = new UserInterface();
        }
        return UserInterface.userInterface;
    }

    public void startUI() {

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);

        // Game Window, main window for Swing GUI, using GridBagLayout
        gameWindow = new JFrame();
        gameWindow.setPreferredSize(new Dimension(800, 600));
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.getContentPane().setBackground(Color.blue);
        gameWindow.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }
        Container container = gameWindow.getContentPane();

        // NEW GAME SCREEN

        // Title pane displaying Game Title
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

        // Field accepting user input for player name
        nameField = new JTextField("Enter your name");
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setFont(normalFont);
        nameField.setBackground(Color.black);
        nameField.setForeground(Color.GREEN);
        nameField.setForeground(Color.gray);
        // Focus listener requiring user enter name
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

        // new game button to start game
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

    // Method to display main game window once new game is initiated
    public static void addComponentsToPane(Container pane) {
        gameWindow.setPreferredSize(new Dimension(1200, 1000));

        // Initial game fields
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
        c = new GridBagConstraints();
        if (shouldFill) {
            //natural height, maximum width
            c.fill = GridBagConstraints.HORIZONTAL;
        }

        // Music control buttons
        JPanel soundButtonPanel = new JPanel(new GridLayout(1, 4));
        soundButtonPanel.setBackground(Color.blue);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.NORTH;
        c.ipady = 0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 4;
        c.gridheight = 2;
        pane.add(soundButtonPanel, c);

        JButton soundOnButton = new JButton("SOUND ON");
        soundOnButton.setFont(normalFont);
        soundOnButton.setPreferredSize(new Dimension(100, 40));
        soundOnButton.setActionCommand("on");
        soundOnButton.addActionListener(userInterface.soundHandler);
        soundButtonPanel.add(soundOnButton);

        JButton soundOffButton = new JButton("SOUND OFF");
        soundOffButton.setFont(normalFont);
        soundOffButton.setPreferredSize(new Dimension(100, 40));
        soundOffButton.setActionCommand("off");
        soundOffButton.addActionListener(userInterface.soundHandler);
        soundButtonPanel.add(soundOffButton);

        JButton volumeUpButton = new JButton("VOL UP");
        volumeUpButton.setFont(normalFont);
        volumeUpButton.setPreferredSize(new Dimension(100, 40));
        volumeUpButton.setActionCommand("up");
        volumeUpButton.addActionListener(userInterface.soundHandler);
        soundButtonPanel.add(volumeUpButton);

        JButton volumeDownButton = new JButton("VOL DOWN");
        volumeDownButton.setFont(normalFont);
        volumeDownButton.setPreferredSize(new Dimension(100, 40));
        volumeDownButton.setActionCommand("down");
        volumeDownButton.addActionListener(userInterface.soundHandler);
        soundButtonPanel.add(volumeDownButton);

        //Game title
        JTextPane textPane = new JTextPane();
        textPane.setText("ZOMBIE CASTLE RUSH");
        textPane.setBackground(Color.black);
        textPane.setForeground(Color.green);
        textPane.setFont(titleFont);
        StyledDocument doc = textPane.getStyledDocument();
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.0;
        c.weighty = 0.2;
        c.insets = new Insets(50, 30, 10, 30);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 4;
        pane.add(textPane, c);
        textPane.setEditable(false);

        // Events area to display combat logs and roulette results
        eventTextArea = new JTextArea("EVENTS:");
        eventTextArea.setFont(normalFont);
        eventTextArea.setBackground(Color.black);
        eventTextArea.setForeground(Color.green);
        eventTextArea.setPreferredSize(new Dimension(200, 200));
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 2;
        pane.add(eventTextArea, c);
        eventTextArea.setEditable(false);

        // Player Status area
        playerTextArea = new JTextArea();
        updatePlayerInfo();
        playerTextArea.setFont(normalFont);
        playerTextArea.setBackground(Color.black);
        playerTextArea.setForeground(Color.green);
        playerTextArea.setPreferredSize(new Dimension(200, 200));
        c.anchor = GridBagConstraints.LINE_END;
        c.gridx = 2;
        c.gridy = 2;
        c.weightx = 0.5;
        c.weighty = 0.5;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 2;
        c.gridheight = 2;
        pane.add(playerTextArea, c);
        playerTextArea.setEditable(false);

        //Description area displaying room descriptions
        descTextArea = new JTextArea();
        descTextArea.setRows(10);
        descTextArea.setLineWrap(true);
        descTextArea.setWrapStyleWord(true);
        descTextArea.setText(Prompter.displayCurrentScene(player));
        descTextArea.setFont(normalFont);
        descTextArea.setBackground(Color.black);
        descTextArea.setForeground(Color.green);
        descTextArea.setPreferredSize(new Dimension(400, 200));
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 4;
        c.weightx = 0.5;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 4;
        c.gridheight = 2;
        pane.add(descTextArea, c);
        descTextArea.setEditable(false);

        // Main button panel shows button for room actions, generated contextually depending on what room player is in
        mainButtonPanel = new JPanel(new GridLayout(2, 3));
        mainButtonPanel.setBackground(Color.blue);
        mainButtonPanel.setPreferredSize(new Dimension(400, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 6;
        c.ipady = 0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 4;
        c.gridheight = 1;
        pane.add(mainButtonPanel, c);
        // add main buttons to main button panel
        mainButtons();
    }

    // method called by new game handler to create and sho main game GUI
    private static void createAndShowGUI() {
        //Set up the content pane.
        addComponentsToPane(gameWindow.getContentPane());

        //Display the window.
        gameWindow.pack();
        gameWindow.setVisible(true);
    }

    // method used by focus listener to verify user input for name field
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

    // generates buttons for main button panel based on actions available in room
    static void mainButtons() {
        // clear main button panel to prevent duplicate buttons
        mainButtonPanel.removeAll();

        List<String> actionList = Prompter.sceneContextmenu(player.getCurrentPosition(), player);

        actionList.forEach(action -> {
            JButton button = new JButton(action.toUpperCase());
            button.setFont(normalFont);
            button.setPreferredSize(new Dimension(100, 40));
            button.setActionCommand(action.toLowerCase());
            button.addActionListener(userInterface.paHandler);
            mainButtonPanel.add(button);
        });

        // adds spin button if in Tomb
        if (player.getCurrentPosition().getName().equalsIgnoreCase("Tomb")) addSpin();

        // add quit button
        addQuit(mainButtonPanel);

        // make main button panel visible and repaint
        mainButtonPanel.setVisible(true);
        mainButtonPanel.revalidate();
        mainButtonPanel.repaint();
    }

    // generates drop menu buttons based on user inventory
    static void dropMenu(List<Item> items) {
        mainButtonPanel.setVisible(false);

        buttonPanel = newButtonPanel();
        gameWindow.getContentPane().add(buttonPanel, c);

        items.forEach(item -> {
            JButton button = new JButton(item.getName().toUpperCase());
            button.setFont(normalFont);
            button.setPreferredSize(new Dimension(100, 40));
            button.setActionCommand(item.getName());
            button.addActionListener(userInterface.dropHandler);
            buttonPanel.add(button);
        });

        // adds cancel and quit buttons to pane
        addCancelQuit(buttonPanel);

        // switches to this panel
        switchPanel();
    }

    // pick up menu buttons generated based on items in room
    static void puMenu(List<Item> items) {
        mainButtonPanel.setVisible(false);

        buttonPanel = newButtonPanel();
        gameWindow.getContentPane().add(buttonPanel, c);

        items.forEach(item -> {
            JButton button = new JButton(item.getName().toUpperCase());
            button.setFont(normalFont);
            button.setPreferredSize(new Dimension(100, 40));
            button.setActionCommand(item.getName());
            button.addActionListener(userInterface.puHandler);
            buttonPanel.add(button);
        });

        addCancelQuit(buttonPanel);

        switchPanel();
    }

    // go menu buttons created based on players current position
    static void goMenu(List<Room> rooms) {
        buttonPanel = newButtonPanel();
        gameWindow.getContentPane().add(buttonPanel, c);

        rooms.forEach(room -> {
            JButton button = new JButton(room.getName().toUpperCase());
            button.setFont(normalFont);
            button.setPreferredSize(new Dimension(100, 40));
            button.setActionCommand(room.getName().toLowerCase());
            button.addActionListener(userInterface.goHandler);
            buttonPanel.add(button);
        });

        addCancelQuit(buttonPanel);

        switchPanel();
    }

    //buy menu generated based on shop inventory
    public static void buyMenu(List<Item> items) {
        mainButtonPanel.setVisible(false);

        buttonPanel = newButtonPanel();
        gameWindow.getContentPane().add(buttonPanel, c);

        items.forEach(item -> {
            JButton button = new JButton(item.getName().toUpperCase());
            button.setFont(normalFont);
            button.setPreferredSize(new Dimension(100, 40));
            button.setActionCommand(item.getName());
            button.addActionListener(userInterface.buyHandler);
            buttonPanel.add(button);
        });

        addCancelQuit(buttonPanel);
    }

    // sell menu generated based on players inventory
    public static void sellMenu(List<Item> items) {
        mainButtonPanel.setVisible(false);

        buttonPanel = newButtonPanel();
        gameWindow.getContentPane().add(buttonPanel, c);

        items.forEach(item -> {
            JButton button = new JButton(item.getName().toUpperCase());
            button.setFont(normalFont);
            button.setPreferredSize(new Dimension(100, 40));
            button.setActionCommand(item.getName());
            button.addActionListener(userInterface.sellHandler);
            buttonPanel.add(button);
        });

        addCancelQuit(buttonPanel);
    }

    // battle menu displayed when player clicks FIGHT button
    public static void battle() {
        mainButtonPanel.setVisible(false);

        buttonPanel = newButtonPanel();
        gameWindow.getContentPane().add(buttonPanel, c);

        // create monster based on player location
        if (player.getCurrentPosition().getName().equalsIgnoreCase("grave-yard")) {
            enemy = new Enemy("Tomb Robber");
        } else {
            enemy = new Enemy("Zombie");
        }

        // create fight button
        JButton fightButton = new JButton("FIGHT");
        fightButton.setFont(normalFont);
        fightButton.setPreferredSize(new Dimension(100, 40));
        fightButton.setActionCommand("fight");
        fightButton.addActionListener(userInterface.otherHandler);
        buttonPanel.add(fightButton);

        // create run button
        JButton runButton = new JButton("RUN AWAY");
        runButton.setFont(normalFont);
        runButton.setPreferredSize(new Dimension(100, 40));
        runButton.setActionCommand("run");
        runButton.addActionListener(userInterface.otherHandler);
        buttonPanel.add(runButton);

        addCancelQuit(buttonPanel);
    }

    // used to switch away from main button panel on certain methods
    public static void switchPanel() {
        mainButtonPanel.setVisible(false);
        buttonPanel.setVisible(true);
        buttonPanel.revalidate();
        buttonPanel.repaint();
    }

    //creates new button panel to insert buttons on
    public static JPanel newButtonPanel() {
        buttonPanel = new JPanel(new GridLayout(1, 4));
        buttonPanel.setBackground(Color.blue);
        buttonPanel.setPreferredSize(new Dimension(400, 200));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.PAGE_END;
        c.gridx = 0;
        c.gridy = 6;
        c.ipady = 0;
        c.weighty = 1.0;
        c.insets = new Insets(10, 10, 10, 10);
        c.gridwidth = 4;
        c.gridheight = 1;

        return buttonPanel;
    }

    // adds spin button, used when player is in tomb
    public static void addSpin() {
        JButton spinButton = new JButton("SPIN");
        spinButton.setFont(normalFont);
        spinButton.setPreferredSize(new Dimension(100, 40));
        spinButton.setActionCommand("spin");
        spinButton.addActionListener(userInterface.otherHandler);
        mainButtonPanel.add(spinButton);
    }

    // adds both cancel and quit buttons to specified panel
    public static void addCancelQuit(JPanel panel) {
        addCancel(panel);
        addQuit(panel);
    }

    // adds cancel button to specified panel
    public static void addCancel(JPanel panel) {
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setFont(normalFont);
        cancelButton.setPreferredSize(new Dimension(100, 40));
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(userInterface.paHandler);
        panel.add(cancelButton);
    }

    // add quit button to sepcified panel
    public static void addQuit(JPanel panel) {
        JButton quitButton = new JButton("QUIT");
        quitButton.setFont(normalFont);
        quitButton.setPreferredSize(new Dimension(100, 40));
        quitButton.setActionCommand("quit");
        quitButton.addActionListener(userInterface.paHandler);
        panel.add(quitButton);
    }

    // hides buttons panel, shows main buttons
    public static void goMain() {
        buttonPanel.setVisible(false);
        mainButtons();
    }

    // method to update event panel text
    public static void updateEventText(String updateString) {
        eventTextArea.setText("EVENTS:\n" + updateString);
        eventTextArea.revalidate();
        eventTextArea.repaint();
    }

    // method to update player info
    public static void updatePlayerInfo() {
        String playerInfo = "PLAYER INFORMATION :\nPlayer Name: " + player.getName()
                + "\nCurrent Location: " + player.getCurrentPosition()
                + "\nHit Points: " + player.getHealth()
                + "\nCoins: " + player.getAcctBalance()
                + "\nInventory: " + player.getInventory();
        playerTextArea.removeAll();
        playerTextArea.setText(playerInfo);
        playerTextArea.revalidate();
        playerTextArea.repaint();
    }

    // method to prompt user for puzzle solution using a input window pop up
    static void attemptPuzzle(Room room) {
        Frame frame = new JFrame();
        Puzzle puzzle = (Puzzle) room.getChallenge();
        String puzzlePrompt = String.format("Here is your puzzle....Remember you only have %s attempt(s) remaining!\n\n %s", (3 - puzzle.getAttempts()), puzzle.getQuestion());
        String answer = null;
        answer = JOptionPane.showInputDialog(frame, puzzlePrompt, null, JOptionPane.QUESTION_MESSAGE);
        String afterPuzzle = Prompter.solvePuzzle(room, answer);
        descTextArea.setText(afterPuzzle);
        mainButtons();
    }

    // New Game action handler
    public class NewGameHandler implements ActionListener {
        @Override
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

                        createAndShowGUI();
                    }
                }
            });
        }
    }

    // Player Actions action handler
    public class PlayerActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String action = event.getActionCommand();
            Prompter.advanceGame(action, player);
        }
    }

    // go buttons action handler
    public class GoActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();

            // update description area with player movement and new scene, go to main buttons
            descTextArea.setText(player.moveTo(action) + "\n" + Prompter.displayCurrentScene(player));
            updatePlayerInfo();
            updateEventText(CoinGod.chance(player));
            goMain();
        }
    }

    // pick up buttons action handler
    public class PickUpActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemString = e.getActionCommand();
            Item pickUpItem = null;
            List<Item> roomItems = player.getCurrentPosition().getInventory().getItems();
            for (Item roomItem : roomItems) {
                if (itemString.equals(roomItem.getName())) {
                    pickUpItem = roomItem;
                    break;
                }
            }
            if (pickUpItem != null) {
                player.pickUp(pickUpItem);
                try {
                    descTextArea.setText(player.getName() + " picked up the " + pickUpItem.getName() + "!\n\n" + Prompter.displayCurrentScene(player));
                } catch (NullPointerException exception) {
                    descTextArea.setText(player.getName() + " could not pick up the " + pickUpItem.getName() + "!\n\nTry again later!");
                }
            }
            // update player info and go back to main buttons
            updatePlayerInfo();
            goMain();
        }
    }

    // drop buttons action handler
    public class DropActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemString = e.getActionCommand();
            Item dropItem = null;
            List<Item> playerItems = player.getInventory().getItems();
            for (Item playerItem : playerItems) {
                if (itemString.equals(playerItem.getName())) {
                    dropItem = playerItem;
                    break;
                }
            }
            if (dropItem != null) {
                player.drop(dropItem);
                try {
                    descTextArea.setText(player.getName() + " dropped the " + dropItem.getName() + "!\n\n" + Prompter.displayCurrentScene(player));
                } catch (NullPointerException exception) {
                    descTextArea.setText(player.getName() + " could not drop the " + dropItem.getName() + "!\n\nTry again later!");
                }
            }
            // update player info and go main buttons
            updatePlayerInfo();
            goMain();
        }
    }

    // but items action handler
    public class BuyActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemString = e.getActionCommand();
            Room currentRoom = player.getCurrentPosition();
            Item buyItem = null;
            List<Item> roomItems = player.getCurrentPosition().getInventory().getItems();
            for (Item roomItem : roomItems) {
                if (itemString.equals(roomItem.getName())) {
                    buyItem = roomItem;
                    break;
                }
            }
            if (buyItem != null) {
                descTextArea.setText(((Shop) currentRoom).sellItemToPlayer(player, buyItem) + "!\n\n" + Prompter.displayCurrentScene(player));
            }
            // update player info and go to main buttons
            updatePlayerInfo();
            goMain();
        }
    }

    // sell action handler
    public class SellActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String itemString = e.getActionCommand();
            Room currentRoom = player.getCurrentPosition();
            Item sellItem = null;
            List<Item> playerInventory = player.getInventory().getItems();
            for (Item playerItem : playerInventory) {
                if (itemString.equals(playerItem.getName())) {
                    sellItem = playerItem;
                    break;
                }
            }
            if (sellItem != null) {
                descTextArea.setText(((Shop) currentRoom).buyItemFromPlayer(player, sellItem) + "!\n\n" + Prompter.displayCurrentScene(player));
            }

            // update player info and go to main buttons
            updatePlayerInfo();
            goMain();
        }
    }

    // action handler for fight, run, and spin,
    public class OtherActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();
            StringBuilder combatText = new StringBuilder();
            Room currentRoom = player.getCurrentPosition();

            // fight
            if (action.equals("fight")) {
                combatText.append(combat.combat(player, enemy));
                updatePlayerInfo();
                if (enemy.getHealth() <= 0 || player.getHealth() <= 0) {
                    if (player.getHealth() <= 0) {
                        Frame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "You have died... Game Over.");
                        game.stop();
                    }
                }
                if (enemy.getHealth() <= 0) {
                    currentRoom.getChallenge().setCleared(true);
                    if (currentRoom.isExit()) {
                        Frame frame = new JFrame();
                        JOptionPane.showMessageDialog(frame, "You have found the exit, killed the last monster, and beaten the game!");
                        game.stop();
                    }
                    updatePlayerInfo();
                    updateEventText(combatText + "\n" + "You defeated " + enemy.getName());
                    goMain();
                } else {
                    updatePlayerInfo();
                    updateEventText(combatText.toString());
                }
            }

            // run
            if (action.equals("run")) {
                Room prevRoom = player.getCurrentPosition();
                Room runRoom = player.getCurrentPosition().getConnectedRooms().get(0);
                player.setCurrentPosition(runRoom);
                updateEventText(enemy.getName() + ": \"That is a weak move. But you have escaped death... for now.\"\n\n " +
                        "You escaped, but you took 15 damage doing so!");
                player.decreaseHealth(15);
                updatePlayerInfo();
                String tgtRoom = String.format("Player %s moved from the %s to the %s\n", player.getName(), prevRoom.getName(), runRoom.getName());
                descTextArea.setText(tgtRoom);
                goMain();
            }

            // spin actions
            if (action.equalsIgnoreCase("spin")) {
                updateEventText("Let's make some coin! \n" + Prompter.playSlot(player));
                updatePlayerInfo();
            }
        }
    }

    public class SoundActionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String action = e.getActionCommand();

            switch (action) {
                case "on":
                    maestro.soundLoop();
                    break;
                case "off":
                    maestro.stop();
                    break;
                case "up":
                    maestro.raiseVolume();
                    break;
                case "down":
                    maestro.lowerVolume();
                    break;
            }
        }
    }
}

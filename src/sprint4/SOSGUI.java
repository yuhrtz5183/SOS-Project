package sprint4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

// SOSGUI - handles user interface
public class SOSGUI extends JFrame {
    
    private SOSGame game;
    private SOSBoard board;
    private JButton newGameButton;
    private JButton[][] boardButtons;
    private JLayeredPane layeredBoardPane;
    private JPanel linePanel;
    
    // Game buttons
    private JRadioButton simpleGameRadioButton;
    private JRadioButton generalGameRadioButton;
    private JComboBox<String> boardSizeField;
    private JLabel statusLabel;
    
    // Player buttons
    private JRadioButton blueHumanRadioButton;
    private JRadioButton blueComputerRadioButton;
    private JRadioButton redHumanRadioButton;
    private JRadioButton redComputerRadioButton;
    private JRadioButton blueSRadioButton;
    private JRadioButton blueORadioButton;
    private JRadioButton redSRadioButton;
    private JRadioButton redORadioButton;
    
    private JLabel blueScoreLabel;
    private JLabel redScoreLabel;
    
    private Timer computerMoveTimer;
    
    public SOSGUI() {
        setTitle("SOS Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Initialize game with default config
        game = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
        board = game.getBoard();
        
        createComponents();
        panelLayout();
        setupEventListeners();
        updateStatus();
        
        // Used so contents in window fit properly
        pack();
    }
    
    private void createComponents() {
        // Top panel components
        newGameButton = new JButton("New Game");
        
        simpleGameRadioButton = new JRadioButton("Simple", true);
        generalGameRadioButton = new JRadioButton("General");
        ButtonGroup gameModeGroup = new ButtonGroup();
        gameModeGroup.add(simpleGameRadioButton);
        gameModeGroup.add(generalGameRadioButton);
        
        boardSizeField = new JComboBox<>(new String[]{"3", "4", "5", "6", "7", "8", "9"});
        boardSizeField.setSelectedItem("3");
        
        // Center panel components
        
        // Player type
        blueHumanRadioButton = new JRadioButton("Human", true);
        blueComputerRadioButton = new JRadioButton("Computer");
        ButtonGroup bluePlayerGroup = new ButtonGroup();
        bluePlayerGroup.add(blueHumanRadioButton);
        bluePlayerGroup.add(blueComputerRadioButton);
        
        redHumanRadioButton = new JRadioButton("Human", true);
        redComputerRadioButton = new JRadioButton("Computer");
        ButtonGroup redPlayerGroup = new ButtonGroup();
        redPlayerGroup.add(redHumanRadioButton);
        redPlayerGroup.add(redComputerRadioButton);
        
        // Player letters
        blueSRadioButton = new JRadioButton("S", true);
        blueORadioButton = new JRadioButton("O");
        ButtonGroup blueLetterGroup = new ButtonGroup();
        blueLetterGroup.add(blueSRadioButton);
        blueLetterGroup.add(blueORadioButton);
        
        redSRadioButton = new JRadioButton("S");
        redORadioButton = new JRadioButton("O", true);
        ButtonGroup redLetterGroup = new ButtonGroup();
        redLetterGroup.add(redSRadioButton);
        redLetterGroup.add(redORadioButton);
        
        // Bottom panel component
        statusLabel = new JLabel("Click 'New Game' to start");
        
        // Create board buttons
        createBoardButtons(board.getSize());
    }
    
    private void createBoardButtons(int size) {
        boardButtons = new JButton[size][size];
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                JButton button = new JButton("");
                button.setPreferredSize(new Dimension(60, 60));
                button.setBackground(Color.WHITE);
                boardButtons[row][col] = button;
            }
        }
    }
    
    private void panelLayout() {
        setLayout(new BorderLayout());
        
        // Top panel(game selection)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(newGameButton);
        
        // Game modes
        JPanel gameModePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gameModePanel.add(new JLabel("Game Mode:"));
        gameModePanel.add(simpleGameRadioButton);
        gameModePanel.add(generalGameRadioButton);
        topPanel.add(gameModePanel);
        
        // Board size
        JPanel boardSizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        boardSizePanel.add(new JLabel("Board Size:"));
        boardSizePanel.add(boardSizeField);
        topPanel.add(boardSizePanel);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center panel for subpanels to go into
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        // Blue player panel
        JPanel bluePlayerPanel = new JPanel();
        bluePlayerPanel.setLayout(new BoxLayout(bluePlayerPanel, BoxLayout.Y_AXIS));
        bluePlayerPanel.setBorder(BorderFactory.createTitledBorder("Blue Player"));
        
        JPanel bluePlayerTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bluePlayerTypePanel.add(new JLabel("Player Type:"));
        bluePlayerTypePanel.add(blueHumanRadioButton);
        bluePlayerTypePanel.add(blueComputerRadioButton);
        
        JPanel blueLetterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blueLetterPanel.add(new JLabel("Letter:"));
        blueLetterPanel.add(blueSRadioButton);
        blueLetterPanel.add(blueORadioButton);
        
        blueScoreLabel = new JLabel("SOS: 0");
        blueScoreLabel.setVisible(false);
        JPanel blueScorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        blueScorePanel.add(blueScoreLabel);
        
        bluePlayerPanel.add(bluePlayerTypePanel);
        bluePlayerPanel.add(blueLetterPanel);
        bluePlayerPanel.add(blueScorePanel);
        
        // Game board panel
        JPanel boardPanel = new JPanel(new BorderLayout());
        boardPanel.setBorder(BorderFactory.createTitledBorder("Game Board"));
        
        layeredBoardPane = new JLayeredPane();
        layeredBoardPane.setPreferredSize(new Dimension(board.getSize() * 62, board.getSize() * 62));
        
        JPanel boardGridPanel = new JPanel(new GridLayout(board.getSize(), board.getSize(), 2, 2));
        boardGridPanel.setBounds(0, 0, board.getSize() * 62, board.getSize() * 62);
        
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                boardGridPanel.add(boardButtons[row][col]);
            }
        }
        
        linePanel = new LinePanel();
        linePanel.setBounds(0, 0, board.getSize() * 62, board.getSize() * 62);
        linePanel.setOpaque(false);
        
        layeredBoardPane.add(boardGridPanel, JLayeredPane.DEFAULT_LAYER);
        layeredBoardPane.add(linePanel, JLayeredPane.PALETTE_LAYER);
        
        boardPanel.add(layeredBoardPane, BorderLayout.CENTER);
        setupBoardEventListeners();
        
        // Red player panel
        JPanel redPlayerPanel = new JPanel();
        redPlayerPanel.setLayout(new BoxLayout(redPlayerPanel, BoxLayout.Y_AXIS));
        redPlayerPanel.setBorder(BorderFactory.createTitledBorder("Red Player"));
        
        JPanel redPlayerTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        redPlayerTypePanel.add(new JLabel("Player Type:"));
        redPlayerTypePanel.add(redHumanRadioButton);
        redPlayerTypePanel.add(redComputerRadioButton);
        
        JPanel redLetterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        redLetterPanel.add(new JLabel("Letter:"));
        redLetterPanel.add(redSRadioButton);
        redLetterPanel.add(redORadioButton);
        
        redScoreLabel = new JLabel("SOS: 0");
        redScoreLabel.setVisible(false);
        JPanel redScorePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        redScorePanel.add(redScoreLabel);
        
        redPlayerPanel.add(redPlayerTypePanel);
        redPlayerPanel.add(redLetterPanel);
        redPlayerPanel.add(redScorePanel);
       
        centerPanel.add(bluePlayerPanel, BorderLayout.WEST);
        centerPanel.add(boardPanel, BorderLayout.CENTER);
        centerPanel.add(redPlayerPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom panel(status)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(statusLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        // Board size changing validation
        boardSizeField.addActionListener(e -> {
            int newSize = Integer.parseInt((String) boardSizeField.getSelectedItem());
            SOSBoard.GameMode currentMode = simpleGameRadioButton.isSelected() 
                ? SOSBoard.GameMode.SIMPLE 
                : SOSBoard.GameMode.GENERAL;
            game = new SOSGame(newSize, currentMode);
            board = game.getBoard();
            updateBoardDisplay(newSize);
            updateStatus();
        });
        
        // Simple game mode
        simpleGameRadioButton.addActionListener(e -> {
            if (simpleGameRadioButton.isSelected()) {
                int currentSize = game.getBoard().getSize();
                game = new SOSGame(currentSize, SOSBoard.GameMode.SIMPLE);
                board = game.getBoard();
                updateStatus();
            }
        });
        
        // General game mode
        generalGameRadioButton.addActionListener(e -> {
            if (generalGameRadioButton.isSelected()) {
                int currentSize = game.getBoard().getSize();
                game = new SOSGame(currentSize, SOSBoard.GameMode.GENERAL);
                board = game.getBoard();
                updateStatus();
            }
        });
        
        // Starts a new game
        newGameButton.addActionListener(e -> {
            updatePlayerSettings();
            game.startNewGame();
            clearBoardDisplay();
            updateGameSettingsEnabled(false);
            updateScoreLabels();
            updateStatus();
            checkComputerMove();
        });
        
        blueSRadioButton.addActionListener(e -> updatePlayerSettings());
        blueORadioButton.addActionListener(e -> updatePlayerSettings());
        redSRadioButton.addActionListener(e -> updatePlayerSettings());
        redORadioButton.addActionListener(e -> updatePlayerSettings());
    }
    
    private void updateBoardDisplay(int newSize) {
        // Remove old board panel
        JPanel mainPanel = (JPanel) getContentPane().getComponent(1);
        JPanel centerPanel = (JPanel) mainPanel.getComponent(1);
        centerPanel.removeAll();
        
        // Create new board buttons with new size
        createBoardButtons(newSize);
        
        // Create layered pane with overlay
        layeredBoardPane = new JLayeredPane();
        layeredBoardPane.setPreferredSize(new Dimension(newSize * 62, newSize * 62));
        
        JPanel boardGridPanel = new JPanel(new GridLayout(newSize, newSize, 2, 2));
        boardGridPanel.setBounds(0, 0, newSize * 62, newSize * 62);
        
        for (int row = 0; row < newSize; row++) {
        	for (int col = 0; col < newSize; col++) {
            	boardGridPanel.add(boardButtons[row][col]);
            }
        }
        
        linePanel = new LinePanel();
        linePanel.setBounds(0, 0, newSize * 62, newSize * 62);
        linePanel.setOpaque(false);
        
        layeredBoardPane.add(boardGridPanel, JLayeredPane.DEFAULT_LAYER);
        layeredBoardPane.add(linePanel, JLayeredPane.PALETTE_LAYER);
        
        centerPanel.add(layeredBoardPane, BorderLayout.CENTER);
        
        // Refreshes the board correctly/accordingly
        revalidate();
        repaint();
        pack();
        
        // Sets up board button event listeners
        setupBoardEventListeners();
    }
    
    private void setupBoardEventListeners() {
        int size = boardButtons.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                for (ActionListener listener : boardButtons[row][col].getActionListeners()) {
                    boardButtons[row][col].removeActionListener(listener);
                }
                
                final int finalRow = row;
                final int finalCol = col;
                boardButtons[row][col].addActionListener(e -> handleBoardClick(finalRow, finalCol));
            }
        }
    }
    
    private void handleBoardClick(int row, int col) {
        if (!game.isGameStarted()) {
            JOptionPane.showMessageDialog(this, 
                "Please start a new game first!", 
                "Game Not Started", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (game.isGameEnded()) {
            JOptionPane.showMessageDialog(this, 
            "Game has ended. Start a new game!", 
            "Game Ended", 
            JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // S and O placement
        SOSBoard.TileState currentLetter = game.getCurrentPlayerLetter();
        
        boolean moveSuccessful = game.makeMove(row, col, currentLetter);
        
        if (moveSuccessful) {
            updateBoardDisplay();
            
            if (game.getBoard().isBoardFull()) {
                game.endGame();
            }
            updateStatus();
            checkComputerMove();
        }
        else {
            JOptionPane.showMessageDialog(this, 
                "Invalid move. Spot is already taken!", 
                "Invalid Input", 
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    private void updatePlayerSettings() {
        SOSGame.PlayerType blueType = blueHumanRadioButton.isSelected() ? SOSGame.PlayerType.HUMAN : SOSGame.PlayerType.COMPUTER;
        SOSGame.PlayerLetter blueLetter = blueSRadioButton.isSelected() ? SOSGame.PlayerLetter.S : SOSGame.PlayerLetter.O;
        
        game.setBluePlayerType(blueType);
        game.setBluePlayerLetter(blueLetter);
        
        SOSGame.PlayerType redType = redHumanRadioButton.isSelected() ? SOSGame.PlayerType.HUMAN : SOSGame.PlayerType.COMPUTER;
        SOSGame.PlayerLetter redLetter = redSRadioButton.isSelected() ? SOSGame.PlayerLetter.S : SOSGame.PlayerLetter.O;
        
        game.setRedPlayerType(redType);
        game.setRedPlayerLetter(redLetter);
    }
    
    private void updateBoardDisplay() {
        SOSBoard board = game.getBoard();
        int size = game.getBoard().getSize();
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                SOSBoard.TileState tileState = board.getTile(i, j);
                JButton button = boardButtons[i][j];
                
                switch (tileState) {
                    case EMPTY:
                        button.setText("");
                        break;
                    case S:
                        button.setText("S");
                        break;
                    case O:
                        button.setText("O");
                        break;
                }
            }
        }
        
        updateScoreLabels();
        if (linePanel != null) {
            linePanel.repaint();
        }
    }
    
    private void updateScoreLabels() {
        if (board.getGameMode() == SOSBoard.GameMode.GENERAL && game.isGameStarted()) {
            blueScoreLabel.setVisible(true);
            redScoreLabel.setVisible(true);
            blueScoreLabel.setText("SOS: " + game.getBlueScore());
            redScoreLabel.setText("SOS: " + game.getRedScore());
        } 
        else {
            blueScoreLabel.setVisible(false);
            redScoreLabel.setVisible(false);
        }
    }
    
    private void clearBoardDisplay() {
        for (int row = 0; row < game.getBoard().getSize(); row++) {
            for (int col = 0; col < game.getBoard().getSize(); col++) {
                JButton button = boardButtons[row][col];
                button.setText("");
            }
        }
        if (linePanel != null) {
            linePanel.repaint();
        }
    }
    
    private void updateStatus() {
        if (!game.isGameStarted()) {
            statusLabel.setText("Click 'New Game' to start");
            updateGameSettingsEnabled(true);
        } 
        else if (game.isGameEnded()) {
            String winner = game.getWinner();
            if (winner == null) {
                statusLabel.setText("Game Over");
            } 
            else if ("Draw".equals(winner)) {
                statusLabel.setText("Game Over - Draw");
            } 
            else {
                statusLabel.setText("Game Over - " + winner + " wins!");
            }
            updateGameSettingsEnabled(true);
        } 
        else {
            statusLabel.setText(game.getCurrentPlayer() + "'s Turn");
        }
    }
    
    // Enables/disables game settings
    private void updateGameSettingsEnabled(boolean enabled) {
        boardSizeField.setEnabled(enabled);
        simpleGameRadioButton.setEnabled(enabled);
        generalGameRadioButton.setEnabled(enabled);
    }
    
    // SOS line panel class
    private class LinePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(3));
            
            java.util.List<LineDrawer> lines = game.getSOSLines();
            if (lines == null) {
                return;
            }
            
            int cellSize = 62;
            int offset = cellSize / 2;
            
            for (LineDrawer line : lines) {
                g2d.setColor(line.getColor());
                int x1 = line.getStartCol() * cellSize + offset;
                int y1 = line.getStartRow() * cellSize + offset;
                int x2 = line.getEndCol() * cellSize + offset;
                int y2 = line.getEndRow() * cellSize + offset;
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
    }
    
    private void checkComputerMove() {
        if (computerMoveTimer != null && computerMoveTimer.isRunning()) {
            computerMoveTimer.stop();
        }
        
        if (game.isGameStarted() && !game.isGameEnded() && game.isCurrentPlayerComputer()) {
            // delay for computer move
            computerMoveTimer = new Timer(250, e -> {
                makeComputerMove();
            });
            computerMoveTimer.setRepeats(false);
            computerMoveTimer.start();
        }
    }
    
    private void makeComputerMove() {
        if (!game.isGameStarted() || game.isGameEnded()) {
            return;
        }
        ComputerPlayer.Move move = game.makeComputerMove();
        
        if (move != null) {
            updateBoardDisplay();
            if (game.getBoard().isBoardFull()) {
                game.endGame();
            }
            updateStatus();
            checkComputerMove();
        }
    }
    
    public static void main(String[] args) {
    	new SOSGUI().setVisible(true);
    }
}
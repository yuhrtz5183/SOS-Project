package sprint0;

import java.awt.*;
import javax.swing.*;


public class GUITesting extends JFrame {
	
	private JButton newGameButton;
	private JButton replayButton;
	
	private JRadioButton simpleGameRadioButton;
	private JRadioButton generalGameRadioButton;
	
	private JCheckBox recordGame;
	
	public GUITesting() {
		setTitle("SOS Game");
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		topPanel();
		centerPanel();
		bottomPanel();
	}
	
	private void topPanel() {
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		newGameButton = new JButton("New Game");
		replayButton = new JButton("Replay");
		topPanel.add(newGameButton);
		topPanel.add(replayButton);
		
		JLabel gameMode = new JLabel("Game Mode: ");
		simpleGameRadioButton = new JRadioButton("simple", true);
		generalGameRadioButton = new JRadioButton("general");
		topPanel.add(gameMode);
		
		ButtonGroup gamemodeGroup = new ButtonGroup();
		gamemodeGroup.add(simpleGameRadioButton);
		gamemodeGroup.add(generalGameRadioButton);
		topPanel.add(simpleGameRadioButton);
		topPanel.add(generalGameRadioButton);
		
		JLabel boardSize = new JLabel("Board Size");
		JTextField boardSizeField = new JTextField("8", 2);
		topPanel.add(boardSize);
		topPanel.add(boardSizeField);
		
		add(topPanel, BorderLayout.NORTH);
	}
	
	private void centerPanel() {
		JPanel centerPanel = new JPanel(new GridLayout(1, 3));
		
		JPanel leftPanel = bluePlayerPanel();
		centerPanel.add(leftPanel);
		
		JPanel gameBoardPanel = gameBoardPanel();
		centerPanel.add(gameBoardPanel);
		
		JPanel rightPanel = redPlayerPanel();
		centerPanel.add(rightPanel);
		
		add(centerPanel, BorderLayout.CENTER);
	}
	
	private JPanel bluePlayerPanel() {
		return playerPanel("Blue Player", true);
	}
	
	private JPanel redPlayerPanel() {
		return playerPanel("Red Player", false);
	}
	
	private JPanel playerPanel(String playerName, Boolean isBlue) {
		JPanel playerPanel = new JPanel();
		playerPanel.setBorder(BorderFactory.createTitledBorder(playerName));
		
		JLabel typeLabel = new JLabel("Player Type:");
		playerPanel.add(typeLabel);
		
		JRadioButton humanRadioButton = new JRadioButton("human", true);
		JRadioButton computerRadioButton = new JRadioButton("computer");
		ButtonGroup playerTypeGroup = new ButtonGroup();
		playerTypeGroup.add(humanRadioButton);
		playerTypeGroup.add(computerRadioButton);
		playerPanel.add(humanRadioButton);
		playerPanel.add(computerRadioButton);
		
		JLabel letterSelection = new JLabel("Letter: ");
		letterSelection.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
		playerPanel.add(letterSelection);
		JRadioButton sRadio = new JRadioButton("S", true);
		JRadioButton oRadio = new JRadioButton("O");
		ButtonGroup letterGroup = new ButtonGroup();
		letterGroup.add(sRadio);
		letterGroup.add(oRadio);
		playerPanel.add(sRadio);
		playerPanel.add(oRadio);
		
		return playerPanel;
	}
	
	private JPanel gameBoardPanel() {
		JPanel boardPanel = new JPanel(new GridLayout(8, 8));
		boardPanel.setPreferredSize(new Dimension(240, 80));
		
		// Create 8x8 grid of buttons
		for (int i = 0; i < 64; i++) {
			JButton button = new JButton();
			button.setPreferredSize(new Dimension(25, 8));
			button.setBackground(Color.WHITE);
			boardPanel.add(button);
		}
		
		return boardPanel;
	}
	
	private void bottomPanel() {
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		recordGame = new JCheckBox("Record Game");
		
		bottomPanel.add(recordGame);

		add(bottomPanel, BorderLayout.SOUTH);
	}
	
	
	public static void main(String[] args) {
        new GUITesting().setVisible(true);
    }
	
}

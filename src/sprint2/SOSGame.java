package sprint2;

// SOSGame - handles overall game logic
public class SOSGame {
	
	public enum PlayerType {
		HUMAN, COMPUTER
	}
	
	public enum PlayerLetter {
		S, O
	}
	
	private SOSBoard board;
	private PlayerType blueType;
	private PlayerType redType;
	private PlayerLetter blueLetter;
	private PlayerLetter redLetter;
	private boolean gameStarted;
	private boolean gameEnded;
	
	public SOSGame(int boardSize, SOSBoard.GameMode gameMode) {
		this.board = new SOSBoard(boardSize, gameMode);
		this.blueType = PlayerType.HUMAN;
		this.redType = PlayerType.HUMAN;
		this.blueLetter = PlayerLetter.S;
		this.redLetter = PlayerLetter.O;
		this.gameStarted = false;
		this.gameEnded = false;
	}

    public SOSBoard getBoard() {
        return board;
    }

    public PlayerType getBluePlayerType() {
        return blueType;
    }

    public void setBluePlayerType(PlayerType playerType) {
        this.blueType = playerType;
    }

    public PlayerType getRedPlayerType() {
        return redType;
    }

    public void setRedPlayerType(PlayerType playerType) {
        this.redType = playerType;
    }

    public PlayerLetter getBluePlayerLetter() {
        return blueLetter;
    }

    public void setBluePlayerLetter(PlayerLetter letter) {
        this.blueLetter = letter;
    }

    public PlayerLetter getRedPlayerLetter() {
        return redLetter;
    }

    public void setRedPlayerLetter(PlayerLetter letter) {
        this.redLetter = letter;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }
	
	public void startNewGame() {
		board.reset();
		gameStarted = true;
		gameEnded = false;
	}
	
    public boolean makeMove(int row, int column, SOSBoard.TileState tileState) {
		if (!gameStarted || gameEnded) {
			return false;
		}
		
		return board.makeMove(row, column, tileState);
	}
	
	public String getCurrentPlayer() {
		return board.getCurrentPlayer();
	}
	
	public boolean isBluePlayerTurn() {
		return board.isBluePlayerTurn();
	}
	
	public SOSBoard.TileState getCurrentPlayerLetter() {
		if (isBluePlayerTurn()) {
			return blueLetter == PlayerLetter.S ? SOSBoard.TileState.S : SOSBoard.TileState.O;
		}
        else {
			return redLetter == PlayerLetter.S ? SOSBoard.TileState.S : SOSBoard.TileState.O;
		}
	}
	
	public void endGame() {
		gameEnded = true;
	}
	
	public boolean isBoardFull() {
		return board.isBoardFull();
	}
	
}
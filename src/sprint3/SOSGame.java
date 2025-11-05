package sprint3;

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
    private GameMode gameModeHandler;
	
	public SOSGame(int boardSize, SOSBoard.GameMode gameMode) {
		this.board = new SOSBoard(boardSize, gameMode);
		this.blueType = PlayerType.HUMAN;
		this.redType = PlayerType.HUMAN;
		this.blueLetter = PlayerLetter.S;
		this.redLetter = PlayerLetter.O;
		this.gameStarted = false;
		this.gameEnded = false;
		
		// Gamemode logic implementation
		if (gameMode == SOSBoard.GameMode.SIMPLE) {
			this.gameModeHandler = new SimpleGameMode(board, this);
		} 
		else {
			this.gameModeHandler = new GeneralGameMode(board, this);
		}
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
		
        if (board.getGameMode() == SOSBoard.GameMode.SIMPLE) {
            gameModeHandler = new SimpleGameMode(board, this);
        }
		else {
            gameModeHandler = new GeneralGameMode(board, this);
        }
	}
	
    public boolean makeMove(int row, int column, SOSBoard.TileState tileState) {
		if (!gameStarted || gameEnded) {
			return false;
		}
		
		boolean moveSuccessful = board.makeMove(row, column, tileState);
		
		if (moveSuccessful) {
			// Use the game mode handler to process the move
			gameModeHandler.handleMove(row, column);
		}
		
		return moveSuccessful;
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
	
	public String getWinner() {
		return gameModeHandler.getWinner();
	}
	
	public int getBlueScore() {
		if (gameModeHandler instanceof GeneralGameMode) {
			return ((GeneralGameMode) gameModeHandler).getBlueScore();
		}
		return 0;
	}
	
	public int getRedScore() {
		if (gameModeHandler instanceof GeneralGameMode) {
			return ((GeneralGameMode) gameModeHandler).getRedScore();
		}
		return 0;
	}
	
	public java.util.List<SOSGUI.SOSLine> getSOSLines() {
		return gameModeHandler.getSOSLines();
	}
}
package sprint2;

// SOSBoard - handles board logic
public class SOSBoard {
	
    public enum TileState {
        EMPTY, S, O
    }
	
	public enum GameMode {
		SIMPLE, GENERAL
	}
	
	private int size;
	private GameMode gameMode;
    private TileState[][] tiles;
	private boolean isBluePlayerTurn;
    
    public int getSize() {
        return size;
    }

    public SOSBoard(int size, GameMode gameMode) {
        if (size < 3 || size > 9) {
            throw new IllegalArgumentException("Board size must be between 3 and 9");
        }
        this.size = size;
        this.gameMode = gameMode;
        this.tiles = new TileState[size][size];
        this.isBluePlayerTurn = true; // Makes blue player start first
        
        // Initializes cells as empty
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = TileState.EMPTY;
            }
        }
    }
    
    public void resizeBoard(int newSize) {
        if (newSize < 3 || newSize > 9) {
            throw new IllegalArgumentException("Board size must be between 3 and 9");
        }
        this.size = newSize;
    }
    
    public static boolean isValidBoardSize(int size) {
        return size >= 3 && size <= 9;
    }
    
    public GameMode getGameMode() {
        return gameMode;
    }
    
    public TileState getTile(int row, int column) {
        if (isValidPosition(row, column)) {
            return tiles[row][column];
        }
        return TileState.EMPTY;
    }
    
    public boolean isValidPosition(int row, int column) {
        return row >= 0 && row < size && column >= 0 && column < size;
    }
    
    public boolean isTileEmpty(int row, int column) {
        return isValidPosition(row, column) && tiles[row][column] == TileState.EMPTY;
    }
    
    public boolean makeMove(int row, int column, TileState tileState) {
        if (isValidPosition(row, column) && isTileEmpty(row, column)) {
            tiles[row][column] = tileState;
            isBluePlayerTurn = !isBluePlayerTurn;
            return true;
        }
        return false;
    }
    
    public boolean isBluePlayerTurn() {
        return isBluePlayerTurn;
    }
    
    public String getCurrentPlayer() {
        return isBluePlayerTurn ? "Blue Player" : "Red Player";
    }
    
    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = TileState.EMPTY;
            }
        }
        isBluePlayerTurn = true;
    }
    
    public boolean isBoardFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == TileState.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
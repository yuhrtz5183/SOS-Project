package sprint3;

// Game logic for general game mode
public class GeneralGameMode implements GameMode {
    private final SOSBoard board;
    private final SOSGame game;
    private String winner;
    private int blueScore;
    private int redScore;

    public GeneralGameMode(SOSBoard board, SOSGame game) {
        this.board = board;
        this.game = game;
        this.winner = null;
        this.blueScore = 0;
        this.redScore = 0;
    }

    @Override
    public void handleMove(int row, int column) {
        int numOfSOS = countSOS(row, column);

        if (numOfSOS > 0) {
            boolean isBlue = !board.isBluePlayerTurn();
            if (isBlue) {
                blueScore += numOfSOS;
            }
            else {
                redScore += numOfSOS;
            }
            board.extraTurn();
        }

        if (board.isBoardFull()) {
            if (blueScore > redScore) {
                winner = "Blue Player";
            }
            else if (redScore > blueScore) {
                winner = "Red Player";
            }
            else {
                winner = "Draw";
            }
            game.endGame();
        }
    }

    private int countSOS(int row, int column) {
        int count = 0;
        int[][] directions = { 
            {0, 1}, 
            {1, 0}, 
            {1, 1}, 
            {1, -1}
        };
    
        for (int[] d : directions) {
            int dr = d[0];
            int dc = d[1];
            
            if (isPattern(row - dr, column - dc, row, column, row + dr, column + dc)) {
                count++;
            }
            if (isPattern(row, column, row + dr, column + dc, row + 2 * dr, column + 2 * dc)) {
                count++;
            }
            if (isPattern(row - 2 * dr, column - 2 * dc, row - dr, column - dc, row, column)) {
                count++;
            }
        }
    
        return count;
    }
    

    private boolean isPattern(int rowS1, int colS1, int rowO, int colO, int rowS2, int colS2) {
        if (!board.isValidPosition(rowS1, colS1) || !board.isValidPosition(rowO, colO) || !board.isValidPosition(rowS2, colS2)) {
            return false;
        }
        return board.getTile(rowS1, colS1) == SOSBoard.TileState.S
            && board.getTile(rowO, colO)  == SOSBoard.TileState.O
            && board.getTile(rowS2, colS2) == SOSBoard.TileState.S;
    }

    @Override
    public String getWinner() {
        return winner;
    }
    
    public int getBlueScore() {
        return blueScore;
    }
    
    public int getRedScore() {
        return redScore;
    }
}


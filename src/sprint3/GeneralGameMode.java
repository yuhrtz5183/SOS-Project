package sprint3;

import java.awt.Color;
import java.util.*;

// Game logic for general game mode
public class GeneralGameMode implements GameMode {
    private final SOSBoard board;
    private final SOSGame game;
    private String winner;
    private int blueScore;
    private int redScore;
    private List<LineDrawer> sosLines;

    public GeneralGameMode(SOSBoard board, SOSGame game) {
        this.board = board;
        this.game = game;
        this.winner = null;
        this.blueScore = 0;
        this.redScore = 0;
        this.sosLines = new ArrayList<>();
    }

    @Override
    public void handleMove(int row, int column) {
        boolean isBlue = !board.isBluePlayerTurn();
        Color playerColor = isBlue ? Color.BLUE : Color.RED;
        int numOfSOS = countAndAddSOSLines(row, column, playerColor);

        if (numOfSOS > 0) {
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

    private int countAndAddSOSLines(int row, int column, Color playerColor) {
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
                sosLines.add(new LineDrawer(row - dr, column - dc, row + dr, column + dc, playerColor));
                count++;
            }
            if (isPattern(row, column, row + dr, column + dc, row + 2 * dr, column + 2 * dc)) {
                sosLines.add(new LineDrawer(row, column, row + 2 * dr, column + 2 * dc, playerColor));
                count++;
            }
            if (isPattern(row - 2 * dr, column - 2 * dc, row - dr, column - dc, row, column)) {
                sosLines.add(new LineDrawer(row - 2 * dr, column - 2 * dc, row, column, playerColor));
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

    @Override
    public List<LineDrawer> getSOSLines() {
        return sosLines;
    }
    
    public int getBlueScore() {
        return blueScore;
    }
    
    public int getRedScore() {
        return redScore;
    }
}


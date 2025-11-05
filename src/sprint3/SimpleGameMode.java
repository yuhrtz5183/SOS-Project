package sprint3;

import java.awt.Color;
import java.util.*;

// Game logic for simple game mode
public class SimpleGameMode implements GameMode {
    private final SOSBoard board;
    private final SOSGame game;
    private String winner;
    private List<SOSGUI.SOSLine> sosLines;

    public SimpleGameMode(SOSBoard board, SOSGame game) {
        this.board = board;
        this.game = game;
        this.winner = null;
        this.sosLines = new ArrayList<>();
    }

    @Override
    public void handleMove(int row, int column) {
        if (board.checkSOS()) {
            winner = board.isBluePlayerTurn() ? "Red Player" : "Blue Player";
            Color playerColor = winner.equals("Blue Player") ? Color.BLUE : Color.RED;
            addSOSLine(playerColor);
            game.endGame();
            return;
        }

        if (board.isBoardFull()) {
            winner = "Draw";
            game.endGame();
        }
    }
    
    private void addSOSLine(Color playerColor) {
        int size = board.getSize();
        int[][] directions = { 
            {0, 1},   // horizontal
            {1, 0},   // vertical
            {1, 1},   // diagonal down-right
            {1, -1}   // diagonal down-left
        };
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                for (int[] dir : directions) {
                    int dr = dir[0];
                    int dc = dir[1];
                    int r1 = row;
                    int c1 = col;
                    int r2 = row + dr;
                    int c2 = col + dc;
                    int r3 = row + 2 * dr;
                    int c3 = col + 2 * dc;
                    
                    if (board.isValidPosition(r1, c1) && board.isValidPosition(r2, c2) && board.isValidPosition(r3, c3)) {
                        if (board.getTile(r1, c1) == SOSBoard.TileState.S && 
                            board.getTile(r2, c2) == SOSBoard.TileState.O && 
                            board.getTile(r3, c3) == SOSBoard.TileState.S) {
                            sosLines.add(new SOSGUI.SOSLine(r1, c1, r3, c3, playerColor));
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getWinner() {
        return winner;
    }
    
    @Override
    public List<SOSGUI.SOSLine> getSOSLines() {
        return sosLines;
    }
}


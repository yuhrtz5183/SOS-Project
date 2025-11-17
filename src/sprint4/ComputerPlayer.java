package sprint4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ComputerPlayer - handles computer player logic
public class ComputerPlayer {
    
    private Random random;
    
    public static class Move {
        public final int row;
        public final int col;
        public final SOSBoard.TileState letter;
        
        public Move(int row, int col, SOSBoard.TileState letter) {
            this.row = row;
            this.col = col;
            this.letter = letter;
        }
    }
    
    public ComputerPlayer() {
        this.random = new Random();
    }

    // Makes a move for the computer player (randomly selects a valid tile/letter)
    public Move makeMove(SOSBoard board) {
        return randomMove(board);
    }
    
    // Random move logic
    private Move randomMove(SOSBoard board) {
        List<Move> validMoves = new ArrayList<>();
        int size = board.getSize();
        
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.isTileEmpty(row, col)) {
                    validMoves.add(new Move(row, col, SOSBoard.TileState.S));
                    validMoves.add(new Move(row, col, SOSBoard.TileState.O));
                }
            }
        }
        if (!validMoves.isEmpty()) {
            return validMoves.get(random.nextInt(validMoves.size()));
        }
        return null;
    }
}


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

    // Makes a move for the computer player (either completing an SOS or random)
    public Move makeMove(SOSBoard board) {
        Move sosMove = findSOSMove(board);
        if (sosMove != null) {
            return sosMove;
        }
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

    // SOS finding logic
    private Move findSOSMove(SOSBoard board) {
        int size = board.getSize();

        // Try all empty tiles with S and O
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (board.isTileEmpty(row, col)) {
                    if (completeSOS(board, row, col, SOSBoard.TileState.S)) {
                        return new Move(row, col, SOSBoard.TileState.S);
                    }
                    if (completeSOS(board, row, col, SOSBoard.TileState.O)) {
                        return new Move(row, col, SOSBoard.TileState.O);
                    }
                }
            }
        }
        return null;
    }

    // Checks if tile chosen would complete an SOS
    private boolean completeSOS(SOSBoard board, int row, int col, SOSBoard.TileState letter) {
        if (letter == SOSBoard.TileState.S) {
            return SOScheckS(board, row, col);
        } else {
            return SOScheckO(board, row, col);
        }
    }

    // Checks if S would complete an SOS
    private boolean SOScheckS(SOSBoard board, int row, int col) {
        int[][] directions = {
            {0, 1},
            {1, 0},
            {1, 1},
            {1, -1}
        };

        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];

            // Check if S is at start and end of SOS
            if (board.isValidPosition(row + dr, col + dc) && board.isValidPosition(row + 2 * dr, col + 2 * dc)) {
                if (board.getTile(row + dr, col + dc) == SOSBoard.TileState.O &&
                    board.getTile(row + 2 * dr, col + 2 * dc) == SOSBoard.TileState.S) {
                    return true;
                }
            }
            if (board.isValidPosition(row - dr, col - dc) && board.isValidPosition(row - 2 * dr, col - 2 * dc)) {
                if (board.getTile(row - dr, col - dc) == SOSBoard.TileState.O &&
                    board.getTile(row - 2 * dr, col - 2 * dc) == SOSBoard.TileState.S) {
                    return true;
                }
            }
        }
        return false;
    }

    // Checks if O would complete an SOS
    private boolean SOScheckO(SOSBoard board, int row, int col) {
        int[][] directions = {
            {0, 1},
            {1, 0},
            {1, 1},
            {1, -1}
        };

        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];

            if (board.isValidPosition(row - dr, col - dc) && board.isValidPosition(row + dr, col + dc)) {
                if (board.getTile(row - dr, col - dc) == SOSBoard.TileState.S &&
                    board.getTile(row + dr, col + dc) == SOSBoard.TileState.S) {
                    return true;
                }
            }
        }
        return false;
    }
}


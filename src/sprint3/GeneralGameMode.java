package sprint3;

// Game logic for general game mode
public class GeneralGameMode implements GameMode {
    private final SOSBoard board;
    private final SOSGame game;
    private String winner;

    public GeneralGameMode(SOSBoard board, SOSGame game) {
        this.board = board;
        this.game = game;
        this.winner = null;
    }

    @Override
    public void handleMove(int row, int column) {
        if (board.isBoardFull()) {
            
            game.endGame();
        }
    }

    @Override
    public String getWinner() {
        return winner;
    }
}


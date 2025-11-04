package sprint3;

// Game logic for simple game mode
public class SimpleGameMode implements GameMode {
    private final SOSBoard board;
    private final SOSGame game;
    private String winner;

    public SimpleGameMode(SOSBoard board, SOSGame game) {
        this.board = board;
        this.game = game;
        this.winner = null;
    }

    @Override
    public void handleMove(int row, int column) {
        // logic here
    }

    @Override
    public String getWinner() {
        return winner;
    }
}


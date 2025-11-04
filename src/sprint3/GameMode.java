package sprint3;

// Simple and general game modes interface
public interface GameMode {

    void handleMove(int row, int column);

    String getWinner();
}

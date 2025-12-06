package sprint5;

import java.util.*;

// GameRecording - Data container for recorded SOS game
public class GameRecording {
    
    private GameConfig configuration;
    private List<MoveRecord> moves;
    private GameResult result;
    private String timestamp;
    
    public GameRecording() {
        this.moves = new ArrayList<>();
    }
    
    public GameConfig getConfiguration() {
        return configuration;
    }
    
    public void setConfiguration(GameConfig configuration) {
        this.configuration = configuration;
    }
    
    public List<MoveRecord> getMoves() {
        return moves;
    }
    
    public void addMove(MoveRecord move) {
        this.moves.add(move);
    }
    
    public GameResult getResult() {
        return result;
    }
    
    public void setResult(GameResult result) {
        this.result = result;
    }
    
    public String getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    // GameConfig - Game settings information
    public static class GameConfig {
        public int boardSize;
        public SOSBoard.GameMode gameMode;
        public SOSGame.PlayerType blueType;
        public SOSGame.PlayerLetter blueLetter;
        public SOSGame.PlayerType redType;
        public SOSGame.PlayerLetter redLetter;
        
        public GameConfig(int boardSize,SOSBoard.GameMode gameMode,
                            SOSGame.PlayerType blueType, SOSGame.PlayerLetter blueLetter,
                            SOSGame.PlayerType redType, SOSGame.PlayerLetter redLetter) {
            this.boardSize = boardSize;
            this.gameMode = gameMode;
            this.blueType = blueType;
            this.blueLetter = blueLetter;
            this.redType = redType;
            this.redLetter = redLetter;
        }
    }
    
    // MoveRecord - Move information for each move done in game
    public static class MoveRecord {
        public final int moveNumber;
        public final int row;
        public final int col;
        public final SOSBoard.TileState letter;
        public final String player;
        
        public MoveRecord(int moveNumber, int row, int col, SOSBoard.TileState letter, String player) {
            this.moveNumber = moveNumber;
            this.row = row;
            this.col = col;
            this.letter = letter;
            this.player = player;
        }
    }
    
    // GameResult - Game winner information
    public static class GameResult {
        public String winner;
        public int blueScore;
        public int redScore;
        
        public GameResult(String winner, int blueScore, int redScore) {
            this.winner = winner;
            this.blueScore = blueScore;
            this.redScore = redScore;
        }
    }
}




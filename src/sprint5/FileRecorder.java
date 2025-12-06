package sprint5;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

// FileRecorder - Read gameRecorder and write to file
public class FileRecorder implements GameRecorder {
    
    private GameRecording recording;
    private boolean isRecording;
    private int moveCounter;
    
    public FileRecorder() {
        this.isRecording = false;
        this.moveCounter = 0;
    }
    
    @Override
    public void startRecording(GameRecording.GameConfig config) {
        this.recording = new GameRecording();
        this.recording.setConfiguration(config);
        this.recording.setTimestamp(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
        this.moveCounter = 0;
        this.isRecording = true;
    }
    
    @Override
    public void recordMove(int row, int col, SOSBoard.TileState letter, String player) {
        if (!isRecording || recording == null) {
            return;
        }
        
        moveCounter++;
        GameRecording.MoveRecord move = new GameRecording.MoveRecord(
            moveCounter, row, col, letter, player
        );
        recording.addMove(move);
    }
    
    @Override
    public void stopRecording(GameRecording.GameResult result) {
        if (isRecording && recording != null) {
            recording.setResult(result);
            isRecording = false;
        }
    }
    
    @Override
    public boolean isRecording() {
        return isRecording;
    }
    
    @Override
    public GameRecording getRecording() {
        return recording;
    }
    
    @Override
    public boolean saveToFile(File file) {
        if (recording == null) {
            return false;
        }
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            gameSettings(writer);
            gameMoves(writer);
            gameResult(writer);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    
    private void gameSettings(PrintWriter writer) {
        GameRecording.GameConfig config = recording.getConfiguration();
        
        writer.println("Date and Time: " + recording.getTimestamp());
        writer.println("Board Size: " + config.boardSize);
        writer.println("Game Mode: " + config.gameMode);
        writer.println("Blue Player Type: " + config.blueType);
        writer.println("Blue Player Letter: " + config.blueLetter);
        writer.println("Red Player Type: " + config.redType);
        writer.println("Red Player Letter: " + config.redLetter);
        writer.println();
    }
    
    private void gameMoves(PrintWriter writer) {
        writer.println("--- GAME MOVES ---");
        for (GameRecording.MoveRecord move : recording.getMoves()) {
            writer.println(String.format("Move %d: Row=%d, Col=%d, Letter=%s, Player=%s",
                move.moveNumber, move.row, move.col, move.letter, move.player));
        }
        writer.println();
    }
    
    private void gameResult(PrintWriter writer) {
        GameRecording.GameResult result = recording.getResult();
        
        writer.println("--- GAME RESULT ---");
        writer.println("Winner: " + (result.winner != null ? result.winner : "DRAW"));
        
        GameRecording.GameConfig config = recording.getConfiguration();
        if (config.gameMode == SOSBoard.GameMode.GENERAL) {
            writer.println("Blue Score: " + result.blueScore);
            writer.println("Red Score: " + result.redScore);
        }
    }
}


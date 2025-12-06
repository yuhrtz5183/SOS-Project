package sprint5;

import java.io.File;

// GameRecorder - Interface for recording game 
public interface GameRecorder {

    void startRecording(GameRecording.GameConfig config);
    
    void recordMove(int row, int col, SOSBoard.TileState letter, String player);

    void stopRecording(GameRecording.GameResult result);
    
    boolean isRecording();
    
    boolean saveToFile(File file);

    GameRecording getRecording();
}


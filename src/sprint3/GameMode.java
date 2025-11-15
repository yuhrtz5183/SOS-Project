package sprint3;

import java.util.*;

// Simple and general game modes interface
public interface GameMode {

    void handleMove(int row, int column);

    String getWinner();
    
    List<LineDrawer> getSOSLines();
}

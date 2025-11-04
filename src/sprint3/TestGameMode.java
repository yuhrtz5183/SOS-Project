package sprint3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGameMode {

    private SOSBoard defaultBoard;
    private SOSGame defaultGame;

    @Before
    public void setUp() {
        defaultBoard = new SOSBoard(3, SOSBoard.GameMode.SIMPLE);
        defaultGame  = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
    }

    
 // AC 2.3: Default game mode is SIMPLE
    @Test
    public void testDefaultGameModeIsSimple() {
        assertEquals(SOSBoard.GameMode.SIMPLE, defaultBoard.getGameMode());
        assertFalse(defaultGame.isGameStarted());
        defaultGame.startNewGame();
        assertTrue(defaultGame.isGameStarted());
    }
}

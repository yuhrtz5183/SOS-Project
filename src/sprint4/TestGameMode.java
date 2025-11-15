package sprint4;

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
    
    // AC 5.1: Simple game ends when first SOS is created
    @Test
    public void testSimpleGame() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
        game.startNewGame();
        
        // Blue player creates S-O-S horizontally
        game.makeMove(0, 0, SOSBoard.TileState.S);  // Blue
        game.makeMove(1, 0, SOSBoard.TileState.O);  // Red
        game.makeMove(0, 1, SOSBoard.TileState.O);  // Blue
        game.makeMove(1, 1, SOSBoard.TileState.S);  // Red
        game.makeMove(0, 2, SOSBoard.TileState.S);  // Blue forms SOS
        
        assertTrue(game.isGameEnded());
        assertEquals("Blue Player", game.getWinner());
    }
    
    // AC 6.1/6.2: General game continues until board full and determines winner by score
    @Test
    public void testGeneralGame() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.GENERAL);
        game.startNewGame();
        
        // Blue creates horizontal SOS (0,0), (0,1), (0,2)
        game.makeMove(0, 0, SOSBoard.TileState.S);  // Blue
        game.makeMove(0, 1, SOSBoard.TileState.O);  // Red
        game.makeMove(0, 2, SOSBoard.TileState.S);  // Blue forms SOS, gets extra turn
        
        // Blue score should be 1, game continues
        assertEquals(1, game.getBlueScore());
        assertFalse(game.isGameEnded());
        
        // Fill rest of board
        game.makeMove(1, 0, SOSBoard.TileState.O);  // Blue (extra turn)
        game.makeMove(1, 1, SOSBoard.TileState.O);  // Red
        game.makeMove(1, 2, SOSBoard.TileState.S);  // Blue
        game.makeMove(2, 0, SOSBoard.TileState.S);  // Red
        game.makeMove(2, 1, SOSBoard.TileState.O);  // Blue
        game.makeMove(2, 2, SOSBoard.TileState.S);  // Red
        
        // Game should end when board is full
        assertTrue(game.isGameEnded());
        assertEquals("Blue Player", game.getWinner());
    }
    
    // 5.2:Test simple game draw scenario
    @Test
    public void testSimpleGameDraw() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
        game.startNewGame();
        
        // Fill board without creating SOS - pattern: O-O-O / S-S-S / O-O-O
        game.makeMove(0, 0, SOSBoard.TileState.O);  // Blue
        game.makeMove(0, 1, SOSBoard.TileState.O);  // Red
        game.makeMove(0, 2, SOSBoard.TileState.O);  // Blue
        game.makeMove(1, 0, SOSBoard.TileState.S);  // Red
        game.makeMove(1, 1, SOSBoard.TileState.S);  // Blue
        game.makeMove(1, 2, SOSBoard.TileState.S);  // Red
        game.makeMove(2, 0, SOSBoard.TileState.O);  // Blue
        game.makeMove(2, 1, SOSBoard.TileState.O);  // Red
        game.makeMove(2, 2, SOSBoard.TileState.O);  // Blue
        
        assertTrue(game.isGameEnded());
        assertEquals("Draw", game.getWinner());
    }
    
    // 7.1: Test general game draw with equal scores
    @Test
    public void testGeneralGameDraw() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.GENERAL);
        game.startNewGame();
        
        // Blue creates horizontal SOS at row 0
        game.makeMove(0, 0, SOSBoard.TileState.S);  // Blue
        game.makeMove(1, 0, SOSBoard.TileState.O);  // Red
        game.makeMove(0, 1, SOSBoard.TileState.O);  // Blue
        game.makeMove(1, 1, SOSBoard.TileState.S);  // Red
        game.makeMove(0, 2, SOSBoard.TileState.S);  // Blue creates SOS, score = 1
        
        // Red creates vertical SOS at column 0
        game.makeMove(1, 2, SOSBoard.TileState.O);  // Blue (extra turn)
        game.makeMove(2, 0, SOSBoard.TileState.S);  // Red creates SOS, score = 1
        
        // Fill remaining cells without creating more SOS
        game.makeMove(2, 1, SOSBoard.TileState.S);  // Red (extra turn)
        game.makeMove(2, 2, SOSBoard.TileState.O);  // Blue
        
        assertTrue(game.isGameEnded());
        assertEquals(1, game.getBlueScore());
        assertEquals(1, game.getRedScore());
        assertEquals("Draw", game.getWinner());
    }
}

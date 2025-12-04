package sprint5;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

// Test for user story 8 and 9
public class TestComputerPlayer {
    
    @Before
    public void setUp() {
    }

    // Test for AC 8.1 (Blue player type selection)
    @Test
    public void bluePlayerTypeSelection() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
        
        assertEquals("Default blue player type should be HUMAN", 
            SOSGame.PlayerType.HUMAN, game.getBluePlayerType());

        game.setBluePlayerType(SOSGame.PlayerType.COMPUTER);
        assertEquals("Blue player type should be COMPUTER after selection", 
            SOSGame.PlayerType.COMPUTER, game.getBluePlayerType());

        game.setBluePlayerType(SOSGame.PlayerType.HUMAN);
        assertEquals("Blue player type should be HUMAN after selection", 
            SOSGame.PlayerType.HUMAN, game.getBluePlayerType());
    }

    // Test for AC 8.2 (Red player type selection)
    @Test
    public void redPlayerTypeSelection() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
        
        assertEquals("Default red player type should be HUMAN", 
            SOSGame.PlayerType.HUMAN, game.getRedPlayerType());

        game.setRedPlayerType(SOSGame.PlayerType.COMPUTER);
        assertEquals("Red player type should be COMPUTER after selection", 
            SOSGame.PlayerType.COMPUTER, game.getRedPlayerType());

        game.setRedPlayerType(SOSGame.PlayerType.HUMAN);
        assertEquals("Red player type should be HUMAN after selection", 
            SOSGame.PlayerType.HUMAN, game.getRedPlayerType());
    }
    
    // Test for AC 9.2 (Computer only makes valid moves)
    @Test
    public void computerChoosesValidTiles() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
        game.setBluePlayerType(SOSGame.PlayerType.COMPUTER);
        game.startNewGame();

        game.makeMove(0, 0, SOSBoard.TileState.S);
        game.makeMove(1, 1, SOSBoard.TileState.O);
    
        // Computer move
        ComputerPlayer.Move move = game.makeComputerMove();
        assertNotNull("Computer should return a move", move);

        boolean chose00 = (move.row == 0 && move.col == 0);
        boolean chose11 = (move.row == 1 && move.col == 1);
    
        assertFalse("Computer should not select occupied tile (0,0)", chose00);
        assertFalse("Computer should not select occupied tile (1,1)", chose11);
    }

    // Test for AC 9.4 (Computer move switches turn to next player)
    @Test
    public void computerSwitchToNextPlayer() {
        SOSGame game = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
        game.setBluePlayerType(SOSGame.PlayerType.COMPUTER);
        game.setRedPlayerType(SOSGame.PlayerType.HUMAN);
        game.startNewGame();

        assertTrue("Blue should start the game",
            game.getBoard().isBluePlayerTurn());

        ComputerPlayer.Move move = game.makeComputerMove();
        assertNotNull("Computer should make a move", move);

        assertFalse("After Blue computer move, it should be Red's turn",
            game.getBoard().isBluePlayerTurn());
    }
}


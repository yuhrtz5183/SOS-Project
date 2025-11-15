package sprint4;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import sprint3.SOSBoard.GameMode;

import static org.junit.Assert.*;

/**
 * Tests for User Story 1: Choose a board size
 * AC 1.1: Selecting a size adjusts the board
 * AC 1.2: If no selection, default to 3x3
 * AC 1.3: Midgame size change is blocked (or requires new game)
 *
 * Assumptions (adjust if your implementation differs):
 * - Valid board sizes are in [3..9]. (Update tests if your bounds differ.)
 * - Constructing SOSBoard with a valid size sets that size.
 * - Calling resizeBoard(int) on SOSBoard updates the board immediately (board-level behavior).
 * - “Default to 3x3 when no selection” is represented here by creating a game/board with size 3.
 *   If you have a different pathway (e.g., GUI with no size chosen), replace the setup accordingly.
 * - Game-level policy to block midgame resize belongs in SOSGame (not SOSBoard). The AC 1.3 test is
 *   provided as @Ignore until your game exposes a method like `requestResize(int)` or similar.
 */
public class TestBoard {

    private SOSBoard board;
    private SOSGame game;

    @Before
    public void setUp() {
        // Start with a small valid default so tests can adjust as needed.
        board = new SOSBoard(3, SOSBoard.GameMode.SIMPLE);
        game  = new SOSGame(3, SOSBoard.GameMode.SIMPLE);
    }

    @After
    public void tearDown() {
        board = null;
        game  = null;
    }

    // -------------------------
    // AC 1.1: Choose board size
    // -------------------------

    @Test
    public void testIsValidBoardSize_CommonValidSizes() {
        assertTrue(board.isValidBoardSize(3));
        assertTrue(board.isValidBoardSize(6));
        assertTrue(board.isValidBoardSize(9));
    }

    @Test
    public void testResizeBoard_ValidSize_AdjustsBoard() {
        // Given a fresh board
        assertEquals(3, board.getSize());

        // When player selects a valid size (e.g., 7)
        int newSize = 7;
        assertTrue("Precondition: size should be valid", board.isValidBoardSize(newSize));
        board.resizeBoard(newSize);

        // Then the board reflects the selection
        assertEquals(newSize, board.getSize());
    }

    // -------------------------------------------------
    // AC 1.2: Default board size when none is selected
    // -------------------------------------------------

    @Test
    public void testDefaultBoardSize_WhenNoSelection_DefaultsTo3x3() {
        // Interpretation: if no explicit selection is made, the system starts at 3x3.
        // Here we represent that with game/board initialized at 3.
        // If your GUI sets this implicitly, keep the assertion—it documents the contract.

        // Game constructed with 3 => default path in absence of a user choice.
        assertFalse("Game should not be started before calling startNewGame()", game.isGameStarted());
        assertEquals("Default board size should be 3x3", 3, new SOSBoard(3, SOSBoard.GameMode.SIMPLE).getSize());

        // Optional: if your game constructs/owns a board internally upon start
        game.startNewGame();
        assertTrue(game.isGameStarted());
        // If you expose a getBoard() later, assert its size == 3 here.
        // e.g., assertEquals(3, game.getBoard().getSize());
    }

    // ----------------------------------------------------------------
    // AC 1.3: Midgame board size change error (policy lives in SOSGame)
    // ----------------------------------------------------------------

    /**
     * This test illustrates the game-level policy:
     * - If a game is in progress, resizing should be blocked (or force new game).
     * Enable this once you expose a game API that mediates board resizing, e.g.:
     *   boolean requestResize(int newSize)  // returns false if game started
     *   or
     *   void resizeBoardDuringGame(int) throws IllegalStateException
     */
    @Ignore("Enable when SOSGame exposes a resize gate (e.g., requestResize or similar).")
    @Test
    public void testMidgameResizeIsBlocked_OrRequiresNewGame() {
        // Given a running game
        game.startNewGame();
        assertTrue(game.isGameStarted());
        assertFalse(game.isGameEnded());

        // When the player tries to change board size midgame
        int attemptedSize = 5;

        // Example A: rejected with status
        // boolean accepted = game.requestResize(attemptedSize);
        // assertFalse("Resize should be rejected while a game is active", accepted);

        // Example B: rejected with exception
        // try {
        //     game.resizeBoardDuringGame(attemptedSize);
        //     fail("Expected an exception to block midgame resize");
        // } catch (IllegalStateException expected) {
        //     // ok
        // }

        // Then: after ending the game, they can start fresh with a new size
        game.endGame();
        assertTrue("Game should be ended", game.isGameEnded());

        // Example fresh start flow:
        // game = new SOSGame(attemptedSize, SOSBoard.GameMode.SIMPLE);
        // game.startNewGame();
        // assertTrue(game.isGameStarted());
        // assertEquals(attemptedSize, game.getBoard().getSize());
    }
}

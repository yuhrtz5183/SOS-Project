package sprint0;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class GUITest {

    @Test
    void testWindowTitle() {
        GUITesting gui = new GUITesting();
        assertEquals("SOS Game", gui.getTitle());
    }
}
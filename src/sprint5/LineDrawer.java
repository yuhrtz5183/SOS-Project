package sprint5;

import java.awt.Color;

// LineDrawer - represents a line drawn on the board when SOS is formed
public class LineDrawer {
    private final int startRow;
    private final int startCol;
    private final int endRow;
    private final int endCol;
    private final Color color;
    
    public LineDrawer(int startRow, int startCol, int endRow, int endCol, Color color) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.color = color;
    }
    
    public int getStartRow() {
        return startRow;
    }
    
    public int getStartCol() {
        return startCol;
    }
    
    public int getEndRow() {
        return endRow;
    }
    
    public int getEndCol() {
        return endCol;
    }
    
    public Color getColor() {
        return color;
    }
}


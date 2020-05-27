package datastructureproject;

/**
 * This enum contains all piece types, their relative values and empty type for
 * empty squares.
 * 
 */
public enum Type {
    KING(200),
    QUEEN(9),
    ROOK(5),
    BISHOP(3),
    KNIGHT(3),
    PAWN(1),
    EMPTY(0);
    
    private final int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
          
}

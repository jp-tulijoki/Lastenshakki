package datastructureproject;

/**
 * This enum contains all piece types, their relative values, abbreviations and 
 * empty type for empty squares.
 */
public enum Type {
    KING(200, 'k'),
    QUEEN(9, 'q'),
    ROOK(5, 'r'),
    BISHOP(3, 'b'),
    KNIGHT(3, 'n'),
    PAWN(1, 'p'),
    EMPTY(0, 'e');
    
    private final int value;
    private final char abbreviation;

    Type(int value, char abbreviation) {
        this.value = value;
        this.abbreviation = abbreviation;
    }

    public int getValue() {
        return value;
    }

    public char getAbbreviation() {
        return abbreviation;
    }
    
    /**
     * This method returns a Type value equivalent to the abbreviation. Used for
     * playing promotion moves in UCI format in the board of the current Game
     * object.
     * @param abbreviation the abbreviation of the piece the pawn is promoted to
     * @return returns a Type value
     */
    public static Type getType(char abbreviation) {
        if (abbreviation == 'b') {
            return Type.BISHOP;
        } else if (abbreviation == 'n') {
            return Type.KNIGHT;
        } else if (abbreviation == 'q') {
            return Type.QUEEN;
        } else {
            return Type.ROOK;
        }
    } 
    
    
          
}

package chess.rules;

import chess.model.Side;

/**
 * This class defines the piece type and side.
 */
public class Piece {
    private Type type;
    private Side side;
    private boolean castlingPossible;

    public Piece(Type type, Side side, boolean castlingPossible) {
        this.type = type;
        this.side = side;
        this.castlingPossible = castlingPossible;
    }

    public Piece(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Side getSide() {
        return side;
    }

    public boolean isCastlingPossible() {
        return castlingPossible;
    }
    
    
}

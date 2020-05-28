package datastructureproject;

import chess.model.Side;

/**
 * This class defines the piece type and side.
 */
public class Piece {
    private Type type;
    private Side side;

    public Piece(Type type, Side side) {
        this.type = type;
        this.side = side;
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.rules;

import chess.model.Side;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tulijoki
 */
public class BoardTest {
    private Board board;
    
    public BoardTest() {
    }
    
    @Before
    public void setUp() {
        this.board = new Board();
    }
    
    @Test
    public void boardContainsNoNullSquares() {
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                assertNotNull(board.getPiece(y, x).getType());
            }
        }
    }
    
    @Test
    public void thePawnsAreInTheFrontRow() {
        for (int x = 0; x <= 7; x++) {
            assertEquals(Side.WHITE, board.getPiece(1, x).getSide());
            assertEquals(Type.PAWN, board.getPiece(1, x).getType());
            assertEquals(Side.BLACK, board.getPiece(6, x).getSide());
            assertEquals(Type.PAWN, board.getPiece(6, x).getType());
        }
    }
    
    @Test
    public void otherPiecesAreInCorrectSquares() {
        for (int x = 0; x <=7; x++) {
            assertEquals(Side.WHITE, board.getPiece(0, x).getSide());
            assertEquals(Side.BLACK, board.getPiece(7, x).getSide());
        }
        assertTrue(board.getPiece(0, 0).getType() == Type.ROOK && board.getPiece(0, 7).getType() == Type.ROOK && board.getPiece(7, 0).getType() == Type.ROOK && board.getPiece(7, 7).getType() == Type.ROOK);
        assertTrue(board.getPiece(0, 1).getType() == Type.KNIGHT && board.getPiece(0, 6).getType() == Type.KNIGHT && board.getPiece(7, 1).getType() == Type.KNIGHT && board.getPiece(7, 6).getType() == Type.KNIGHT);
        assertTrue(board.getPiece(0, 2).getType() == Type.BISHOP && board.getPiece(0, 5).getType() == Type.BISHOP && board.getPiece(7, 2).getType() == Type.BISHOP && board.getPiece(7, 5).getType() == Type.BISHOP);
        assertTrue(board.getPiece(0, 3).getType() == Type.QUEEN && board.getPiece(7, 3).getType() == Type.QUEEN);
        assertTrue(board.getPiece(0, 4).getType() == Type.KING && board.getPiece(7, 4).getType() == Type.KING);
    }
    
    @Test
    public void castlingIsPossibleForKingsAndRooksOnly() {
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                if (y == 0 || y == 7) {
                    if (x == 0 || x == 4 || x == 7) {
                        assertTrue(board.getPiece(y, x).isCastlingPossible());
                        continue;
                    }
                }
                assertFalse(board.getPiece(y, x).isCastlingPossible());
            }
        }
    }

}

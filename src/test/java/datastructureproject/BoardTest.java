/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import datastructureproject.Board;
import datastructureproject.Type;
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
        this.board = new Board();
    }
    
    @Test
    public void initializedBoardContainsNoNullSquares() {
        board.initBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                assertNotNull(board.getPiece(y, x).getType());
            }
        }
    }
    
    @Test
    public void initializedPawnsAreInTheFrontRow() {
        board.initBoard();
        for (int x = 0; x <= 7; x++) {
            assertEquals(Side.WHITE, board.getPiece(1, x).getSide());
            assertEquals(Type.PAWN, board.getPiece(1, x).getType());
            assertEquals(Side.BLACK, board.getPiece(6, x).getSide());
            assertEquals(Type.PAWN, board.getPiece(6, x).getType());
        }
    }
    
    @Test
    public void otherPiecesAreInCorrectSquaresWhenInitialized() {
        board.initBoard();
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
    public void castlingIsPossibleForKingsAndRooksOnlyWhenInitialized() {
        board.initBoard();
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
    
    @Test
    public void unhinderedPawnsMoveOneStep() {
        board.initBoard();
        Piece whitePawn = board.getPiece(1, 0);
        Piece blackPawn = board.getPiece(6, 0);
        board.addRegularPawnMove(whitePawn, 1, 0);
        board.addRegularPawnMove(blackPawn, 6, 0);
        Piece[][] whiteMove = board.getLegalMoves().get(0);
        Piece[][] blackMove = board.getLegalMoves().get(1);
        assertEquals(Type.PAWN, whiteMove[2][0].getType());
        assertEquals(Side.WHITE, whiteMove[2][0].getSide());
        assertEquals(Type.EMPTY, whiteMove[1][0].getType());
        assertEquals(Type.PAWN, blackMove[5][0].getType());
        assertEquals(Side.BLACK, blackMove[5][0].getSide());
        assertEquals(Type.EMPTY, blackMove[6][0].getType());
    }
    
    @Test
    public void unhinderedPawnsMoveTwoSteps() {
        board.initBoard();
        Piece whitePawn = board.getPiece(1, 0);
        Piece blackPawn = board.getPiece(6, 0);
        board.addTwoStepPawnMove(whitePawn, 1, 0);
        board.addTwoStepPawnMove(blackPawn, 6, 0);
        Piece[][] whiteMove = board.getLegalMoves().get(0);
        Piece[][] blackMove = board.getLegalMoves().get(1);
        assertEquals(Type.PAWN, whiteMove[3][0].getType());
        assertEquals(Side.WHITE, whiteMove[3][0].getSide());
        assertEquals(Type.EMPTY, whiteMove[1][0].getType());
        assertEquals(Type.PAWN, blackMove[4][0].getType());
        assertEquals(Side.BLACK, blackMove[4][0].getSide());
        assertEquals(Type.EMPTY, blackMove[6][0].getType());
    }
    
    @Test
    public void hinderedPawnsDoNotMove() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        Piece whitePawn = board.getPiece(1, 0);
        Piece blackPawn = board.getPiece(6, 0);
        board.movePiece(currentBoard, 0, 1, 2, 0);
        board.movePiece(currentBoard, 7, 1, 5, 0);
        board.addRegularPawnMove(whitePawn, 1, 0);
        board.addTwoStepPawnMove(whitePawn, 1, 0);
        board.addRegularPawnMove(blackPawn, 6, 0);
        board.addTwoStepPawnMove(blackPawn, 6, 0);
        assertTrue(board.getLegalMoves().isEmpty());
    }
    
    @Test
    public void pawnsDoNotAttackOutOfBounds() {
        
    }
    
    

}

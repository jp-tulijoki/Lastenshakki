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
    public void pawnsDoNotAttackOutOfBoundsOrEmptySquares() {
        board.initBoard();
        Piece whitePawn = board.getPiece(1, 0);
        Piece blackPawn = board.getPiece(6, 0);
        board.addPawnAttack(whitePawn, 1, 0);
        board.addPawnAttack(blackPawn, 6, 0);
        assertTrue(board.getLegalMoves().isEmpty());
    }
    
    @Test
    public void pawnsNoNotAttackOwnSidePieces() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        Piece whitePawn = board.getPiece(1, 1);
        Piece blackPawn = board.getPiece(6, 1);
        board.movePiece(currentBoard, 0, 1, 2, 0); //own side knight
        board.movePiece(currentBoard, 7, 1, 5, 0); //own side knight
        board.addPawnAttack(whitePawn, 1, 1);
        board.addPawnAttack(blackPawn, 6, 1);
        assertTrue(board.getLegalMoves().isEmpty());
    }
    @Test
    public void pawnsAttackEnemies() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        Piece whitePawn = board.getPiece(1, 0);
        Piece blackPawn = board.getPiece(6, 0);
        board.movePiece(currentBoard, 7, 3, 2, 1); //enemy queen
        board.movePiece(currentBoard, 0, 3, 5, 1); //enemy queen
        board.addPawnAttack(whitePawn, 1, 0);
        board.addPawnAttack(blackPawn, 6, 0);
        Piece[][] whiteMove = board.getLegalMoves().get(0);
        Piece[][] blackMove = board.getLegalMoves().get(1);
        assertEquals(Type.PAWN, whiteMove[2][1].getType());
        assertEquals(Side.WHITE, whiteMove[2][1].getSide());
        assertEquals(Type.EMPTY, whiteMove[1][0].getType());
        assertEquals(Type.PAWN, blackMove[5][1].getType());
        assertEquals(Side.BLACK, blackMove[5][1].getSide());
        assertEquals(Type.EMPTY, blackMove[6][0].getType());
    }
    
    @Test
    public void knightMovesToEmptyAndEnemySquares() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        Piece whiteKnight = board.getPiece(0, 1);
        board.movePiece(currentBoard, 7, 3, 2, 2); //enemy queen
        board.addKnightMoves(whiteKnight, 0, 1);
        Piece[][] knightMove1 = board.getLegalMoves().get(0);
        Piece[][] knightMove2 = board.getLegalMoves().get(1);
        assertTrue(board.getLegalMoves().size() == 2);
        assertEquals(Type.KNIGHT, knightMove1[2][2].getType());
        assertEquals(Type.EMPTY, knightMove1[0][1].getType());
        assertEquals(Type.PAWN, knightMove1[1][3].getType());
        assertEquals(Type.KNIGHT, knightMove2[2][0].getType());
        assertEquals(Type.EMPTY, knightMove2[0][1].getType());
        assertEquals(Type.PAWN, knightMove2[1][3].getType());
    }
    
    @Test
    public void initializedRookDoesNotMove() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        Piece whiteRook = board.getPiece(0, 0);
        board.addRookMoves(whiteRook, 0, 0);
        assertTrue(board.getLegalMoves().size() == 0);
    }
    
    // A white rook is moved to square (y4,x3) in otherwise initialized board,
    // so the rook should be able to move two steps down (own side pawn),
    // two up (enemy pawn), four to the right (right boundary) and three to the
    // left (left boundary) and eleven moves in total.
    @Test
    public void rookHasCorrectNumberOfLegalMoves() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        board.movePiece(currentBoard, 0, 0, 4, 3);
        Piece whiteRook = board.getPiece(4, 3);
        board.addRookMoves(whiteRook, 4, 3);
        assertEquals(11, board.getLegalMoves().size());
    }
    
    @Test
    public void initializedBishopDoesNotMove() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        Piece whiteBishop = board.getPiece(0, 2);
        board.addBishopMoves(whiteBishop, 0, 2);
        assertTrue(board.getLegalMoves().size() == 0);
    }
    
    // A white bishop is moved to square (y4, x3) in otherwise initialized board,
    // so the bishop should be able to move two steps up right (enemy pawn), 
    // two steps down right (own side pawn), two steps up left (enemy pawn) and
    // two steps down left (own side pawn) and eigth moves in total.
    @Test
    public void bishopHasCorrectNumberOfLegalMoves() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        board.movePiece(currentBoard, 0, 2, 4, 3);
        Piece whiteBishop = board.getPiece(4, 3);
        board.addBishopMoves(whiteBishop, 4, 3);
        assertEquals(8, board.getLegalMoves().size());
    }
    
    @Test
    public void initializedQueenDoesNotMove() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        Piece whiteQueen = board.getPiece(0, 3);
        board.addQueenMoves(whiteQueen, 0, 3);
        assertTrue(board.getLegalMoves().size() == 0);
    }
    
    // A white queen is moved to square (y4, x3) in otherwise initialized board,
    // so the queen should be able to move two steps up left (enemy pawn), two
    // steps up (enemy pawn), two steps up right (enemy pawn), four steps right
    // (right boundary), two steps down right (own side pawn), two steps down
    // (own side pawn), two steps down left (own side pawn) and three steps left
    // (left boundary) and 19 steps in total.
    @Test
    public void queenHasCorrectNumberOfLegalMoves() {
        board.initBoard();
        Piece[][] currentBoard = board.getCurrentBoard();
        board.movePiece(currentBoard, 0, 3, 4, 3);
        Piece whiteQueen = board.getPiece(4, 3);
        board.addQueenMoves(whiteQueen, 4, 3);
        assertEquals(19, board.getLegalMoves().size());
    }
}

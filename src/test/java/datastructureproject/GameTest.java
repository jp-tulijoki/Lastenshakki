/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import chess.model.Side;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tulijoki
 */
public class GameTest {
    private Game game;
    
    public GameTest() {
        this.game = new Game();
    }
    
    @Test
    public void initializedBoardContainsNoNullSquares() {
        game.initBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                assertNotNull(game.getPiece(y, x).getType());
            }
        }
    }
    
    @Test
    public void initializedPawnsAreOnTheFrontRow() {
        game.initBoard();
        for (int x = 0; x <= 7; x++) {
            assertEquals(Side.WHITE, game.getPiece(1, x).getSide());
            assertEquals(Type.PAWN, game.getPiece(1, x).getType());
            assertEquals(Side.BLACK, game.getPiece(6, x).getSide());
            assertEquals(Type.PAWN, game.getPiece(6, x).getType());
        }
    }
    
    @Test
    public void otherPiecesAreInCorrectSquaresWhenInitialized() {
        game.initBoard();
        for (int x = 0; x <=7; x++) {
            assertEquals(Side.WHITE, game.getPiece(0, x).getSide());
            assertEquals(Side.BLACK, game.getPiece(7, x).getSide());
        }
        assertTrue(game.getPiece(0, 0).getType() == Type.ROOK && game.getPiece(0, 7).getType() == Type.ROOK && game.getPiece(7, 0).getType() == Type.ROOK && game.getPiece(7, 7).getType() == Type.ROOK);
        assertTrue(game.getPiece(0, 1).getType() == Type.KNIGHT && game.getPiece(0, 6).getType() == Type.KNIGHT && game.getPiece(7, 1).getType() == Type.KNIGHT && game.getPiece(7, 6).getType() == Type.KNIGHT);
        assertTrue(game.getPiece(0, 2).getType() == Type.BISHOP && game.getPiece(0, 5).getType() == Type.BISHOP && game.getPiece(7, 2).getType() == Type.BISHOP && game.getPiece(7, 5).getType() == Type.BISHOP);
        assertTrue(game.getPiece(0, 3).getType() == Type.QUEEN && game.getPiece(7, 3).getType() == Type.QUEEN);
        assertTrue(game.getPiece(0, 4).getType() == Type.KING && game.getPiece(7, 4).getType() == Type.KING);
    }
    
    @Test
    public void unhinderedPawnsMoveOneStep() {
        game.initBoard();
        Piece whitePawn = game.getPiece(1, 0);
        Piece blackPawn = game.getPiece(6, 0);
        Piece[][] board = game.getCurrentBoard();
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addRegularPawnMove(board, moves, whitePawn, 1, 0);
        game.addRegularPawnMove(board, moves, blackPawn, 6, 0);
        Piece[][] whiteMove = moves.get(0);
        Piece[][] blackMove = moves.get(1);
        assertEquals(Type.PAWN, whiteMove[2][0].getType());
        assertEquals(Side.WHITE, whiteMove[2][0].getSide());
        assertEquals(Type.EMPTY, whiteMove[1][0].getType());
        assertEquals(Type.PAWN, blackMove[5][0].getType());
        assertEquals(Side.BLACK, blackMove[5][0].getSide());
        assertEquals(Type.EMPTY, blackMove[6][0].getType());
    }
    
    @Test
    public void unhinderedPawnsMoveTwoSteps() {
        game.initBoard();
        Piece whitePawn = game.getPiece(1, 0);
        Piece blackPawn = game.getPiece(6, 0);
        Piece[][] board = game.getCurrentBoard();
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addTwoStepPawnMove(board, moves, whitePawn, 1, 0);
        game.addTwoStepPawnMove(board, moves, blackPawn, 6, 0);
        Piece[][] whiteMove = moves.get(0);
        Piece[][] blackMove = moves.get(1);
        assertEquals(Type.PAWN, whiteMove[3][0].getType());
        assertEquals(Side.WHITE, whiteMove[3][0].getSide());
        assertEquals(Type.EMPTY, whiteMove[1][0].getType());
        assertEquals(Type.PAWN, blackMove[4][0].getType());
        assertEquals(Side.BLACK, blackMove[4][0].getSide());
        assertEquals(Type.EMPTY, blackMove[6][0].getType());
    }
    
    @Test
    public void twoStepMoveDoesNotEndInOccupiedSquare() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whitePawn = game.getPiece(1, 0);
        Piece blackPawn = game.getPiece(6, 0);
        game.movePiece(currentBoard, 0, 1, 3, 0); // white knight
        game.movePiece(currentBoard, 0, 0, 4, 0); // white rook
        Piece[][] board = game.getCurrentBoard();
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addTwoStepPawnMove(board, moves, whitePawn, 1, 0);
        game.addTwoStepPawnMove(board, moves, blackPawn, 6, 0);
        assertTrue(moves.isEmpty());
    }
    
    @Test
    public void hinderedPawnsDoNotMove() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whitePawn = game.getPiece(1, 0);
        Piece blackPawn = game.getPiece(6, 0);
        game.movePiece(currentBoard, 0, 1, 2, 0);
        game.movePiece(currentBoard, 7, 1, 5, 0);
        Piece[][] board = game.getCurrentBoard();
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addRegularPawnMove(board, moves, whitePawn, 1, 0);
        game.addTwoStepPawnMove(board, moves, whitePawn, 1, 0);
        game.addRegularPawnMove(board, moves, blackPawn, 6, 0);
        game.addTwoStepPawnMove(board, moves, blackPawn, 6, 0);
        assertTrue(moves.isEmpty());
    }
    
    @Test
    public void pawnsDoNotAttackOutOfBoundsOrEmptySquares() {
        game.initBoard();
        Piece whitePawn = game.getPiece(1, 0);
        Piece blackPawn = game.getPiece(6, 0);
        Piece[][] board = game.getCurrentBoard();
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addPawnAttack(board, moves, whitePawn, 1, 0);
        game.addPawnAttack(board, moves, blackPawn, 6, 0);
        assertTrue(moves.isEmpty());
    }
    
    @Test
    public void pawnsNoNotAttackOwnSidePieces() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whitePawn = game.getPiece(1, 1);
        Piece blackPawn = game.getPiece(6, 1);
        game.movePiece(currentBoard, 0, 1, 2, 0); //own side knight
        game.movePiece(currentBoard, 7, 1, 5, 0); //own side knight
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addPawnAttack(currentBoard, moves, whitePawn, 1, 1);
        game.addPawnAttack(currentBoard, moves, blackPawn, 6, 1);
        assertTrue(moves.isEmpty());
    }
    @Test
    public void pawnsAttackEnemies() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whitePawn = game.getPiece(1, 0);
        Piece blackPawn = game.getPiece(6, 0);
        game.movePiece(currentBoard, 7, 3, 2, 1); //enemy queen
        game.movePiece(currentBoard, 0, 3, 5, 1); //enemy queen
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addPawnAttack(currentBoard, moves, whitePawn, 1, 0);
        game.addPawnAttack(currentBoard, moves, blackPawn, 6, 0);
        Piece[][] whiteMove = moves.get(0);
        Piece[][] blackMove = moves.get(1);
        assertEquals(Type.PAWN, whiteMove[2][1].getType());
        assertEquals(Side.WHITE, whiteMove[2][1].getSide());
        assertEquals(Type.EMPTY, whiteMove[1][0].getType());
        assertEquals(Type.PAWN, blackMove[5][1].getType());
        assertEquals(Side.BLACK, blackMove[5][1].getSide());
        assertEquals(Type.EMPTY, blackMove[6][0].getType());
    }
    
    @Test
    public void enPassantWorksProperly() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 1, 4, 4, 4);
        game.movePiece(currentBoard, 6, 5, 4, 5);
        game.setEnPassant(game.getPiece(4, 5));
        Piece whitePawn = game.getPiece(4, 4);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addEnPassant(currentBoard, moves, whitePawn, 4, 4);
        assertEquals(1, moves.size());
        Piece[][] newBoard = moves.get(0);
        assertEquals(Side.WHITE, newBoard[5][5].getSide());
        assertEquals(Type.PAWN, newBoard[5][5].getType());
        assertEquals(Type.EMPTY, newBoard[4][5].getType());
    }
    
    @Test
    public void pawnPromotionOnNormalMoveWorksProperly() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        currentBoard[0][5] = new Piece(Type.EMPTY);
        currentBoard[0][6] = new Piece(Type.EMPTY);
        currentBoard[0][7] = new Piece(Type.EMPTY);
        game.movePiece(currentBoard, 6, 6, 1, 6);
        Piece blackPawn = game.getPiece(1, 6);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addAllPawnMoves(currentBoard, moves, blackPawn, 1, 6);
        assertTrue(moves.size() == 1);
        assertEquals(Type.QUEEN, moves.get(0)[0][6].getType());
        assertEquals(Side.BLACK, moves.get(0)[0][6].getSide());
    }
    
    @Test
    public void pawnPromotionOnAttackWorksProperly() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 1, 0, 6, 0);
        Piece whitePawn = game.getPiece(6, 0);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addAllPawnMoves(currentBoard, moves, whitePawn, 6, 0);
        assertTrue(moves.size() == 1);
        assertEquals(Type.QUEEN, moves.get(0)[7][1].getType());
        assertEquals(Side.WHITE, moves.get(0)[7][1].getSide());
    }
    
    @Test
    public void knightMovesToEmptyAndEnemySquares() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whiteKnight = game.getPiece(0, 1);
        game.movePiece(currentBoard, 7, 3, 2, 2); //enemy queen
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addKnightMoves(currentBoard, moves, whiteKnight, 0, 1);
        Piece[][] knightMove1 = moves.get(0);
        Piece[][] knightMove2 = moves.get(1);
        assertTrue(moves.size() == 2);
        assertEquals(Type.KNIGHT, knightMove1[2][2].getType());
        assertEquals(Type.EMPTY, knightMove1[0][1].getType());
        assertEquals(Type.PAWN, knightMove1[1][3].getType());
        assertEquals(Type.KNIGHT, knightMove2[2][0].getType());
        assertEquals(Type.EMPTY, knightMove2[0][1].getType());
        assertEquals(Type.PAWN, knightMove2[1][3].getType());
    }
    
    @Test
    public void initializedRookDoesNotMove() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whiteRook = game.getPiece(0, 0);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addRookMoves(currentBoard, moves, whiteRook, 0, 0);
        assertTrue(moves.isEmpty());
    }
    
    // A white rook is moved to square (y4,x3) in otherwise initialized board,
    // so the rook should be able to move two steps down (own side pawn),
    // two up (enemy pawn), four to the right (right boundary) and three to the
    // left (left boundary) and eleven moves in total.
    @Test
    public void rookHasCorrectNumberOfLegalMoves() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 0, 0, 4, 3);
        Piece whiteRook = game.getPiece(4, 3);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addRookMoves(currentBoard, moves, whiteRook, 4, 3);
        assertEquals(11, moves.size());
    }
    
    @Test
    public void initializedBishopDoesNotMove() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whiteBishop = game.getPiece(0, 2);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addBishopMoves(currentBoard, moves, whiteBishop, 0, 2);
        assertTrue(moves.isEmpty());
    }
    
    // A white bishop is moved to square (y4, x3) in otherwise initialized board,
    // so the bishop should be able to move two steps up right (enemy pawn), 
    // two steps down right (own side pawn), two steps up left (enemy pawn) and
    // two steps down left (own side pawn) and eigth moves in total.
    @Test
    public void bishopHasCorrectNumberOfLegalMoves() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 0, 2, 4, 3);
        Piece whiteBishop = game.getPiece(4, 3);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addBishopMoves(currentBoard, moves, whiteBishop, 4, 3);
        assertEquals(8, moves.size());
    }
    
    @Test
    public void initializedQueenDoesNotMove() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whiteQueen = game.getPiece(0, 3);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addQueenMoves(currentBoard, moves, whiteQueen, 0, 3);
        assertTrue(moves.isEmpty());
    }
    
    // A white queen is moved to square (y4, x3) in otherwise initialized board,
    // so the queen should be able to move two steps up left (enemy pawn), two
    // steps up (enemy pawn), two steps up right (enemy pawn), four steps right
    // (right boundary), two steps down right (own side pawn), two steps down
    // (own side pawn), two steps down left (own side pawn) and three steps left
    // (left boundary) and 19 steps in total.
    @Test
    public void queenHasCorrectNumberOfLegalMoves() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 0, 3, 4, 3);
        Piece whiteQueen = game.getPiece(4, 3);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addQueenMoves(currentBoard, moves, whiteQueen, 4, 3);
        assertEquals(19, moves.size());
    }
    
    @Test
    public void unhinderedwhiteCastlingWorksProperly() {
        Game game = new Game();
        Piece[][] board = game.getCurrentBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                board[y][x] = new Piece(Type.EMPTY);
            }
        }
        Piece whiteRook = new Piece(Type.ROOK, Side.WHITE);
        Piece whiteKing = new Piece(Type.KING, Side.WHITE);
        board[0][0] = whiteRook;
        board[0][4] = whiteKing;
        board[3][4] = new Piece(Type.BISHOP, Side.BLACK);
        game.checkWhiteCastling(board);
        assertTrue(game.getCastling()[4]);
        assertFalse(game.getCastling()[5]);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addWhiteCastling(board, moves, whiteKing, 0, 4);
        assertEquals(1, moves.size());
        Piece[][] castling = moves.get(0);
        assertEquals(whiteKing, castling[0][2]);
        assertEquals(whiteRook, castling[0][3]);
        assertEquals(Type.EMPTY, castling[0][0].getType());
        assertEquals(Type.EMPTY, castling[0][4].getType());
    }
    
    @Test
    public void unhinderedBlackCastlingWorksProperly() {
        Game game = new Game();
        Piece[][] board = game.getCurrentBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                board[y][x] = new Piece(Type.EMPTY);
            }
        }
        Piece blackRook = new Piece(Type.ROOK, Side.BLACK);
        Piece blackKing = new Piece(Type.KING, Side.BLACK);
        board[7][7] = blackRook;
        board[7][4] = blackKing;
        board[3][3] = new Piece(Type.BISHOP, Side.WHITE);
        game.checkBlackCastling(board);
        assertTrue(game.getCastling()[7]);
        assertFalse(game.getCastling()[6]);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addBlackCastling(board, moves, blackKing, 7, 4);
        assertEquals(1, moves.size());
        Piece[][] castling = moves.get(0);
        assertEquals(blackKing, castling[7][6]);
        assertEquals(blackRook, castling[7][5]);
        assertEquals(Type.EMPTY, castling[7][4].getType());
        assertEquals(Type.EMPTY, castling[7][7].getType());
    }
    
    @Test
    public void attackedKingCannotCastle() {
        Game game = new Game();
        Piece[][] board = game.getCurrentBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                board[y][x] = new Piece(Type.EMPTY);
            }
        }
        Piece whiteRook1 = new Piece(Type.ROOK, Side.WHITE);
        Piece whiteRook2 = new Piece(Type.ROOK, Side.WHITE);
        Piece whiteKing = new Piece(Type.KING, Side.WHITE);
        board[0][0] = whiteRook1;
        board[0][7] = whiteRook2;
        board[0][4] = whiteKing;
        board[4][0] = new Piece(Type.BISHOP, Side.BLACK);
        game.checkWhiteCastling(board);
        assertFalse(game.getCastling()[4]);
        assertFalse(game.getCastling()[5]);
    }
    
    @Test
    public void KingCannotCastleOverAttackedSquares() {
        Game game = new Game();
        Piece[][] board = game.getCurrentBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                board[y][x] = new Piece(Type.EMPTY);
            }
        }
        Piece blackRook1 = new Piece(Type.ROOK, Side.BLACK);
        Piece blackRook2 = new Piece(Type.ROOK, Side.BLACK);
        Piece blackKing = new Piece(Type.KING, Side.BLACK);
        board[7][0] = blackRook1;
        board[7][7] = blackRook2;
        board[7][4] = blackKing;
        board[6][4] = new Piece(Type.BISHOP, Side.WHITE);
        game.checkBlackCastling(board);
        assertFalse(game.getCastling()[6]);
        assertFalse(game.getCastling()[7]);
    }
    
    @Test
    public void initializedKingDoesNotMove() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece whiteKing = game.getPiece(0, 4);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addRegularKingMoves(currentBoard, moves, whiteKing, 0, 3);
        assertTrue(moves.isEmpty());
    }
    
    public void kingHasCorrectNumberOfLegalMoves() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 0, 4, 3, 4);
        Piece whiteKing = game.getPiece(3, 4);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addRegularKingMoves(currentBoard, moves, whiteKing, 3, 4);
        assertEquals(8, moves.size());
    }
    
    @Test
    public void bothKingsAreAliveInTheBeginning() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        assertFalse(game.isKingDead(currentBoard, Side.WHITE));
        assertFalse(game.isKingDead(currentBoard, Side.BLACK));
    }
    
    @Test
    public void deadKingIsDetected() {
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 0, 3, 7, 4);
        assertTrue(game.isKingDead(currentBoard, Side.BLACK));
    }
    
    @Test
    public void correctNumberOfMovesInTheBeginning() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        ArrayList<Piece[][]> moves = game.addAllLegalMoves(board, Side.WHITE);
        assertEquals(20, moves.size());
    }
    
}

package datastructureproject;

import chess.model.Side;
import org.junit.Test;
import static org.junit.Assert.*;

public class MoveSelectorTest {
    
    private Game game;
    private MoveSelector ms;
    
    public MoveSelectorTest() {
        this.game = new Game();
        this.ms = new MoveSelector(game, 2, false, 0.0, false, 0.0);
    }
    
    @Test
    public void bothPlayersHaveSameInitialValue() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        double value = ms.evaluateBoard(board);
        assertEquals(0, value, 0.1);
    }
    
    @Test
    public void moveSelectorReturnsMove() {
        game.initBoard();
        Piece[][] whiteMove = ms.getBestWhiteMove();
        assertTrue(whiteMove != null);
        Piece[][] blackMove = ms.getBestBlackMove();
        assertTrue(blackMove != null);
    }
    
    @Test
    public void normalMoveSelectorDoesNotFailIn20Rounds() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        for (int i = 1; i <= 20; i++) {
            board = ms.getBestWhiteMove();
            assertTrue(board != null);
            assertFalse(game.isKingDead(board, Side.BLACK));
            board = ms.getBestBlackMove();
            assertTrue(board != null);
            assertFalse(game.isKingDead(board, Side.WHITE));
        }
    }
    
    @Test 
    public void problemMoveOne() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        game.movePiece(board, 1, 6, 3, 6);
        Piece[][] move = ms.getBestBlackMove();
        assertTrue(move != null);
    }
    
    @Test
    public void problemMoveTwo() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        board[7][5] = new Piece(Type.EMPTY);
        game.movePiece(board, 1, 6, 6, 6);
        ChessboardList moves = new ChessboardList();
        game.addPawnAttack(board, moves, board[6][6], 6, 6);
        board = moves.getNextBoard();
        assertEquals(Type.BISHOP, board[7][7].getType());
        Piece[][] move = ms.getBestBlackMove();
        assertTrue(move != null);
    }
    
    @Test
    public void problemMoveThree() {
        Piece[][] board = game.getCurrentBoard();
        for (int y = 0; y <=7; y++) {
            for (int x = 0; x <= 7; x++) {
                board[y][x] = new Piece(Type.EMPTY);
            }
        }
        board[0][6] = new Piece(Type.KNIGHT, Side.WHITE);
        board[0][7] = new Piece(Type.ROOK, Side.WHITE);
        board[1][7] = new Piece(Type.PAWN, Side.WHITE);
        board[2][0] = new Piece(Type.KNIGHT, Side.WHITE);
        board[2][1] = new Piece(Type.PAWN, Side.WHITE);
        board[2][5] = new Piece(Type.PAWN, Side.WHITE);
        board[2][6] = new Piece(Type.KING, Side.WHITE);
        board[3][0] = new Piece(Type.PAWN, Side.WHITE);
        board[3][6] = new Piece(Type.BISHOP, Side.WHITE);
        board[4][6] = new Piece(Type.QUEEN, Side.BLACK);
        board[5][4] = new Piece(Type.PAWN, Side.BLACK);
        board[5][7] = new Piece(Type.KNIGHT, Side.BLACK);
        board[6][0] = new Piece(Type.PAWN, Side.BLACK);
        board[6][1] = new Piece(Type.PAWN, Side.BLACK);
        board[6][2] = new Piece(Type.QUEEN, Side.WHITE);
        board[6][3] = new Piece(Type.BISHOP, Side.BLACK);
        board[6][5] = new Piece(Type.PAWN, Side.BLACK);
        board[6][6] = new Piece(Type.PAWN, Side.BLACK);
        board[6][7] = new Piece(Type.PAWN, Side.BLACK);
        board[7][0] = new Piece(Type.ROOK, Side.BLACK);
        board[7][4] = new Piece(Type.KING, Side.BLACK);
        board[7][7] = new Piece(Type.ROOK, Side.BLACK);
        game.setCurrentBoard(board);
        ms.getBestBlackMove();
    }
   
    @Test
    public void problemMoveFour() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        board[1][6] = new Piece(Type.EMPTY);
        board[4][4] = new Piece(Type.KNIGHT, Side.BLACK);
        board[5][5] = new Piece(Type.PAWN, Side.BLACK);
        board[6][5] = new Piece(Type.EMPTY);
        board[6][7] = new Piece(Type.EMPTY);
        board[7][0] = new Piece(Type.EMPTY);
        board[7][1] = new Piece(Type.ROOK, Side.BLACK);
        board[7][6] = new Piece(Type.QUEEN, Side.WHITE);
        game.setCurrentBoard(board);
        Piece[][] move = ms.getBestBlackMove();
        assertTrue(move != null);
    }
    
    @Test
    public void problemMoveFive() {
        Piece[][] board = game.getCurrentBoard();
        for (int y = 0; y <=7; y++) {
            for (int x = 0; x <= 7; x++) {
                board[y][x] = new Piece(Type.EMPTY);
            }
        }
        board[0][3] = new Piece(Type.QUEEN, Side.WHITE);
        board[0][4] = new Piece(Type.KING, Side.WHITE);
        board[0][6] = new Piece(Type.KNIGHT, Side.WHITE);
        board[0][7] = new Piece(Type.ROOK, Side.WHITE);
        board[1][0] = new Piece(Type.BISHOP, Side.BLACK);
        board[1][2] = new Piece(Type.ROOK, Side.BLACK);
        board[1][5] = new Piece(Type.PAWN, Side.WHITE);
        board[1][6] = new Piece(Type.BISHOP, Side.WHITE);
        board[2][3] = new Piece(Type.PAWN, Side.WHITE);
        board[2][5] = new Piece(Type.PAWN, Side.WHITE);
        board[3][7] = new Piece(Type.PAWN, Side.WHITE);
        board[4][1] = new Piece(Type.KNIGHT, Side.WHITE);
        board[4][4] = new Piece(Type.PAWN, Side.BLACK);
        board[5][3] = new Piece(Type.PAWN, Side.BLACK);
        board[6][3] = new Piece(Type.KING, Side.BLACK);
        board[6][4] = new Piece(Type.KNIGHT, Side.BLACK);
        board[6][5] = new Piece(Type.PAWN, Side.BLACK);
        board[6][6] = new Piece(Type.PAWN, Side.BLACK);
        board[6][7] = new Piece(Type.PAWN, Side.BLACK);
        board[7][0] = new Piece(Type.QUEEN, Side.WHITE);
        game.setCurrentBoard(board);
        Piece[][] move = ms.getBestBlackMove();
        assertTrue(move != null);
    }
    
    @Test
    public void handicapValueIsNotExdeeded() {
        this.ms = new MoveSelector(game, 2, false, 0.0, true, -3.0);
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        for (int i = 1; i <= 5; i++) {
            board = ms.getBestWhiteMove();
            assertTrue(board != null);
            assertFalse(game.isKingDead(board, Side.BLACK));
            board = ms.getBestBlackMove();
            assertTrue(board != null);
            assertFalse(game.isKingDead(board, Side.WHITE));
        }
        board = game.getCurrentBoard();
        double boardValue = ms.evaluateBoard(board);
        assertTrue(boardValue > -3.5);
    }
    
    @Test
    public void illegalMovesAreRemovedFromLegalMovesList() {
        this.ms = new MoveSelector(game, 2, false, 0.0, false, 0.0);
        Piece[][] board = game.getCurrentBoard();
        for (int y = 0; y <=7; y++) {
            for (int x = 0; x <= 7; x++) {
                board[y][x] = new Piece(Type.EMPTY);
            }
        }
        board[0][0] = new Piece(Type.KING, Side.WHITE);
        board[2][0] = new Piece(Type.ROOK, Side.BLACK);
        board[1][1] = new Piece(Type.ROOK, Side.BLACK);
        ChessboardList moves = game.addAllMoves(board, Side.WHITE);
        assertEquals(3, moves.getTail());
        ChessboardList legalMoves = ms.filterLegalMoves(moves, Side.WHITE);
        assertEquals(1, legalMoves.getTail());
        assertEquals(Type.KING, legalMoves.getNextBoard()[1][1].getType());
    }
}

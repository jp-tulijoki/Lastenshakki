package chess.bot;

import chess.model.Side;
import org.junit.Test;
import static org.junit.Assert.*;
import datastructureproject.*;

public class BotTest {
    
    private TrainerBot bot;
    
    public BotTest() {
        this.bot = new TrainerBot();
    }
    
    @Test
    public void moveParserWorksProperly() {
        Game game = bot.getGame();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece[][] newBoard = game.copyBoard(currentBoard);
        game.movePiece(newBoard, 1, 0, 3, 0);
        String move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("a2a4", move);
    }
    
    @Test
    public void moveParserDetectsCastling() {
        Game game = new Game();
        Piece[][] currentBoard = game.getCurrentBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                currentBoard[y][x] = new Piece(Type.EMPTY);
            }
        }
        Piece blackRook = new Piece(Type.ROOK, Side.BLACK);
        Piece blackKing = new Piece(Type.KING, Side.BLACK);
        currentBoard[7][7] = blackRook;
        currentBoard[7][4] = blackKing;
        game.setCurrentBoard(currentBoard);
        Piece[][] newBoard = game.copyBoard(currentBoard);
        game.movePiece(newBoard, 7, 7, 7, 5);
        game.movePiece(newBoard, 7, 4, 7, 6);
        String move = bot.parseMove(Side.BLACK, currentBoard, newBoard);
        assertEquals("e8g8", move);
    }
    
    @Test
    public void moveParserDetectsPromotion() {
        Game game = new Game();
        Piece[][] currentBoard = game.getCurrentBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                currentBoard[y][x] = new Piece(Type.EMPTY);
            }
        }
        currentBoard[6][0] = new Piece(Type.PAWN, Side.WHITE);
        game.setCurrentBoard(currentBoard);
        ChessboardList moves = new ChessboardList();
        game.addRegularPawnMove(currentBoard, moves, currentBoard[6][0], 6, 0);
        Piece[][] newBoard = moves.getNextBoard();
        String move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("a7a8b", move);
        newBoard = moves.getNextBoard();
        move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("a7a8n", move);
        newBoard = moves.getNextBoard();
        move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("a7a8q", move);
        newBoard = moves.getNextBoard();
        move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("a7a8r", move);
    }
    
    @Test
    public void moveParserDetectsEnPassant() {
        Game game = new Game();
        Piece[][] currentBoard = game.getCurrentBoard();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                currentBoard[y][x] = new Piece(Type.EMPTY);
            }
        }
        currentBoard[4][0] = new Piece(Type.PAWN, Side.WHITE);
        currentBoard[4][1] = new Piece(Type.PAWN, Side.BLACK);
        currentBoard[4][7] = new Piece(Type.PAWN, Side.WHITE);
        currentBoard[4][6] = new Piece(Type.PAWN, Side.BLACK);
        currentBoard[3][0] = new Piece(Type.PAWN, Side.BLACK);
        currentBoard[3][1] = new Piece(Type.PAWN, Side.WHITE);
        currentBoard[3][7] = new Piece(Type.PAWN, Side.BLACK);
        currentBoard[3][6] = new Piece(Type.PAWN, Side.WHITE);
        Piece newBoard[][] = game.copyBoard(currentBoard);
        game.movePiece(newBoard, 4, 0, 5, 1);
        newBoard[4][1] = new Piece(Type.EMPTY);
        String move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("a5b6", move);
        currentBoard = game.copyBoard(newBoard);
        game.movePiece(newBoard, 4, 7, 5, 6);
        newBoard[4][6] = new Piece(Type.EMPTY);
        move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("h5g6", move);
        currentBoard = game.copyBoard(newBoard);
        game.movePiece(newBoard, 3, 0, 2, 1);
        newBoard[3][1] = new Piece(Type.EMPTY);
        move = bot.parseMove(Side.BLACK, currentBoard, newBoard);
        assertEquals("a4b3", move);
        currentBoard = game.copyBoard(newBoard);
        game.movePiece(newBoard, 3, 7, 2, 6);
        newBoard[3][6] = new Piece(Type.EMPTY);
        move = bot.parseMove(Side.BLACK, currentBoard, newBoard);
        assertEquals("h4g3", move);
    }
    
    @Test
    public void castlingArrayUpdatesQueensideRookMoves() {
        Game game = bot.getGame();
        game.initBoard();
        bot.updateLatestMove("a1a3");
        assertFalse(game.getCastling()[0]);
        assertTrue(game.getCastling()[1]);
        assertTrue(game.getCastling()[2]);
        assertTrue(game.getCastling()[3]);
        bot.updateLatestMove("a8a6");
        assertFalse(game.getCastling()[0]);
        assertTrue(game.getCastling()[1]);
        assertFalse(game.getCastling()[2]);
        assertTrue(game.getCastling()[3]);
    }
    
    @Test
    public void castlingArrayUpdatesKingsideRookMoves() {
        Game game = bot.getGame();
        game.initBoard();
        bot.updateLatestMove("h1h3");
        assertTrue(game.getCastling()[0]);
        assertFalse(game.getCastling()[1]);
        assertTrue(game.getCastling()[2]);
        assertTrue(game.getCastling()[3]);
        bot.updateLatestMove("h8h6");
        assertTrue(game.getCastling()[0]);
        assertFalse(game.getCastling()[1]);
        assertTrue(game.getCastling()[2]);
        assertFalse(game.getCastling()[3]);
    }
    
    @Test
    public void castlingArrayUpdatesKingMoves() {
        Game game = bot.getGame();
        game.initBoard();
        bot.updateLatestMove("e1e3");
        assertFalse(game.getCastling()[0]);
        assertFalse(game.getCastling()[1]);
        assertTrue(game.getCastling()[2]);
        assertTrue(game.getCastling()[3]);
        bot.updateLatestMove("e8e6");
        assertFalse(game.getCastling()[0]);
        assertFalse(game.getCastling()[1]);
        assertFalse(game.getCastling()[2]);
        assertFalse(game.getCastling()[3]);
    }
    
    @Test
    public void moveUpdaterWorksProperly() {
        Game game = bot.getGame();
        game.initBoard();
        bot.updateLatestMove("a2a4");
        Piece[][] currentBoard = game.getCurrentBoard();
        assertEquals(Type.EMPTY, game.getPiece(1, 0).getType());
        assertEquals(Type.PAWN, game.getPiece(3, 0).getType());
        assertEquals(Side.WHITE, game.getPiece(3, 0).getSide());
        Piece whitePawn = game.getPiece(3, 0);
        assertEquals(whitePawn, game.getEnPassant());
    }
    
    @Test
    public void enPassantIsNullIfNoTwoSquarePawnMove() {
        Game game = bot.getGame();
        game.initBoard();
        bot.updateLatestMove("b1a3");
        assertEquals(null, game.getEnPassant());
        bot.updateLatestMove("d2d3");
        assertEquals(null, game.getEnPassant());
    }
    
    @Test
    public void pawnPromotionIsDoneAccordingtoUCI() {
        Game game = bot.getGame();
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        game.movePiece(board, 1, 6, 6, 6);
        game.setCurrentBoard(board);
        bot.updateLatestMove("g7h8q");
        assertEquals(Type.QUEEN, game.getCurrentBoard()[7][7].getType());
        assertEquals(Side.WHITE, game.getCurrentBoard()[7][7].getSide());
        
    }
    
    @Test
    public void pawnPromotionTestTwo() {
        Game game = bot.getGame();
        game.initBoard();
        Piece[][] currentBoard = game.getCurrentBoard();
        game.movePiece(currentBoard, 1, 7, 6, 7);
        game.setCurrentBoard(currentBoard);
        ChessboardList moves = new ChessboardList();
        game.addPawnAttack(currentBoard, moves, currentBoard[6][7], 6, 7);
        moves.getNextBoard();
        moves.getNextBoard();
        Piece[][] newBoard = moves.getNextBoard();
        String move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("h7g8q", move);
        bot.updateLatestMove(move);
        assertEquals(Type.QUEEN, game.getCurrentBoard()[7][6].getType());
        assertEquals(Side.WHITE, game.getCurrentBoard()[7][6].getSide());
    }  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chess.bot;

import chess.model.Side;
import org.junit.Test;
import static org.junit.Assert.*;
import datastructureproject.*;

/**
 *
 * @author tulijoki
 */
public class BotTest {
    
    private TrainerBot bot;
    
    public BotTest() {
        this.bot = new TrainerBot();
    }
    
    @Test
    public void moveParserWorksProperly() {
        Game game = bot.getGame();
        Piece[][] currentBoard = game.getCurrentBoard();
        Piece[][] newBoard = game.copyCurrentBoard();
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
        Piece[][] newBoard = game.copyCurrentBoard();
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
        Piece[][] newBoard = game.copyCurrentBoard();
        game.movePiece(newBoard, 6, 0, 7, 0);
        String move = bot.parseMove(Side.WHITE, currentBoard, newBoard);
        assertEquals("a7a8q", move);
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
}

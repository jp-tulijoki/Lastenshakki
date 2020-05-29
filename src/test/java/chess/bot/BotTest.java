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
}

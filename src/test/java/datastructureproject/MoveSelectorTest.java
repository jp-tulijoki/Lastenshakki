/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import chess.model.Side;
import java.util.ArrayList;
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
public class MoveSelectorTest {
    
    private Game game;
    private MoveSelector ms;
    
    public MoveSelectorTest() {
        this.game = new Game();
        this.ms = new MoveSelector(game);
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
        Piece[][] blackMove = ms.getBestBlackMove(1);
        assertTrue(blackMove != null);
    }
    
    @Test
    public void moveSelectorDoesNotFailIn20Rounds() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        for (int i = 1; i <= 20; i++) {
            board = ms.getBestWhiteMove();
            assertTrue(board != null);
            assertFalse(game.isKingDead(board, Side.BLACK));
            board = ms.getBestBlackMove(1);
            assertTrue(board != null);
            assertFalse(game.isKingDead(board, Side.WHITE));
        }
    }
    
    @Test 
    public void problemMoveOne() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        game.movePiece(board, 1, 6, 3, 6);
        Piece[][] move = ms.getBestBlackMove(1);
        assertTrue(move != null);
    }
    
    @Test
    public void problemMoveTwo() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        board[7][5] = new Piece(Type.EMPTY);
        game.movePiece(board, 1, 6, 6, 6);
        ArrayList<Piece[][]> moves = new ArrayList();
        game.addPawnAttack(board, moves, board[6][6], 6, 6);
        board = moves.get(0);
        assertEquals(Type.QUEEN, board[7][7].getType());
        Piece[][] move = ms.getBestBlackMove(2);
        assertTrue(move != null);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

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
        Piece[][] move = ms.getBestBlackMove();
        assertTrue(move != null);
    }
    
}

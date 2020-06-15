/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import chess.model.Side;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tulijoki
 */
public class ChessboardListTest {
    private Game game;
    private ChessboardList list;
    
    public ChessboardListTest() {
        this.game = new Game();
        this.list = new ChessboardList();
    }
    
    @Test
    public void storedChessboardIsRetrievedCorrectly() {
        game.initBoard();
        Piece[][] board = game.getCurrentBoard();
        list.add(board);
        Piece[][] retrievedBoard = list.getNextBoard();
        for (int x = 0; x <= 7; x++) {
            assertTrue(retrievedBoard[0][x].getSide() == Side.WHITE);
            assertTrue(retrievedBoard[1][x].getSide() == Side.WHITE);
            assertTrue(retrievedBoard[1][x].getType() == Type.PAWN);
            assertTrue(retrievedBoard[7][x].getSide() == Side.BLACK);
            assertTrue(retrievedBoard[6][x].getSide() == Side.BLACK);
            assertTrue(retrievedBoard[6][x].getType() == Type.PAWN);
            for (int y = 2; y <= 5; y++) {
                assertTrue(retrievedBoard[y][x].getType() == Type.EMPTY);
            }
            assertTrue(retrievedBoard[0][4].getType() == Type.KING);
            assertTrue(retrievedBoard[7][3].getType() == Type.QUEEN);
        }
    }
    
//    @Test
//    public void ArrayIsGrownWhenNecessary() {
//        for (int i = 1; i < 40; i++) {
//            list.add(new Piece[8][8]);
//        }
//        assertEquals(40, list.getSize());
//        list.add(new Piece[8][8]);
//        assertEquals(80, list.getSize());
//    }
    
}

package datastructureproject;

import chess.model.Side;

/**
 * The performance test for the algorithm.
 * 
 */
public class PerformanceTest {

    public static void main(String[] args) {
        Game game = new Game();
        MoveSelector ms = new MoveSelector(game);
        game.initBoard();
        long start = System.nanoTime();
        for (int i = 1; i <= 50; i++) {
            Piece[][] board = game.getCurrentBoard();
            board = ms.getBestWhiteMove();
            printBoard(board);
            game.setCurrentBoard(board);
            board = ms.getBestBlackMove();
            printBoard(board);
            game.setCurrentBoard(board);
        }
        long end = System.nanoTime();
        System.out.println((end - start) / 1e9 + " sec.");
    }
    
    public static void printBoard(Piece[][] board) {
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x <= 7; x++) {
                Side side = board[y][x].getSide();
                if (side != null) {
                    System.out.print(side);
                }
                System.out.print(board[y][x].getType());
                if (x != 7) {
                    System.out.print(" | ");
                }    
            }
            System.out.println("");
        }
    }

}

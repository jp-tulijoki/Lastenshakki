package datastructureproject;

import chess.model.Side;

/**
 * The performance test for the algorithm.
 * 
 */
public class PerformanceTest {

    public static void main(String[] args) {
        testEvaluationTools();
        test50moves(1);
        test50moves(2);
        test50moves(3);
    }
    
    public static void testEvaluationTools() {
        Game game = new Game();
        game.initBoard();
        MoveSelector ms = new MoveSelector(game, 2, false, 0.0, false, 0.0);
        Piece[][] board = null;
        long time = 0;
        for (int i = 1; i <= 10; i++) {  
            board = ms.getBestWhiteMove();
            game.setCurrentBoard(board);
            long start = System.nanoTime();
            ms.evaluateBoard(board);
            long end = System.nanoTime();
            time += end - start;
            board = ms.getBestBlackMove();
            game.setCurrentBoard(board);
            start = System.nanoTime();
            ms.evaluateBoard(board);
            end = System.nanoTime();
            time += end - start;
        }
        System.out.println(time / 20 + " ns");
        printBoard(board);
    }
    
    public static void test50moves(int depth) {     
        Game game = new Game();
        game.initBoard();
        MoveSelector ms = new MoveSelector(game, depth, false, 0.0, false, 0.0);
        Piece[][] board = null;
        long start = System.nanoTime();
        for (int i = 1; i <= 50; i++) {  
            board = ms.getBestWhiteMove();
            game.setCurrentBoard(board);
            board = ms.getBestBlackMove();
            game.setCurrentBoard(board);
        }
        long end = System.nanoTime();
        System.out.println((end - start) / 1e9 + " sec");
        board = game.getCurrentBoard();
        printBoard(board);
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

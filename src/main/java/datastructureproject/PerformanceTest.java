package datastructureproject;

import chess.model.Side;

/**
 * The performance test for the move selection algorithm.
 */
public class PerformanceTest {

    public static void main(String[] args) {
        testEvaluationTools();
        test50moves(1);
        test50moves(2);
        test50moves(3);
    }
    
    /**
     * This method counts the average speed of one board evaluation.
     */
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
    
    /**
     * This method counts the duration of selection 50 consecutive moves for
     * each side, 100 moves in total.
     * @param depth the depth of the recursion of the minimax algorithm. 
     */
    public static void test50moves(int depth) {     
        Game game = new Game();
        game.initBoard();
        MoveSelector ms = new MoveSelector(game, depth, false, 0.0, false, 0.0);
        Piece[][] board = null;
        int moves = 0;
        long start = System.nanoTime();
        for (int i = 1; i <= 50; i++) {  
            board = ms.getBestWhiteMove();
            if (board == null) {
                break;
            }
            moves++;
            game.setCurrentBoard(board);
            board = ms.getBestBlackMove();
            if (board == null) {
                break;
            }
            moves++;
            game.setCurrentBoard(board);
        }
        long end = System.nanoTime();
        System.out.println((end - start) / 1e9 + " sec");
        System.out.println("Moves made: " + moves);
        board = game.getCurrentBoard();
        printBoard(board);
    }
    
    /**
     * This method prints the board status. Used for controlling that moves are
     * counted for a valid game, i.e. both kings are still alive. 
     * @param board 
     */
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

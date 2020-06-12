package datastructureproject;

import chess.bot.ChessBot;
import chess.bot.TrainerBot;
import chess.engine.GameState;
import chess.model.Side;
import java.util.ArrayList;
import java.util.List;

/**
 * Use this class to write performance tests for your bot.
 * 
 */
public class PerformanceTest {

    private List<GameState> gsList = new ArrayList();

    public void setGsList(List<GameState> gsList) {
        this.gsList = gsList;
    }


    public static void main(String[] args) {
        Game game = new Game();
        MoveSelector ms = new MoveSelector(game);
        game.initBoard();
        long start = System.nanoTime();
        for (int i = 1; i <= 50; i++) {
            Piece[][] board = game.getCurrentBoard();
            board = ms.getBestWhiteMove();
            game.setCurrentBoard(board);
            board = ms.getBestBlackMove();
            game.setCurrentBoard(board);
        }
        long end = System.nanoTime();
        System.out.println((end-start)/1e9 + " sec.");
        
        for (int y = 7; y >= 0; y--) {
            for (int x = 0; x <= 7; x++) {
                Piece[][] board = game.getCurrentBoard();
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

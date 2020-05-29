package chess.bot;

import chess.engine.GameState;
import chess.model.Side;
import datastructureproject.*;
import java.util.ArrayList;
import java.util.Random;

public class TrainerBot implements ChessBot {
    
    private Game game;
    
    private Random r;

    public TrainerBot() {
        this.game = new Game();
        game.initBoard();
        this.r = new Random();
    }

    public Game getGame() {
        return game;
    }
    
    public String parseMove(Piece[][] currentBoard, Piece[][] newBoard) {
        String start = "";
        String end = "";
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                Piece currentBoardPiece = currentBoard[y][x];
                Piece newBoardPiece = newBoard[y][x];
                if (currentBoardPiece != newBoardPiece) {
                    if (newBoardPiece.getType() == Type.EMPTY) {
                        start += (char)(x + 97) + String.valueOf(y + 1);
                    } else {
                        end += (char)(x + 97) + String.valueOf(y + 1);
                    }
                }
            }
        }
        String move = start + end;
        return move;
    }
    
    @Override
    public String nextMove(GameState gamestate) {
        if (gamestate.getMoveCount() != 0) {
            String latestMove = gamestate.getLatestMove();
            updateLatestMove(latestMove);
        }
        if (gamestate.playing == Side.BLACK) {
            ArrayList<Piece[][]> moves = game.addAllLegalMoves(Side.BLACK);
            int random = r.nextInt(moves.size());
            Piece[][] newBoard = moves.get(random);
            String move = parseMove(game.getCurrentBoard(), newBoard);
            updateLatestMove(move);
            return move;
        }
        return null;
    }
    
    public void updateLatestMove(String move) {
        Piece[][] board = game.copyCurrentBoard();
        int currentY = Character.getNumericValue(move.charAt(1)) - 1;
        int currentX = (int)move.charAt(0) - 97;
        int newY = Character.getNumericValue(move.charAt(3)) - 1;
        int newX = (int)move.charAt(2) - 97;
        game.movePiece(board, currentY, currentX, newY, newX);
        game.setCurrentBoard(board);
    }
    
}

package chess.bot;

import chess.engine.GameState;
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
        Piece[][] currentBoard = game.getCurrentBoard();
        game.addAllLegalMoves(gamestate.playing);
        ArrayList<Piece[][]> legalMoves = game.getLegalMoves();
        int random = r.nextInt(legalMoves.size());
        Piece[][] newBoard = legalMoves.get(random);
        String move = parseMove(currentBoard, newBoard);
        return move;
    }
    
    public void updateLatestMove(String move) {
        Piece[][] board = game.getCurrentBoard();
        int currentY = Character.getNumericValue(move.charAt(1)) - 1;
        int currentX = (int)move.charAt(0) - 97;
        int newY = Character.getNumericValue(move.charAt(3)) - 1;
        int newX = (int)move.charAt(2) - 97;
        game.movePiece(board, currentY, currentX, newY, newX);
        game.setCurrentBoard(board);
    }
    
}

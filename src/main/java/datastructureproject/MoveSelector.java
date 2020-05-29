/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import chess.model.Side;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class takes care of evaluation of board situations and selects a 
 * suitable next move for the chess bot.
 *
 */
public class MoveSelector {
    
    private Game game;

    public MoveSelector(Game game) {
        this.game = game;
    }
    
    /**
     * This method evaluates the given chessboard situation. The main principles
     * for piece values and mobility bonus can be found here:
     * https://www.chessprogramming.org/Evaluation. In addition to these, the
     * method gives a bonus for a bishop pair and pawns close to promotion.
     * @param board the evaluated board situation
     * @return returns an evaluation of the given board in which positive value
     * indicates a stronger white player and negative value a stronger black 
     * player.
     */
    public double evaluateBoard(Piece[][] board) {
        double whiteValue = 0;
        double blackValue = 0;
        int whiteBishops = 0;
        int blackBishops = 0;
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                Piece piece = board[y][x];
                if (piece.getType() == Type.EMPTY) {
                    continue;
                }
                if (piece.getSide() == Side.WHITE) {
                    whiteValue += piece.getType().getValue();
                    whiteValue += countMobilityBonus(board, y, x);
                    if (piece.getType() == Type.BISHOP) {
                        whiteBishops++;
                    }
                } else {
                    blackValue += piece.getType().getValue();
                    blackValue += countMobilityBonus(board, y, x);
                    if (piece.getType() == Type.BISHOP) {
                        blackBishops++;
                    }
                }
            }
        }
        if (whiteBishops == 2) {
            whiteValue += 1;
        }
        if (blackBishops == 2) {
            blackValue += 1;
        }
        return whiteValue - blackValue;
    }
    
    /**
     * This method counts mobility bonus used in board evaluation including
     * special situations concerning pawn pieces (blocked, isolated, doubled and
     * close to promotion).
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated piece
     * @param x the x-coordinate of the evaluated piece
     * @return returns mobility bonus as a double value
     */
    public double countMobilityBonus(Piece[][] board, int y, int x) {
        Piece piece = board[y][x];
        double value = 0;
        ArrayList<Piece[][]> moves = game.getOnePieceMoves(board, y, x);
        if (piece.getType() == Type.PAWN) {
            if (moves.isEmpty()) {
                value -= 0.5;
            }
            if (checkPawnIsolation(board, y, x)) {
                value -= 0.5;
            }
            if (checkDoubledPawns(board, y, x)) {
                value -= 0.5;
            }
            value += addCloseToPromotionBonus(board, y, x);
        }
        value += moves.size() * 0.1;
        return value;
    }
    
    /**
     * This method checks if a certain pawn has no adjaent friendly pieces.
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated pawn
     * @param x the x-coordinate of the evaluated pawn
     * @return returns false if the pawn has an adjacent frindly piece and true
     * if not.
     */
    public boolean checkPawnIsolation(Piece[][] board, int y, int x) {
        Piece pawn = board[y][x];
        for (int y2 = y - 1; y2 <= y + 1; y2++) {
            for (int x2 = x - 1; x2 <= x + 1; x2++) {
                if (x2 == y && x2 == x) {
                    continue;
                }
                if (y2 < 0 || y2 > 7 || x2 < 0 || x2 > 7) {
                    continue;
                }
                if (board[y2][x2].getSide() == pawn.getSide()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * This method checks if there is a friendly pawn in the same file (column).
     * To avoid double penalties, only ranks greater than the current rank
     * are checked.
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated pawn
     * @param x the x-coordinate of the evaluated pawn
     * @return returns true if there are doubled pawns and otherwise false
     */
    public boolean checkDoubledPawns(Piece[][] board, int y, int x) {
        Piece pawn = board[y][x];
        for (int y2 = y + 1; y2 <= 7; y2++) {
            if (board[y2][x].getType() == pawn.getType() && board[y2][x].getSide() == pawn.getSide()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * This method counts a bonus for pawns close to promotion.
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated pawn
     * @param x the x-coordinate of the evaluated pawn
     * @return returns 1 if the pawn has two steps to promotion, 2 if only one 
     * step and otherwise 0.
     */
    public int addCloseToPromotionBonus(Piece[][] board, int y, int x) {
        Piece pawn = board[y][x];
        if (pawn.getSide() == Side.WHITE && y == 5) {
            return 1;
        }
        if (pawn.getSide() == Side.WHITE && y == 6) {
            return 2;
        }
        if (pawn.getSide() == Side.BLACK && y == 2) {
            return 1;
        }
        if (pawn.getSide() == Side.BLACK && y == 1) {
            return 2;
        }
        return 0;
    }
    
    public Side getOppositeSide(Side side) {
        if (side == Side.WHITE) {
            return Side.BLACK;
        } else {
            return Side.WHITE;
        }
    }
    
    /**
     * This method selects the next move for the bot. (At this point, it selects
     * a random legal move.)
     * @param side the side of the player
     * @return returns a move as a chessboard representation.
     */
    public Piece[][] selectMove(Side side) {
        if (side == Side.BLACK) {
            game.checkBlackCastling();
        } else {
            game.checkWhiteCastling();
        }
        ArrayList<Piece[][]> moves = game.addAllLegalMoves(side);
//        Collections.shuffle(moves);
        Piece[][] currentBoard = game.copyCurrentBoard();
        Piece[][] selectedMove = null;
        
        for (Piece[][] move : moves) {
            boolean illegalMove = false;
            game.setCurrentBoard(move);
            ArrayList<Piece[][]> oppositeMoves = game.addAllLegalMoves(getOppositeSide(side));
            for (Piece[][] oppositeMove : oppositeMoves) {
                if (game.isKingDead(oppositeMove, side)) {
                    illegalMove = true;
                    break;
                }
            }
            if (illegalMove) {
                continue;
            } else {
                selectedMove = move;
                break;
            }
        }
        game.setCurrentBoard(currentBoard);
        return selectedMove;
    }

}

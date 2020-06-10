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
    private double[][] positionBonus;
    

    public MoveSelector(Game game) {
        this.game = game;
        this.positionBonus = new double[][]{{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0},
        {0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0},
        {0.0, 0.1, 0.2, 0.2, 0.2, 0.2, 0.1, 0.0},
        {0.0, 0.1, 0.2, 0.25, 0.25, 0.2, 0.1, 0.0},
        {0.0, 0.1, 0.2, 0.25, 0.25, 0.2, 0.1, 0.0},
        {0.0, 0.1, 0.2, 0.2, 0.2, 0.2, 0.1, 0.0},
        {0.0, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.0},
        {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0}};
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
                Type type = piece.getType();
                Side side = piece.getSide();
                if (type == Type.EMPTY) {
                    continue;
                }
                if (piece.getSide() == Side.WHITE) {
                    
                    whiteValue += type.getValue();
//                    whiteValue += countMobilityBonus(board, y, x);
                    whiteValue += countPositionBonus(type, y, x);
                    if (piece.getType() == Type.BISHOP) {
                        whiteBishops++;
                    }
                } else {
                    blackValue += piece.getType().getValue();
//                    blackValue += countMobilityBonus(board, y, x);
                    blackValue += countPositionBonus(type, y, x);
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
    
    public double countPositionBonus(Type type, int y, int x) {
        double value = 0;
        if (type == Type.BISHOP || type == Type.KNIGHT || type == Type.QUEEN || type == Type.ROOK) {
            value += type.getValue() * this.positionBonus[y][x];
        }
        return value;
    }
    
    
    /**
     * (not in use at the moment) This method counts mobility bonus used in board evaluation including
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
     * (not in use at the moment) This method checks if a certain pawn has no adjaent friendly pieces.
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
     * (not in use at the moment) This method checks if there is a friendly pawn in the same file (column).
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
     * (not in use at the moment) This method counts a bonus for pawns close to promotion.
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated pawn
     * @param x the x-coordinate of the evaluated pawn
     * @return returns 1 if the pawn has two steps to promotion, 2 if only one 
     * step and otherwise 0.
     */
    public int addCloseToPromotionBonus(Piece[][] board, int y, int x) {
        Piece pawn = board[y][x];
        if (pawn.getSide() == Side.WHITE) {
            if (y == 5) {
                return 1;
            } else if (y == 6) {
                return 2;
            }
        } else {
            if (y == 2) {
                return 1;
            } else if (y == 1) {
                return 2;
            }
        }
        return 0;
    }
    
    /**
     * This method is the max part of the minimax algorithm.
     * @param board the given chessboard to be analyzed.
     * @param depth the depth of the recursion.
     * @param alpha the alpha variable of alpha-beta-pruning.
     * @param beta the beta variable of alpha-beta-pruning.
     * @return returns max board value, i.e. best value for the white player
     */
    public double maxBoardValue(Piece[][] board, int depth, double alpha, double beta) {
        if (game.isKingDead(board, Side.WHITE)) {
            return -99999.99;
        }
        if (game.isKingDead(board, Side.BLACK)) {
            return 99999.99;
        }
        if (depth == 0) {
            return evaluateBoard(board);
        }
        double bestValue = -99999.99;
        ArrayList<Piece[][]> moves = game.addAllLegalMoves(board, Side.WHITE);
        for (Piece[][] move : moves) {
            double value = minBoardValue(move, depth - 1, alpha, beta);
            bestValue = Math.max(value, bestValue);
            alpha = Math.max(alpha, bestValue);
            if (beta <= alpha) {
                break;
            }
        }
        return bestValue;
    }
    

    /**
     * This method is the min part of the minimax algorithm.
     * @param board the given chessboard to be analyzed.
     * @param depth the depth of the recursion.
     * @param alpha the alpha variable of alpha-beta-pruning.
     * @param beta the beta variable of alpha-beta-pruning.
     * @return returns min board value, i.e. best value for the black player
     */    
    public double minBoardValue(Piece[][] board, int depth, double alpha, double beta) {
        if (game.isKingDead(board, Side.WHITE)) {
            return -99999.99;
        }
        if (game.isKingDead(board, Side.BLACK)) {
            return 99999.99;
        }
        if (depth == 0) {
            return evaluateBoard(board);
        }    
        double bestValue = 99999.99;
        ArrayList<Piece[][]> moves = game.addAllLegalMoves(board, Side.BLACK);
        for (Piece[][] move : moves) {
            double value = maxBoardValue(move, depth - 1, alpha, beta);
            bestValue = Math.min(value, bestValue);
            beta = Math.min(beta, bestValue);
            if (beta <= alpha) {
                break;
            }
        }
        return bestValue;
    }
    
    /**
     * This method selects the best move for the white player.
     * @return returns a chessboard representation of the best move
     */
    public Piece[][] getBestWhiteMove() {
        Piece[][] board = game.copyBoard(game.getCurrentBoard());
        game.checkWhiteCastling(board);
        ArrayList<Piece[][]> moves = game.addAllLegalMoves(board, Side.WHITE);
        double bestValue = -99999.99;
        Piece[][] bestMove = null;
        for (Piece[][] move : moves) {
            double value = minBoardValue(move, 2, -99999, 99999);
            if (value > bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }
    
    /**
     * This method selects the best move for the black player.
     * @return returns a chessboard representation of the best move
     */
    public Piece[][] getBestBlackMove() {
        Piece[][] board = game.copyBoard(game.getCurrentBoard());
        game.checkBlackCastling(board);
        ArrayList<Piece[][]> moves = game.addAllLegalMoves(board, Side.BLACK);
        double bestValue = 99999.99;
        Piece[][] bestMove = null;
        for (Piece[][] move : moves) {
            double value = maxBoardValue(move, 3, -99999.99, 99999.99);
            if (value < bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }
    
    public Side getOppositeSide(Side side) {
        if (side == side.WHITE) {
            return Side.BLACK;
        } else {
            return Side.WHITE;
        }
    }
    
    /**
     * This method selects the random move for the bot (for testing purposes). 
     * @param side the side of the player
     * @return returns a move as a chessboard representation.
     */
    public Piece[][] selectRandomMove(Side side) {
        Piece[][] currentBoard = game.getCurrentBoard();
        ArrayList<Piece[][]> moves = game.addAllLegalMoves(currentBoard, side);
        Collections.shuffle(moves);
        
        Piece[][] selectedMove = null;
        
        for (Piece[][] move : moves) {
            boolean illegalMove = false;
            ArrayList<Piece[][]> oppositeMoves = game.addAllLegalMoves(move, getOppositeSide(side));
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
        return selectedMove;
    }
}

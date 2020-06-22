/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import chess.model.Side;

/**
 *  
 * This class takes care of evaluation of board situations and selects a 
 * suitable next move for the chess bot.
 *
 */
public class MoveSelector {
    
    private Game game;
    private MathUtils math;
    private int depth;
    private boolean whiteHandicap;
    private double whiteMaxValue;
    private boolean blackHandicap;
    private double blackMinValue;
    
    /**
     * The constructor for this class.
     * @param game the game object for which the move selector makes the next 
     * move
     * @param whiteHandicap defines if white plays with handicap
     * @param whiteMaxValue sets the max board value white may accomplish with
     * next move. The larger the better. Does not limit white from making a
     * checkmate.
     * @param blackHandicap defines if black plays with handicap
     * @param blackMinValue sets the min board value white may accomplish with
     * next move. The smaller the better. Does not limit black from making a
     * checkmate.
     */
    public MoveSelector(Game game, int depth, boolean whiteHandicap, double whiteMaxValue,
            boolean blackHandicap, double blackMinValue) {
        this.game = game;
        this.math = new MathUtils();
        this.depth = depth;
        this.whiteHandicap = whiteHandicap;
        this.whiteHandicap = whiteHandicap;
        this.blackHandicap = blackHandicap;
        this.blackHandicap = blackHandicap;
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
        ChessboardList moves = game.getOnePieceMoves(board, y, x);
        if (piece.getType() == Type.PAWN) {
            if (moves.getTail() == 0) {
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
        value += moves.getTail() * 0.1;
        return value;
    }
    
    /**
     * This method checks if a certain pawn has no adjacent friendly pieces.
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
        ChessboardList moves = game.addAllMoves(board, Side.WHITE);
        while (true) {
            Piece[][] move = moves.getNextBoard();
            if (move == null) {
                break;
            }
            double value = minBoardValue(move, depth - 1, alpha, beta);
            bestValue = math.max(value, bestValue);
            alpha = math.max(alpha, bestValue);
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
        ChessboardList moves = game.addAllMoves(board, Side.BLACK);
        while (true) {
            Piece[][] move = moves.getNextBoard();
            if (move == null) {
                break;
            }
            double value = maxBoardValue(move, depth - 1, alpha, beta);
            bestValue = math.min(value, bestValue);
            beta = math.min(beta, bestValue);
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
        ChessboardList moves = game.addAllMoves(board, Side.WHITE);
        ChessboardList legalMoves = filterLegalMoves(moves, Side.WHITE);
        Piece[][] bestMove = legalMoves.getNextBoard();
        double bestValue = minBoardValue(bestMove, depth, -99999, 99999);
        while (true) {
            Piece[][] move = legalMoves.getNextBoard();
            if (move == null) {
                break;
            }
            double value = minBoardValue(move, depth, -99999, 99999);
            if (whiteHandicap) {
                if (value > whiteMaxValue && value < 90000.00) {
                    continue;
                }    
            }
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
        ChessboardList moves = game.addAllMoves(board, Side.BLACK);
        ChessboardList legalMoves = this.filterLegalMoves(moves, Side.BLACK);
        Piece[][] bestMove = legalMoves.getNextBoard();
        double bestValue = maxBoardValue(bestMove, depth, -99999.99, 99999.99);
        while (true) {
            Piece[][] move = legalMoves.getNextBoard();
            if (move == null) {
                break;
            }
            double value = maxBoardValue(move, depth, -99999.99, 99999.99);
            if (blackHandicap) {
                if (value < blackMinValue && value > -90000.00) {
                    continue;
                }
            }
            if (value < bestValue) {
                bestValue = value;
                bestMove = move;
            }
        }
        return bestMove;
    }
    
    public ChessboardList filterLegalMoves(ChessboardList moves, Side side) {
        ChessboardList legalMoves = new ChessboardList();
        while (true) {
            Piece[][] move = moves.getNextBoard();
            boolean legal = true;
            if (move == null) {
                break;
            }
            ChessboardList opponentMoves = game.addAllMoves(move, getOppositeSide(side));
            while (true) {
                Piece[][] opponentMove = opponentMoves.getNextBoard();
                if (opponentMove == null) {
                    break;
                }
                if (game.isKingDead(opponentMove, side)) {
                    legal = false;
                    break;
                }
            }
            if (legal) {
                legalMoves.add(move);
            }
        }
        return legalMoves;
    }
    
    public Side getOppositeSide(Side side) {
        if (side == side.WHITE) {
            return Side.BLACK;
        } else {
            return Side.WHITE;
        }
    }
}

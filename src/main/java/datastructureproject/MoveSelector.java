package datastructureproject;

import chess.model.Side;

/**
 * This class takes care of evaluation of board situations and selects a 
 * suitable next move for the chess bot.
 */
public class MoveSelector {
    
    private final Game game;
    private final MathUtils math;
    private final int depth;
    private final boolean whiteHandicap;
    private final double whiteMaxValue;
    private final boolean blackHandicap;
    private final double blackMinValue;
    
    /**
     * The constructor for this class.
     * @param game the game object for which the move selector makes the next 
     * move
     * @param depth the recursion depth of the minimax algorithm
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
        this.whiteMaxValue = whiteMaxValue;
        this.blackHandicap = blackHandicap;
        this.blackMinValue = blackMinValue;
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
                if (side == Side.WHITE) {
                    whiteValue += type.getValue();
                    whiteValue += countMobilityBonus(board, y, x);
                    if (type == Type.BISHOP) {
                        whiteBishops++;
                    }
                } else {
                    blackValue += type.getValue();
                    blackValue += countMobilityBonus(board, y, x);
                    if (type == Type.BISHOP) {
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
     * close to promotion). King pieces are ignored.
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated piece
     * @param x the x-coordinate of the evaluated piece
     * @return returns mobility bonus as a double value
     */
    public double countMobilityBonus(Piece[][] board, int y, int x) {
        Piece piece = board[y][x];
        double value = 0.0;
        if (piece.getType() == Type.PAWN) {
            if (checkBlockedPawn(board, y, x)) {
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
        if (piece.getType() == Type.KNIGHT) {
            value += 0.1 * countKnightMoves(board, piece, y, x);
        } else if (piece.getType() == Type.ROOK) {
            value += 0.1 * countRookMoves(board, piece, y, x);
        } else if (piece.getType() == Type.BISHOP) {
            value += 0.1 * countBishopMoves(board, piece, y, x);
        } else if (piece.getType() == Type.QUEEN) {
            value += 0.1 * countQueenMoves(board, piece, y, x);
        }
        return value;
    }
    
    /**
     * This method checks if a pawn piece is blocked, i.e. can not move at all.
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated pawn
     * @param x the x-coordinate of the evaluated pawn
     * @return returns true if the pawn is blocked and otherwise false.
     */
    public boolean checkBlockedPawn(Piece[][] board, int y, int x) { 
        Piece pawn = board[y][x];
        if (pawn.getSide() == Side.WHITE) {
            if (board[y + 1][x].getType() == Type.EMPTY) {
                return false;
            }
            if (x > 0) {
                if (board[y + 1][x - 1].getSide() == Side.BLACK) {
                    return false;
                }
            } 
            if (x < 7) {
                if (board[y + 1][x + 1].getSide() == Side.BLACK) {
                    return false;
                }
            }
        } else {
            if (board[y - 1][x].getType() == Type.EMPTY) {
                return false;
            }
            if (x > 0) {
                if (board[y - 1][x - 1].getSide() == Side.WHITE) {
                    return false;
                }
            } 
            if (x < 7) {
                if (board[y - 1][x + 1].getSide() == Side.WHITE) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * This method checks if a certain pawn has no adjacent friendly pieces.
     * @param board the evaluated board situation
     * @param y the y-coordinate of the evaluated pawn
     * @param x the x-coordinate of the evaluated pawn
     * @return returns false if the pawn has an adjacent friendly piece and true
     * if not.
     */
    public boolean checkPawnIsolation(Piece[][] board, int y, int x) {
        Piece pawn = board[y][x];
        for (int y2 = y - 1; y2 <= y + 1; y2++) {
            for (int x2 = x - 1; x2 <= x + 1; x2++) {
                if (y2 == y && x2 == x) {
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
     * This method counts the number of possible knight moves for mobility 
     * bonus.
     * @param board the evaluated board situation
     * @param knight the evaluated knight piece
     * @param y the y-coordinate of the evaluated knight
     * @param x the x-coordinate of the evaluated knight
     * @return returns the number of moves
     */
    public int countKnightMoves(Piece[][] board, Piece knight, int y, int x) {
        int moves = 0;
        int[][] knightMoves = {{2, 2, 1, 1, -1, -1, -2, -2},
            {1, -1, 2, -2, 2, -2, 1, -1}};
        for (int i = 0; i < knightMoves[0].length; i++) {
            int newY = y + knightMoves[0][i];
            int newX = x + knightMoves[1][i];
            
            if (newY < 0 || newY > 7 || newX < 0 || newX > 7) {
                continue;
            }
            
            if (knight.getSide() != board[newY][newX].getSide()) {
                moves++;
            }
        }
        return moves;
    }
    
    /**
     * This method counts the number of possible knight moves for mobility 
     * bonus.
     * @param board the evaluated board situation
     * @param rook the evaluated rook piece
     * @param y the y-coordinate of the evaluated rook
     * @param x the x-coordinate of the evaluated rook
     * @return returns the number of moves
     */
    public int countRookMoves(Piece[][] board, Piece rook, int y, int x) {
        int up = y + 1;
        int down = y - 1;
        int right = x + 1;
        int left = x - 1;
        int moves = 0;
        
        while (up <= 7) {
            if (board[up][x].getSide() == rook.getSide()) {
                break;
            }
            moves++;
            if (board[up][x].getSide() != null) {
                break;
            }
            up++;
        }
        
        while (down >= 0) {
            if (board[down][x].getSide() == rook.getSide()) {
                break;
            }
            moves++;
            if (board[down][x].getSide() != null) {
                break;
            }
            down--;
        }
        
        while (right <= 7) {
            if (board[y][right].getSide() == rook.getSide()) {
                break;
            }
            moves++;
            if (board[y][right].getSide() != null) {
                break;
            }
            right++;
        }
        
        while (left >= 0) {
            if (board[y][left].getSide() == rook.getSide()) {
                break;
            }
            moves++;
            if (board[y][left].getSide() != null) {
                break;
            }
            left--;
        }
        return moves;
    }
    
    /**
     * This method counts the number of possible bishop moves for mobility 
     * bonus.
     * @param board the evaluated board situation
     * @param bishop the evaluated bishop piece
     * @param y the y-coordinate of the evaluated bishop
     * @param x the x-coordinate of the evaluated bishop
     * @return returns the number of moves
     */
    public int countBishopMoves(Piece[][] board, Piece bishop, int y, int x) {
        int up = y + 1;
        int down = y - 1;
        int right = x + 1;
        int left = x - 1;
        int moves = 0;
        
        while (up <= 7 && right <= 7) {
            if (board[up][right].getSide() == bishop.getSide()) {
                break;
            }
            moves++;
            if (board[up][right].getSide() != null) {
                break;
            }
            up++;
            right++;
        }
        up = y + 1;
        right = x + 1;
        
        while (down >= 0 && right <= 7) {
            if (board[down][right].getSide() == bishop.getSide()) {
                break;
            }
            moves++;
            if (board[down][right].getSide() != null) {
                break;
            }
            down--;
            right++;
        }
        down = y - 1;
        right = x + 1;
        
        while (up <= 7 && left >= 0) {
            if (board[up][left].getSide() == bishop.getSide()) {
                break;
            }
            moves++;
            if (board[up][left].getSide() != null) {
                break;
            }
            up++;
            left--;
        }
        left = x - 1;
        
        while (down >= 0 && left >= 0) {
            if (board[down][left].getSide() == bishop.getSide()) {
                break;
            }
            moves++;
            if (board[down][left].getSide() != null) {
                break;
            }
            down--;
            left--;
        }
        return moves;
    }
    /**
     * This method counts the number of possible queen moves for mobility 
     * bonus by calling the equivalent rook and bishop methods.
     * @param board the evaluated board situation
     * @param queen the evaluated queen piece
     * @param y the y-coordinate of the evaluated queen
     * @param x the x-coordinate of the evaluated queen
     * @return returns the number of moves
     */
    public int countQueenMoves(Piece[][] board, Piece queen, int y, int x) {
        int moves = 0;
        moves += countRookMoves(board, queen, y, x);
        moves += countBishopMoves(board, queen, y, x);
        return moves;
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
     * @return returns a chessboard representation of the best move or null if 
     * there is no legal move
     */
    public Piece[][] getBestWhiteMove() {
        Piece[][] board = game.copyBoard(game.getCurrentBoard());
        game.checkWhiteCastling(board);
        ChessboardList moves = game.addAllMoves(board, Side.WHITE);
        ChessboardList legalMoves = filterLegalMoves(moves, Side.WHITE);
        if (legalMoves.getTail() == 0) {
            return null;
        }
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
     * @return returns a chessboard representation of the best move or null if 
     * there is no legal move
     */
    public Piece[][] getBestBlackMove() {
        Piece[][] board = game.copyBoard(game.getCurrentBoard());
        game.checkBlackCastling(board);
        ChessboardList moves = game.addAllMoves(board, Side.BLACK);
        ChessboardList legalMoves = this.filterLegalMoves(moves, Side.BLACK);
        if (legalMoves.getTail() == 0) {
            return null;
        }
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
    
    /**
     * This method creates a moves list which does not contain illegal moves,
     * i.e. moves that leave king in check.
     * @param moves the list of all technically possible moves
     * @param side the side of the player who has the turn
     * @return returns a ChessboardList object containing only legal moves
     */
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
        if (side == Side.WHITE) {
            return Side.BLACK;
        } else {
            return Side.WHITE;
        }
    }
}

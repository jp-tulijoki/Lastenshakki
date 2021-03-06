package chess.bot;

import chess.engine.GameState;
import chess.model.Side;
import datastructureproject.*;

/**
 * This class is the chess bot implementation that communicates the moves to the
 * chess platform and updates the game data to the current game object.
 */
public class TrainerBot implements ChessBot {
    
    private final Game game;
    private final MoveSelector ms;
    private final MathUtils math;

    public TrainerBot() {
        this.game = new Game();
        this.ms = new MoveSelector(game, 3, false, 0.0, false, 0.0);
        this.math = new MathUtils();
        game.initBoard();
    }

    public Game getGame() {
        return game;
    }
    
    /**
     * This method parses the difference between two chessboard representations
     * to a UCI move.
     * @param side the side of the current player
     * @param currentBoard the board before the move
     * @param newBoard the board after the move
     * @return returns a move in UCI format
     */
    public String parseMove(Side side, Piece[][] currentBoard, Piece[][] newBoard) {
        String start = "";
        String end = "";
        String move = parseCastling(side, currentBoard, newBoard);
        if (!move.isEmpty()) {
            return move;
        }
        boolean promotion = false;
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                Piece currentBoardPiece = currentBoard[y][x];
                Piece newBoardPiece = newBoard[y][x];
                if (currentBoardPiece != newBoardPiece) {
                    if (currentBoardPiece.getType() == Type.PAWN && (y == 3 || y == 4)) {
                        move = parseEnPassant(side, y, x, currentBoard, newBoard);
                        if (!move.isEmpty()) {
                            return move;
                        }
                    }
                    if (newBoardPiece.getType() == Type.EMPTY) {
                        start += (char) (x + 97) + String.valueOf(y + 1);
                        if ((y == 6) && currentBoardPiece.getType() == Type.PAWN && currentBoardPiece.getSide() 
                                == Side.WHITE) {
                            promotion = true;
                        } else if ((y == 1) && currentBoardPiece.getType() == Type.PAWN 
                                && currentBoardPiece.getSide() == Side.BLACK) {
                            promotion = true;
                        }
                    } else {
                        end += (char) (x + 97) + String.valueOf(y + 1);
                    }
                }
            }
        }
        move = start + end;
        if (promotion) {
            int y = Character.getNumericValue(end.charAt(1)) - 1;
            int x = (int) end.charAt(0) - 97;
            move += newBoard[y][x].getType().getAbbreviation();
        }
        return move;
    }
    
    /**
     * This method detects if a castling move is made and parses the move to 
     * UCI format. This method is called during the parseMove method.
     * @param side the side of the current player
     * @param currentBoard the board before the move
     * @param newBoard the board after the move
     * @return returns a castling move in UCI format if there is one and an
     * empty string otherwise
     */
    public String parseCastling(Side side, Piece[][] currentBoard, Piece[][] newBoard) {
        if (side == Side.WHITE) {
            if (currentBoard[0][4].getType() == Type.KING && currentBoard[0][4].getSide() == Side.WHITE 
                    && newBoard[0][2].getType() == Type.KING && newBoard[0][2].getSide() == Side.WHITE) {
                return "e1c1";
            }
            if (currentBoard[0][4].getType() == Type.KING && currentBoard[0][4].getSide() == Side.WHITE 
                    && newBoard[0][6].getType() == Type.KING && newBoard[0][6].getSide() == Side.WHITE) {
                return "e1g1";
            }
        } else {
            if (currentBoard[7][4].getType() == Type.KING && currentBoard[7][4].getSide() == Side.BLACK 
                    && newBoard[7][2].getType() == Type.KING && newBoard[7][2].getSide() == Side.BLACK) {
                return "e8c8";
            }
            if (currentBoard[7][4].getType() == Type.KING && currentBoard[7][4].getSide() == Side.BLACK 
                    && newBoard[7][6].getType() == Type.KING && newBoard[7][6].getSide() == Side.BLACK) {
                return "e8g8";
            }
        }
        return "";
    }
    
    /**
     * Special parsing for the enPassant move.
     * @param side the side of the player who made the move
     * @param y the y-coordinate of the moved piece before the mpve
     * @param x the x-coordinate of the moved piece before the move
     * @param currentBoard the board before the move
     * @param newBoard the board after the move
     * @return returns an enPassant move in UCI format if there is one and an 
     * empty string otherwise
     */
    public String parseEnPassant(Side side, int y, int x, Piece[][] currentBoard, Piece[][] newBoard) {
        if (side == Side.WHITE && y == 4) {
            if (x > 0) {
                if (currentBoard[y][x - 1].getType() == Type.PAWN && currentBoard[y][x - 1].getSide()
                        == Side.BLACK && newBoard[y][x - 1].getType() == Type.EMPTY) {
                    return (char) (x + 97) + String.valueOf(y + 1) + (char) (x + 96) + String.valueOf(y + 2);
                }
            }
            if (x < 7) {
                if (currentBoard[y][x + 1].getType() == Type.PAWN && currentBoard[y][x + 1].getSide()
                        == Side.BLACK && newBoard[y][x + 1].getType() == Type.EMPTY) {
                    return (char) (x + 97) + String.valueOf(y + 1) + (char) (x + 98) + String.valueOf(y + 2);
                }
            }
        }
        if (side == Side.BLACK && y == 3) {
            if (x > 0) {
                if (currentBoard[y][x - 1].getType() == Type.PAWN && currentBoard[y][x - 1].getSide()
                        == Side.WHITE && newBoard[y][x - 1].getType() == Type.EMPTY) {
                    return (char) (x + 97) + String.valueOf(y + 1) + (char) (x + 96) + String.valueOf(y);
                }
            }
            if (x < 7) {
                if (currentBoard[y][x + 1].getType() == Type.PAWN && currentBoard[y][x + 1].getSide()
                        == Side.WHITE && newBoard[y][x + 1].getType() == Type.EMPTY) {
                    return (char) (x + 97) + String.valueOf(y + 1) + (char) (x + 98) + String.valueOf(y);
                }
            }
        }
        return "";
    }
    
    /**
     * This method updates the latest move and returns the next move to be made 
     * in UCI format.
     * @param gamestate the current gamestate
     * @return returns a move in UCI format
     */
    @Override
    public String nextMove(GameState gamestate) {
        if (gamestate.getMoveCount() != 0) {
            String latestMove = gamestate.getLatestMove();
            updateLatestMove(latestMove);
        }
        Piece[][] currentBoard = game.getCurrentBoard();
        String move = "";
        if (gamestate.playing == Side.BLACK) {    
            Piece[][] newBoard = ms.getBestBlackMove();
            move = parseMove(Side.BLACK, currentBoard, newBoard);
        } else {
            Piece[][] newBoard = ms.getBestWhiteMove();
            move = parseMove(Side.WHITE, currentBoard, newBoard);
        }
        updateLatestMove(move);
        return move;
    }
    
    /**
     * This method updates the castling array during the next move update
     * if necessary.
     * @param currentY the y-coordinate of the moved piece
     * @param currentX the x-coordinate of the moved piece
     */
    public void updateCastlingStatus(int currentY, int currentX) {
        boolean[] castling = game.getCastling();
        if (!castling[0] && !castling[1] && !castling[2] && !castling[3]) {
            return;
        }
        if (currentY == 0) {
            if (currentX == 4) {
                castling[0] = false;
                castling[1] = false;
            } else if (currentX == 0) {
                castling[0] = false;
            } else if (currentX == 7) {
                castling[1] = false;
            } 
        } else if (currentY == 7) {
            if (currentX == 4) {
                castling[2] = false;
                castling[3] = false;
            } else if (currentX == 0) {
                castling[2] = false;
            } else if (currentX == 7) {
                castling[3] = false;
            } 
        }
    }
    
    
    
    /**
     * This method is called by the nextMove method and takes care of updating
     * the latest UCI format move to the board of the current Game object.
     * @param move the move in UCI format
     */
    public void updateLatestMove(String move) {
        Piece[][] board = game.getCurrentBoard();
        int currentY = Character.getNumericValue(move.charAt(1)) - 1;
        int currentX = (int) move.charAt(0) - 97;
        int newY = Character.getNumericValue(move.charAt(3)) - 1;
        int newX = (int) move.charAt(2) - 97;
        if (move.length() == 5) {
            char promotionChar = move.charAt(4);
            Side pawnSide = board[currentY][currentX].getSide();
            board[currentY][currentX] = new Piece(Type.getType(promotionChar), pawnSide);
        }
        if (board[currentY][currentX].getType() == Type.KING && math.abs(newX - currentX) == 2) {
            if (currentX > newX) {
                game.movePiece(board, currentY, 0, newY, 3);
            } else {
                game.movePiece(board, currentY, 7, newY, 5);
            }
        }
        if (board[currentY][currentX].getType() == Type.PAWN && board[newY][newX].getType() 
                == Type.EMPTY && math.abs(newX - currentX) == 1) {
            board[currentY][newX] = new Piece(Type.EMPTY);
        }
        game.movePiece(board, currentY, currentX, newY, newX);
        if (board[newY][newX].getType() == Type.PAWN && math.abs(newY - currentY) == 2) {
            game.setEnPassant(board[newY][newX]);
        } else {
            game.setEnPassant(null);
        }
        updateCastlingStatus(currentY, currentX);
        game.setCurrentBoard(board);
    }
}

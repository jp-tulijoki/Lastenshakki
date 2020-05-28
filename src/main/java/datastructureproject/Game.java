package datastructureproject;

import chess.model.Side;
import java.util.ArrayList;

/**
 * This class contains the main functionalities of the chessboard such as moving
 * the pieces according to the rules. This class keeps track of the current
 * piece locations on the chessboard and a list of legal moves.
 */
public class Game {
    private Piece[][] currentBoard;
    private ArrayList<Piece[][]> legalMoves;
    private Piece enPassant;
    private boolean[] castling;

    public Game() {
        this.currentBoard = new Piece[8][8];
        this.legalMoves = new ArrayList();
        this.castling = new boolean[]{true, true, true, true, true, true, true, true};
    }
    
    /**
     * This method initializes the chessboard with starting positions.
     */
    public void initBoard() {
        for (int x = 0; x <= 7; x++) {
            currentBoard[1][x] = new Piece(Type.PAWN, Side.WHITE);
            currentBoard[6][x] = new Piece(Type.PAWN, Side.BLACK);
            for (int y = 2; y <= 5; y++) {
                currentBoard[y][x] = new Piece(Type.EMPTY);
            }
        }
        
        currentBoard[0][0] = new Piece(Type.ROOK, Side.WHITE);
        currentBoard[0][1] = new Piece(Type.KNIGHT, Side.WHITE);
        currentBoard[0][2] = new Piece(Type.BISHOP, Side.WHITE);
        currentBoard[0][3] = new Piece(Type.QUEEN, Side.WHITE);
        currentBoard[0][4] = new Piece(Type.KING, Side.WHITE);
        currentBoard[0][5] = new Piece(Type.BISHOP, Side.WHITE);
        currentBoard[0][6] = new Piece(Type.KNIGHT, Side.WHITE);
        currentBoard[0][7] = new Piece(Type.ROOK, Side.WHITE);
        
        currentBoard[7][0] = new Piece(Type.ROOK, Side.BLACK);
        currentBoard[7][1] = new Piece(Type.KNIGHT, Side.BLACK);
        currentBoard[7][2] = new Piece(Type.BISHOP, Side.BLACK);
        currentBoard[7][3] = new Piece(Type.QUEEN, Side.BLACK);
        currentBoard[7][4] = new Piece(Type.KING, Side.BLACK);
        currentBoard[7][5] = new Piece(Type.BISHOP, Side.BLACK);
        currentBoard[7][6] = new Piece(Type.KNIGHT, Side.BLACK);
        currentBoard[7][7] = new Piece(Type.ROOK, Side.BLACK);
    }

    public Piece getPiece(int y, int x) {
        return currentBoard[y][x];
    }

    public ArrayList<Piece[][]> getLegalMoves() {
        return legalMoves;
    }

    public Piece[][] getCurrentBoard() {
        return currentBoard;
    }
    
    public void setCurrentBoard(Piece[][] board) {
        this.currentBoard = board;
    }
    
    public Piece[][] copyCurrentBoard() {
        Piece[][] newBoard = new Piece[8][8];
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                newBoard[y][x] = currentBoard[y][x];
            }
        }
        return newBoard;
    }
    
    public void setEnPassant(Piece pawn) {
        this.enPassant = pawn;
    }
    
    public Piece getEnPassant() {
        return enPassant;
    }
    
    public void nullifyEnPassant() {
        this.enPassant = null;
    }

    public boolean[] getCastling() {
        return castling;
    }
    
    
    /**
     * This method moves a chesspiece to the new square and replaces the old 
     * location with empty square.
     * @param board the current piece locations on the chessboard
     * @param currentY the current y-coordinate of the chesspiece moved
     * @param currentX the current x-coordinate of the chesspiece moved
     * @param newY the y-coordinate the chesspiece is moved to
     * @param newX the x-coordinate the chesspiece is moved to
     */
    public void movePiece(Piece[][] board, int currentY, int currentX, int newY, int newX) {
        Piece movedPiece = board[currentY][currentX];
        board[newY][newX] = movedPiece;
        board[currentY][currentX] = new Piece(Type.EMPTY);
    }
    
    /**
     * This method adds a regular one step pawn move to the legal moves list if 
     * it's possible.
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addRegularPawnMove(Piece pawn, int y, int x) {           
        if (pawn.getSide() == Side.WHITE && currentBoard[y + 1][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y + 1, x);
            legalMoves.add(newBoard);
            return;
        }
        if (pawn.getSide() == Side.BLACK && currentBoard[y - 1][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y - 1, x);
            legalMoves.add(newBoard);
        }
    }
    
    /**
     * This method adds a two step pawn move to the legal moves list if 
     * it's possible.
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addTwoStepPawnMove(Piece pawn, int y, int x) {
        if (pawn.getSide() == Side.WHITE && y != 1) {
            return;
        }
        if (pawn.getSide() == Side.BLACK && y != 6) {
            return;
        }
        if (pawn.getSide() == Side.WHITE && currentBoard[y + 1][x].getType() == 
                Type.EMPTY && currentBoard[y + 2][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y + 2, x);
            legalMoves.add(newBoard);
            return;
        }
        if (pawn.getSide() == Side.BLACK && currentBoard[y - 1][x].getType() == 
                Type.EMPTY && currentBoard[y - 2][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y - 2, x);
            legalMoves.add(newBoard);
        }
    }
    
    /**
     * This method adds a pawn attack to the legal moves list if it's possible.
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addPawnAttack(Piece pawn, int y, int x) {
        if (x != 0) {
            if (pawn.getSide() == Side.WHITE && currentBoard[y + 1][x - 1].getSide() == Side.BLACK) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y + 1, x - 1);
                legalMoves.add(newBoard);
            }
            if (pawn.getSide() == Side.BLACK && currentBoard[y - 1][x - 1].getSide() == Side.WHITE) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y - 1, x - 1);
                legalMoves.add(newBoard);
            }
        }
        if (x != 7) {
            if (pawn.getSide() == Side.WHITE && currentBoard[y + 1][x + 1].getSide() == Side.BLACK) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y + 1, x + 1);
                legalMoves.add(newBoard);
            }
            if (pawn.getSide() == Side.BLACK && currentBoard[y - 1][x + 1].getSide() == Side.WHITE) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y - 1, x + 1);
                legalMoves.add(newBoard);
            }
        }
    }
    
    public void addEnPassant(Piece pawn, int y, int x) {
        if (x > 0) {
            if (currentBoard[y][x - 1] == enPassant) {
                if (pawn.getSide() == Side.WHITE) {
                    Piece[][] newBoard = copyCurrentBoard();
                    movePiece(newBoard, y, x, y + 1, x - 1);
                    newBoard[y][x - 1] = new Piece(Type.EMPTY);
                    legalMoves.add(newBoard);
                } else {
                    Piece[][] newBoard = copyCurrentBoard();
                    movePiece(newBoard, y, x, y - 1, x - 1);
                    newBoard[y][x - 1] = new Piece(Type.EMPTY);
                    legalMoves.add(newBoard);
                }
                return;
            }
        }
        
        if (x < 7) {
            if (currentBoard[y][x + 1] == enPassant) {
                if (pawn.getSide() == Side.WHITE) {
                    Piece[][] newBoard = copyCurrentBoard();
                    movePiece(newBoard, y, x, y + 1, x + 1);
                    newBoard[y][x + 1] = new Piece(Type.EMPTY);
                    legalMoves.add(newBoard);
                } else {
                    Piece[][] newBoard = copyCurrentBoard();
                    movePiece(newBoard, y, x, y - 1, x + 1);
                    newBoard[y][x + 1] = new Piece(Type.EMPTY);
                    legalMoves.add(newBoard);
                }
            }
        }
    }
    
    public void addAllPawnMoves(Piece pawn, int y, int x) {
        addRegularPawnMove(pawn, y, x);
        addTwoStepPawnMove(pawn, y, x);
        addPawnAttack(pawn, y, x);
        addEnPassant(pawn, y, x);
    }
    
    /**
     * This method adds all possible moves of a certain knight to legal moves
     * list.
     * @param knight the specified knight piece
     * @param y the y-coordinate of the current location of the knight piece
     * @param x the x-coordinate of the current location of the knight piece
     */
    public void addKnightMoves(Piece knight, int y, int x) {
        int[][] knightMoves = {{2, 2, 1, 1, -1, -1, -2, -2},
            {1, -1, 2, -2, 2, -2, 1, -1}};
        for (int i = 0; i < knightMoves[0].length; i++) {
            int newY = y + knightMoves[0][i];
            int newX = x + knightMoves[1][i];
            
            if (newY < 0 || newY > 7 || newX < 0 || newX > 7) {
                continue;
            }
            
            if (knight.getSide() != currentBoard[newY][newX].getSide()) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, newY, newX);
                legalMoves.add(newBoard);
            }
        }
    }
    
    /**
     * This method adds all possible moves of a certain rook to legal moves list
     * @param rook the specified rook piece
     * @param y the y-coordinate of the current location of the rook piece
     * @param x the x-coordinate of the current location of the rook piece
     */
    public void addRookMoves(Piece rook, int y, int x) {
        int up = y + 1;
        int down = y - 1;
        int right = x + 1;
        int left = x - 1;
        
        while (up <= 7) {
            if (currentBoard[up][x].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, up, x);
            legalMoves.add(newBoard);
            if (currentBoard[up][x].getSide() != null) {
                break;
            }
            up++;
        }
        
        while (down >= 0) {
            if (currentBoard[down][x].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, down, x);
            legalMoves.add(newBoard);
            if (currentBoard[down][x].getSide() != null) {
                break;
            }
            down--;
        }
        
        while (right <= 7) {
            if (currentBoard[y][right].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y, right);
            legalMoves.add(newBoard);
            if (currentBoard[y][right].getSide() != null) {
                break;
            }
            right++;
        }
        
        while (left >= 0) {
            if (currentBoard[y][left].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y, left);
            legalMoves.add(newBoard);
            if (currentBoard[y][left].getSide() != null) {
                break;
            }
            left--;
        }
    }
    
    /**
     * This method adds all possible moves of a certain bishop to legal moves
     * list.
     * @param bishop the specified bishop
     * @param y the y-coordinate of the current location of the bishop
     * @param x the x-coordinate of the current location of the bishop
     */
    public void addBishopMoves(Piece bishop, int y, int x) {
        int up = y + 1;
        int down = y - 1;
        int right = x + 1;
        int left = x - 1;
        
        while (up <= 7 && right <= 7) {
            if (currentBoard[up][right].getSide() == bishop.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, up, right);
            legalMoves.add(newBoard);
            if (currentBoard[up][right].getSide() != null) {
                break;
            }
            up++;
            right++;
        }
        up = y + 1;
        right = x + 1;
        
        while (down >= 0 && right <= 7) {
            if (currentBoard[down][right].getSide() == bishop.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, down, right);
            legalMoves.add(newBoard);
            if (currentBoard[down][right].getSide() != null) {
                break;
            }
            down--;
            right++;
        }
        down = y - 1;
        right = x + 1;
        
        while (up <= 7 && left >= 0) {
            if (currentBoard[up][left].getSide() == bishop.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, up, left);
            legalMoves.add(newBoard);
            if (currentBoard[up][left].getSide() != null) {
                break;
            }
            up++;
            left--;
        }
        left = x - 1;
        
        while (down >= 0 && left >= 0) {
            if (currentBoard[down][left].getSide() == bishop.getSide()) {
                break;
            }
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, down, left);
            legalMoves.add(newBoard);
            if (currentBoard[down][left].getSide() != null) {
                break;
            }
            down--;
            left--;
        }
    }
    
    /**
     * This method adds all possible moves of a certain queen to legal 
     * moves list by combining the moves of the rook and the bishop piece.
     * @param queen the specified queen
     * @param y the y-coordinate of the current location of the queen
     * @param x the x-coordinate of the current location of the queen
     */
    public void addQueenMoves(Piece queen, int y, int x) {
        addRookMoves(queen, y, x);
        addBishopMoves(queen, y, x);
    }
    
    /**
     * This method adds all possible moves of a certain king to legal moves 
     * list.
     * @param king the specified king
     * @param y the y-coordinate of the current location of the king
     * @param x the x-coordinate of the current location of the king
     */
    public void addRegularKingMoves(Piece king, int y, int x) {
        for (int newY = y - 1; newY <= y + 1; newY++) {
            for (int newX = x - 1; newX <= x + 1; newX++) {
                if (newY == y && newX == x) {
                    continue;
                }
                if (newY < 0 || newY > 7 || newX < 0 || newX > 7) {
                    continue;
                }
                if (currentBoard[newY][newX].getSide() == king.getSide()) {
                    continue;
                }
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, newY, newX);
                legalMoves.add(newBoard);
            }
        }
    }
    
    public void checkWhiteCastling() {
        castling[4] = true;
        castling[5] = true;
        if (castling[0] == false) {
            castling[4] = false;
        }
        if (castling[1] == false) {
            castling[5] = false;
        }
        if (castling[4] == false & castling[5] == false) {
            return;
        }
        if (currentBoard[0][1].getType() != Type.EMPTY || currentBoard[0][2].getType() != Type.EMPTY || currentBoard[0][3].getType() != Type.EMPTY) {
            castling[4] = false;
        }
        if (currentBoard[0][5].getType() != Type.EMPTY || currentBoard[0][6].getType() == Type.EMPTY) {
            castling[5] = false;
        }
        if (castling[4] == false & castling[5] == false) {
            return;
        }
        addAllLegalMoves(Side.BLACK);
        for (Piece[][] move : legalMoves) {
            if (move[0][2].getType() != Type.EMPTY || move[0][3].getType() != Type.EMPTY || move[0][4].getType() != Type.KING) {
                castling[4] = false;
            }
            if (move[0][4].getType() != Type.KING || move[0][5].getType() != Type.EMPTY || move[0][6].getType() != Type.EMPTY) {
                castling[5] = false;
            }
            if (castling[4] == false && castling[5] == false) {
                break;
            }
        }
    }       
    
    public void checkBlackCastling() {
        if (castling[2] == false) {
            castling[6] = false;
        }
        if (castling[3] == false) {
            castling[7] = false;
        }
        if (castling[6] == false & castling[7] == false) {
            return;
        }
        if (currentBoard[7][1].getType() == Type.EMPTY && currentBoard[7][2].getType() == Type.EMPTY && currentBoard[7][3].getType() == Type.EMPTY) {
            castling[6] = true;
        }
        if (currentBoard[7][5].getType() == Type.EMPTY && currentBoard[7][6].getType() == Type.EMPTY) {
            castling[7] = true;
        }
        if (castling[6] == false & castling[7] == false) {
            return;
        }
        addAllLegalMoves(Side.WHITE);
        for (Piece[][] move : legalMoves) {
            if (move[7][2].getType() != Type.EMPTY || move[7][3].getType() != Type.EMPTY || move[7][4].getType() != Type.KING) {
                castling[6] = false;
            }
            if (move[7][4].getType() != Type.KING || move[7][5].getType() != Type.EMPTY || move[7][6].getType() != Type.EMPTY) {
                castling[7] = false;
            }
            if (castling[6] == false && castling[7] == false) {
                break;
            }
        }
    }       
    
    public void addWhiteCastling(Piece whiteKing, int y, int x) {
        if (castling[4] == false && castling[5] == false) {
            return;
        }
        if (castling[4]) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y, x - 2);
            movePiece(newBoard, y, 0, y, 3);
            legalMoves.add(newBoard);
        }
        if (castling[5]) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y, x + 2);
            movePiece(newBoard, y, 7, y, 5);
            legalMoves.add(newBoard);
        }
    }
    
    public void addBlackCastling(Piece blackKing, int y, int x) {
        if (castling[6] == false && castling[7] == false) {
            return;
        }
        if (castling[6]) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y, x - 2);
            movePiece(newBoard, y, 0, y, 3);
            legalMoves.add(newBoard);
        }
        if (castling[5]) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y, x + 2);
            movePiece(newBoard, y, 7, y, 5);
            legalMoves.add(newBoard);
        }
    }
    
    public void addAllKingMoves(Piece king, int y, int x) {
        addRegularKingMoves(king, y, x);
        if (king.getSide() == Side.WHITE) {
            addWhiteCastling(king, y, x);
        } else {
            addBlackCastling(king, y, x);
        }
    }
    
    /**
     * This method checks if the specified board situation contains the king
     * of the specified side.
     * @param board the specified board situation 
     * @param side the side of the king
     * @return returns true if the king of the specified side is dead and false
     * if the king is alive
     */
    public boolean isKingDead(Piece[][] board, Side side) {
        boolean dead = true;
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                if (board[y][x].getSide() == side && 
                        board[y][x].getType() == Type.KING) {
                    dead = false;
                }
            }
        }
        return dead;
    }
    
    public void addAllLegalMoves(Side side) {
        legalMoves = new ArrayList();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <=7; x++) {
                Piece piece = currentBoard[y][x];
                if (piece.getSide() != side) {
                    continue;
                }
                if (piece.getType() == Type.PAWN) {
                    addAllPawnMoves(piece, y, x);
                } else if (piece.getType() == Type.KNIGHT) {
                    addKnightMoves(piece, y, x);
                } else if (piece.getType() == Type.ROOK) {
                    addRookMoves(piece, y, x);
                } else if (piece.getType() == Type.BISHOP) {
                    addBishopMoves(piece, y, x);
                } else if (piece.getType() == Type.QUEEN) {
                    addQueenMoves(piece, y, x);
                } else if (piece.getType() == Type.KING) {
                    addAllKingMoves(piece, y, x);
                }
            }
        }
    }
    
    public ArrayList<Piece[][]> getOnePieceMoves(Piece[][] board, int y, int x) {
        legalMoves = new ArrayList();
        Piece piece = board[y][x];
        if (piece.getType() == Type.PAWN) {
            addAllPawnMoves(piece, y, x);
        } else if (piece.getType() == Type.KNIGHT) {
            addKnightMoves(piece, y, x);
        } else if (piece.getType() == Type.ROOK) {
            addRookMoves(piece, y, x);
        } else if (piece.getType() == Type.BISHOP) {
            addBishopMoves(piece, y, x);
        } else if (piece.getType() == Type.QUEEN) {
            addQueenMoves(piece, y, x);
        } else if (piece.getType() == Type.KING) {
            addAllKingMoves(piece, y, x);
        }
        return legalMoves;
    }
    
}

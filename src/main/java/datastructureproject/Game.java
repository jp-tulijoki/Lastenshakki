package datastructureproject;

import chess.model.Side;

/**
 * This class contains the main functionalities of the chessboard such as moving
 * the pieces according to the rules. This class keeps track of the current
 * piece locations on the chessboard and castling and en passant status.
 */
public class Game {
    private Piece[][] currentBoard;
    private Piece enPassant;
    private boolean[] castling;
    private final Type[] promotionTypes;

    /**
     * The constructor. The chessboard is represented as a 8*8 two-dimensional
     * array. EnPassant has a reference to the pawn piece that has proceeded two
     * steps in the previous turn. The boolean array for castling contains 
     * following values:
     * 0 = white queenside castling possible in the current game
     * 1 = white kingside castling possible in the current game
     * 2 = black queenside castling possible in the current game
     * 3 = black kingside castling possible in the current game
     * 4 = white queenside castling possible in the current turn
     * 5 = white kingside castling possible in the current turn
     * 6 = black queenside castling possible in the current turn
     * 7 = black kingside castling possible in the current turn
     * Promotion types are listed in an array as well.
     */
    public Game() {
        this.currentBoard = new Piece[8][8];
        this.castling = new boolean[]{true, true, true, true, false, false, false, false};
        this.promotionTypes = new Type[]{Type.BISHOP, Type.KNIGHT, Type.QUEEN, Type.ROOK}; 
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

    public Piece[][] getCurrentBoard() {
        return currentBoard;
    }
    
    public void setCurrentBoard(Piece[][] board) {
        this.currentBoard = board;
    }
    
    public Piece[][] copyBoard(Piece[][] board) {
        Piece[][] newBoard = new Piece[8][8];
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                newBoard[y][x] = board[y][x];
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

    public boolean[] getCastling() {
        return castling;
    }

    public void setCastling(boolean[] castling) {
        this.castling = castling;
    }
        
    /**
     * This method moves a chess piece to the new square and replaces the old 
     * location with empty square.
     * @param board the current piece locations on the chessboard
     * @param currentY the current y-coordinate of the chess piece moved
     * @param currentX the current x-coordinate of the chess piece moved
     * @param newY the y-coordinate the chess piece is moved to
     * @param newX the x-coordinate the chess piece is moved to
     */
    public void movePiece(Piece[][] board, int currentY, int currentX, int newY, int newX) {
        Piece movedPiece = board[currentY][currentX];
        board[newY][newX] = movedPiece;
        board[currentY][currentX] = new Piece(Type.EMPTY);
    }
    
    /**
     * This method adds a regular one step pawn move to the moves list if 
     * it's possible.
     * @param board the board in which the move is made 
     * @param moves the list the move is added to
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addRegularPawnMove(Piece[][] board, ChessboardList moves, Piece pawn, int y, int x) {           
        if (pawn.getSide() == Side.WHITE && board[y + 1][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y + 1, x);
            for (int i = 0; i <= 3; i++) {
                if (promotePawn(newBoard, y + 1, x, i)) {
                    moves.add(newBoard);
                    newBoard = copyBoard(newBoard);
                } else {
                    moves.add(newBoard);
                    break;
                }
            }
            return;
        }
        if (pawn.getSide() == Side.BLACK && board[y - 1][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y - 1, x);
            for (int i = 0; i <= 3; i++) {
                if (promotePawn(newBoard, y - 1, x, i)) {
                    moves.add(newBoard);
                    newBoard = copyBoard(newBoard);
                } else {
                    moves.add(newBoard);
                    break;
                }
            }
        }
    }
    
    /**
     * This method adds a two step pawn move to the moves list if 
     * it's possible.
     * @param board the board in which the move is made 
     * @param moves the list the move is added to
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addTwoStepPawnMove(Piece[][] board, ChessboardList moves, Piece pawn, int y, int x) {
        if (pawn.getSide() == Side.WHITE && y != 1) {
            return;
        }
        if (pawn.getSide() == Side.BLACK && y != 6) {
            return;
        }
        if (pawn.getSide() == Side.WHITE && board[y + 1][x].getType() 
                == Type.EMPTY && board[y + 2][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y + 2, x);
            moves.add(newBoard);
            return;
        }
        if (pawn.getSide() == Side.BLACK && board[y - 1][x].getType() 
                == Type.EMPTY && board[y - 2][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y - 2, x);
            moves.add(newBoard);
        }
    }
    
    /**
     * This method adds pawn attacks to the moves list if it's possible.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addPawnAttack(Piece[][] board, ChessboardList moves, Piece pawn, int y, int x) {
        if (x != 0) {
            if (pawn.getSide() == Side.WHITE && board[y + 1][x - 1].getSide() == Side.BLACK) {
                Piece[][] newBoard = copyBoard(board);
                movePiece(newBoard, y, x, y + 1, x - 1);
                for (int i = 0; i <= 3; i++) {
                    if (promotePawn(newBoard, y + 1, x - 1, i)) {
                        moves.add(newBoard);
                        newBoard = copyBoard(newBoard);
                    } else {
                        moves.add(newBoard);
                        break;
                    }
                }                            }
            if (pawn.getSide() == Side.BLACK && board[y - 1][x - 1].getSide() == Side.WHITE) {
                Piece[][] newBoard = copyBoard(board);
                movePiece(newBoard, y, x, y - 1, x - 1);
                for (int i = 0; i <= 3; i++) {
                    if (promotePawn(newBoard, y - 1, x - 1, i)) {
                        moves.add(newBoard);
                        newBoard = copyBoard(newBoard);
                    } else {
                        moves.add(newBoard);
                        break;
                    }
                }
            }
        }
        if (x != 7) {
            if (pawn.getSide() == Side.WHITE && board[y + 1][x + 1].getSide() == Side.BLACK) {
                Piece[][] newBoard = copyBoard(board);
                movePiece(newBoard, y, x, y + 1, x + 1);
                for (int i = 0; i <= 3; i++) {
                    if (promotePawn(newBoard, y + 1, x + 1, i)) {
                        moves.add(newBoard);
                        newBoard = copyBoard(newBoard);
                    } else {
                        moves.add(newBoard);
                        break;
                    }
                }
            }
            if (pawn.getSide() == Side.BLACK && board[y - 1][x + 1].getSide() == Side.WHITE) {
                Piece[][] newBoard = copyBoard(board);
                movePiece(newBoard, y, x, y - 1, x + 1);
                for (int i = 0; i <= 3; i++) {
                    if (promotePawn(newBoard, y - 1, x + 1, i)) {
                        moves.add(newBoard);
                        newBoard = copyBoard(newBoard);
                    } else {
                        moves.add(newBoard);
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * This method adds enPassant to the moves list if it's possible.
     * @param board the board in which the move is made 
     * @param moves the list the move is added to
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addEnPassant(Piece[][] board, ChessboardList moves, Piece pawn, int y, int x) {
        if (x > 0) {
            if (board[y][x - 1] == enPassant) {
                if (pawn.getSide() == Side.WHITE) {
                    Piece[][] newBoard = copyBoard(board);
                    movePiece(newBoard, y, x, y + 1, x - 1);
                    newBoard[y][x - 1] = new Piece(Type.EMPTY);
                    moves.add(newBoard);
                } else {
                    Piece[][] newBoard = copyBoard(board);
                    movePiece(newBoard, y, x, y - 1, x - 1);
                    newBoard[y][x - 1] = new Piece(Type.EMPTY);
                    moves.add(newBoard);
                }
                return;
            }
        }
        
        if (x < 7) {
            if (board[y][x + 1] == enPassant) {
                if (pawn.getSide() == Side.WHITE) {
                    Piece[][] newBoard = copyBoard(board);
                    movePiece(newBoard, y, x, y + 1, x + 1);
                    newBoard[y][x + 1] = new Piece(Type.EMPTY);
                    moves.add(newBoard);
                } else {
                    Piece[][] newBoard = copyBoard(board);
                    movePiece(newBoard, y, x, y - 1, x + 1);
                    newBoard[y][x + 1] = new Piece(Type.EMPTY);
                    moves.add(newBoard);
                }
            }
        }
    }
    
    /**
     * This method promotes the pawn piece which has proceeded to the other end 
     * of the board.
     * @param board the board in which the move is made 
     * @param y the y-coordinate of the promoted pawn
     * @param x the x-coordinate of the promoted pawn
     * @param i the index for the promotion types array. See constructor for
     * array values.
     * @return returns true if promotion was made and false otherwise
     */
    public boolean promotePawn(Piece[][] board, int y, int x, int i) {
        if (y == 7) {
            board[y][x] = new Piece(promotionTypes[i], Side.WHITE);
            return true;
        } else if (y == 0) {
            board[y][x] = new Piece(promotionTypes[i], Side.BLACK);
            return true;
        }
        return false;
    }
    
    /**
     * This method calls all pawn move methods to add all different pawn moves.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param pawn the specified pawn piece
     * @param y the y-coordinate of the current location of the pawn piece
     * @param x the x-coordinate of the current location of the pawn piece
     */
    public void addAllPawnMoves(Piece[][] board, ChessboardList moves, Piece pawn, int y, int x) {
        if (y == 0 || y == 7) {
            return;
        }
        addRegularPawnMove(board, moves, pawn, y, x);
        addTwoStepPawnMove(board, moves, pawn, y, x);
        addPawnAttack(board, moves, pawn, y, x);
        addEnPassant(board, moves, pawn, y, x);
    }
    
    /**
     * This method adds all possible moves of a certain knight to moves
     * list.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param knight the specified knight piece
     * @param y the y-coordinate of the current location of the knight piece
     * @param x the x-coordinate of the current location of the knight piece
     */
    public void addKnightMoves(Piece[][] board, ChessboardList moves, Piece knight, int y, int x) {
        int[][] knightMoves = {{2, 2, 1, 1, -1, -1, -2, -2},
            {1, -1, 2, -2, 2, -2, 1, -1}};
        for (int i = 0; i < knightMoves[0].length; i++) {
            int newY = y + knightMoves[0][i];
            int newX = x + knightMoves[1][i];
            
            if (newY < 0 || newY > 7 || newX < 0 || newX > 7) {
                continue;
            }
            
            if (knight.getSide() != board[newY][newX].getSide()) {
                Piece[][] newBoard = copyBoard(board);
                movePiece(newBoard, y, x, newY, newX);
                moves.add(newBoard);
            }
        }
    }
    
    /**
     * This method adds all possible moves of a certain rook to moves list.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param rook the specified rook piece
     * @param y the y-coordinate of the current location of the rook piece
     * @param x the x-coordinate of the current location of the rook piece
     */
    public void addRookMoves(Piece[][] board, ChessboardList moves, Piece rook, int y, int x) {
        int up = y + 1;
        int down = y - 1;
        int right = x + 1;
        int left = x - 1;
        
        while (up <= 7) {
            if (board[up][x].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, up, x);
            moves.add(newBoard);
            if (board[up][x].getSide() != null) {
                break;
            }
            up++;
        }
        
        while (down >= 0) {
            if (board[down][x].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, down, x);
            moves.add(newBoard);
            if (board[down][x].getSide() != null) {
                break;
            }
            down--;
        }
        
        while (right <= 7) {
            if (board[y][right].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y, right);
            moves.add(newBoard);
            if (board[y][right].getSide() != null) {
                break;
            }
            right++;
        }
        
        while (left >= 0) {
            if (board[y][left].getSide() == rook.getSide()) {
                break;
            }
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y, left);
            moves.add(newBoard);
            if (board[y][left].getSide() != null) {
                break;
            }
            left--;
        }
    }
    
    /**
     * This method adds all possible moves of a certain bishop to moves
     * list.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param bishop the specified bishop
     * @param y the y-coordinate of the current location of the bishop
     * @param x the x-coordinate of the current location of the bishop
     */
    public void addBishopMoves(Piece[][] board, ChessboardList moves, Piece bishop, int y, int x) {
        int up = y + 1;
        int down = y - 1;
        int right = x + 1;
        int left = x - 1;
        
        while (up <= 7 && right <= 7) {
            if (board[up][right].getSide() == bishop.getSide()) {
                break;
            }
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, up, right);
            moves.add(newBoard);
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
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, down, right);
            moves.add(newBoard);
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
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, up, left);
            moves.add(newBoard);
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
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, down, left);
            moves.add(newBoard);
            if (board[down][left].getSide() != null) {
                break;
            }
            down--;
            left--;
        }
    }
    
    /**
     * This method adds all possible moves of a certain queen to moves 
     * list by combining the moves of the rook and the bishop piece.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param queen the specified queen
     * @param y the y-coordinate of the current location of the queen
     * @param x the x-coordinate of the current location of the queen
     */
    public void addQueenMoves(Piece[][] board, ChessboardList moves, Piece queen, int y, int x) {
        addRookMoves(board, moves, queen, y, x);
        addBishopMoves(board, moves, queen, y, x);
    }
    
    /**
     * This method adds all possible moves of a certain king to moves 
     * list.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param king the specified king
     * @param y the y-coordinate of the current location of the king
     * @param x the x-coordinate of the current location of the king
     */
    public void addRegularKingMoves(Piece[][] board, ChessboardList moves, Piece king, int y, int x) {
        for (int newY = y - 1; newY <= y + 1; newY++) {
            for (int newX = x - 1; newX <= x + 1; newX++) {
                if (newY == y && newX == x) {
                    continue;
                }
                if (newY < 0 || newY > 7 || newX < 0 || newX > 7) {
                    continue;
                }
                if (board[newY][newX].getSide() == king.getSide()) {
                    continue;
                }
                Piece[][] newBoard = copyBoard(board);
                movePiece(newBoard, y, x, newY, newX);
                moves.add(newBoard);
            }
        }
    }
    
    /**
     * This method updates the castling array values for the white 
     * player. See constructor for value clarifications.
     * @param board the board that is used for checking if castling is possible
     */
    public void checkWhiteCastling(Piece[][] board) {
        castling[4] = true;
        castling[5] = true;
        if (!castling[0]) {
            castling[4] = false;
        }
        if (!castling[1]) {
            castling[5] = false;
        }
        if (!castling[4] && !castling[5]) {
            return;
        }
        if (board[0][1].getType() != Type.EMPTY || board[0][2].getType() 
                != Type.EMPTY || board[0][3].getType() != Type.EMPTY) {
            castling[4] = false;
        }
        if (board[0][5].getType() != Type.EMPTY || board[0][6].getType() == Type.EMPTY) {
            castling[5] = false;
        }
        if (!castling[4] && !castling[5]) {
            return;
        }
        ChessboardList moves = addAllMoves(board, Side.BLACK);
        while (true) {
            Piece[][] move = moves.getNextBoard();
            if (move == null) {
                break;
            }
            if (move[0][2].getType() != Type.EMPTY || move[0][3].getType() 
                    != Type.EMPTY || move[0][4].getType() != Type.KING) {
                castling[4] = false;
            }
            if (move[0][4].getType() != Type.KING || move[0][5].getType() 
                    != Type.EMPTY || move[0][6].getType() != Type.EMPTY) {
                castling[5] = false;
            }
            if (!castling[4] && !castling[5]) {
                break;
            }
        }
    }       
    
    /**
     * This method updates the castling array values for the black 
     * player. See constructor for value clarifications.
     * @param board the board that is used for checking if castling is possible
     */
    public void checkBlackCastling(Piece[][] board) {
        castling[6] = true;
        castling[7] = true;
        if (!castling[2]) {
            castling[6] = false;
        }
        if (!castling[3]) {
            castling[7] = false;
        }
        if (board[7][1].getType() != Type.EMPTY || board[7][2].getType() 
                != Type.EMPTY || board[7][3].getType() != Type.EMPTY) {
            castling[6] = false;
        }
        if (board[7][5].getType() != Type.EMPTY || board[7][6].getType() != Type.EMPTY) {
            castling[7] = false;
        }
        if (!castling[6] && !castling[7]) {
            return;
        }
        ChessboardList moves = addAllMoves(board, Side.WHITE);
        while (true) {
            Piece[][] move = moves.getNextBoard();
            if (move == null) {
                break;
            }
            if (move[7][2].getType() != Type.EMPTY || move[7][3].getType() 
                    != Type.EMPTY || move[7][4].getType() != Type.KING) {
                castling[6] = false;
            }
            if (move[7][4].getType() != Type.KING || move[7][5].getType() 
                    != Type.EMPTY || move[7][6].getType() != Type.EMPTY) {
                castling[7] = false;
            }
            if (!castling[6] && !castling[7]) {
                break;
            }
        }
    }       
    
    /**
     * This method adds castling moves for white player to the moves list if
     * it's possible
     * @param board the board in which the castling move is made
     * @param moves the list the moves are added to
     * @param whiteKing the white king
     * @param y the y-coordinate of the current location of the king
     * @param x the x-coordinate of the current location of the king
     */
    public void addWhiteCastling(Piece[][] board, ChessboardList moves, Piece whiteKing, int y, int x) {
        if (!castling[4] && !castling[5]) {
            return;
        }
        if (castling[4]) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y, x - 2);
            movePiece(newBoard, y, 0, y, 3);
            moves.add(newBoard);
        }
        if (castling[5]) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y, x + 2);
            movePiece(newBoard, y, 7, y, 5);
            moves.add(newBoard);
        }
    }
    
    /**
     * This method adds castling moves for black player to the moves list if
     * it's possible
     * @param board the board in which the castling move is made
     * @param moves the list the moves are added to
     * @param blackKing the black king
     * @param y the y-coordinate of the current location of the king
     * @param x the x-coordinate of the current location of the king
     */
    public void addBlackCastling(Piece[][] board, ChessboardList moves, Piece blackKing, int y, int x) {
        if (!castling[6] && !castling[7]) {
            return;
        }
        if (castling[6]) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y, x - 2);
            movePiece(newBoard, y, 0, y, 3);
            moves.add(newBoard);
        }
        if (castling[7]) {
            Piece[][] newBoard = copyBoard(board);
            movePiece(newBoard, y, x, y, x + 2);
            movePiece(newBoard, y, 7, y, 5);
            moves.add(newBoard);
        }
    }
    
    /**
     * This method calls both king move method to add all king moves to the 
     * moves list.
     * @param board the board in which the move is made 
     * @param moves the list the moves are added to
     * @param king the specified king
     * @param y the y-coordinate of the current location of the king
     * @param x the x-coordinate of the current location of the king
     */
    public void addAllKingMoves(Piece[][] board, ChessboardList moves, Piece king, int y, int x) {
        addRegularKingMoves(board, moves, king, y, x);
        if (king.getSide() == Side.WHITE && y == 0 && x == 4) {
            addWhiteCastling(board, moves, king, y, x);
        } else if (king.getSide() == Side.WHITE && y == 7 && x == 4) {
            addBlackCastling(board, moves, king, y, x);
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
                if (board[y][x].getSide() == side 
                        && board[y][x].getType() == Type.KING) {
                    dead = false;
                }
            }
        }
        return dead;
    }
    
    /**
     * This method adds all moves of the specified player to the moves list.
     * @param board the board in which the moves are made 
     * @param side the side of the player
     * @return returns a list of moves which are legal for an individual piece,
     * but legality concerning the entire game situation, e.g. king check is not 
     * controlled.
     */
    public ChessboardList addAllMoves(Piece[][] board, Side side) {
        ChessboardList moves = new ChessboardList();
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                Piece piece = board[y][x];
                if (piece.getSide() != side) {
                    continue;
                }
                if (piece.getType() == Type.PAWN) {
                    addAllPawnMoves(board, moves, piece, y, x);
                } else if (piece.getType() == Type.KNIGHT) {
                    addKnightMoves(board, moves, piece, y, x);
                } else if (piece.getType() == Type.ROOK) {
                    addRookMoves(board, moves, piece, y, x);
                } else if (piece.getType() == Type.BISHOP) {
                    addBishopMoves(board, moves, piece, y, x);
                } else if (piece.getType() == Type.QUEEN) {
                    addQueenMoves(board, moves, piece, y, x);
                } else if (piece.getType() == Type.KING) {
                    addAllKingMoves(board, moves, piece, y, x);
                }
            }
        }
        return moves;
    }
}
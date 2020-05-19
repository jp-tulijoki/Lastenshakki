/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import chess.model.Side;
import java.util.ArrayList;

/**
 *
 * @author tulijoki
 */
public class Board {
    private Piece[][] currentBoard;
    private ArrayList<Piece[][]> legalMoves;

    public Board() {
        this.currentBoard = new Piece[8][8];
        this.legalMoves = new ArrayList();
    }
        
    public void initBoard() {
        for (int x = 0; x <= 7; x++) {
            currentBoard[1][x] = new Piece(Type.PAWN, Side.WHITE, false);
            currentBoard[6][x] = new Piece(Type.PAWN, Side.BLACK, false);
            for (int y = 2; y <= 5; y++) {
                currentBoard[y][x] = new Piece(Type.EMPTY);
            }
        }
        
        currentBoard[0][0] = new Piece(Type.ROOK, Side.WHITE, true);
        currentBoard[0][1] = new Piece(Type.KNIGHT, Side.WHITE, false);
        currentBoard[0][2] = new Piece(Type.BISHOP, Side.WHITE, false);
        currentBoard[0][3] = new Piece(Type.QUEEN, Side.WHITE, false);
        currentBoard[0][4] = new Piece(Type.KING, Side.WHITE, true);
        currentBoard[0][5] = new Piece(Type.BISHOP, Side.WHITE, false);
        currentBoard[0][6] = new Piece(Type.KNIGHT, Side.WHITE, false);
        currentBoard[0][7] = new Piece(Type.ROOK, Side.WHITE, true);
        
        currentBoard[7][0] = new Piece(Type.ROOK, Side.BLACK, true);
        currentBoard[7][1] = new Piece(Type.KNIGHT, Side.BLACK, false);
        currentBoard[7][2] = new Piece(Type.BISHOP, Side.BLACK, false);
        currentBoard[7][3] = new Piece(Type.QUEEN, Side.BLACK, false);
        currentBoard[7][4] = new Piece(Type.KING, Side.BLACK, true);
        currentBoard[7][5] = new Piece(Type.BISHOP, Side.BLACK, false);
        currentBoard[7][6] = new Piece(Type.KNIGHT, Side.BLACK, false);
        currentBoard[7][7] = new Piece(Type.ROOK, Side.BLACK, true);
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
    
    public Piece[][] movePiece(Piece[][] board, int currentY, int currentX, int newY, int newX) {
        Piece movedPiece = board[currentY][currentX];
        board[newY][newX] = movedPiece;
        board[currentY][currentX] = new Piece(Type.EMPTY);
        return board;
    }
    
    public void addRegularPawnMove(Piece pawn, int y, int x) {           
        if (pawn.getSide() == Side.WHITE && currentBoard[y+1][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y+1, x);
            legalMoves.add(newBoard);
            return;
        }
        if (pawn.getSide() == Side.BLACK && currentBoard[y-1][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y-1, x);
            legalMoves.add(newBoard);
        }
    }
    
    public void addTwoStepPawnMove(Piece pawn, int y, int x) {
        if (pawn.getSide() == Side.WHITE && y != 1) {
            return;
        }
        if (pawn.getSide() == Side.BLACK && y != 6) {
            return;
        }
        if (pawn.getSide() == Side.WHITE && currentBoard[y+1][x].getType() == Type.EMPTY && currentBoard[y+2][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y+2, x);
            legalMoves.add(newBoard);
            return;
        }
        if (pawn.getSide() == Side.BLACK && currentBoard[y-1][x].getType() == Type.EMPTY && currentBoard[y-2][x].getType() == Type.EMPTY) {
            Piece[][] newBoard = copyCurrentBoard();
            movePiece(newBoard, y, x, y-2, x);
            legalMoves.add(newBoard);
        }
    }
    
    public void addPawnAttack(Piece pawn, int y, int x) {
        if (x != 0) {
            if (pawn.getSide() == Side.WHITE && currentBoard[y+1][x-1].getSide() == Side.BLACK) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y+1, x-1);
                legalMoves.add(newBoard);
            }
            if (pawn.getSide() == Side.BLACK && currentBoard[y-1][x-1].getSide() == Side.WHITE) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y-1, x-1);
                legalMoves.add(newBoard);
            }
        }
        if (x != 7) {
            if (pawn.getSide() == Side.WHITE && currentBoard[y+1][x+1].getSide() == Side.BLACK) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y+1, x+1);
                legalMoves.add(newBoard);
            }
            if (pawn.getSide() == Side.BLACK && currentBoard[y-1][x+1].getSide() == Side.WHITE) {
                Piece[][] newBoard = copyCurrentBoard();
                movePiece(newBoard, y, x, y-1, x+1);
                legalMoves.add(newBoard);
            }
        }
    }
    
    public void addEnPassant(Piece pawn, int y, int x) {
        
    }
    
    
    
}

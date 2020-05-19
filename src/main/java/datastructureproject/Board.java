/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

import chess.model.Side;

/**
 *
 * @author tulijoki
 */
public class Board {
    private Piece[][] pieces;
    
    public Board() {
        this.pieces = new Piece[8][8];
        for (int i = 0; i <= 7; i++) {
            pieces[1][i] = new Piece(Type.PAWN, Side.WHITE, false);
            pieces[6][i] = new Piece(Type.PAWN, Side.BLACK, false);
            for (int j = 2; j <= 5; j++) {
                pieces[j][i] = new Piece(Type.EMPTY);
            }
        }
        
        pieces[0][0] = new Piece(Type.ROOK, Side.WHITE, true);
        pieces[0][1] = new Piece(Type.KNIGHT, Side.WHITE, false);
        pieces[0][2] = new Piece(Type.BISHOP, Side.WHITE, false);
        pieces[0][3] = new Piece(Type.QUEEN, Side.WHITE, false);
        pieces[0][4] = new Piece(Type.KING, Side.WHITE, true);
        pieces[0][5] = new Piece(Type.BISHOP, Side.WHITE, false);
        pieces[0][6] = new Piece(Type.KNIGHT, Side.WHITE, false);
        pieces[0][7] = new Piece(Type.ROOK, Side.WHITE, true);
        
        pieces[7][0] = new Piece(Type.ROOK, Side.BLACK, true);
        pieces[7][1] = new Piece(Type.KNIGHT, Side.BLACK, false);
        pieces[7][2] = new Piece(Type.BISHOP, Side.BLACK, false);
        pieces[7][3] = new Piece(Type.QUEEN, Side.BLACK, false);
        pieces[7][4] = new Piece(Type.KING, Side.BLACK, true);
        pieces[7][5] = new Piece(Type.BISHOP, Side.BLACK, false);
        pieces[7][6] = new Piece(Type.KNIGHT, Side.BLACK, false);
        pieces[7][7] = new Piece(Type.ROOK, Side.BLACK, true);
    }

    public Piece getPiece(int y, int x) {
        return pieces[y][x];
    }
    
    
    
}

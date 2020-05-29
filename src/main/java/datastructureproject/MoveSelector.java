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
 *
 * @author tulijoki
 */
public class MoveSelector {
    
    private Game game;

    public MoveSelector(Game game) {
        this.game = game;
    }
    
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
                if (board[y2][x2].getType() != Type.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean checkDoubledPawns(Piece[][] board, int y, int x) {
        Piece pawn = board[y][x];
        for (int y2 = y + 1; y2 <= 7; y2++) {
            if (board[y2][x].getType() == pawn.getType() && board[y2][x].getSide() == pawn.getSide()) {
                return true;
            }
        }
        return false;
    }
    
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
    

}

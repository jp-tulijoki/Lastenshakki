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
public class MoveSelector {
    
    private Game game;

    public MoveSelector(Game game) {
        this.game = game;
    }
    
    public double evaluateBoard(Piece[][] board) {
        double whiteValue = 0;
        double blackValue = 0;
        for (int y = 0; y <= 7; y++) {
            for (int x = 0; x <= 7; x++) {
                Piece piece = board[y][x];
                if (piece.getType() == Type.EMPTY) {
                    continue;
                }
                if (piece.getSide() == Side.WHITE) {
                    whiteValue += piece.getType().getValue();
                    whiteValue += countMobilityBonus(board, y, x);
                } else {
                    blackValue += piece.getType().getValue();
                    blackValue += countMobilityBonus(board, y, x);
                }
            }
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
}

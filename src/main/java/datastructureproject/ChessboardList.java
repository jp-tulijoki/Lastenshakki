/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

/**
 * This class contains the array in which the chessboard representations are 
 * stored for selecting next move.
 */
public class ChessboardList {
    private int size;
    private Object[] list;
    private int tail;
    private int next;

    public ChessboardList() {
        this.size = 40;
        this.list = new Object[size];
        this.tail = 0;
        this.next = 0;
    }

    public int getSize() {
        return size;
    }

    public int getTail() {
        return tail;
    }
    
    
    
    /**
     * This method stores the chessboard representation in the array and
     * increments the tail pointer.
     * @param o the stored chessboard 
     */
    public void add(Object o) {
        list[tail] = o;
        tail++;
        if (tail == size) {
            grow();
        }
    }
    
    /**
     * This method doubles the array size if the original array is too small.
     */
    public void grow() {
        size *= 2;
        Object[] newList = new Object[size];
        for (int i = 0; i < tail; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }
    
    /**
     * This method returns the next board representation in the array.
     * @return returns a Piece[][] array
     */
    public Piece[][] getNextBoard() {
        if (next < tail) {
            Piece[][] board = (Piece[][]) list[next];
            next++;
            return board;
        }
        return null;         
    }
    
}

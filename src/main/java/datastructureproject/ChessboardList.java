/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructureproject;

/**
 *
 * @author tulijoki
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
    
    public void add(Object o) {
        list[tail] = o;
        tail++;
        if (tail == size) {
            grow();
        }
    }
    
    public void grow() {
        size *= 2;
        Object[] newList = new Object[size];
        for (int i = 0; i < tail; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }
    
    public Piece[][] getNextBoard() {
        if (next < tail) {
            Piece[][] board = (Piece[][])list[next];
            next++;
            return board;
        } else {
            return null;
        }         
    }
    
}

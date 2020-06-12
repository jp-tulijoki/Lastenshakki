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
public class MathUtils {
    
    public double min(double a, double b) {
        if (a <= b) {
            return a;
        } 
        return b;
    }
    
    public double max(double a, double b) {
        if (a >= b) {
            return a;
        } 
        return b;
    }
    
    public int abs(int a) {
        if (a < 0) {
            return -1 * a;
        } 
        return a;
    }
}

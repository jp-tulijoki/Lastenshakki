package datastructureproject;

/**
 * This class provides the math functions used in the application.
 */
public class MathUtils {
    
    /**
     * The math min function.
     * @param a the first value
     * @param b the second value
     * @return returns smaller value of the two given values.
     */
    public double min(double a, double b) {
        if (a <= b) {
            return a;
        } 
        return b;
    }
    
    /**
     * The math max function.
     * @param a the first value
     * @param b the second value
     * @return returns larger value of the two given values.
     */
    public double max(double a, double b) {
        if (a >= b) {
            return a;
        } 
        return b;
    }
    
    /**
     * The math abs function.
     * @param a the given value
     * @return returns the absolute value of the given value
     */
    public int abs(int a) {
        if (a < 0) {
            return -1 * a;
        } 
        return a;
    }
}

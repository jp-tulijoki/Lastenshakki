package datastructureproject;

import org.junit.Test;
import static org.junit.Assert.*;

public class MathUtilsTest {
    private MathUtils utils;
    
    public MathUtilsTest() {
        this.utils = new MathUtils();
    }
    
    @Test
    public void minReturnsCorrectValues() {
        double a = -1.0;
        double b = 2.0;
        double c = 2.2;
        assertEquals(-1.0, utils.min(a, b), 0.1);
        assertEquals(2.0, utils.min(c, b), 0.1);
        assertEquals(2.2, utils.min(c, c), 0.1);
    }
    
    @Test
    public void maxReturnsCorrectValues() {
        double a = -1.0;
        double b = 2.0;
        double c = 2.2;
        assertEquals(2.0, utils.max(a, b), 0.1);
        assertEquals(2.2, utils.max(c, b), 0.1);
        assertEquals(2.2, utils.max(c, c), 0.1);
    }
    
    @Test
    public void absReturnsCorrectValues() {
        int a = 2;
        int b = -5;
        assertEquals(2, utils.abs(a));
        assertEquals(5, utils.abs(b));
    } 
}
